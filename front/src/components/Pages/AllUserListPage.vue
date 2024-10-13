<template>
    <div class="container">
        <div class="user-list-block">
            <div class="window">
                <div class="header">
                    Пользователи
                </div>
                <div class="main">
                    <div class="user" v-if="users" v-for="(user, index) in users" :key="user.id">
                            <img v-if="user.photo" class="user-photo" :src="photo(user.photo)" alt="user-photo">
                            <img v-if="!user.photo" class="user-photo" :src="img" alt="user-photo">
                            <div class="user-information">
                                <div>{{ user.fam }}</div>
                                <div>{{ user.name }}</div>
                                <div>{{ user.otch }}</div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="search-block">
            <div class="window">
                <div class="header">
                    Поиск
                </div>
                <div class="main">

                </div>
            </div>
        </div>
    </div>
</template>

<script>
import img from '@/assets/images/default_photo_user.png';
import base_url from '@/axios.js';

export default {
    data() {
        return {
            img: img,
            users: []
        };
    },
    methods: {
        getAllUsers() {
            base_url.get('users/all')
                .then(value => {
                    this.users = value.data
                })
                .catch(error => {

                });
        },
        photo(filename) {
            return `http://localhost:8080/api/files/photo/user/${filename}`;
        },
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

.user-photo {
    aspect-ratio: 1 / 1;
    width: 130px;
    margin-right: 20px;
    border-radius: 6px;
    object-fit: cover;
    overflow: hidden;
}

.search-block {
    min-width: 200px;
    height: 100%;
}

.main{
    display: flex;
    flex-direction: column;
}
.user {
    display: flex;
    margin-bottom: 10px; /* Отступ снизу для всех .user */
}

.user:last-child {
    margin-bottom: 0; /* Последнему элементу отступ не нужен */
}
</style>