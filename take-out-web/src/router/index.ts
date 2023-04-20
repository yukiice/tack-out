import type { App } from "vue";
import { createRouter,createWebHashHistory } from "vue-router";
import {InitRouterList} from "./routes";
console.log(InitRouterList())
export const router = createRouter({
    history:createWebHashHistory(),
    routes:InitRouterList(),
    scrollBehavior: () => ({ left: 0, top: 0 })
})

export const setUpRouter = (app:App<Element>)=>{
    app.use(router);   
}



