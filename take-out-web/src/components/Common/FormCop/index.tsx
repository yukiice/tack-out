import { Form } from "vant";
import { defineComponent } from "vue";
import FieldCop from "../FieldCop";
const FormCop = defineComponent({
    name:"FromCop",
    setup(props,ctx){
        return ()=>(
            <div>
                <Form>
                    <FieldCop />
                </Form>
            </div>
        )
    }
})

export default FormCop;