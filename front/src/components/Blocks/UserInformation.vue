<script>
import img from '@/assets/images/photo_2024-02-27_18-38-46.jpg';

export default {
    data() {
        return {
            photo: img,
        };
    },
};
</script>

<template>
    <div class="window">
        <div class="header">
            <div class="fio">Кузнецов Николай Комодович</div>
            <div class="online">последний раз в сети 15.09.24 15:16</div>
        </div>
        <div class="main">
            <img class="photo" :src="photo" alt=""/>
            <div class="information">
                <div class="info">
                    <div>День рождения:</div>
                    <div>17.09.1994</div>
                </div>
                <div class="info">
                    <div>Место жительства:</div>
                    <div>Дудинка</div>
                </div>
                <div class="info">
                    <div>Место учёбы:</div>
                    <div>СОШ №15</div>
                </div>
                <div class="info">
                    <div>Место работы:</div>
                    <div>ООО "СОЦ-ИНФОРМ"</div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

.header {
    justify-content: space-between;
}

.photo {
    aspect-ratio: 1 / 1;
    width: 230px;
    margin-right: 20px;
    border-radius: 50%;
    object-fit: cover;
    overflow: hidden;
}

.information .info {
    display: flex;
    gap: 5px;
    padding: 2px 0;
}

.header .fio {
    font-size: 1.6em;
    font-weight: 600;
    color: white;
}

.header .online {
    font-size: 1em;
}
</style>
