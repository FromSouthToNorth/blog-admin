import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/ruoyi'

// 查询博客类别
export function listBlog(query) {
  return request({
    url: '/system/blog/list',
    method: 'get',
    params: query
  })
}

// 查询博客详情
export function getBlog(blogId) {
  return request({
    url: '/system/blog/' + praseStrEmpty(blogId),
    method: 'get'
  })
}

// 新增博客
export function addBlog(data) {
  return request({
    url: '/system/blog',
    method: 'post',
    data: data
  })
}

// 修改博客
export function updateBlog(data) {
  return request({
    url: '/system/blog',
    method: 'put',
    data: data
  })
}

// 删除博客
export function delBlog(blogId) {
  return request({
    url: '/system/blog/' + blogId,
    method: 'delete'
  })
}

// 修改博客发布状态
export function changeBlogPublishedStatus(blogId, published) {
  const data = {
    blogId,
    published
  }
  return request({
    url: '/system/blog/changePublished',
    method: 'put',
    data: data
  })
}

// 获取博客内容
export function getContent(blogId) {
  return request({
    url: '/system/blog/getcontent/' + blogId,
    method: 'get'
  })
}
