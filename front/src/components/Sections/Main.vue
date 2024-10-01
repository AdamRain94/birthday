<script setup>

</script>

<template>
    <div class="layout">
        <router-view name="sidebar" class="layout-left pr-2" v-slot="{ Component }">
            <transition name="fade" mode="out-in">
                <component :is="Component"/>
            </transition>
        </router-view>
        <router-view class="layout-right" v-slot="{ Component }">
            <transition name="fade" mode="out-in">
                <component :is="Component"/>
            </transition>
        </router-view>
    </div>
</template>

<style scoped>
.layout {
    display: flex;
    width: 100%;
    height: 100%;
}

.layout .layout-left {
    min-width: 160px;
    height: 100%;
}

.layout .layout-right {
    position: relative;
    width: 760px;
    min-width: 760px;
    height: 100%;
}

.fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from, .fade-leave-to {
    opacity: 0;
}

.fade-enter-to {
    opacity: 1;
}
</style>
