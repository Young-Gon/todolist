import Vue from "vue"
import Vuex from "vuex"
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators"
import {User} from "@/response/user"
import Axios, {setHeader} from "@/lib/axios.custom"

Vue.use(Vuex)

const debug = process.env.VUE_APP_ENV === "development"

@Module
class UserState extends VuexModule {
	private stateAuthenticated = false
	private stateToken: string | null = null
	private stateCurrentUser: User | null = null

	get token(): string | null {
		return this.stateToken
	}

	@Mutation
	public setToken(token: string | null) {
		this.stateToken = token
		localStorage.ACCESS_TOKEN = token
		setHeader(token)
	}

	get authenticated(): boolean {
		return this.stateAuthenticated
	}

	@Mutation
	public setUserDetail(user: User | null) {
		this.stateCurrentUser = user
		this.stateAuthenticated = user !== null
	}

	@Action({commit: "setUserDetail"})
	public async getUserDetail() {
		if (this.stateToken == null || this.stateToken === "null" || this.stateToken === "") {
			return
		}

		try {
			const response = await Axios.get(`/user/me`)
			return response.data as User
		} catch (e) {
			Vue.notify({
				group: "noti",
				type: "error",
				duration: 6000,
				title: e.response.state,
				text: e.response.data.message,
			})
		}
	}

	get user(): User | null {
		return this.stateCurrentUser
	}
}

export default new Vuex.Store({
	modules: {UserState},
	plugins: [(store) => {
		const { ACCESS_TOKEN } = localStorage
		if (ACCESS_TOKEN) {
			store.commit("setToken", ACCESS_TOKEN)
		}
	}],
	strict: debug,
})
