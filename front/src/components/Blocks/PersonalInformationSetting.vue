<template>
    <div class="container" v-if="user">
        <div class="window">
            <div class="header">
                Личная ифнормация
            </div>
            <div class="main">
                <div class="left_block">
                    <div class="input-block">
                        <div class="title">ID Пользователя</div>
                        <input disabled :value="id" class="value" placeholder="ID Пользователя">
                    </div>
                    <div class="input-block">
                        <div class="title">Фамилия</div>
                        <input v-model="user.fam" maxlength="20" class="value" placeholder="Фамилия"
                               @input="updateFam">
                    </div>
                    <div class="input-block">
                        <div class="title">Имя</div>
                        <input v-model="user.name" maxlength="20" class="value" placeholder="Имя"
                               @input="updateName">
                    </div>
                    <div class="input-block">
                        <div class="title">Отчество</div>
                        <input v-model="user.otch" maxlength="20" class="value" placeholder="Отчество"
                               @input="updateOtch">
                    </div>
                    <div class="input-block">
                        <div class="title">Дата рождения</div>
                        <date-of-birthday v-model="user.dateOfBirth" class="value"/>
                    </div>
                    <div class="input-block">
                        <div class="title">Пол</div>
                        <sex-select v-model="user.sex" class="value"/>
                    </div>
                    <div class="input-block">
                        <div class="title">Номер телефона</div>
                        <input v-model="user.tel" maxlength="18" type="tel" class="value" placeholder="Номер телефона"
                               @input="updateTel">
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
                        <img v-if="newUserPhoto || !user.photo" class="user_photo" :src="img" alt="user-photo"/>
                        <img v-if="user.photo && !newUserPhoto" class="user_photo" :src="photo" alt="user-photo"/>
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
import DateOfBirthday from '@/components/UI/DateOfBirthdaySelect.vue';
import SexSelect from '@/components/UI/SexSelect.vue';

export default {
    components: {SexSelect, DateOfBirthday},
    data() {
        return {
            img: img,
            password: ''
        };
    },
    computed: {
        ...mapGetters('user', ['user']),
        ...mapGetters('user', ['newUserPhoto']),
        photo() {
            return `http://localhost:8080/api/files/photo/user/${this.user.photo}`;
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
        },
        id(){
            return this.user.id;
        }
    },
    mounted() {
        this.getUser();
    },
    methods: {
        ...mapMutations('user', ['setUser']),
        ...mapMutations('error', ['setError', 'setDisable', 'clearUserData']),
        ...mapMutations('user', ['setNewUserPhoto']),
        ...mapActions('user', ['getUser']),
        onImageChange(event) {
            const file = event.target.files[0];
            if (file && file.type.includes('image')) {
                this.img = URL.createObjectURL(file);
                this.setNewUserPhoto(file);
            }
        },
        checkPasswords() {
            if (this.user.password !== this.password) {
                this.setError('Пароли не совпадают!');
                this.setDisable(true);
            } else {
                this.setError('');
                this.setDisable(false);
            }
        },
        filter(value) {
            const regex = /^[А-Яа-яЁё\s]*$/;
            this.setError('')
            if (!regex.test(value)) {
                this.setError('Разрешены только русские буквы!')
                value = value.replace(/[^А-Яа-яЁё\s]/g, '');
            }
            return value;
        },
        filterTel(value) {
            const regex = /^[0-9()\s+-]*$/;
            this.setError('')
            if (!regex.test(value)) {
                this.setError('Разрешены только цифры и специальные символы!')
                value = value.replace(/[^0-9()\s+-]/g, '');
            }
            return value;
        },
        updateFam(event) {
            const filteredFam = this.filter(event.target.value);
            this.setUser({...this.user, fam: filteredFam});
        },
        updateName(event) {
            const filteredName = this.filter(event.target.value);
            this.setUser({...this.user, name: filteredName});
        },
        updateOtch(event) {
            const filteredOtch = this.filter(event.target.value);
            this.setUser({...this.user, otch: filteredOtch});
        },
        updateTel(event) {
            const filteredTel = this.filterTel(event.target.value);
            this.setUser({...this.user, tel: filteredTel});  // Используем мутацию для обновления значения tel
        }
    },
    beforeRouteLeave(to, from, next) {
        this.clearUserData(); // Очистка данных пользователя при уходе с маршрута
        this.setNewUserPhoto(null);
        this.getUser();
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
    width: 360px;
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