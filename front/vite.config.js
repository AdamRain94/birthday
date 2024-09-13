import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  base: './',
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: 'dist', // Директория для собранных файлов
    rollupOptions: {
      input: {
        main: './index.html' // Главный HTML файл
      }
    }
  },
  server: {
    host: true, // Позволяет доступ к серверу из сети
  },
})
