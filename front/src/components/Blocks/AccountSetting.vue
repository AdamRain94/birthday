<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Аккаунт
            </div>
            <div class="main">
                <div class="buttons">
                    <button :disabled="loading || disable" @click="exit" class="btn"
                            :class="{ loading : loading}">
                        <span>Выйти из аккаунта</span>
                    </button>
                </div>
                <div class="message">{{ error }}</div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';

export default {
    computed: {
        ...mapGetters('error', ['disable']),
        ...mapGetters('error', ['error']),
        ...mapGetters('error', ['loading'])
    },
    methods: {
        ...mapActions('auth', ['exit']),
    }
};
</script>

<style scoped>

</style>