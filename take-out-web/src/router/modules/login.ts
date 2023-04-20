import type { RouteRecordRaw } from 'vue-router'

export default {
  path: '/login',
  name: 'Login',
  component: () => import('@/views/Login'),
  meta: { requiresAuth: false, icon: '', title: '登录' }
} as RouteRecordRaw 