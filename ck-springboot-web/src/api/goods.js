import request from "@/utils/request.js"

export const listServer = async (pageNum, pageSize) => {
    return await request.get("/goods?pageNum=" + pageNum.value + "&pageSize=" + pageSize.value);
}
export const searchServer = async (searchBody) => {
    return await request.get("/goods/list", {
        params: {
            pageNum: searchBody.value.pageNum,
            pageSize: searchBody.value.pageSize,
            name: searchBody.value.searchContext,
            storageId: searchBody.value.storageId,
            goodstypeId: searchBody.value.goodstypeId
        }
    });
}
export const addServer = async (item) => {
    await request.post("/goods", item.value);
}
export const editServer = async (item) => {
    await request.put("/goods", item.value);
}
export const delServer = async (id) => {
    await request.delete("/goods?id=" + id);
}