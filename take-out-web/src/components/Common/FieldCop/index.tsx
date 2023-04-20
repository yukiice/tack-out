import { CellGroup, Field } from 'vant';
import { defineComponent } from 'vue';
const FieldCop = defineComponent({
    name: "FieldCop",
    setup(props, ctx) {
        return () => (
            <CellGroup inset>
                <Field
                modelValue="2"
                    name="用户名"
                    label="用户名"
                    placeholder="用户名" />
            </CellGroup>
        )
    }
})

export default FieldCop;