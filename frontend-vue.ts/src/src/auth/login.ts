import {Component, Vue} from "vue-property-decorator"
import Axios from "@/lib/axios.custom"

@Component
export default class Login extends Vue {

	public static getOauthUrl(platform: string): string {
		return `${process.env.VUE_APP_API}/oauth2/authorize/${platform}?redirect_uri=${process.env.VUE_APP_ORIGIN}${process.env.VUE_APP_OAUTH2_REDIRECT_URI}`
	}

	public GOOGLE_AUTH_URL: string = Login.getOauthUrl("google")
	public FACEBOOK_AUTH_URL: string = Login.getOauthUrl("facebook")
	public NAVER_AUTH_URL: string = Login.getOauthUrl("naver")
	public KAKAO_AUTH_URL: string = Login.getOauthUrl("kakao")

	public user: {
		email: string,
		password: string,
	} = {
		email: "",
		password: "",
	}

	public async login() {
		try {
			const response = await Axios.post("/auth/login", this.user)

			this.$store.commit("setToken", response.data.accessToken)
			this.$store.dispatch("getUserDetail")
			this.$router.replace("/profile")

			this.$notify({
				group: "noti",
				type: "success",
				duration: 6000,
				title: "로그인 성공",
			})
		} catch (err) {
			this.$notify({
				group: "noti",
				type: "error",
				duration: 6000,
				title: err.response.state,
				text: err.response.data.message,
			})
		}
	}
}
