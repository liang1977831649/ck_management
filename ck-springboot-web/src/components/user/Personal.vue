<script lang="ts" setup>

import useUserInfoStore from "@/store/userInfo"
const user=useUserInfoStore().userInfo;


import {ref,computed,watchEffect} from 'vue'

// 02
const formatdata=computed(()=>{
  const oDate=new Date()
  const year=oDate.getFullYear()
  const month=oDate.getMonth()+1
  const day=oDate.getDate()
  const hour=oDate.getHours()
  const min=oDate.getMinutes();
  const second=oDate.getSeconds();
  return year+'-'+month+'-'+day+' '+hour+':'+min+':'+second
})
</script>

<template>
  <div>
    <el-descriptions
        class="margin-top"
        title="欢迎来到仓储系统"
        :column="3"
        border
    >
      <template #extra>
        <el-button type="primary">Operation</el-button>
      </template>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <user/>
            </el-icon>
            用户名
          </div>
        </template>
        {{ user.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <iphone/>
            </el-icon>
            手机
          </div>
        </template>
        {{ user.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <location/>
            </el-icon>
            性别
          </div>
        </template>
        <el-tag :type="user.sex===1?'success':'primary'">
          {{user.sex===1?'男':'女'}}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <tickets/>
            </el-icon>
            身份
          </div>
        </template>
        <el-tag :type="user.roleId===1?'danger':(user.roleId==='2'?'primary':'success')" >
          {{user.roleId===1?'超管员':(user.roleId===2?'管理员':'用户')}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <office-building/>
            </el-icon>
            年龄
          </div>
        </template>
        {{ user.age }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <office-building/>
            </el-icon>
            账号
          </div>
        </template>
        {{ user.no }}
      </el-descriptions-item>
    </el-descriptions>
    <h1 style="margin-left:26%;font-size: 36px;margin-top: 5%">{{'当地时间：'+formatdata }}</h1>
  </div>
</template>
<style scoped>
.el-descriptions {
  margin-left: 5%;
  width: 90%;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>