<script setup>

</script>

<template>
    <div class="container">
        <div class="window">
            <diV>
                Авторизация
            </diV>
            <div>
                Логин
            </div>
            <div>
                <input/>
            </div>
            <div>
                Пароль
            </div>
            <div>
                <input/>
            </div>
        </div>
    </div>
</template>

<style scoped>
.container {
    display: flex;
    align-items: center;
    min-height: 100vh;
    margin: 0 auto;
}
.window{
    box-sizing: border-box;
    justify-content: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 300px;
    margin: 0 auto;
    height: 300px;
    padding: 20px;
    border-radius: 12px;
    background-color: var(--header);
    border: 1px solid var(--header-border);
    box-shadow:
        10px 10px 20px rgba(255, 255, 255, 0.02),
        -10px -10px 20px rgba(255, 255, 255, 0.02),
        10px -10px 20px rgba(255, 255, 255, 0.02),
        -10px 10px 20px rgba(255, 255, 255, 0.02);
}
</style>
