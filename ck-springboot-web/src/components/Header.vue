<script setup>
import {Fold, CaretBottom} from "@element-plus/icons-vue";
import useUserInfo from "@/store/userInfo.js";
import {useRouter} from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";


const userInfo = useUserInfo();
const router = useRouter();
const center = () => {
  // console.log("个人中心")
  router.push('/Personal')
}
const handleCommand = (command) => {
  console.log('已发出command命令');
  // console.log(command)
  if (command === 'loginOut') {
    ElMessageBox.confirm(
        '你确认退出系统吗?',
        '温馨提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: "warning",
        })
        .then(() => {
          //清楚User的pinia
          userInfo.userInfo = '';
          //改变路由
          router.push('/');
          ElMessage.success("已退出")
        })
        .catch(() => {
          ElMessage.info('用户取消退出')
        })
  } else {
    router.push("/" + command)
  }
}

</script>

<template>
  <div class="toolbar" style="display: flex;line-height: 60px">
    <div>
      <el-icon style="margin-top: 25px;margin-left: 2px">
        <el-button @click="collapse()" style="background:none;font-size: 25px">
          <Fold/>
        </el-button>
      </el-icon>
    </div>
    <div style="flex: 1;text-align: center;font-size: 34px">
      <p>欢迎来到仓储系统</p>
    </div>
    <div>
      <span>{{ userInfo.userInfo.name }}</span>
    </div>
    <div>
      <el-dropdown @command="handleCommand">
        <el-icon style="margin-right: 8px; margin-top: 18px;font-size: 20px">
          <CaretBottom/>
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="Personal">个人中心</el-dropdown-item>
            <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!--    <el-icon style="margin-right: 8px; margin-top: 1px">-->
    <!--      <setting/>-->
    <!--    </el-icon>-->

  </div>
</template>

<style scoped>

</style>