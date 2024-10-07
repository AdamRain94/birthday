import base_url from '@/axios.js';

export default {
    namespaced: true,
    state() {
        return {
            userPhoto: null,
            newUserPhoto: null
        };
    },
    mutations: {
        setUserPhoto(state, photo) {
            state.userPhoto = photo;
        },
        setNewUserPhoto(state, photo) {
            state.newUserPhoto = photo;
        },
        clearUserPhoto(state){
            state.userPhoto = null;
            state.newUserPhoto = null;
        }
    },
    actions: {
        async getUserPhoto({commit}) {
            commit('error/setLoading', true, {root: true});
            await base_url.get('/setting/photo', {
                responseType: 'blob'
            }).then((data) => {
                if (data.data.size) {
                    const url = URL.createObjectURL(data.data);
                    commit('setUserPhoto', url);
                    commit('setNewUserPhoto', null)
                } else {
                    commit('setUserPhoto', null);
                }
            }).catch(error => {
                commit('error/setError', error.response.data, {root: true});
            }).finally(() => {
                commit('error/setLoading', false, {root: true});
            });
        },
        async updateUserPhoto({commit, state, dispatch}) {
            commit('error/setLoading', true, {root: true});
            if (state.newUserPhoto != null) {
                const formData = new FormData();
                formData.append('photo', state.newUserPhoto);
                await base_url.post('/setting/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(()=>{
                    dispatch('getUserPhoto')
                }).catch(error => {
                    commit('error/setError', error.response.data, {root: true});
                }).finally(() => {
                    commit('error/setLoading', false, {root: true});
                });
            }
        }
    },
    getters: {
        userPhoto(state) {
            return state.userPhoto;
        },
        newUserPhoto(state) {
            return state.newUserPhoto;
        },
    }
};
