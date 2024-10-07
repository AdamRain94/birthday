<template>
    <div class="layout">
        <router-view class="layout-left pr-2" v-slot="{ Component }">
            <transition name="fade" mode="out-in">
                <component :is="Component"/>
            </transition>
        </router-view>
        <router-view name="sidebarInSetting" class="layout-right" v-slot="{ Component }">
            <transition name="fade" mode="out-in">
                <component :is="Component"/>
            </transition>
        </router-view>
    </div>
</template>

<script>
export default {
    mounted() {
        document.title = 'Настройки';
    }
};
</script>

<style scoped>

.layout {
    display: flex;
    width: 100%;
    height: 100%;
}

.layout .layout-left {
    min-width: 560px;
    height: 100%;
}

.layout .layout-right {
    min-width: 200px;
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