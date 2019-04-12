import {Component, Prop, Vue} from "vue-property-decorator";
import {Survey} from "@/response/survey"

@Component
export default class SurveyCard extends Vue {

	@Prop()
	private survey!: Survey

	public async onClick() {
		this.$router.push({name: "Survey", params: {surveyId: `${this.survey.id}`}})
	}
}
