
import { createApp } from 'vue'
import ArcoVue from '@arco-design/web-vue';
import { createPinia } from 'pinia'
import '@arco-design/web-vue/dist/arco.css';
import App from './App.vue'
import router from './router'
import '@/access/assess.ts'
const app = createApp(App)
import 'bytemd/dist/index.css'
app.use(createPinia())
app.use(router)
app.use(ArcoVue);
app.mount('#app')
