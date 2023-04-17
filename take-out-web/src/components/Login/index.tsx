import {defineComponent} from "vue"
import {Button} from "vant";
import './index.less'
const LoginCop =defineComponent({
    name:"LoginCop",
    setup(props,ctx){
        return ()=>(
            <div>
                <Button text="点击" type="primary" />
            </div>
        )
    }
})

export default LoginCop