import {createStore} from 'vuex';

import base_url from '@/axios.js'
import router from '@/router/router.js';
const store = createStore({
    state() {
        return {
            isAuthenticated: !!localStorage.getItem('isAuthenticated'),
            user: JSON.parse(localStorage.getItem('user')) || null,
            error: null
        };
    },
    mutations: {
        login(state, user) {
            state.isAuthenticated = true;
            state.user = user;
            state.error = null;
            localStorage.setItem('isAuthenticated', true);
            localStorage.setItem('user', JSON.stringify(user));
        },
        logout(state) {
            state.isAuthenticated = false;
            state.user = null;
            localStorage.removeItem('isAuthenticated');
            localStorage.removeItem('user');
        },
        setError(state, error) {
            state.error = error;
        },
        setUser(state, user) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
    },
    actions: {
        async login({commit}, credentials) {
            await base_url.post('/authorization/login', credentials).then(data => {
                console.log(data)
                commit('login', data.data);
            }).catch(error => {
                commit('setError', error);
                throw error;
            });
        },
        async logout({commit}) {
            await base_url.post('/authorization/logout')
                .then(data => {
                    console.log(data)
                    commit('logout');
                    router.push('/authorization');
                }).catch(error => {
                    commit('setError', error);
                });
        },
        async register({commit}, user) {
            await base_url.post('/register', user)
                .then(data => {
                    commit('login', data.data);
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        },
        async getUser({commit}) {
            await base_url.get('/setting/user')
                .then(response => {
                    commit('setUser', response.data);
                })
                .catch(error => {
                    commit('setError', error);
                });
        },
        async updateUser({commit}, updatedUser) {
            await base_url.put('/setting/user', updatedUser)
                .then(response => {
                    commit('setUser', response.data);
                })
                .catch(error => {
                    commit('setError', error);
                });
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
        }
    }
});

export default store;
