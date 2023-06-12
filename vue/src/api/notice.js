import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(param, success, fail) {
  api.get(`/notice/list`, { params: param }).then(success).catch(fail);
}

function writeNotice(article, success, fail) {
  api.post(`/notice/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getNotice(articleno, success, fail) {
  api.get(`/notice/${articleno}`).then(success).catch(fail);
}

function modifyNotice(article, success, fail) {
  api.put(`/notice/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteNotice(articleno, success, fail) {
  api.delete(`/notice/delete/${articleno}`).then(success).catch(fail);
}

export { listArticle, writeNotice, getNotice, modifyNotice, deleteNotice };
