import {defineComponent} from "vue"
import './index.less'
import FormCop from "../Common/FormCop"

const LoginCop = defineComponent({
    name: "LoginCop",
    setup(props, ctx) {
        return () => (
            <FormCop />
        )
    }
})

export default LoginCop