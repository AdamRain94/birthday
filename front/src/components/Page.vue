<script>
import img from '@/assets/images/photo_2024-02-27_18-38-46.jpg';
import user from '@/assets/images/0b2jq30sQEc.jpg';

export default {
    data() {
        return {
            image: img,
            user: user
        };
    }
};
</script>

<template>
    <div class="container">
        <div class="block_information">
            <div class="window">
                <div class="header">
                    <div class="fio">Кузнецов Николай Комодович</div>
                    <div class="online">последний раз в сети 15.09.24 15:16</div>
                </div>
                <div class="main">
                    <img class="photo" :src="image" alt=""/>
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
        </div>
        <div class="block_2">
            <div class="left">
                <div class="window desires">
                    <div class="header">Желания</div>
                    <div class="main">
                        <div class="podarki">
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                            <div class="podarok">
                                <img class="photo" :src="user" :alt="user">
                                <div class="title">КолЧанова</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>
                <div class="window">
                    <div class="header">Желания</div>
                    <div class="main"></div>
                </div>

            </div>
            <div class="right">
                <div class="window">
                    <div class="header">Подписки</div>
                    <div class="main">
                        <div class="user">
                            <img class="photo" :src="user" alt=""/>
                            <div class="fio">Колчанова</div>
                        </div>
                        <div class="user">
                            <img class="photo" :src="user" alt=""/>
                            <div class="fio">Колчанова</div>
                        </div>
                        <div class="user">
                            <img class="photo" :src="user" alt=""/>
                            <div class="fio">Колчанова</div>
                        </div>
                        <div class="user">
                            <img class="photo" :src="user" alt=""/>
                            <div class="fio">Колчанова</div>
                        </div>
                        <div class="user">
                            <img class="photo" :src="user" alt=""/>
                            <div class="fio">Колчанова</div>
                        </div>

                    </div>
                </div>
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

.window {
    display: flex;
    width: 100%;
    margin-bottom: 20px;
    flex-direction: column;
    border-radius: 12px;
    background-color: var(--1-color);
    border: 1px solid var(--2-color50);
}

.window .header {
    width: 100%;
    padding: 10px;
    border-top-left-radius: 11px;
    border-top-right-radius: 11px;
    background-color: var(--2-color);
    font-size: 1.2em;
}

.window .main {
    display: flex;
    padding: 10px;
    min-height: 30px;
    overflow: hidden;
}

.block_information .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.block_information .photo {
    aspect-ratio: 1 / 1;
    width: 150px;
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
    font-size: 0.8em;
}

.block_2 {
    display: flex;
}

.block_2 .left {
    width: 70%;
    display: flex;
    flex-direction: column;
    margin-right: 20px;
}

.block_2 .right {
    width: 30%;
    display: flex;
    flex-direction: column;
}

.podarki {
    display: flex;
    overflow-x: scroll;
    border-radius: 6px;
    scrollbar-width: none;
}

.podarok .photo {
    width: 100px;
    aspect-ratio: 3 / 4;
    border-radius: 6px;
    object-fit: cover;
    margin-right: 10px;
}

.podarok .title {
    text-align: center;
}

.right .main {
    flex-direction: column;
}

.user .photo {
    width: 40px;
    aspect-ratio: 1 / 1;
    border-radius: 50%;
}

.user .fio {
    font-size: 1em;
    margin: 0;
    font-weight: 100;
}

.user {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    border-radius: 6px;
    padding: 5px;
    overflow: hidden;
}

.user:hover {
    background-color: var(--2-color50);
    transition: background-color .3s linear;
}
</style>
