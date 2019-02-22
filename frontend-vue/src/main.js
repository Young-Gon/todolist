import Vue from 'vue'
import router from './router';
import store from './store';
import App from './App.vue';
import Notifications from 'vue-notification';
import axios from './libs/axios.custom'

Vue.use(Notifications);
Vue.prototype.axios = axios;
Vue.config.productionTip = false;

const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');

export default app;
