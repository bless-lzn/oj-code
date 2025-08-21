
import { createApp } from 'vue'
import ArcoVue from '@arco-design/web-vue';
import { createPinia } from 'pinia'
import '@arco-design/web-vue/dist/arco.css';
import App from './App.vue'
import router from './router'
import '@/access/assess.ts'

import 'bytemd/dist/index.css'
const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ArcoVue);
app.mount('#app')
