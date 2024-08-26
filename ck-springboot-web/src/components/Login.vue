<script setup>
import {ref} from "vue";
import {loginServer} from "@/api/user.js";
import {ElMessage} from "element-plus";
const loginForm = ref({
  no: '',
  password: ''
})
// const confirm=ref(true);

const rules = ref({
  no:[
    {required: true, message: '账号不能为空', trigger: 'blur'},
    {min: 4, max: 11, message: '长度在4-11之间', trigger: 'blur'}
  ],
  password:[
    {required: true, message: '密码不能为空', trigger: 'blur'},
  ]
})
const form=ref(null)
import { useRouter } from 'vue-router'
import useUserInfo from '@/store/userInfo.js'
const router=useRouter();
const userInfo=useUserInfo();
const confirm=()=>{
  form.value.validate(async (valid)=>{
      if(valid){
        const user=await loginServer(loginForm);
        ElMessage.success("登陆成功")
        //存储对象
        userInfo.setUserInfo(user.data);
        console.log(user.data);
        //改变路由
        router.push("/HomeView")
      }
  })
}
</script>

<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">用户登录</h1>
        <el-form :model="loginForm" label-width="100px"
                 :rules="rules" ref="form">
          <el-form-item label="账号" prop="no">
            <el-input style="width: 200px" type="text" v-model="loginForm.no"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input style="width: 200px" type="password" v-model="loginForm.password"
                      show-password autocomplete="off" size="small" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="confirm()">确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}

.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: #fff;
  border-radius: 5%;

}

.login-title {
  margin: 20px 0;
  text-align: center;
}

.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>