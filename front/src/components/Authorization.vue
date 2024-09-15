<template>
    <div class="container">
        <div class="window">
            <diV class="header">
                Авторизация
            </diV>
            <div class="main">
                <div @click="registration" class="registration">Регистрация →</div>
                <div class="message">{{ message }}</div>
                <div>
                    <input v-model="login" placeholder="Логин"/>
                    <input v-model="password" placeholder="Пароль" type="password"/>
                </div>
                <div class="buttons">
                    <button @click="enter" class="btn">Войти</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router/router.js';

export default {
    data() {
        return {
            login: '',
            password: '',
            message: ''
        };
    },
    methods: {
        enter() {
            this.message = '';
            if (this.login === '') {
                this.message = 'Введите логин!';
                return;
            }
            if (this.password === '') {
                this.message = 'Введите пароль!';
                return;
            }
            router.push('/page');
        },
        registration() {
            router.push('/registration');
        }
    }
};
</script>

<style scoped>
.container {
    display: flex;
    align-items: center;
    min-height: 100vh;
    margin: 0 auto;
}

.window {
    display: flex;
    flex-direction: column;
    position: relative;
    width: 300px;
    height: 300px;
    margin: 0 auto;
    border-radius: 12px;
    background-color: var(--1-color);
    border: 1px solid var(--2-color50);
}

.header {
    cursor: default;
    width: 100%;
    padding: 10px;
    text-align: center;
    border-top-left-radius: 11px;
    border-top-right-radius: 11px;
    background-color: var(--2-color);
    font-size: 1.2em;
}

.main {
    display: flex;
    width: 100%;
    flex-grow: 1;
    padding: 30px;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
}

.buttons {
    width: 100%;
}

.registration {
    align-self: end;
    cursor: pointer;
}

.registration:hover {
    font-weight: 600;
}

.message {
    position: absolute;
    top: 55px;
    color: red;
    cursor: default;
}
</style>
