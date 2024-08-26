<script setup>
import {ref} from "vue";
import {searchServer, addServer, editServer, delServer} from "@/api/goods.js"
import {listStorageServer} from "@/api/storage.js"
import {listGoodsTypeServer} from "@/api/goodsType.js"
import {ElMessage, ElMessageBox} from "element-plus";
import {saveRecordServer} from "@/api/record.js"

const item = ref({
  id: '1',
  name: 'asd',
  storageId: '33',
  goodstypeId: '22',
  count: '11',
  remark: '22'
})

const dialogVisible = ref(false);
const title = ref('添加货物分类');

// 分页数据
const currentPage = ref(1)
const pageSize = ref(5)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(20);


//数据域
const tableData = ref(
    [
      {
        id: '1',
        name: 'asd',
        storageId: '33',
        goodstypeId: '22',
        count: '11',
        remark: '22',
        storageName: 'xx',
        goodstypeName: 'yy'
      }
    ]
)

//清空
const clearItem = () => {
  item.value.id = '';
  item.value.name = ''
  item.value.storageId = ''
  item.value.goodstypeId = ''
  item.value.count = ''
  item.value.remark = ''
}
// 拷贝
const rowToItem = (row) => {
  item.value.id = row.id
  item.value.name = row.name
  item.value.storageId = row.storageId
  item.value.goodstypeId = row.goodstypeId
  item.value.count = row.count
  item.value.remark = row.remark
}
// 搜索体
const searchBody = ref({
  searchContext: '',
  storageId: '',
  goodstypeId: '',
  pageNum: '',
  pageSize: ''
})

//数据回显函数
// 1.加载存储仓库
const storageList = ref([])
const goodstypeList = ref([])

const listStorage = async () => {
  const dataList = await listStorageServer();
  storageList.value = dataList.data;
}
const listGoodsTypeList = async () => {
  const dataList = await listGoodsTypeServer();
  goodstypeList.value = dataList.data;
}
// 调用list函数
listStorage();
listGoodsTypeList();

//将货物分类id、仓库id转换成对应的名称
const idToName = () => {
  for (let i = 0; i < tableData.value.length; i++) {
    //将处置storage的名称
    for (let j = 0; j < storageList.value.length; j++) {
      if (tableData.value[i].storageId === storageList.value[j].id) {
        tableData.value[i].storageName = storageList.value[j].name;
      }
    }
    //在处置goodstype的名称
    for (let j = 0; j < goodstypeList.value.length; j++) {
      if (tableData.value[i].goodstypeId === goodstypeList.value[j].id) {
        tableData.value[i].goodstypeName = goodstypeList.value[j].name;
      }
    }
  }
  // console.log("tableData.value=", tableData.value)
}

const list = async () => {
  let dataList;
  // console.log("searchBody.value=", searchBody.value)
  dataList = await searchServer(searchBody)
  tableData.value = dataList.data.records;
  allNumber.value = dataList.data.total;
  idToName();
}
list();
const search = async () => {
  searchBody.value.pageNum = '1';
  searchBody.value.pageSize = '5';
  list()
}
const reset = async () => {
  searchBody.value.storageId = '';
  searchBody.value.searchContext = '';
  searchBody.value.goodstypeId = '';
}
// 改变分页的大小
const handleSizeChange = (number) => {
  searchBody.value.pageSize = pageSize;
  list()
}
// 改变分页的页数
const handleCurrentChange = (number) => {
  searchBody.value.pageNum = currentPage;
  list();
}
const add = async () => {
  await addServer(item);
  dialogVisible.value = false;
  clearItem();
  ElMessage.success("添加成功")
  //添加完之后，重新至搜索条件为空
  searchBody.value.searchContext = '';
  list();
}

// 弹窗并初始化
const editView = (row) => {
  rowToItem(row);
  // console.log(item);
  title.value = '编辑货物'
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
      '你确认要删除该货物吗?',
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
    {required: true, message: '货物名不能为空', trigger: 'blur'},
    {min: 2, max: 15, message: '长度在2-15之间', trigger: 'blur'}
  ]
}

