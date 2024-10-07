import {createRouter, createWebHistory} from 'vue-router';
import Authorization from '@/components/Pages/AuthorizationPage.vue';
import Main from '@/components/Sections/Main.vue';
import Registration from '@/components/Pages/RegistrationPage.vue';
import Page from '@/components/Pages/UserPage.vue';
import Page2 from '@/components/Pages/UserPage2.vue';
import Sidebar from '@/components/Bar/Sidebar.vue';
import store from '@/store/index.js';
import SettingPage from '@/components/Pages/SettingPage.vue';
import SidebarInSetting from '@/components/Bar/SidebarInSetting.vue';
import LichnayaInformation2 from '@/components/Blocks/LichnayaInformation2.vue';
import PersonalInformation from '@/components/Blocks/PersonalInformation.vue';

const routes = [
    {
        path: '/authorization',
        name: 'Authorization',
        component: Authorization,
        meta: {requiresAuth: false}
    },
    {
        path: '/',
        name: 'Home',
        component: Main,
        meta: {requiresAuth: true}
    },
    {
        path: '/registration',
        name: 'Registration',
        component: Registration,
        meta: {requiresAuth: false}
    },
    {
        path: '/page',
        name: 'page',
        components: {
            default: Page,
            sidebar: Sidebar
        },
        meta: {requiresAuth: true}
    },
    {
        path: '/page2',
        name: 'page2',
        components: {
            default: Page2,
            sidebar: Sidebar
        },
        meta: {requiresAuth: true}
    },
    {
        path: '/setting',
        name: 'setting',
        components: {
            default: SettingPage,
            sidebar: Sidebar
        },
        children: [
            {
                path: 'me',
                components: {
                    default: PersonalInformation,
                    sidebarInSetting: SidebarInSetting
                }
            },
            {
                path: 'me2',
                components: {
                    default: LichnayaInformation2,
                    sidebarInSetting: SidebarInSetting
                }
            }
        ],
        meta: {requiresAuth: true}
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = store.getters['auth/isAuthenticated'];
    // Проверка: если пользователь авторизован и пытается зайти на страницы авторизации или регистрации
    if (isAuthenticated && (to.path === '/authorization' || to.path === '/registration')) {
        next('/page'); // Перенаправляем на страницу для авторизованных пользователей (например, '/page')
    } else if (to.meta.requiresAuth && !isAuthenticated) {
        next('/authorization'); // Перенаправляем неавторизованных пользователей на страницу авторизации
    } else {
        next(); // Если всё ок, продолжаем маршрут
    }
});

export default router;
