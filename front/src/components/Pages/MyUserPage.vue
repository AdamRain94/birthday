<template>
    <div class="container" v-if="user">
        <UserInformation :photo="photo" :loading="loading" :user="user" class="mb-2"/>
        <div class="block_2">
            <div class="left mr-2">
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
import {mapGetters} from 'vuex';
import {loadUserPhoto} from '@/utils.js';
import default_photo from '@/assets/images/default_photo_user.png';


export default {
    components: {UserDesires, UserInformation, UserSubscriptions},
    computed: {
        ...mapGetters('user', ['user'])
    },
    data() {
        return {
            photo: default_photo,
            loading: false
        };
    },
    async mounted() {
        this.loading = true;
        try {
            this.photo = await loadUserPhoto(this.user);
        } finally {
            this.loading = false;
        }
        document.title = (this.user.name || '') + ' ' + (this.user.fam || '');
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
}

.block_2 .right {
    width: 240px;
    display: flex;
    flex-direction: column;
}

</style>
