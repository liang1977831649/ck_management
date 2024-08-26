<script  setup>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue"

// const tableData = ref(Array.from({ length: 10 }).fill(item))
</script>

<template>
  <el-container class="layout-container-demo" >
    <el-aside >
      <Aside ></Aside>
    </el-aside>

    <el-container  >
      <el-header style="text-align: right; font-size: 12px;width: 100%">
        <Header ></Header>
      </el-header>

      <el-main style="height: 100%;width: 100%">
<!--        <User ></User>-->
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  right: 20px;
}
</style>