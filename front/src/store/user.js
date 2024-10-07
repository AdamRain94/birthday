import base_url from '@/axios.js';

export default {
    namespaced: true,
    state() {
        return {
            user: JSON.parse(localStorage.getItem('user')) || null
        };
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
        clearUser(state) {
            state.user = null;
            localStorage.removeItem('user');
        }
    },
    actions: {
        async getUser({commit}) {
            commit('error/setLoading', true, {root: true});
            await base_url.get('/setting/user')
                .then((data) => {
                    commit('setUser', data.data);
                }).catch(error => {
                    commit('error/setError', error, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async updateUser({commit, state}) {
            commit('error/setLoading', true, {root: true});
            await base_url.post('/setting/user', state.user)
                .then((data) => {
                    commit('setUser', data.data);
                }).catch(error => {
                    commit('error/setError', error, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        }
    },
    getters: {
        user(state) {
            return state.user;
        },
    }
};
