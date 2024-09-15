import {createRouter, createWebHistory} from 'vue-router';
import Authorization from "@/components/Authorization.vue";
import Main from '@/components/Main.vue';
import Registration from '@/components/Registration.vue';
import Page from '@/components/Page.vue';
import Sidebar from '@/components/Sidebar.vue';
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
