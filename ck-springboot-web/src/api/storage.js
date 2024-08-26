import request from "@/utils/request.js"

export const listServer= async (pageNum,pageSize)=>{
   return await request.get("/storage?pageNum="+pageNum.value+"&pageSize="+pageSize.value);
}
export const searchServer=async (searchContext,pageNum,pageSize)=>{
   return await request.get("/storage/search?searchContext="+searchContext.value+"&pageNum="+pageNum.value+"&pageSize="+pageSize.value);
}
export const addServer= async (item)=>{
   await request.post("/storage",item.value);
}
export const editServer=async (item)=>{
   await request.put("/storage",item.value);
}
export const delServer =async (id)=>{
   await request.delete("/storage?id="+id);
}
export const listStorageServer=async ()=>{
   return await request.get("/storage/listAll");
}