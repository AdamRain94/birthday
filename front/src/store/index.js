import {createStore} from 'vuex';
import auth from '@/store/auth.js';
import user from '@/store/user.js';
import photo from '@/store/photo.js';
import error from '@/store/error.js';

const store = createStore({
    modules: {
        auth,
        user,
        photo,
        error
    }
});

export default store;
