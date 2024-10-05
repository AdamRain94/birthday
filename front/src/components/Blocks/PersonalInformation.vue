<script>
import {mapGetters} from 'vuex';
import img from '@/assets/images/default_photo_user.png';
export default {
    data() {
        return {
            img: img
        };
    },
    computed: {
        ...mapGetters(['user']),
        ...mapGetters(['userPhoto']),
        photo() {
            const img = new Image();
            img.src = this.userPhoto;
            img.onerror = () => {
                this.$store.dispatch('getUserPhoto') // Запрашиваем фото заново
            };
            return this.userPhoto;
        },
    },
    mounted() {
        this.$store.dispatch('getUser');
    },
    methods: {
        onImageChange(event) {
            const file = event.target.files[0];
            if (file) {
                this.$store.commit('setUserPhoto', URL.createObjectURL(file));
                this.$store.commit('setNewUserPhoto', file);
            }
        }
    }
};
</script>

<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Личная ифнормация
            </div>
            <div class="main">
                <div>
                </div>
                <div class="file-input-wrapper">
                    <img v-if="photo" class="user_photo" :src="photo" alt="user-photo"/>
                    <img v-if="!photo" class="user_photo" :src="img" alt="user-photo"/>
                    <input type="file" @change="onImageChange" accept="image/*" class="file-input" id="file-input"/>
                    <label for="file-input" class="btn">
                        Выбрать фото
                    </label>
                </div>
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
            </div>
        </div>
    </div>
</template>

<style scoped>

/* Контейнер для обёртки элемента input */
.file-input-wrapper {
    width: 150px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 10px;
}

/* Скрываем стандартный input */
.file-input {
    display: none;
}

.btn {
    text-align: center;
}

.main {
    display: flex;
    flex-direction: column;
    padding: 20px;
}

.user_photo {
    aspect-ratio: 1 / 1;
    width: 150px;
    border-radius: 6px;
    object-fit: cover;
    overflow: hidden;
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


</style>