<template>
    <div class="container" v-if="user">
        <UserInformation :user="user" :photo="photo" :loading="loading" class="mb-2"/>
        <div class="block_2">
            <div class="left mr-2">
                <UserDesires/>
                <UserDesires/>
                <UserDesires/>
            </div>
            <div class="right">
                <UserSubscriptions/>
            </div>
        </div>
    </div>
</template>

<script>
import UserInformation from '@/components/Blocks/UserInformation.vue';
import UserDesires from '@/components/Blocks/UserDesires.vue';
import UserSubscriptions from '@/components/Blocks/UserSubscriptions.vue';
import api from '@/axios.js';
import {loadUserPhoto} from '@/utils.js';
import default_photo from '@/assets/images/default_photo_user.png';

export default {
    components: {UserDesires, UserInformation, UserSubscriptions},
    data() {
        return {
            user: {},
            photo: default_photo,
            loading: false
        };
    },
    async created() {
        this.loading = true;
        try {
            const userId = this.$route.params.id;
            await this.getUser(userId);
            this.photo = await loadUserPhoto(this.user);

        } finally {
            this.loading = false;
        }
    },
    mounted() {
        document.title = (this.user.name || '') + ' ' + (this.user.fam || '');
    },
    methods: {
        async getUser(userId) {
            try {
                const response = await api.get(`/users/${userId}`);
                this.user = response.data;
            } catch (error) {
                console.error('Ошибка при получении пользователя:', error);
            }
        }
    }
};
</script>

<style scoped>

.container {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.block_2 {
    display: flex;
}

.block_2 .left {
    width: 500px;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.block_2 .right {
    width: 240px;
    display: flex;
    flex-direction: column;
}

</style>
