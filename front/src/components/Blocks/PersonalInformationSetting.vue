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
                        <input autocomplete="family-name" v-model="user.fam" maxlength="20" class="value" placeholder="Фамилия"
                               @input="updateFam">
                    </div>
                    <div class="input-block">
                        <div class="title">Имя</div>
                        <input autocomplete="given-name" v-model="user.name" maxlength="20" class="value" placeholder="Имя"
                               @input="updateName">
                    </div>
                    <div class="input-block">
                        <div class="title">Отчество</div>
                        <input autocomplete="additional-name" v-model="user.otch" maxlength="20" class="value" placeholder="Отчество"
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
                        <input autocomplete="tel" v-model="user.tel" maxlength="18" type="tel" class="value" placeholder="Номер телефона"
                               @input="updateTel">
                    </div>
                    <div class="input-block">
                        <div class="title">Пароль</div>
                        <input autocomplete="new-password" v-model="user.password" maxlength="20" type="password" class="value" placeholder="Пароль"
                               @input="checkPasswords">
                    </div>
                    <div class="input-block">
                        <div class="title">Подтвердите пароль</div>
                        <input autocomplete="new-password" v-model="password" maxlength="20" type="password" class="value"
                               placeholder="Подтвердите пароль" @input="checkPasswords">
                    </div>

                </div>
                <div class="right_block">
                    <div class="file-input-wrapper">
                        <img class="user_photo" :src="photo" alt="user-photo"/>
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
import moment from 'moment';
import DateOfBirthday from '@/components/UI/DateOfBirthdaySelect.vue';
import SexSelect from '@/components/UI/SexSelect.vue';
import {loadUserPhoto, filter, filterTel} from '@/utils.js'
import default_photo from '@/assets/images/default_photo_user.png';

export default {
    components: {SexSelect, DateOfBirthday},
    data() {
        return {
            photo: default_photo,
            password: ''
        };
    },
    computed: {
        ...mapGetters('user', ['user']),
        ...mapGetters('user', ['newUserPhoto']),

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
        id() {
            return this.user.id;
        }
    },
    async mounted() {
        await this.getUser();
        this.photo = await loadUserPhoto(this.user);
    },
    methods: {
        ...mapMutations('user', ['setUser']),
        ...mapMutations('error', ['setError', 'setDisable', 'clearUserData']),
        ...mapMutations('user', ['setNewUserPhoto']),
        ...mapActions('user', ['getUser']),
        onImageChange(event) {
            const file = event.target.files[0];
            if (file && file.type.includes('image')) {
                this.photo = URL.createObjectURL(file);
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
        updateFam(event) {
            const filteredFam = filter(event.target.value, this.setError);
            this.setUser({...this.user, fam: filteredFam});
        },
        updateName(event) {
            const filteredName = filter(event.target.value, this.setError);
            this.setUser({...this.user, name: filteredName});
        },
        updateOtch(event) {
            const filteredOtch = filter(event.target.value, this.setError);
            this.setUser({...this.user, otch: filteredOtch});
        },
        updateTel(event) {
            const filteredTel = filterTel(event.target.value, this.setError);
            this.setUser({...this.user, tel: filteredTel});  // Используем мутацию для обновления значения tel
        },
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