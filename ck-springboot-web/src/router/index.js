import { createRouter, createWebHistory } from "vue-router";

import HomeView from "@/components/HomeView.vue";
import Login from "@/components/Login.vue";
import Personal from "@/components/user/Personal.vue"
import UserManagement from "@/components/user/UserManagement.vue";
import AdminManagement from "@/components/user/AdminManagement.vue";
import StorageManagement from "@/components/storage/StorageManagement.vue";
import GoodsTypeManagement from "@/components/goodstyle/GoodsTypeManagement.vue";
import GoodsManagement from "@/components/goods/GoodsManagement.vue";
import RecordManagement from "@/components/record/RecordManagement.vue";

const routers=[

    {
        path:'/',
        component:Login
    },
    {
        path: '/HomeView',
        component: HomeView,
        redirect: "/Personal",
        children:[
            {path:'/Personal',component:Personal},
            {path: '/UserManagement',component:UserManagement },
            {path: '/AdminManagement',component: AdminManagement},
            {path: '/StorageManagement',component: StorageManagement},
            {path: '/GoodsTypeManagement',component: GoodsTypeManagement},
            {path: '/GoodsManagement',component: GoodsManagement},
            {path: '/RecordManagement',component: RecordManagement}
        ]
    }
]

//创建一个路由
const router=createRouter({
    history: createWebHistory(),
    routes: routers,
});

export default  router;