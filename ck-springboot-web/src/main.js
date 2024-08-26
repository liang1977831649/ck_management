import "@/assets/global.css"
import ElementPlus from "element-plus"
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'
import {createPinia} from "pinia";
import { createApp } from 'vue'
import App from './App.vue'

import router from "@/router"
const pinia=createPinia();
var app = createApp(App);

app.use(ElementPlus,{locale});
app.use(router)
app.use(pinia);
app.mount('#app')
