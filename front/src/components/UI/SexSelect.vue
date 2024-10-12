<template>
    <div>
        <v-select
            class="sex"
            v-model="selectedSex"
            :options="sex"
            label="value"
            placeholder="Пол"
            :reduce="option => option.id"
        />
    </div>
</template>

<script>
import { defineComponent } from 'vue';
import vSelect from 'vue3-select';

export default defineComponent({
    components: { vSelect },
    props: {
        modelValue: {
            type: Number,
            default: null
        }
    },
    computed: {
        selectedSex: {
            get() {
                return this.modelValue;
            },
            set(value) {
                this.$emit('update:modelValue', value); // Передаем обновленное значение родителю
            }
        }
    },
    data() {
        return {
            sex: [
                { id: 1, value: 'Мужской' },
                { id: 2, value: 'Женский' }
            ]
        };
    }
});
</script>

<style scoped>
.v-select.sex {
    width: 225px;
}
</style>
