import axios from 'axios';
import store from './store'; // Импортируйте store

let isRefreshing = false;
let subscribers = [];

function onAccessTokenFetched(newAccessToken) {
    subscribers.forEach(callback => callback(newAccessToken));
    subscribers = [];
}

function addSubscriber(callback) {
    subscribers.push(callback);
}

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
    }
});

apiClient.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            if (isRefreshing) {
                return new Promise((resolve) => {
                    addSubscriber((newAccessToken) => {
                        originalRequest.headers['Authorization'] = 'Bearer ' + newAccessToken;
                        resolve(apiClient(originalRequest));
                    });
                });
            }

            originalRequest._retry = true;
            isRefreshing = true;

            try {
                await store.dispatch('refreshToken'); // Запрашиваем новый токен через Vuex
                const newAccessToken = store.getters.accessToken; // Получаем новый токен из Vuex

                // Обновляем токен в запросе
                originalRequest.headers['Authorization'] = 'Bearer ' + newAccessToken;

                onAccessTokenFetched(newAccessToken);

                return apiClient(originalRequest); // Повторяем запрос
            } catch (err) {
                await store.dispatch('logout'); // В случае ошибки логаут
                return Promise.reject(err);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;
