import { defineComponent} from "vue";
import LoginCop from "@/components/Login";

const LoginPage  =defineComponent({
    name:'LoginPage',
    setup(props,ctx){
        return ()=>(
            <LoginCop />
        )
    }
})

export default LoginPage;