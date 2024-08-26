import request from "@/utils/request.js"

export const listServer= async (pageNum,pageSize)=>{
   return await request.get("/goodsType?pageNum="+pageNum.value+"&pageSize="+pageSize.value);
}
export const searchServer=async (searchContext,pageNum,pageSize)=>{
   return await request.get("/goodsType/search?searchContext="+searchContext.value+"&pageNum="+pageNum.value+"&pageSize="+pageSize.value);
}
export const addServer= async (item)=>{
   await request.post("/goodsType",item.value);
}
export const editServer=async (item)=>{
   await request.put("/goodsType",item.value);
}
export const delServer =async (id)=>{
   await request.delete("/goodsType?id="+id);
}

export const listGoodsTypeServer=async ()=>{
   return await request.get("/goodsType/listAll");
}