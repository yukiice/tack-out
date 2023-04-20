import { CommonType } from "@/types/Common";
import type { RouteRecord } from "vue-router";
export const InitRouterList: CommonType.RouterListFn = () => {
    const routesList: RouteRecord[] = []
    const modules: Object = import.meta.glob("./modules/*.ts", { eager: true })
    Object.keys(modules).forEach((key: string) => {
        modules[key].default && routesList.push(modules[key].default)
    })
    return routesList
};