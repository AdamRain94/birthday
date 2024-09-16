<script >
import img from '@/assets/images/0b2jq30sQEc.jpg';
export default {
    data() {
        return {
            photo: img
        };
    },
};
</script>

<template>
    <div class="window">
        <div class="header">Желания</div>
        <div class="main">
            <div class="desires">
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
                <div class="desire">
                    <img class="photo" :src="photo" alt="desire">
                    <div class="title">КолЧанова</div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

.desires {
    display: flex;
    border-radius: 6px;
    overflow: hidden;
}

.desire .photo {
    width: 130px;
    aspect-ratio: 1 / 1;
    border-radius: 6px;
    object-fit: cover;
    margin-right: 10px;
}

.desire .title {
    text-align: center;
}
</style>
