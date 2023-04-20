import { FieldType } from "vant";
import { PropType } from "vue";

const FieldCopProps = () => ({
    name: {
        type:String,
        default:'',
        description:'名称，作为提交表单时的标识符',
    },
    label: {
        type:String,
        default:'',
        description:'输入框左侧文本',
    },
    placeholder: {
        type:String,
        default:'',
        description:'输入框占位提示文字',
    },
    modelValue:{
        type: String || Number,
        default:'',
        description:'当前输入的值',
    },,
    type:{
        type:String as PropType<FieldType>,
        default: "text"
    },
    maxlength:String || Number,
    disabled:Boolean,
    readonly:Boolean,
    colon:Boolean,
    required:Boolean,
    center:Boolean,
    clearable:Boolean
})