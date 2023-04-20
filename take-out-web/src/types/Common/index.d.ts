import { RouteRecord } from "vue-router";

declare module CommonType{
    export type RouterListFn = () => RouteRecord[]
}