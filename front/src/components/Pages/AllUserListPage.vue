<template>
    <div class="container">
        <div class="user-list-block">
            <div class="window" :class="{'min-height': loading}">
                <div class="header">
                    Пользователи
                </div>
                <div class="main">
                    <user-view-in-list-page class="user" v-if="users" v-for="(user) in users " :user="user"
                                            :key="user.id" @click="goToPageUser(user.id)"/>
                </div>
            </div>
        </div>
        <div class="search-block">
            <div class="window">
                <div class="header">
                    Поиск
                </div>
                <div class="main">
                    <input placeholder="Имя"/>
                    <input placeholder="Фамилия"/>
                    <input placeholder="Отчество"/>
                    <date-of-birthday class="value"/>
                </div>
            </div>
            <div class="buttons">
                <button class="btn">
                    <span>Поиск</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import api from '@/axios.js';
import UserViewInListPage from '@/components/Blocks/UserViewInListPage.vue';
import router from '@/router/router.js';
import DateOfBirthday from '@/components/UI/DateOfBirthdaySelect.vue';

export default {
    components: {DateOfBirthday, UserViewInListPage},
    data() {
        return {
            users: [],
            loading: false
        };
    },
    methods: {
        async getAllUsers() {
            this.loading = true;
            try {
                let response = await api.get('users/all');
                this.users = response.data;
            } finally {
                this.loading = false;
            }
        },
        goToPageUser(id) {
            router.push(`/user/${id}`);
        }
    },
    mounted() {
        this.getAllUsers();
    }
};
</script>

<style scoped>
.container {
    display: flex;
    width: 100%;
    height: 100%;
}

.user-list-block {
    min-width: 560px;
    height: 100%;
    padding-right: 20px;
}

.min-height {
    min-height: 300px;
}

.search-block {
    min-width: 200px;
    height: 100%;
}

.main {
    display: flex;
    flex-direction: column;
}

.user {
    display: flex;
    cursor: pointer;
    border-radius: 6px;
    margin-bottom: 10px; /* Отступ снизу для всех .user */
}

.user:hover {
    background-color: var(--2-color50);
    transition: background-color .3s linear;
}

.user:last-child {
    margin-bottom: 0; /* Последнему элементу отступ не нужен */
}

.window {
    margin-bottom: 20px;
}

.btn {
    border-radius: 10px;
}

</style>
