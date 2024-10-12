<template>
    <div class="container">
        <div class="window">
            <div class="header">Настройки</div>
            <div class="main">
                <router-link class="li" to="/setting/me">
                    Личная информация
                </router-link>
                <router-link class="li" to="/setting/account">
                    Аккаунт
                </router-link>
                <router-link class="li" to="/setting/me">
                    Какой-то ещё блок
                </router-link>
                <router-link class="li" to="/setting/account">
                    Какой-то ещё блок
                </router-link>
            </div>
        </div>
        <div class="buttons">
            <button :disabled="loading || disable" @click="save" class="btn"
                    :class="{ loading : loading}">
                <span>Сохранить</span>
            </button>
        </div>
        <div class="message">{{ error }}</div>
    </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';

export default {
    computed: {
        ...mapGetters('error',['disable']),
        ...mapGetters('error', ['error']),
        ...mapGetters('error', ['loading'])
    },
    methods: {
        ...mapActions('user',['updateUser']),
        ...mapActions('photo',['updateUserPhoto']),
         save() {
            this.updateUser();
            this.updateUserPhoto()
        }
    }
};
</script>

<style scoped>
.main {
    flex-direction: column;
}

.window {
    margin-bottom: 20px;
}

.buttons {
    padding-bottom: 5px;
}

.btn {
    border-radius: 10px;
}

.btn.loading {
    background: linear-gradient(90deg, var(--2-color) 0%, var(--2-color20) 50%, var(--2-color) 100%);
    background-size: 200% 100%;
    animation: loadingAnimation 1.5s ease-in-out infinite;
    color: var(--3-color);
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

.li {
    display: flex;
    align-items: center;
    color: var(--3-color);
    font-weight: 600;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 6px;
    margin-bottom: 5px;
}

.li:last-child {
    margin-bottom: 0;
}

.li:hover {
    background-color: var(--2-color50);
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