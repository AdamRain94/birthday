<script>
import UserInformation from "@/components/Blocks/UserInformation.vue";
import UserDesires from "@/components/Blocks/UserDesires.vue";
import UserSubscriptions from "@/components/Blocks/UserSubscriptions.vue";
import {mapGetters} from 'vuex';

export default {
    components: {UserDesires, UserInformation,UserSubscriptions},
    computed: {
        ...mapGetters(['user']),
    }
};
</script>

<template>
    <div class="container" v-if="user">
        <UserInformation class="mb-2"/>
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
