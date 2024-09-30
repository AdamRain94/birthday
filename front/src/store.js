import { createStore } from 'vuex';
import base_url from '@/axios.js';
import router from '@/router/router.js';

const store = createStore({
    state() {
        return {
            isAuthenticated: !!localStorage.getItem('accessToken'),
            user: JSON.parse(localStorage.getItem('user')) || null,
            accessToken: localStorage.getItem('accessToken') || null,
            refreshToken: localStorage.getItem('refreshToken') || null,
            error: null
        };
    },
    mutations: {
        login(state, { user, accessToken, refreshToken }) {
            state.isAuthenticated = true;
            state.user = user;
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            localStorage.setItem('user', JSON.stringify(user));
        },
        logout(state) {
            state.isAuthenticated = false;
            state.user = null;
            state.accessToken = null;
            state.refreshToken = null;
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('user');
        },
        updateTokens(state, { accessToken, refreshToken }) {
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            localStorage.removeItem('accessToken');
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
        },
        setError(state, error) {
            state.error = error;
        },
        setUser(state, user) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        }
    },
    actions: {
        async login({ commit }, credentials) {
            await base_url.post('/auth/login', credentials)
                .then(({ data }) => {
                    commit('login', { user: data, accessToken: data.token, refreshToken: data.refreshToken });
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async register({ commit }, user) {
            await base_url.post('/auth/register', user)
                .then(({ data }) => {
                    commit('login', { user: data, accessToken: data.token, refreshToken: data.refreshToken });
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async   logout({ commit }) {
            await base_url.post('/auth/logout')
                .then(() => {
                    commit('logout');
                    router.push('/authorization');
                })
                .catch(error => {
                    commit('setError', error);
                });
        },
        async refreshToken({ commit, state }) {
            const refreshToken = state.refreshToken;
            await base_url.post('/auth/refresh-token', { refreshToken })
                .then(({ data }) => {
                    commit('updateTokens', { accessToken: data.accessToken, refreshToken: data.refreshToken });
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async getUser({ commit }) {
            await base_url.get('/setting/user')
                .then((data) => {
                    console.log(data)
                })
                .catch(error => {
                    commit('setError', error);
                });
        },
    },
    getters: {
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
        user(state) {
            return state.user;
        },
        error(state) {
            return state.error;
        },
        accessToken(state) {
            return state.accessToken;
        },
        refreshToken(state) {
            return state.refreshToken;
        }
    }
});

export default store;
