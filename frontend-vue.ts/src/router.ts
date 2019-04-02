import Vue from "vue"
import Router from "vue-router"
import Home from "./components/home.vue"
import {isAuthenticated} from "@/lib/authentication"

Vue.use(Router)

export default new Router({
	mode: "history",
	base: process.env.BASE_URL,
	routes: [
		{
			path: "/",
			name: "home",
			component: Home,
			beforeEnter: isAuthenticated,
		},
		{
			path: "/login",
			name: "login",
			component: () => import("./components/auth/login.vue"),
		},
		{
			path: '/signup',
			name: 'Signup',
			component: () => import("./components/auth/signup.vue"),
		},
		{
			path: '/profile',
			name: 'Profile',
			component: () => import("./components/profile.vue"),
			beforeEnter: isAuthenticated,
		},
		{
			path: '/oauth2/redirect',
			name: 'Oauth2redirect',
			component: () => import("./components/auth/oauth2redirect.vue"),
			beforeEnter: isAuthenticated,
		},
		{
			path: '*',
			name: '/NotFound',
			component:  () => import("./components/notFound.vue"),
		}
	],
})
