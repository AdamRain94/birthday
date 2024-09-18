import {createStore} from 'vuex';

import axios from 'axios';
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
            await axios.post('http://localhost:8080/api/authorization/login', credentials).then(data => {
                commit('login', data.data);
                console.log(data.data)
            }).catch(error => {
                commit('setError', error);
                throw error;
            });
        },
        async logout({commit}) {
            await axios.post('http://localhost:8080/api/authorization/logout')
                .then(data => {
                    commit('logout');
                    router.push('/authorization');
                }).catch(error => {
                    commit('setError', error);
                });
        },
        async register({commit}, user) {
            await axios.post('http://localhost:8080/api/register', user)
                .then(data => {
                    commit('login', data.data);
                })
                .catch(error => {
                    commit('setError', error);
                    throw error;
                });
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
        user(state) {
            console.log(state.user)
            return state.user;
        },
        error(state) {
            console.log(state.error);
            return state.error;
        }
    }
});

export default store;
