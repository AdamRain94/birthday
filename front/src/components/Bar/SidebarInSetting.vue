<script>
import {mapActions} from 'vuex';

export default {
    data() {
        return {
            isLoading: false
        };
    },
    methods: {
        ...mapActions(['updateUser']),
        ...mapActions(['updateUserPhoto']),
        save() {
            this.isLoading = true;
            this.updateUser().then(() => {
            }).catch((error) => {
                console.log(error)
            })
            this.updateUserPhoto().then(() => {
            }).catch((error) => {
                console.log(error)
            }).finally(() => {
                this.isLoading = false;
            });
        }
    }
};
</script>

<template>
    <div class="container">
        <div class="window">
            <div class="header">Настройки</div>
            <div class="main">
                <router-link class="li" to="/setting/me">
                    Личная информация
                </router-link>
                <router-link class="li" to="/setting/me2">
                    Личная информация2
                </router-link>
            </div>
        </div>
        <div class="buttons">
            <button :disabled="isLoading" @click="save" class="btn"
                    :class="{ loading : isLoading}">
                <span v-if="isLoading">Сохранение...</span>
                <span v-else>Сохранить</span>
            </button>
        </div>
    </div>
</template>

<style scoped>
.main {
    flex-direction: column;
}

.window {
    margin-bottom: 20px;
}

.btn {
    border-radius: 10px;
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
</style>