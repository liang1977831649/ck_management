<script lang="ts" setup>
import {Avatar, HomeFilled, UserFilled,OfficeBuilding,Menu,Grid,View} from '@element-plus/icons-vue'
import {ref} from "vue";
import {menuServer} from "@/api/menu.js";
import useUserInfoStore from "@/store/userInfo"

//获取到userInfo的pinia
const user=useUserInfoStore();

//菜单图标映射数组
const iconMapping=ref({
  "Avatar":Avatar,
  "UserFilled":UserFilled,
  "OfficeBuilding":OfficeBuilding,
  "Menu":Menu,
  "Grid":Grid,
  "View":View
})

const isCollapse = ref(false)
const collapse_transition = ref(false);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}



// 定义一个动态导航
const menus = ref([
  {
    menuName: '用户管理',
    menuIcon: 'UserFilled',
    menuIndex: '/User'
  },
  {
    menuName: '管理员管理',
    menuIcon: 'Avatar',
    menuIndex: '/Admin'
  }
])

//根据用户的权限来获取对应的菜单
const getMenu= async ()=>{
  const menuList= await menuServer(user.userInfo.roleId);
  menus.value=menuList.data
}
getMenu();

</script>

<template>
  <el-scrollbar>
    <el-menu style="height: 100vh;"
             background-color="#545c64"
             class="el-menu-vertical-demo"
             active-text-color="#ffd04b"
             text-color="#fff"
             default-active="/Personal"
             :collapse="isCollapse"
             :collapse-transition="collapse_transition"
             @open="handleOpen"
             @close="handleClose"
             router
    >

      <el-menu-item index="/Personal">
        <el-icon>
          <HomeFilled/>
        </el-icon>
        <template #title>主页</template>
      </el-menu-item>

      <el-menu-item :index=item.menuIndex v-for="(item,i) in menus" :key="i">
        <template #title>
          <el-icon>
            <component :is='iconMapping[item.menuIcon]'/>
          </el-icon>
          <span>{{ item.menuName }}</span>
        </template>
      </el-menu-item>

    </el-menu>

  </el-scrollbar>
</template>

<style scoped>

</style>
