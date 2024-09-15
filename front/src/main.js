import './assets/styles/default.scss';
import router from '@/router/router.js';
import { createApp } from 'vue'
import App from './App.vue'

createApp(App)
    .use(router)
    .mount('#app')