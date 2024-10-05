import {createStore} from 'vuex';
import base_url from '@/axios.js';
import router from '@/router/router.js';

const store = createStore({
    state() {
        return {
            isAuthenticated: !!localStorage.getItem('accessToken'),
            user: JSON.parse(localStorage.getItem('user')) || null,
            accessToken: localStorage.getItem('accessToken') || null,
            refreshToken: localStorage.getItem('refreshToken') || null,
            userPhoto: null,
            newUserPhoto: null,
            error: null
        };
    },
    mutations: {
        login(state, {user, accessToken, refreshToken}) {
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
            state.userPhoto = null;
            state.newUserPhoto = null;
            state.accessToken = null;
            state.refreshToken = null;
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('user');
            localStorage.removeItem('userPhoto');
        },
        updateTokens(state, {accessToken, refreshToken}) {
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
        },
        setUserPhoto(state, photo) {
            state.userPhoto = photo;
        },
        setNewUserPhoto(state, photo) {
            state.newUserPhoto = photo;
        }
    },
    actions: {
        async login({commit, dispatch}, credentials) {
            await base_url.post('/auth/login', credentials)
                .then(({data}) => {
                    commit('login', {user: data, accessToken: data.token, refreshToken: data.refreshToken});
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async register({commit}, user) {
            await base_url.post('/auth/register', user)
                .then(({data}) => {
                    commit('login', {user: data, accessToken: data.token, refreshToken: data.refreshToken});
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async logout({commit}) {
            await base_url.post('/auth/logout')
                .then(() => {
                    commit('logout');
                    router.push('/authorization');
                })
                .catch(error => {
                    commit('setError', error);
                });
        },
        async refreshToken({commit, state}) {
            const refreshToken = state.refreshToken;
            await base_url.post('/auth/refresh-token', {refreshToken})
                .then(({data}) => {
                    commit('updateTokens', {accessToken: data.accessToken, refreshToken: data.refreshToken});
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async getUser({commit}) {
            await base_url.get('/setting/user')
                .then((data) => {
                    commit('setUser', data.data);
                })
                .catch(error => {
                    commit('setError', error);
                });
        },
        async updateUser({commit, state}) {
            await base_url.post('/setting/user', state.user)
                .then((data) => {
                    commit('setUser', data.data);
                })
                .catch(error => {
                    commit('setError', error);
                });

        },
        async getUserPhoto({commit}) {
            await base_url.get('/setting/photo', {
                responseType: 'blob'
            }).then((data) => {
                if (data.data.size) {
                    const url = URL.createObjectURL(data.data);
                    commit('setUserPhoto', url);
                }
            }).catch(error => {
                commit('setError', error);
            });
        },
        async updateUserPhoto({commit, state}) {
            if (state.newUserPhoto != null) {
                const formData = new FormData();
                formData.append('photo', state.newUserPhoto);
                await base_url.post('/setting/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).catch(error => {
                    commit('setError', error);
                });
            }
        }
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
        },
        userPhoto(state) {
            return state.userPhoto;
        }
    }
});

export default store;
