<template>
    <div class="general">
        <Header class="header"/>
        <Main class="main"/>
    </div>
</template>

<script>
import {defineComponent} from 'vue';
import Header from '@/components/Sections/Header.vue';
import Main from '@/components/Sections/Main.vue';
import Footer from '@/components/Sections/Footer.vue';

export default defineComponent({
    components: {Footer, Main, Header}
});
</script>

<style scoped>

.main {
    display: flex;
    width: 100%;
    max-width: 960px;
    min-width: 960px;
    height: 100%;
    margin: 0 auto;
    padding: 20px;
}

.header {
    position: sticky;
    height: 60px;
    top: 0;
    border-bottom: 1px solid var(--2-color50);
    background: var(--1-color);
}

.general{
    height: 100%;
}
</style>
