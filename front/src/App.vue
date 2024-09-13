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
    <div class="wrapper">
        <header>
            <Header/>
        </header>
        <main>
            <Main/>
        </main>
        <footer>
            <Footer/>
        </footer>
    </div>
</template>

