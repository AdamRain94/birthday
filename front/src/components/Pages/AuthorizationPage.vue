<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Авторизация
            </div>
            <div class="main">
                <div>
                    <div @click="registration" class="registration">Регистрация →</div>
                    <input v-model="tel" maxlength="18" placeholder="Номер телефона" @input="filterTel"/>
                    <input v-model="password" maxlength="20" placeholder="Пароль" type="password" class="mb-0"/>
                    <div class="message" >{{ error }}</div>
                </div>
                <div class="buttons">
                    <button :disabled="isDisabled || loading" @click="enter" class="btn"
                            :class="{ loading : loading}">
                        <span v-if="loading">Загрузка...</span>
                        <span v-else>Войти</span>
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
            tel: '',
            password: '',
        };
    },
    methods: {
        ...mapActions('auth',['login']),
        ...mapMutations('error', ['setError']),
        enter() {
            this.login({tel: this.tel, password: this.password})
        },
        registration() {
            router.push('/registration');
        },
        filterTel() {
            const regex = /^[0-9()+-]*$/;
            this.setError('')
            if (!regex.test(this.tel)) {
                this.setError('Разрешены только цифры и специальные символы!')
                this.tel = this.tel.replace(/[^0-9()+-]/g, '');
            }
        },
    },
    computed: {
        ...mapGetters('error', ['loading']),
        ...mapGetters('error', ['error']),
        isDisabled() {
            return !(
                this.tel !== '' &&
                this.password !== ''
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
    min-height: 273px;
    transform: translateY(-17%);
    margin: auto auto;
}

.header {
    font-size: 1.2em;
}

.main {
    flex-grow: 1;
    padding: 30px;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
}

.buttons {
    width: 100%;
}


.registration {
    padding-bottom: 2px;
    display: flex;
    justify-content: flex-end;
    cursor: pointer;
}

.registration:hover {
    font-weight: 600;
}

.message {
    text-align: center;
    top: 55px;
    min-height: 25px;
    cursor: default;
    font-size: 0.9em;
    padding-bottom: 2px;
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
