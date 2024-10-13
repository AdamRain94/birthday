import base_url from '@/axios.js';

export default {
    namespaced: true,
    state() {
        return {
            user: JSON.parse(localStorage.getItem('user')) || null,
            newUserPhoto: null
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
        },
        setNewUserPhoto(state, photo) {
            state.newUserPhoto = photo;
        }
    },
    actions: {
        async getUser({commit}) {
            commit('error/setLoading', true, {root: true});
            await base_url.get('/setting/user')
                .then((data) => {
                    commit('setUser', data.data);
                    commit('error/setError', '', {root: true});
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async updateUser({commit, state}) {
            commit('error/setLoading', true, {root: true});
            await base_url.post('/setting/user', state.user)
                .then((data) => {
                    commit('setUser', data.data);
                    commit('error/setError', '', {root: true});
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
        },
        async updateUserPhoto({commit, state}) {
            commit('error/setLoading', true, {root: true});
            if (state.newUserPhoto != null) {
                const formData = new FormData();
                formData.append('photo', state.newUserPhoto);
                await base_url.post('/setting/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((data) => {
                    commit('setUser', data.data);
                    commit('setNewUserPhoto', null);
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
            }
        }

    },
    getters: {
        user(state) {
            return state.user;
        },
        newUserPhoto(state) {
            return state.newUserPhoto;
        }
    }
};
