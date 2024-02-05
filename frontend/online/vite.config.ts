import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path';
import legacy from '@vitejs/plugin-legacy';
// 在plugins中添加


// https://vitejs.dev/config/
export default defineConfig({
    base: './',

    plugins: [
        vue(),
        legacy({
            targets: ['defaults', 'not IE 11']
        }),],
    server: { //主要是加上这段代码
        host: '127.0.0.1',
        port: 5173,
        proxy: {
            '/images': {
                target: 'http://8.140.143.119:8000/.',	//实际请求地址
                changeOrigin: true,
                rewrite: (path) => path//.replace(/^\/images/, '')
            },
            '/2024': {
                target: 'https://img2.imgtp.com/.',	//实际请求地址
                changeOrigin: true,
                rewrite: (path) => path//.replace(/^\/images/, '')
            },
            '/dev': {
                target: 'http://8.140.143.119:8080/.',	//实际请求地址
                changeOrigin: true,
                rewrite: (path) => path//.replace(/^\/images/, '')
            },
        }
    },


    resolve: {
        // 别名配置
        alias: {
            // '@assets': path.join(__dirname, 'src/assets'),
            // '@static': path.join(__dirname, 'src/img'),
            // "@": path.resolve(__dirname, "/src"),
            // "~@": path.resolve(__dirname, "/src"),
        }
    }
})
