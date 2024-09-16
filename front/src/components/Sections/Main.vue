<script setup>

</script>

<template>
    <div class="layout">
        <router-view name="sidebar" class="layout-left pr-2" v-slot="{ Component }">
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
    min-width: 160px;
}
.layout .layout-right{
    width: 740px;
}

</style>
