import {createRouter, createWebHistory} from 'vue-router';
import Authorization from "@/components/Authorization.vue";
const routes = [
    {
        path: '/authorization',
        name: 'Authorization',
        component: Authorization,
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
