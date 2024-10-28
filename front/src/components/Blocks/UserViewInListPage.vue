<template>
    <div>
        <div v-if="loading" class="user-photo loading"></div>
        <img v-else class="user-photo" :class="{loading : loading}" :src="photo" alt="user-photo">
        <div class="user-information">
            <div>{{ user.fam }}</div>
            <div>{{ user.name }}</div>
            <div>{{ user.otch }}</div>
            <div>{{ dateOfBirth(user.dateOfBirth) }}</div>
        </div>
    </div>
</template>

<script>
import {loadUserPhoto} from '@/utils.js';
import moment from 'moment';
import default_photo from '@/assets/images/default_photo_user.png';

export default {
    props: {
        user: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            loading: false,
            photo: null
        };
    },
    async mounted() {
        this.loading = true;
        try {
            this.photo = await loadUserPhoto(this.user);
        } finally {
            this.loading = false;
        }
    },
    methods: {
        dateOfBirth(date) {
            return date
                ? moment(date).format('DD.MM.YYYY')
                : '';
        }
    }
};
</script>

<style scoped>

.user-photo {
    aspect-ratio: 1 / 1;
    width: 130px;
    margin-right: 20px;
    border-radius: 6px;
    object-fit: cover;
    overflow: hidden;
}

.user-information {
    padding: 10px 0;
}

</style>