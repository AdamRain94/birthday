<script setup>

</script>

<template>
    <div class="layout">
        <router-view name="sidebar" class="layout-left p-2" v-slot="{ Component }">
            <component :is="Component"/>
        </router-view>
        <router-view class="layout-right" v-slot="{ Component }">
            <component :is="Component"/>
        </router-view>
    </div>
</template>

<style scoped>
.layout{
    display: flex;
    width: 100%;
    height: 100%;
}
.layout .layout-left{
    min-width: 20%;
}
.layout .layout-right{
    width: 80%;
}
.p-2{
    padding-right: 10px;
}
</style>
