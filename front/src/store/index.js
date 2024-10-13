import {createStore} from 'vuex';
import auth from '@/store/auth.js';
import user from '@/store/user.js';
import error from '@/store/error.js';

const store = createStore({
    modules: {
        auth,
        user,
        error
    }
});

export default store;
