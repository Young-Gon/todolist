import store from "@/store";
import Vue from "vue"
import {Route} from "vue-router"

type Next = (path?: string) => void

export const isAuthenticated = (to: Route, from: Route, next: Next) => {
	if (store.state.authenticated === null) {
		Vue.notify({
			group: "noti",
			type: "error",
			text: "접근권한이 없습니다",
		});
		next("/")
	}
	next();
}
