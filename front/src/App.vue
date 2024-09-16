<script>
import {defineComponent} from 'vue';
import GeneralPage from '@/components/Pages/GeneralPage.vue';

export default defineComponent({
    components: {GeneralPage}
});
</script>

<template>
    <GeneralPage class="general"/>
</template>

<style scoped>
.general{
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
}
</style>
