import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
// 创建 axios 实例
let service: AxiosInstance | any;
service = axios.create({
    baseURL: "https://jxzh.worldmaipu.com/", // api 的 base_url
    // baseURL: "http://192.168.1.206:8760/",
    timeout: 50000 // 请求超时时间
});

// request 拦截器 axios 的一些配置
service.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        // @ts-ignore
        return config;
    },
    (error: any) => {
        // Do something with request error
        console.error("error:", error); // for debug
    }
);
// respone 拦截器 axios 的一些配置
service.interceptors.response.use(
    (res: AxiosResponse) => {
        // Some example codes here:
        // code == 0: success
        if (res.status === 200) {
            console.log(res)
            const data = res.data
            if (data.code === 0) {
                return  data
            } else {
            }

        } else {
            return Promise.reject(new Error(res.data.message || "Error"));
        }
    },
    (error: any) => Promise.reject(error)
);

export default service;