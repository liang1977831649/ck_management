import request from "@/utils/request.js";
export const menuServer=async (roleId)=>{
    return await request.get("/menu/getMenu/"+roleId);
}