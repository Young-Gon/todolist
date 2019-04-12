<template>
	<v-container
			id="lateral"
			style="max-width: 1200px; margin: auto;"
			class="py-5"
			grid-list-xl
	>
		<v-layout wrap>
			<v-flex
					v-for="(survey, index) in surveyList"
					:key="index"
					xs4
			>
				<survey-card :survey="survey"/>
			</v-flex>
		</v-layout>
		<v-btn
				dark
				fab
				bottom
				right
				fixed
				color="pink"
				@click="onClickFloatingActionButton()"
		>
			<v-icon>add</v-icon>
		</v-btn>
	</v-container>
</template>

<script>
	import MySurveyList from "../src/my-survey-list"

	export default MySurveyList
</script>

<style scoped>
	#lateral .v-btn--floating {
		margin: 0 0 16px 16px;
	}
</style>
