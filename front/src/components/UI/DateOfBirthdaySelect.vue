<template>
    <div class="container">
        <v-select
            class="year"
            v-model="selectedYear"
            :options="years"
            placeholder="1986"
        />
        <v-select
            class="month"
            v-model="selectedMonth"
            :options="months"
            placeholder="Апр"
        />
        <v-select
            class="day"
            v-model="selectedDay"
            :options="daysInMonth"
            placeholder="26"
        />
    </div>
</template>

<script>
import vSelect from 'vue3-select';

export default {
    components: {vSelect},
    props: {
        modelValue: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            selectedDay: null,
            selectedMonth: null,
            selectedYear: null,
            months: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
            years: Array.from({length: new Date().getFullYear() - 1899}, (_, i) => new Date().getFullYear() - i)
        };
    },
    computed: {
        daysInMonth() {
            const monthIndex = this.months.indexOf(this.selectedMonth); // Индекс выбранного месяца
            const year = this.selectedYear || new Date().getFullYear(); // Если год не выбран, использовать текущий год

            if (monthIndex === -1) return []; // Если месяц не выбран

            return this.getDaysInMonth(year, monthIndex);
        }
    },
    watch: {
        modelValue: {
            immediate: true,  // 1. Сразу вызываем обработчик при монтировании компонента
            handler(newValue) {  // 2. Обработчик изменений
                this.setDateFromModel(newValue);  // 3. Вызываем метод для установки даты
            }
        },
        selectedDay() {
            this.updateDate();
        },
        selectedMonth() {
            this.adjustDayIfNeeded();
            this.updateDate();

        },
        selectedYear() {
            this.adjustDayIfNeeded();
            this.updateDate();

        }
    },
    methods: {
        getDaysInMonth(year, monthIndex) {
            const lastDay = new Date(year, monthIndex + 1, 0).getDate();
            return Array.from({length: lastDay}, (_, i) => i + 1);
        },
        adjustDayIfNeeded() {
            const maxDay = this.daysInMonth[this.daysInMonth.length - 1]; // Последний день месяца
            if (this.selectedDay > maxDay) {
                this.selectedDay = maxDay; // Устанавливаем максимально возможный день
            }
        },
        updateDate() {
            if (this.selectedDay && this.selectedMonth && this.selectedYear) {
                const monthIndex = this.months.indexOf(this.selectedMonth) + 1;
                const day = String(this.selectedDay).padStart(2, '0');
                const month = String(monthIndex).padStart(2, '0');
                const year = this.selectedYear;
                const formattedDate = `${year}-${month}-${day}`;
                this.$emit('update:modelValue', formattedDate);
            }
        },
        setDateFromModel(date) {
            if (date) {
                const [year, month, day] = date.split('-');
                this.selectedYear = parseInt(year);
                this.selectedMonth = this.months[parseInt(month) - 1];
                this.selectedDay = parseInt(day);
            } else {
                this.selectedDay = null;
                this.selectedMonth = null;
                this.selectedYear = null;
            }
        }
    }

};
</script>

<style scoped>
.container {
    display: flex;
    gap: 10px;
}

.v-select.day {
    width: 60px;
}

.v-select.month {
    width: 70px;
}

.v-select.year {
    width: 75px;
}

</style>
