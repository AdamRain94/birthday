<script>
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
    <div class="window mb-2">
        <div class="header">Подписки</div>
        <div class="main">
            <div class="user">
                <img class="photo" :src="photo" alt="user"/>
                <div class="fio">Колчанова</div>
            </div>
            <div class="user">
                <img class="photo" :src="photo" alt="user"/>
                <div class="fio">Колчанова</div>
            </div>
            <div class="user">
                <img class="photo" :src="photo" alt="user"/>
                <div class="fio">Колчанова</div>
            </div>
            <div class="user">
                <img class="photo" :src="photo" alt="user"/>
                <div class="fio">Колчанова</div>
            </div>
            <div class="user">
                <img class="photo" :src="photo" alt="user"/>
                <div class="fio">Колчановаdddddddddddddddddddddddddddddddddddddd</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    flex-direction: column;
    gap: 10px;
}

.user {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    border-radius: 6px;
    overflow: hidden;
}

.user:hover {
    background-color: var(--2-color50);
    transition: background-color .3s linear;
}

.user .fio {
    font-size: 1em;
    margin: 0;
    font-weight: 100;
}
.user .photo {
    width: 40px;
    aspect-ratio: 1 / 1;
    border-radius: 6px;
}
</style>
