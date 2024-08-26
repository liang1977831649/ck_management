import request from "@/utils/request.js"

export const searchServer = async (searchBody) => {
    return await request.get("/record/list", {
        params: {
            pageNum: searchBody.value.pageNum,
            pageSize: searchBody.value.pageSize,
            searchContext: searchBody.value.searchContext,
            storageId: searchBody.value.storageId,
            goodstypeId: searchBody.value.goodstypeId
        }
    });
}

export const saveRecordServer=async (inOutModel)=>{
    await request.post("/record",inOutModel.value)
}
