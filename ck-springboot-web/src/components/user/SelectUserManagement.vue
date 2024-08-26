<script   setup>
import {ref} from "vue";
import {listServer, searchServer, addServer, editServer, delServer} from "@/api/user.js"


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
const title = ref('添加用户');
// 分页数据
const currentPage = ref(1)
const pageSize = ref(5)
// const size = ref<ComponentSize>('default')
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(20);

//搜索内容
const searchContext = ref("");



const roleId=ref(3)

const tableData = ref(Array.from({length: 10}).fill(item))



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

// 父与子组件之间的数据传递
// 引入
// 子->父
const emit = defineEmits(["event"])
const handleClick = (val) => {
    emit('event',val)
}

</script>

<template>
  <div>

    <div style="margin: 0 0 10px 5px">
      <el-input style="width: 30%;" v-model="searchContext" placeholder="请输入用户姓名"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="search()">搜索</el-button>
    </div>

    <el-scrollbar>

      <el-table :data="tableData"
                highlight-current-row
                @current-change="handleClick">
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

  </div>
</template>

<style scoped>

</style>