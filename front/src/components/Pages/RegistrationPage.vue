<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Регистрация
            </div>
            <div class="main">
                <div class="message">{{ message }}</div>
                <div @click="authorization" class="authorization">← Войти</div>
                <div>
                    <input v-model="name" placeholder="Имя"/>
                    <input v-model="tel" placeholder="Номер телефона" type="tel"/>
                    <input v-model="password" placeholder="Пароль" type="password" @input="checkPasswords"/>
                    <input v-model="password_2" placeholder="Подтвердите пароль" type="password"
                           @input="checkPasswords"/>
                </div>
                <div class="buttons">
                    <button :disabled="isDisabled" @click="enter" class="btn">Регистрация</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router/router.js';
import {mapActions, mapGetters} from 'vuex';

export default {
    data() {
        return {
            name: '',
            tel: '',
            password: '',
            password_2: '',
            message: '',
            isLoading: false
        };
    },
    methods: {
        ...mapActions(['register']),
        checkPasswords() {
            this.message = '';
            if (this.password_2 !== this.password) {
                this.message = 'Пароли не совпадают!';
            }
        },
        async enter() {
            this.isLoading = true; // Начинаем загрузку
            this.message = '';
            this.message = '';
            await this.register({
                name: this.name,
                tel: this.tel,
                password: this.password
            })
                .then(() => {
                    this.$router.push('/page');
                })
                .catch((error) => {
                    this.message = error.response.data;
                })
                .finally(() => {
                    this.isLoading = false;
                })

        },
        authorization() {
            router.push('/authorization');
        }
    },
    computed: {
        ...mapGetters(['error']),
        isDisabled() {
            return !(
                this.name !== '' &&
                this.tel !== '' &&
                this.password !== '' &&
                this.password_2 !== '' &&
                this.password === this.password_2
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

.authorization {
    align-self: start;
    cursor: pointer;
}

.authorization:hover {
    font-weight: 600;
}

.window {
    position: relative;
    width: 300px;
    height: 420px;
    margin: 0 auto;

}

.header {
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

.message {
    position: absolute;
    padding: 0 20px;
    text-align: center;
    top: 55px;
    color: var(--4-color);
    cursor: default;
}
</style>
