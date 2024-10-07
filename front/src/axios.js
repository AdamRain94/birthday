import axios from 'axios';
import store from '@/store/index.js'; // Импортируйте index

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
});

apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('accessToken'); // Берём актуальный токен из localStorage
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
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
                await store.dispatch('auth/refreshToken'); // Запрашиваем новый токен через Vuex
                const newAccessToken = store.getters['auth/accessToken']; // Получаем новый токен из Vuex
                // Обновляем токен в запросе
                originalRequest.headers['Authorization'] = 'Bearer ' + newAccessToken;
                onAccessTokenFetched(newAccessToken);

                return apiClient(originalRequest); // Повторяем запрос
            } catch (err) {
                await store.dispatch('auth/exit'); // В случае ошибки логаут
                return Promise.reject(err);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;
