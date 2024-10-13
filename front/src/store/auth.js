import base_url from '@/axios.js';
import router from '@/router/router.js';

export default {
    namespaced: true,
    state() {
        return {
            isAuthenticated: !!localStorage.getItem('accessToken'),
            accessToken: localStorage.getItem('accessToken') || null,
            refreshToken: localStorage.getItem('refreshToken') || null
        };
    },
    mutations: {
        login(state, {accessToken, refreshToken}) {
            state.isAuthenticated = true;
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
        },
        logout(state) {
            state.isAuthenticated = false;
            state.accessToken = null;
            state.refreshToken = null;
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
        },
        updateTokens(state, {accessToken, refreshToken}) {
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
        }
    },
    actions: {
        async login({commit, dispatch}, credentials) {
            commit('error/setLoading', true, {root: true});
            await base_url.post('/auth/login', credentials)
                .then(({data}) => {
                    commit('login', {accessToken: data.token, refreshToken: data.refreshToken});
                    commit('user/setUser', data, {root: true});
                    router.push('/page');
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async register({commit}, credentials) {
            commit('error/setLoading', true, {root: true});
            await base_url.post('/auth/register', credentials)
                .then(({data}) => {
                    commit('login', {accessToken: data.token, refreshToken: data.refreshToken});
                    commit('user/setUser', data, {root: true});
                    router.push('/page');
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async exit({commit}) {
            commit('error/setLoading', true, {root: true});
            await base_url.post('/auth/logout')
                .then(() => {
                    commit('logout');
                    commit('user/clearUser', null, {root: true});
                    router.push('/authorization');
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async refreshToken({commit, state}) {
            commit('error/setLoading', true, {root: true});
            const refreshToken = state.refreshToken;
            await base_url.post('/auth/refresh-token', {refreshToken})
                .then(({data}) => {
                    commit('updateTokens', {accessToken: data.accessToken, refreshToken: data.refreshToken});
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
        accessToken(state) {
            return state.accessToken;
        },
        refreshToken(state) {
            return state.refreshToken;
        }
    }
};
