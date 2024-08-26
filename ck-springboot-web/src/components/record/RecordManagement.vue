<script setup>
import {ref} from "vue";
import {searchServer} from "@/api/record.js"
import {listStorageServer} from "@/api/storage.js"
import {listGoodsTypeServer} from "@/api/goodsType.js"

const item = ref({
  id: '1',
  name: 'asd',
  storageId: '33',
  goodstypeId: '22',
  count: '11',
  remark: '22'
})

const dialogVisible = ref(false);

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
          goodsId: '33',
          userId: '22',
          adminId: '11',
          count: '22',
          createTime:'',
          remark:'',

          userName:'xx',
          adminName:'yy',
          goodsName:'zz',
          storageName:'aa',
          goodstypeName:'cc'
        }
    ]
)

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
  console.log(storageList.value)
}
const listGoodsTypeList = async () => {
  const dataList = await listGoodsTypeServer();
  goodstypeList.value = dataList.data;
  console.log(goodstypeList.value)
}
// 调用list函数
listStorage();
listGoodsTypeList();

const list = async () => {
  let dataList;
  console.log("searchBody.value=",searchBody.value)
  dataList = await searchServer(searchBody)
  tableData.value = dataList.data.records;
  allNumber.value = dataList.data.total;
}
list()
const search = async () => {
  searchBody.value.pageNum = '1';
  searchBody.value.pageSize = '5';
  list()
}
const reset=async ()=>{
  searchBody.value.storageId='';
  searchBody.value.searchContext='';
  searchBody.value.goodstypeId='';
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

</script>

<template>
  <div>
    <div style="margin: 0 0 10px 5px">
      <el-form>
        <el-form-item>
          <el-input style="width: 20%;margin-top: 15px" v-model="searchBody.searchContext" placeholder="请输入货物名"></el-input>
        </el-form-item>
        <!--下拉框1.货物分类的下拉框。2.仓库的下拉框-->
        <!--下拉框1-->
        <el-form-item style="width: 20%;float: left" >
          <el-select placeholder="货物类别" v-model="searchBody.goodstypeId">
            <el-option v-for="g in goodstypeList" :key="g.id" :label="g.name" :value="g.id"/>
          </el-select>
        </el-form-item>

        <!--下拉框2-->
        <el-form-item style="width: 20%;float: left">
          <el-select placeholder="所属仓库" v-model="searchBody.storageId">
            <el-option v-for="s in storageList" :key="s.id" :label="s.name" :value="s.id"/>
          </el-select>
        </el-form-item>

        <el-form-item >
        <el-button type="primary" style="margin-left: 5px" @click="search()">搜索</el-button>
          <el-button type="info" style="margin-left: 5px" @click="reset()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--列表-->
    <el-scrollbar>
      <el-table :data="tableData">
        <el-table-column prop="id" label="ID"/>
        <el-table-column prop="goodsName" label="商品名"/>
        <el-table-column prop="storageName" label="仓库"/>
        <el-table-column prop="goodstypeName" label="分类"/>
        <el-table-column prop="adminName" label="操作人"/>
        <el-table-column prop="userName" label="申请人"/>
        <el-table-column prop="adminName" label="操作">
          <template #default="scope">
            <el-tag :type="scope.row.operation==='1'?'primary':'danger'" disable-transitions>
              {{scope.row.operation==='1'?'入库':'出库'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="时间"/>
        <el-table-column prop="remark" label="备注"/>
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