//校验表单信息的校验结果回馈
const form = ref(null);
const subFormAddEdit = () => {
  form.value.validate(async (valid) => {
    if (valid) {
      if (title.value === '添加货物') {
        // console.log(item.value)
        await add();
      } else {
        await edit();
      }
    } else {
      ElMessage.error("数据格式错误")
    }
  })
}
const cancel = () => {
  clearItem();
  dialogVisible.value = false;
  form.value.resetFields();
}

// ------------------------------------有关出入库的操作和数据-------------------------------------------
//出入库数据模型
const inOutModel = ref({
  id: '',
  goodsId: '',
  goodsName: '',
  userId: '',
  userName: '',
  adminId: '',
  adminName: '',

  count: '',
  createTime: '',
  remark: '',
  operation: ''
})

// 获取当前人的阿信息
import useUserInfoStore from "@/store/userInfo"

const user = useUserInfoStore().userInfo;

// 入库或出库的窗口
const inOutDialog = ref(false);//出入库弹窗
const inOutTitle = ref("入库");//出入库标题
// 选中某一行记录
const selectCurrentPage = (val) => {
  if(val!=null){
    inOutModel.value.goodsId = val.id;
    inOutModel.value.goodsName = val.name;
  }
  // console.log(inOutModel.value)
}
// 入库函数
const inGoods = () => {
  inOutModel.value.adminId = user.id;
  inOutModel.value.adminName = user.name;
  //看是入库还是出库操作
  inOutModel.value.operation=inOutTitle.value==='入库'?'1':'2'//1表示入库，2表示出库
  inOutDialog.value = true;

}

// 嵌入窗口控制变量
const innerDialog = ref(false);

import SelectUserManagement from "@/components/user/SelectUserManagement.vue";
// 父子组件的数据传递，父亲接收
const reception = (val) => {
  if (val !== null) {
    // console.log("我要你有你才有：",val);
    inOutModel.value.userName = val.name;
    inOutModel.value.userId = val.id;
  }
}
const rules2 = {
  goodsName: [
    {required: true, message: "请输入商品名称", trigger: 'blur'},
  ],
  adminName: [
    {required: true, message: "请输入操作员", trigger: 'blur'},
  ],
  userName: [
    {required: true, message: "请输入申请人", trigger: 'blur'},
  ],
  count: [
    {required: true, message: "请输入数量", trigger: 'blur'},
  ]
}
//校验表单信息的校验结果回馈
const form2 = ref(null);

const subFormAddEditInOutGoods = () => {
  form2.value.validate(async (valid) => {
    if (valid) {
      if(inOutTitle.value==='入库'&&inOutModel.value.count<=0){
        ElMessage.error("商品数量有误")
        return;
      }else if(inOutTitle.value==='出库'&&inOutModel.value.count>=0){
        ElMessage.error("商品数量有误")
        return;
      }
      await saveRecordServer(inOutModel)
      clearInOutModel();
      inOutDialog.value=false;
      list();
      ElMessage.success(inOutTitle.value==='入库'?'入库成功':'出库成功')

    } else {
      ElMessage.error("数据格式错误")
    }
  })
}
const cancelInOutGoods = () => {
  clearInOutModel()
  inOutDialog.value = false;
  form2.value.resetFields();
}

const clearInOutModel = () => {
  inOutModel.value.id = '';
  inOutModel.value.userId = ''
  inOutModel.value.userName = ''
  inOutModel.value.adminId = ''
  inOutModel.value.adminName = ''
  inOutModel.value.count = ''
  inOutModel.value.goodsId = ''
  inOutModel.value.goodsName = ''
}
</script>

