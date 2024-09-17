<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Регистрация
            </div>
            <div class="main">
                <div class="message">{{ message }}</div>
                <div>
                    <input v-model="name" placeholder="Имя"/>
                    <input v-model="tel" placeholder="Номер телефона" type="tel"/>
                    <input v-model="password" placeholder="Пароль" type="password" @input="checkPasswords"/>
                    <input v-model="password_2" placeholder="Подтвердите пароль" type="password" @input="checkPasswords"/>
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

export default {
    data() {
        return {
            name: '',
            tel: '',
            password: '',
            password_2: '',
            message: '',
        };
    },
    methods: {
        checkPasswords(){
            this.message = '';
            if (this.password_2 !== this.password) {
                this.message = 'Пароли не совпадают!';
            }
        },
        enter() {
            router.push('/page');
        },
    },
    computed: {
        isDisabled() {
            return !(
                this.name !== '' &&
                this.tel !== '' &&
                this.password !== '' &&
                this.password_2 !== '' &&
                this.password === this.password_2
            );
        }
    },
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
    position: relative;
    width: 300px;
    height: 400px;
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
    color: red;
    cursor: default;
}
</style>
