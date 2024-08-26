<script   setup>
import {ref} from "vue";
import {listServer, searchServer, addServer, editServer, delServer} from "@/api/goodsType.js"
import {ElMessage, ElMessageBox} from "element-plus";

const item = ref({
  id: '',
  name: '',
  remark:''
})

const dialogVisible = ref(false);
const title = ref('添加物品分类');

// 分页数据
const currentPage = ref(1)
const pageSize = ref(5)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(20);

//搜索内容
const searchContext = ref("");

//数据域
const tableData = ref(Array.from({length: 10}).fill(item))

//清空
const clearItem = () => {
  item.value.id = '';
  item.value.remark = ''
  item.value.name = ''
}
// 拷贝
const rowToItem = (row) => {
  item.value.id = row.id
  item.value.remark = row.remark
  item.value.name = row.name
}
const list = async () => {
  let dataList;
  if (searchContext.value !== '') {
    dataList = await searchServer(searchContext, currentPage, pageSize);
  } else {
    dataList = await listServer(currentPage, pageSize);
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
  await addServer(item);
  dialogVisible.value = false;
  clearItem();
  ElMessage.success("添加成功")
  //添加完之后，重新至搜索条件为空
  searchContext.value = '';
  list();
}

// 弹窗并初始化
const editView = (row) => {
  rowToItem(row);
  console.log(item);
  title.value = '编辑物品分类'
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
      '你确认要删除该物品分类吗?',
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
  ]
}

//校验表单信息的校验结果回馈
const form=ref(null);
const subFormAddEdit=()=>{
  item.value.roleId=3;
  form.value.validate(async (valid) => {
    if(valid){
        if(title.value==='添加物品分类'){
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
  dialogVisible.value=false;
  form.value.resetFields();
}

</script>

<template>
  <div>
    <div style="margin: 10px 0 10px 5px">
      <el-button type="primary" @click="dialogVisible=true;title='添加物品分类';clearItem();">添加物品分类</el-button>
      <el-button>其他</el-button>
    </div>
    <div style="margin: 0 0 10px 5px">
      <el-input style="width: 30%;" v-model="searchContext" placeholder="请输入物品分类名"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="search()">搜索</el-button>
    </div>

    <el-scrollbar>

      <el-table :data="tableData">
        <el-table-column prop="id" label="ID"/>
        <el-table-column prop="name" label="物品分类名"/>

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
    <el-dialog v-model="dialogVisible" :title="title" width="34%" >
      <el-form :model="item" :rules="rules"  ref="form"  >

        <el-form-item label="物品分类名" label-width="100px" prop="name">
          <el-input v-model="item.name"></el-input>
        </el-form-item>

        <el-form-item label="备注" label-width="100px">
          <el-input v-model="item.remark" type="textarea" />
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