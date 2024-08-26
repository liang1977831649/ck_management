import {ElMessage} from "element-plus";
import axios from "axios";
const baseURL="http://127.0.0.1:8080/"
var instance = axios.create({baseURL});

instance.interceptors.response.use(
    result=>{
        if(result.data.code===0){
            // 如果返回结果成功
            return result.data;
        }else{
            ElMessage.error(result.data.message?result.data.message:"服务器异常")
            return Promise.reject(result.data);
        }
    },
    error => {
        ElMessage.error("服务器异常!!!");
        return Promise.reject(error);
    }
)
export default instance;