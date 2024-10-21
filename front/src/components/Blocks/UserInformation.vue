<template>
    <div class="window">
        <div class="header">
            <div class="fio">{{ user.fam }} {{ user.name }} {{ user.otch }}</div>
            <div class="online">последний раз в сети 15.09.24 15:16</div>
        </div>
        <div class="main">
            <img class="photo" :src="photo" alt="userPhoto"/>
            <div class="information">
                <div v-if="dateOfBirth" class="info">
                    <div>День рождения:</div>
                    <div>{{ dateOfBirth }}</div>
                </div>
                <div v-if="age" class="info">
                    <div>Возраст:</div>
                    <div>{{ age }}</div>
                </div>
                <div v-if="sex" class="info">
                    <div>Пол:</div>
                    <div>{{ sex }}</div>
                </div>
                <div class="info">
                    <div>Номер телефон</div>
                    <div>{{ user.tel }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import moment from 'moment';


export default {
    props: {
        user: {
            type: Object,
            required: true
        },
        photo: {
            type: null,
            required: true
        }
    },
    computed: {
        dateOfBirth() {
            return this.user.dateOfBirth
                ? moment(this.user.dateOfBirth).format('DD.MM.YYYY')
                : '';
        },
        age() {
            if (this.user.dateOfBirth) {
                const birthDate = moment(this.user.dateOfBirth);
                return moment().diff(birthDate, 'years'); // Расчет возраста
            }
            return ''; // Если дата рождения отсутствует
        },
        sex(){
            return this.user.sex ? (this.user.sex === 1 ? 'Мужской' : 'Женский') : '';
        }
    },
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
