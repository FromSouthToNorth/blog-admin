import request from '@/utils/request'

// 查询随笔列表
export function listGossip(query) {
  return request({
    url: '/system/gossip/list',
    method: 'get',
    params: query
  })
}

// 查询随笔详细
export function getGossip(gossipId) {
  return request({
    url: '/system/gossip/' + gossipId,
    method: 'get'
  })
}

// 新增随笔
export function addGossip(data) {
  return request({
    url: '/system/gossip',
    method: 'post',
    data: data
  })
}

// 修改随笔
export function updateGossip(data) {
  return request({
    url: '/system/gossip',
    method: 'put',
    data: data
  })
}

// 删除随笔
export function delGossip(gossipId) {
  return request({
    url: '/system/gossip/' + gossipId,
    method: 'delete'
  })
}

// 导出随笔
export function exportGossip(query) {
  return request({
    url: '/system/gossip/export',
    method: 'get',
    params: query
  })
}