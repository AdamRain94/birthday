<template>
    <div class="container">
        <div class="navbar">
            <div class="fon_logo">
                <img class="logo" :src="logo" alt="logo">
            </div>
            ВКУРСЕ
        </div>
    </div>
</template>

<script>
import cake from '@/assets/images/cake.png';

export default {
    data() {
        return {
            logo: cake
        };
    }
};
</script>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    z-index: 10000;
}

.navbar {
    display: flex;
    align-items: center;
    width: 920px;
    height: 100%;
    gap: 5px;
    padding: 10px;
    color: white;
    font-size: 2.5rem;
    font-weight: 900;
}
.fon_logo{
    display: flex;
    justify-content: center;
    width: 40px;
    height: 100%;
    border-radius: 50%;
    border: 5px solid #ffffff;

}
.logo {
    height: 90%;
    padding: 2px;
}
</style>
