import {defineComponent, onMounted} from "vue";
import { Sunburst } from '@antv/g2plot';
const DemoPage = defineComponent({
    name:"DemoPage",
    setup(props,ctx){
        onMounted(()=>{

            fetch('https://gw.alipayobjects.com/os/antfincdn/ryp44nvUYZ/coffee.json')
                .then((data) => data.json())
                .then((data) => {
                    const plot = new Sunburst('container', {
                        data,
                        innerRadius: 0.3,
                        tooltip:{
                            fields:['name'],
                            position:'right'
                        },
                        interactions: [{ type: 'element-active' }],
                    });
                    plot.render();
                });

        })
        return ()=>(
            <div class="container" id="container">
            </div>
        )
    }
})

export default DemoPage;