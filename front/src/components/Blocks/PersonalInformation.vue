<script>
export default {
    computed: {
        user() {
            return this.$store.state.user || {};
        }
    },
    mounted() {
        this.$store.dispatch('getUser');
    },
};
</script>

<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Личная ифнормация
            </div>
            <div class="main">
                <div class="input-block">
                    <div class="title">Фамилия</div>
                    <input v-model="user.fam" class="value" placeholder="Фамилия">
                </div>
                <div class="input-block">
                    <div class="title">Имя</div>
                    <input v-model="user.name" class="value" placeholder="Имя">
                </div>
                <div class="input-block">
                    <div class="title">Отчество</div>
                    <input v-model="user.otch" class="value" placeholder="Отчество">
                </div>
                <div class="input-block">
                    <div class="title">Дата рождения</div>
                    <input v-model="user.dateOfBirth" type="date" class="value" placeholder="Дата рождения">
                </div>
                <div class="input-block">
                    <div class="title">Номер телефона</div>
                    <input v-model="user.tel" type="tel" class="value" placeholder="Номер телефона">
                </div>
                <div class="input-block">
                    <div class="title">Пароль</div>
                    <input v-model="user.password" class="value" placeholder="Пароль">
                </div>
                <!--                <button class="btn mt-2">Сохранить</button>-->
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    display: flex;
    flex-direction: column;
    padding: 20px;
}

.input-block {
    display: flex;
    align-items: center;
    gap: 10px;
    padding-bottom: 10px;
}

.input-block .title {
    width: 100px;
}

.input-block .value {
    width: 230px;
    margin: 0;
}

.btn {
}


</style>