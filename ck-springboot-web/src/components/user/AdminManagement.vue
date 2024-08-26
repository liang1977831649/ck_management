<script setup>
import {ref} from "vue";
import {listServer, searchServer, addServer, editServer, delServer} from "@/api/user.js"
import {ElMessage, ElMessageBox} from "element-plus";
const item = ref({
  id: '',
  no: '',
  name: '',
  password: '',
  age: "",
  sex: '',
  phone: '',
  roleId: '',
  isvalid: ''
})
const dialogVisible = ref(false);
const title = ref('添加用户');
// 分页数据
const currentPage = ref(1)
const pageSize = ref(5)
// const size = ref<ComponentSize>('default')
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(20);

const roleId=ref(2);
//搜索内容
const searchContext = ref("");

//动态绑定是否禁止输入与不禁止输入
const disable = ref(false);

const tableData = ref(Array.from({length: 10}).fill(item))

const clearItem = () => {
  item.value.id = '';
  item.value.no = ''
  item.value.name = ''
  item.value.password = ''
  item.value.age = ''
  item.value.sex = ''
  item.value.phone = ''
  item.value.roleId = ''
  item.value.isvalid = ''
}
const rowToItem = (row) => {
  item.value.id = row.id
  item.value.no = row.no
  item.value.name = row.name
  item.value.password = row.password
  item.value.age = row.age
  item.value.sex = row.sex
  item.value.phone = row.phone
  item.value.roleId = row.roleId
  item.value.isvalid = row.isvalid
}
const list = async () => {
  let dataList;
  if (searchContext.value !== '') {
    dataList = await searchServer(searchContext, currentPage, pageSize,roleId);
  } else {
    dataList = await listServer(currentPage, pageSize,roleId);
  }
  tableData.value = dataList.data.records;
  allNumber.value = dataList.data.total;
}
list();
const search = async () => {
  currentPage.value = 1;
  pageSize.value = 5;
  list();
}
const handleSizeChange = (number) => {
  list()
}
const handleCurrentChange = (number) => {
  list();
}
const add = async () => {
  // console.log("添加用户")
  console.log("item.value=", item.value)
  await addServer(item);
  dialogVisible.value = false;
  clearItem();
  ElMessage.success("添加成功")
  //添加完之后，重新至搜索条件为空
  searchContext.value = '';
  list();
}

const editView = (row) => {
  //禁止修改账号
  disable.value = true;
  rowToItem(row);
  console.log(item);
  title.value = '编辑用户'
  dialogVisible.value = true;
}
const edit = async () => {
  await editServer(item);
  clearItem();
  dialogVisible.value = false;
  ElMessage.success("编辑成功")
  list();
}
const del = async (row) => {
  ElMessageBox.confirm(
      '你确认要删除该用户吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: "warning",
      })
      .then(async () => {
        await delServer(row.id)
        list();
        ElMessage.success("删除成功")
      })
      .catch(() => {
        ElMessage.info('用户取消了删除')
      })
}

