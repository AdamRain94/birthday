<template>
    <div class="window">
        <div class="header">
            <div class="fio">{{ userInfo.fam }} {{ userInfo.name }} {{ userInfo.otch }}</div>
            <div class="online">последний раз в сети 15.09.24 15:16</div>
        </div>
        <div class="main">
            <img v-if="photo" class="photo" :src="photo" alt="userPhoto"/>
            <img v-if="!photo" class="photo" :src="img" alt="userPhoto"/>
            <div class="information">
                <div class="info">
                    <div>День рождения:</div>
                    <div>{{ dateOfBirth }}</div>
                </div>
                <div class="info">
                    <div>Номер телефон</div>
                    <div>{{ userInfo.tel }}</div>
                </div>
                <div class="info">
                    <div>Место учёбы:</div>
                    <div>СОШ №15</div>
                </div>
                <div class="info">
                    <div>Место работы:</div>
                    <div>ООО "СОЦ-ИНФОРМ"</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from 'vuex';
import moment from 'moment';
import img from '@/assets/images/default_photo_user.png';


export default {
    data() {
        return {
            img: img
        };
    },
    methods:{
        ...mapActions('photo', ['getUserPhoto'])
    },
    computed: {
        ...mapGetters('user',['user']),
        ...mapGetters('photo',['userPhoto']),
        userInfo() {
            return this.user || {};
        },
        photo() {
            const img = new Image();
            img.src = this.userPhoto;
            img.onerror = () => {
                this.getUserPhoto()
            };
            return this.userPhoto;
        },
        dateOfBirth() {
            return this.userInfo.dateOfBirth
                ? moment(this.userInfo.dateOfBirth).format('DD.MM.YYYY')
                : '';
        }
    }

};
</script>


<style scoped>

.header {
    justify-content: space-between;
}

.photo {
    aspect-ratio: 1 / 1;
    width: 300px;
    margin-right: 20px;
    border-radius: 6px;
    object-fit: cover;
    overflow: hidden;
}

.information .info {
    display: flex;
    gap: 5px;
    padding: 2px 0;
}

.header .fio {
    font-size: 1.6em;
    font-weight: 600;
    color: white;
    min-height: 27px;
}

.header .online {
    font-size: 1em;
}
</style>
