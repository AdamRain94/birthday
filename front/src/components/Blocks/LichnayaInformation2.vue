<script>
export default {

};
</script>

<template>
    <div class="container">
        <div class="window">
            <div class="header">
                Личная ифнормация
            </div>
            <div class="main">
                другая инфа
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    display: flex;
    flex-direction: column;
    padding: 20px;
}

.input-block {
    display: flex;
    align-items: center;
    gap: 10px;
    padding-bottom: 10px;
}

.input-block .title {
    width: 100px;
}

.input-block .value {
    width: 230px;
    margin: 0;
}

.btn {
}


</style>