import 'vue3-select/dist/vue3-select.css';
import '@/assets/styles/default.scss';
import '@/assets/styles/select.scss'
import { createApp } from 'vue';
import router from '@/router/router.js';
import App from '@/App.vue';
import store from '@/store/index.js';

createApp(App)
    .use(router)
    .use(store)
    .mount('#app');
