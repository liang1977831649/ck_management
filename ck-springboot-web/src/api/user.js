import request from "@/utils/request.js"

export const listServer= async (pageNum,pageSize,roleId)=>{
   return await request.get("/user?pageNum="+pageNum.value+"&pageSize="+pageSize.value+"&roleId="+roleId.value);
}
export const searchServer=async (searchContext,pageNum,pageSize,roleId)=>{
   return await request.get("/user/search?searchContext="+searchContext.value+"&pageNum="+pageNum.value+"&pageSize="+pageSize.value+"&roleId="+roleId.value);
}
export const addServer= async (item)=>{
   await request.post("/user",item.value);
}
export const editServer=async (item)=>{
   await request.put("/user",item.value);
}
export const delServer =async (id)=>{
   await request.delete("/user?id="+id);
}
export const loginServer=async (loginForm)=>{
   return await request.post("/user/login",loginForm.value);
}