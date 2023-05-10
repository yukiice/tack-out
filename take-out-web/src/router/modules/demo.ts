import type { RouteRecordRaw } from 'vue-router'

export default {
    path: '/demo',
    name: 'Demo',
    component: () => import('@/views/Demo/index'),
    meta: { requiresAuth: false, icon: '', title: "测试" }
} as RouteRecordRaw