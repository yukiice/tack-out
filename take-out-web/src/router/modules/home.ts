import type { RouteRecordRaw } from 'vue-router'

export default {
  path: '/',
  name: 'Home',
  component: () => import('@/views/Home/index'),
  meta: { requiresAuth: false, icon: '', title: '主页' }
} as RouteRecordRaw