import {Component, Vue} from "vue-property-decorator";
import Axios from "@/lib/axios.custom";
import {Survey, SurveyImp} from "@/response/survey";
import {AxiosResponse} from "axios";

@Component
export default class SurveyVue extends Vue {
	public survey: Survey = new SurveyImp();

	public created() {
		this.getSurvey(this.$route.params.surveyId)
	}

	public async onAddQuestion(position: number) {
		try {
			this.survey = (await Axios.get(`/survey/${this.survey.id}/question/${position}`) as AxiosResponse<Survey>).data

			console.log(this.survey)
		} catch (e) {
			console.log(e)
			this.$notify({
				group: "noti",
				type: "error",
				duration: 6000,
				title: e.response.state,
				text: e.response.data.message,
			})
		}
	}

	private async getSurvey(surveyId: string) {
		try {
			this.survey = (await Axios.get(`/survey/${surveyId}`) as AxiosResponse<Survey>).data

			console.log(this.survey)
		} catch (e) {
			console.log(e)
			this.$notify({
				group: "noti",
				type: "error",
				duration: 6000,
				title: e.response.state,
				text: e.response.data.message,
			})
			this.$router.go(-1)
		}
	}
}
