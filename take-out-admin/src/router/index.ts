import {createRouter, createWebHashHistory, Router, RouteRecordRaw} from "vue-router";
import Login from "@/pages/Login/index.vue"
const routes:Array<RouteRecordRaw> = [
    {
        path:'/login',
        name:'Login',
        components:Login,
    }
]

const router:Router = createRouter({
    history:createWebHashHistory(),
    routes
})

router.beforeEach((to,from,next)=>{
    next()
})

export default router;
