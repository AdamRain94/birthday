<script>
import {defineComponent} from 'vue';
import Header from '@/components/Header.vue';
import Main from '@/components/Main.vue';
import Footer from '@/components/Footer.vue';

export default defineComponent({
    components: {Footer, Main, Header}
});
</script>

<template>
    <div>
        <Header class="header"/>
        <Main class="main"/>
    </div>
</template>

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
    min-width: 960px;
    top: 0;
    padding: 30px;
    border-bottom: 1px solid var(--2-color50);
    background: var(--1-color);
}
</style>
