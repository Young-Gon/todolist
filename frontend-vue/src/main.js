import Vue from 'vue'
import router from './router';
import store from './store';
import App from './App.vue';
import Notifications from 'vue-notification';
import axios from './libs/axios.custom'
import Vuetify from 'vuetify'
// index.js or main.js
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader

Vue.use(Vuetify);
Vue.use(Notifications);
Vue.prototype.axios = axios;
Vue.config.productionTip = false;

const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');

export default app;
