<template>
    <div class="container" v-if="user">
        <div class="window">
            <div class="header">
                Личная ифнормация
            </div>
            <div class="main">
                <div class="left_block">
                    <div class="input-block">
                        <div class="title">Фамилия</div>
                        <input v-model="user.fam" maxlength="20" class="value" placeholder="Фамилия"
                               @input="user.fam = filter($event.target.value)">
                    </div>
                    <div class="input-block">
                        <div class="title">Имя</div>
                        <input v-model="user.name" maxlength="20" class="value" placeholder="Имя"
                               @input="user.name = filter($event.target.value)">
                    </div>
                    <div class="input-block">
                        <div class="title">Отчество</div>
                        <input v-model="user.otch" maxlength="20" class="value" placeholder="Отчество"
                               @input="user.otch = filter($event.target.value)">
                    </div>
                    <div class="input-block">
                        <div class="title">Дата рождения</div>
                        <input v-model="dateOfBirth" type="date" class="value" placeholder="Дата рождения">
                    </div>
                    <div class="input-block">
                        <div class="title">Номер телефона</div>
                        <input v-model="user.tel" maxlength="18" type="tel" class="value" placeholder="Номер телефона"
                               @input="user.tel = filterTel($event.target.value)">
                    </div>
                    <div class="input-block">
                        <div class="title">Пароль</div>
                        <input v-model="user.password" maxlength="20" type="password" class="value" placeholder="Пароль"
                               @input="checkPasswords">
                    </div>
                    <div class="input-block">
                        <div class="title">Подтвердите пароль</div>
                        <input v-model="password" maxlength="20" type="password" class="value"
                               placeholder="Подтвердите пароль" @input="checkPasswords">
                    </div>

                </div>
                <div class="right_block">
                    <div class="file-input-wrapper">
                        <img v-if="newUserPhoto || !photo" class="user_photo" :src="img" alt="user-photo"/>
                        <img v-if="photo && !newUserPhoto" class="user_photo" :src="photo" alt="user-photo"/>
                        <input type="file" @change="onImageChange" accept="image/*" class="file-input" id="file-input"/>
                        <label for="file-input" class="btn">
                            Выбрать фото
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from 'vuex';
import img from '@/assets/images/default_photo_user.png';
import moment from 'moment';

export default {
    data() {
        return {
            img: img,
            password: ''
        };
    },
    computed: {
        ...mapGetters('user', ['user']),
        ...mapGetters('photo', ['userPhoto']),
        ...mapGetters('photo', ['newUserPhoto']),
        photo() {
            const img = new Image();
            img.src = this.userPhoto;
            img.onerror = () => {
                this.getUserPhoto(null);
            };
            return this.userPhoto;
        },
        dateOfBirth: {
            get() {
                return this.user.dateOfBirth
                    ? moment(this.user.dateOfBirth).format('YYYY-MM-DD')
                    : '';
            },
            set(value) {
                this.setUser({
                    ...this.user,
                    dateOfBirth: value
                });
            }
        }
    },
    mounted() {
        this.getUser(null);
        this.getUserPhoto(null);
    },
    methods: {
        ...mapMutations('user', ['setUser']),
        ...mapMutations('error', ['setError', 'setDisable', 'clearUserData']),
        ...mapMutations('photo', ['setNewUserPhoto']),
        ...mapActions('user', ['getUser']),
        ...mapActions('photo', ['getUserPhoto']),
        onImageChange(event) {
            const file = event.target.files[0];
            if (file && file.type.includes('image')) {
                this.img = URL.createObjectURL(file)
                this.setNewUserPhoto(file);
            }
        },
        checkPasswords() {
            if (this.user.password !== this.password) {
                this.setError('Пароли не совпадают!');
                this.setDisable(true)
            } else {
                this.setError('');
                this.setDisable(false)
            }
        },
        filter(value) {
            const regex = /^[А-Яа-яЁё\s]*$/;
            if (!regex.test(value)) {
                value = value.replace(/[^А-Яа-яЁё\s]/g, '');
            }
            return value;
        },
        filterTel(value) {
            const regex = /^[0-9()+-]*$/;
            if (!regex.test(value)) {
                value = value.replace(/[^0-9()+-]/g, '');
            }
            return value;
        }
    },
    beforeRouteLeave(to, from, next) {
        this.clearUserData(); // Очистка данных пользователя при уходе с маршрута
        this.setNewUserPhoto(null);
        next();
    }
};
</script>

<style scoped>
.left_block {
    display: flex;
    flex-direction: column;
    flex-grow: 1;

}

.file-input-wrapper {
    width: 150px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 10px;
}

.file-input {
    display: none;
}

.btn {
    height: 35px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.main {
    display: flex;
    padding: 10px;
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
    margin-right: 10px;
}

.input-block .title {
    width: 210px;
}

.input-block .value {
    height: 35px;
    flex-grow: 1;
    margin: 0;
}


</style>