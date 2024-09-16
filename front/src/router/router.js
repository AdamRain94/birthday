import {createRouter, createWebHistory} from 'vue-router';
import Authorization from "@/components/Pages/AuthorizationPage.vue";
import Main from '@/components/Sections/Main.vue';
import Registration from '@/components/Pages/RegistrationPage.vue';
import Page from '@/components/Pages/UserPage.vue';
import Sidebar from '@/components/Bar/Sidebar.vue';
const routes = [
    {
        path: '/authorization',
        name: 'Authorization',
        component: Authorization,
    },
    {
        path: '/',
        name: 'Home',
        component: Main,
    },
    {
        path: '/registration',
        name: 'Registration',
        component: Registration,
    },
    {
        path: '/page',
        name: 'page',
        components: {
            default: Page,
            sidebar: Sidebar
        },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
