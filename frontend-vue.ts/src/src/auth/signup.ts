import {Component, Vue} from "vue-property-decorator"
import Axios from "@/lib/axios.custom"
import Login from "@/src/auth/login"

@Component
export default class Signup extends Vue {

	public GOOGLE_AUTH_URL: string = Login.getOauthUrl("google")
	public FACEBOOK_AUTH_URL: string = Login.getOauthUrl("facebook")
	public NAVER_AUTH_URL: string = Login.getOauthUrl("naver")
	public KAKAO_AUTH_URL: string = Login.getOauthUrl("kakao")

	public user: {
		name: string,
		email: string,
		password: string,
	} = {
		name: "",
		email: "",
		password: "",
	}

	public async signup() {
		try {
			const response = await Axios.post("/auth/signup", this.user);

			this.$notify({
				group: "noti",
				type: "success",
				duration: 6000,
				title: "회원가입 성공",
			})
			this.$router.push("/login");
		} catch (err) {
			this.$notify({
				group: "noti",
				type: "error",
				duration: 6000,
				title: err.response.state,
				text: err.response.data.message,
			});
		}
	}
}
