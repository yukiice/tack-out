import { createApp } from 'vue'
import './style.css'
import App from './App'
import 'vant/lib/index.css';
import { setUpRouter } from './router';
function bootstrap(){
    const app = createApp(App)
    setUpRouter(app)
    app.mount("#app")
}
bootstrap();
