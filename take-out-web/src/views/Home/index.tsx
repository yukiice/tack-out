import { defineComponent } from "vue";
import './index.less'
const HomePage = defineComponent({
    name:"HomePage",
    setup(props,ctx){
        return ()=>(
            <div>
                Home
            </div>
        )
    }
})

export default HomePage;