//检验规则
const rules = {
  name: [
    {required: true, message: '姓名不能为空', trigger: 'blur'},
    {min: 2, max: 15, message: '长度在2-15之间', trigger: 'blur'}
  ],
  no: [
    {required: true, message: '账号不能为空', trigger: 'blur'},
    {min: 4, max: 11, message: '长度在4-11之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    {pattern: /^(\d){6,20}$/, message: '长度在6-20之间', trigger: 'blur'}
  ],
  age:[
    {required: true, message: '年龄不能为空', trigger: 'blur'},
    {pattern: /^(\d){2,3}$/, message: '年龄不在范围内', trigger: 'blur'}
  ],
  sex:[
    {required: true, message: '请选择性别', trigger: 'change'}
  ],
  roleId:[
    {required: true, message: '请选择角色',trigger: 'change'},
  ],
  isvalid:[
    {required: true, message: '请选择该用户合法性', trigger: 'change'},
  ]
}
//校验表单信息的校验结果回馈
const form=ref(null);
const subFormAddEdit=()=>{
  item.value.roleId=2;
  form.value.validate(async (valid) => {
    if(valid){
      if(title.value==='添加用户'){
        await add();
      }else{
        await edit();
      }
    }else{
      ElMessage.error("数据格式错误")
    }
  })
}
const cancel=()=>{
  clearItem();
  form.value.resetFields();
  dialogVisible.value=false;
}
// 右上角关闭✖窗口
const handleDialogClose=()=>{
  console.log("已关闭窗口")
//   关闭弹窗
  dialogVisible.value=false;
  //清空数据
  clearItem();
  //清空校验异常信息
  form.value.resetFields();
}

</script>

<template>
  <div>
    <div style="margin: 10px 0 10px 5px">
      <el-button type="primary" @click="dialogVisible=true;disable=false;title='添加用户';clearItem();">添加用户</el-button>
      <el-button>其他</el-button>
    </div>
    <div style="margin: 0 0 10px 5px">
      <el-input style="width: 30%;" v-model="searchContext" placeholder="请输入用户姓名"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="search()">搜索</el-button>
    </div>

    <el-scrollbar>

      <el-table :data="tableData">
        <el-table-column prop="id" label="ID"/>
        <el-table-column prop="no" label="账号"/>
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="age" label="年龄"/>

        <el-table-column
            prop="sex"
            label="性别"
            :filters="[
        { text: '男', value: 1 },
        { text: '女', value: 0 },]">
          <template #default="scope">
            <el-tag
                :type="scope.row.sex === 1 ? 'success' : 'primary'"
                disable-transitions>
              {{ scope.row.sex === 1 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="phone" label="电话"/>
        <!--      <el-table-column prop="roleId" label="角色"/>-->
        <el-table-column
            prop="roleId"
            label="角色">
          <template #default="scope">
            <el-tag
                :type="scope.row.roleId === 1 ? 'danger' :(scope.row.roleId===2?'primary':'success')"
                disable-transitions>
              {{ scope.row.roleId === 1 ? '超管员' : (scope.row.roleId === 2 ? '管理员' : '用户') }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editView(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="del(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--:size="size"-->
      <!--分页插件-->
      <el-pagination style="float: right;margin-top: 20px"
                     v-model:current-page="currentPage"
                     v-model:page-size="pageSize"
                     :page-sizes="[3,5, 10]"
                     :disabled="disabled"
                     :background="background"
                     layout="prev, pager, next, jumper,total, sizes"
                     :total="allNumber"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"/>
    </el-scrollbar>

    <!-- 添加和编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="34%" :before-close="handleDialogClose">
      <el-form :model="item" :rules="rules"  ref="form"  >

        <el-form-item label="用户账号" label-width="100px" prop="no">
          <el-input :disabled="disable" v-model="item.no"></el-input>
        </el-form-item>

        <el-form-item label="用户姓名" label-width="100px" prop="name">
          <el-input v-model="item.name"></el-input>
        </el-form-item>

        <el-form-item label="用户密码" label-width="100px" prop="password">
          <el-input placeholder="请输入新密码" type="password" v-model="item.password"></el-input>
        </el-form-item>

        <el-form-item label="用户年龄" label-width="100px" prop="age">
          <el-input type="number" v-model="item.age"></el-input>
        </el-form-item>

        <el-form-item label="用户性别" label-width="100px" prop="sex">
          <el-radio-group v-model="item.sex">
            <el-radio label="男" value="1"></el-radio>
            <el-radio label="女" value="0"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="用户电话" label-width="100px" >
          <el-input type="number" v-model="item.phone"></el-input>
        </el-form-item>

<!--        <el-form-item label="用户角色" label-width="100px" prop="roleId">-->
<!--          <el-select placeholder="请选择" v-model="item.roleId">-->
<!--            <el-option label="超管员" value="1"></el-option>-->
<!--            <el-option label="管理员" value="2"></el-option>-->
<!--            <el-option label="用户" value="3"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->

        <el-form-item label="是否有效" label-width="100px" prop="isvalid">
          <el-radio-group v-model="item.isvalid">
            <el-radio label="合法" value="Y"></el-radio>
            <el-radio label="非法" value="N"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancel()">取消</el-button>
                <el-button type="primary" @click="subFormAddEdit()">确认 </el-button>
            </span>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped>

</style>