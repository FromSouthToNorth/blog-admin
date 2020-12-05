import request from '@/utils/request'

// 查询博客类型列表
export function listBlogType(query) {
  return request({
    url: '/system/type/list',
    method: 'get',
    params: query
  })
}

// 查询类博客型列表（排除节点）
export function listTypeExcludeChild(typeId) {
  return request({
    url: '/system/type/list/exclude/' + typeId,
    method: 'get'
  })
}

// 查询类型详细
export function getBlogType(typeId) {
  return request({
    url: '/system/type/' + typeId,
    method: 'get'
  })
}

// 查询博客类型下拉树结构
export function treeselect() {
  return request({
    url: '/system/type/treeselect',
    method: 'get'
  })
}

// 新增博客类型
export function addBlogType(data) {
  return request({
    url: '/system/type',
    method: 'post',
    data: data
  })
}

// 修改博客类型信息
export function updateBlogType(data) {
  return request({
    url: '/system/type',
    method: 'put',
    data: data
  })
}

// 删除博客信息
export function delBlogType(typeId) {
  return request({
    url: '/system/type/' + typeId,
    method: 'delete'
  })
}
