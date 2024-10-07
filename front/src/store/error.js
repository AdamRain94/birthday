export default {
    namespaced: true,
    state() {
        return {
            isDisable: false,
            isLoading: false,
            error: null
        };
    },
    mutations: {
        setError(state, error) {
            state.error = error;
        },
        setLoading(state, status){
          state.isLoading = status
        },
        setDisable(state, status){
          state.isDisable = status
        },
        clearUserData(state) {
            state.isDisable = false;
            state.error = null;
        }
    },
    actions: {


    },
    getters: {
        error(state) {
            return state.error;
        },
        disable(state) {
            return state.isDisable;
        },
        loading(state){
            return state.isLoading;
        }
    }
};
