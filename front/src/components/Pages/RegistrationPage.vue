<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Регистрация
            </div>
            <div class="main">
                <div>
                    <div @click="authorization" class="authorization">← Авторизация</div>
                    <input v-model="name" maxlength="20" placeholder="Имя" @input="filterName"/>
                    <input v-model="tel" maxlength="18" placeholder="Номер телефона" type="tel" @input="filterTel"/>
                    <input v-model="password" maxlength="20" placeholder="Пароль" type="password"
                           @input="checkPasswords"/>
                    <input v-model="password_2" maxlength="20" placeholder="Подтвердите пароль" type="password"
                           @input="checkPasswords"/>
                    <div class="message">{{ error }}</div>
                </div>
                <div class="buttons">
                    <button :disabled="isDisabled || loading" @click="enter" class="btn"
                            :class="{ loading : loading}">
                        <span v-if="loading">Загрузка...</span>
                        <span v-else>Зарегистрироваться</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router/router.js';
import {mapActions, mapGetters, mapMutations} from 'vuex';

export default {
    data() {
        return {
            name: '',
            tel: '',
            password: '',
            password_2: '',
        };
    },
    methods: {
        ...mapActions('auth', ['register']),
        ...mapMutations('error', ['setError', 'setDisable']),
        checkPasswords() {
            this.setError('');
            if (this.password_2 !== this.password) {
                this.setError('Пароли не совпадают!');
            }
        },
        enter() {
            this.register({
                name: this.name,
                tel: this.tel,
                password: this.password
            });
        },
        authorization() {
            router.push('/authorization');
        },
        filterName() {
            const regex = /^[А-Яа-яЁё\s]*$/;
            if (!regex.test(this.name)) {
                this.name = this.name.replace(/[^А-Яа-яЁё\s]/g, '');
            }
        },
        filterTel() {
            const regex = /^[0-9()+-]*$/;
            if (!regex.test(this.tel)) {
                this.tel = this.tel.replace(/[^0-9()+-]/g, '');
            }
        }
    },
    computed: {
        ...mapGetters('error', ['error']),
        ...mapGetters('error', ['loading']),
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
    beforeRouteLeave(to, from, next) {
        this.setError(null);
        next();
    }
};
</script>

<style scoped>
.container {
    position: absolute;
    left: 50%;
    height: 100%;
    transform: translateX(-50%);
    display: flex;
}

.window {
    width: 300px;
    min-height: 370px;
    transform: translateY(-12%);
    margin: auto auto;
}

.header {
    font-size: 1.2em;
}

.main {
    flex-grow: 1;
    padding: 30px;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
}

.buttons {
    width: 100%;
}

.authorization {
    padding-bottom: 2px;
    display: flex;
    justify-content: flex-start;
    cursor: pointer;
}

.authorization:hover {
    font-weight: 600;
}

.message {
    text-align: center;
    top: 55px;
    min-height: 25px;
    cursor: default;
    font-size: 0.9em;
    padding-bottom: 10px;
    color: var(--4-color);
}

.btn.loading {
    background: linear-gradient(90deg, var(--2-color) 0%, var(--2-color20) 50%, var(--2-color) 100%);
    background-size: 200% 100%;
    animation: loadingAnimation 1.5s ease-in-out infinite;
    color: var(--3-color);
}

@keyframes loadingAnimation {
    0% {
        background-position: 200% 0;
    }
    100% {
        background-position: -200% 0;
    }
}
</style>
