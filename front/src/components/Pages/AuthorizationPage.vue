<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Авторизация
            </div>
            <div class="main">
                <div @click="registration" class="registration">Регистрация →</div>
                <div class="message">{{ message }}</div>
                <div>
                    <input v-model="tel" placeholder="Логин"/>
                    <input v-model="password" placeholder="Пароль" type="password"/>
                </div>
                <div class="buttons">
                    <button :disabled="isDisabled" @click="enter" class="btn">Войти</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router/router.js';
import {mapActions} from 'vuex';

export default {
    data() {
        return {
            tel: '',
            password: '',
            message: ''
        };
    },
    methods: {
        ...mapActions(['login', 'logout']),
        enter() {
            this.login({tel: this.tel, password: this.password})
                .then(() => {
                    this.$router.push('/page');
                })
                .catch(() => {
                    this.message = 'Ошибка авторизации';
                });
        },
        registration() {
            router.push('/registration');
        }
    },
    computed: {
        isDisabled() {
            return !(
                this.tel !== '' &&
                this.password !== ''
            );
        }
    }
};
</script>

<style scoped>
.container {
    display: flex;
    align-items: center;
    height: 100%;
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