<template>
  <div>
    <div style="margin: 10px 0 10px 5px">
      <el-button type="primary" @click="dialogVisible=true;title='添加货物';clearItem();">添加货物</el-button>
      <el-button>其他</el-button>
    </div>
    <div style="margin: 0 0 10px 5px">
      <el-form>
        <el-form-item>
          <el-input style="width: 20%" v-model="searchBody.searchContext" placeholder="请输入货物名"></el-input>
        </el-form-item>
        <!--下拉框1.货物分类的下拉框。2.仓库的下拉框-->
        <!--下拉框1-->
        <el-form-item style="width: 20%;float: left">
          <el-select placeholder="货物类别" v-model="searchBody.goodstypeId">
            <el-option v-for="g in goodstypeList" :key="g.id" :label="g.name" :value="g.id"/>
          </el-select>
        </el-form-item>

        <!--下拉框2-->
        <el-form-item style="width: 20%;float: left">
          <el-select placeholder="请选择" v-model="searchBody.storageId">
            <el-option v-for="s in storageList" :key="s.id" :label="s.name" :value="s.id"/>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" style="margin-left: 5px" @click="search()">搜索</el-button>
          <el-button type="info" style="margin-left: 5px" @click="reset()">重置</el-button>
          <el-button type="success" style="margin-left: 5px" @click="inOutTitle='入库';inGoods()">入库</el-button>
          <el-button type="danger" style="margin-left: 5px" @click="inOutTitle='出库';inGoods()">出库</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--列表-->
    <el-scrollbar>
      <el-table :data="tableData"
                highlight-current-row
                @current-change="selectCurrentPage">
        <el-table-column prop="id" label="ID"/>
        <el-table-column prop="name" label="物品分类名"/>
        <el-table-column prop="storageName" label="仓库"/>
        <el-table-column prop="goodstypeName" label="类型"/>
        <el-table-column prop="count" label="数量"/>
        <el-table-column prop="remark" label="备注"/>
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
    <el-dialog v-model="dialogVisible" :title="title" width="34%">
      <el-form :model="item" ref="form">
        <el-form-item label="货物名称" label-width="100px" prop="name">
          <el-input v-model="item.name"></el-input>
        </el-form-item>

        <el-form-item label="货物仓库" label-width="100px">
          <el-select placeholder="请选择仓库" v-model="item.storageId">
            <el-option v-for="s in storageList" :key="s.id" :value="s.id" :label="s.name"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item aria-placeholder="请选择分类" label="货物分类" label-width="100px">
          <el-select v-model="item.goodstypeId">
            <el-option v-for="g in goodstypeList" :key="g.id" :value="g.id" :label="g.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货物数量" label-width="100px" prop="number">
          <el-input v-model="item.count" type="number"/>
        </el-form-item>

        <el-form-item label="货物备注" label-width="100px">
          <el-input v-model="item.remark" type="textarea"/>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancel()">取消</el-button>
                <el-button type="primary" @click="subFormAddEdit()">确认</el-button>
            </span>
      </template>
    </el-dialog>

    <!--出入库窗口-->
    <el-dialog v-model="inOutDialog" :title="inOutTitle" width="40%">
      <!--内部弹窗-->
      <el-dialog
          v-model="innerDialog"
          width="1000"
          title="请选择申请人"
          append-to-body
      >
        <SelectUserManagement @event="reception"></SelectUserManagement>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="inOutModel.userId='';inOutModel.userName='';innerDialog=false">取消</el-button>
                <el-button type="primary" @click="innerDialog=false">确认</el-button>
            </span>
        </template>
      </el-dialog>

      <!--内容项-->
      <el-form :model="inOutModel" ref="form2" :rules="rules2">
        <el-form-item label="货物名称" label-width="100px" prop="goodsName">
          <el-input v-model="inOutModel.goodsName"></el-input>
        </el-form-item>

        <el-form-item label="出入库操作员" label-width="100px" prop="adminName">
          <el-input disabled="disabled" v-model="inOutModel.adminName"></el-input>
        </el-form-item>

        <el-form-item label="出入库申请人" label-width="100px" prop="userName">
          <el-input v-model="inOutModel.userName" @click="innerDialog=true"></el-input>
        </el-form-item>

        <el-form-item label="出入库数量" label-width="100px" prop="count">
          <el-input v-model="inOutModel.count" type="number"/>
        </el-form-item>

        <el-form-item label="出入库备注" label-width="100px">
          <el-input v-model="inOutModel.remark" type="textarea"/>
        </el-form-item>

      </el-form>
      <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancelInOutGoods()">取消</el-button>
                <el-button type="primary" @click="subFormAddEditInOutGoods()">确认</el-button>
            </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>