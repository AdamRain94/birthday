import {createRouter, createWebHistory} from 'vue-router';
import Authorization from '@/components/Pages/AuthorizationPage.vue';
import Main from '@/components/Sections/Main.vue';
import Registration from '@/components/Pages/RegistrationPage.vue';
import Page from '@/components/Pages/MyUserPage.vue';
import UserPage from '@/components/Pages/UserPage.vue';
import Sidebar from '@/components/Bar/Sidebar.vue';
import store from '@/store/index.js';
import SettingPage from '@/components/Pages/SettingPage.vue';
import SidebarInSetting from '@/components/Bar/SidebarInSetting.vue';
import PersonalInformation from '@/components/Blocks/PersonalInformationSetting.vue';
import AccountSetting from '@/components/Blocks/AccountSetting.vue';
import AllUserListPage from '@/components/Pages/AllUserListPage.vue';

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
        path: '/user/:id',
        name: 'user',
        components:  {
            default: UserPage,
            sidebar: Sidebar
        },
        meta: {requiresAuth: true}
    },
    {
        path: '/all-users',
        name: 'all-users',
        components: {
            default: AllUserListPage,
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
                path: 'account',
                components: {
                    default: AccountSetting,
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
