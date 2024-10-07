import './assets/styles/default.scss';
import { createApp } from 'vue'
import router from '@/router/router.js';
import App from '@/App.vue'
import store from '@/store/index.js';

createApp(App)
    .use(router)
    .use(store)
    .mount('#app')
