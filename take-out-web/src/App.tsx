import {defineComponent} from "vue"
import LoginCop from "@/components/Login/index"
export default defineComponent({
    name:"App",
    setup(props,ctx){
        return ()=>(
           <LoginCop />
        )
    }
})