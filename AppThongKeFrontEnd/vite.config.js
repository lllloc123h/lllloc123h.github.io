// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: '/AppThongKe/',
  server: { // Thêm phần server này vào
    proxy: {
      '/api-provinces': {
        target: 'https://provinces.open-api.vn',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api-provinces/, '/api'),
        secure: false, // Dùng false trong môi trường dev nếu gặp lỗi chứng chỉ SSL (như tự ký)
      },
    },
  },
})