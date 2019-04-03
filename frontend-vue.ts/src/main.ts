import Vue from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import Vuetify from "vuetify"
import Notifications from "vue-notification"
// index.js or main.js
import "vuetify/dist/vuetify.min.css" // Ensure you are using css-loader

Vue.use(Vuetify)
Vue.use(Notifications)
Vue.config.productionTip = false

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount("#app")
