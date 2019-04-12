import {Component, Vue} from "vue-property-decorator"
import Axios from "@/lib/axios.custom";
import {AxiosResponse} from "axios";
import {ArrayPage} from "@hallysonh/pageable";
import {Survey} from "@/response/survey"
import SurveyCard from "@/components/survey-card.vue"

@Component({
	components: {
		SurveyCard,
	},
})
export default class MySurveyList extends Vue {
	public pagable: ArrayPage<Survey> | undefined
	public surveyList: Survey[] = [];

	public created() {
		this.getSurvey()
	}

	public async getSurvey() {
		try {
			const query = `page=${this.pagable !== undefined
				? this.pagable.number + 1 : 0}`;

			if (this.pagable !== undefined && this.pagable.last) {
				return;
			}

			const result: AxiosResponse<ArrayPage<Survey>> = await Axios.get(`/survey/my?${query}`)
			console.log(result)
			this.pagable = result.data
			this.surveyList = this.surveyList.concat(this.pagable.content)
			console.log(this.surveyList.length)
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

	public async onClickFloatingActionButton() {
		try {
			const result: AxiosResponse<Survey> = await Axios.post("/survey")
			this.$router.push({ name: "Survey", params: { surveyId: `${result.data.id}`}})
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
}
