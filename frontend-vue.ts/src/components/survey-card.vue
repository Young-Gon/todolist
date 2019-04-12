<template>
	<v-card color="cyan darken-2" class="white--text" hover ripple @click="onClick">
		<v-layout>
			<v-flex xs5>
				<v-img
						src="https://cdn.vuetifyjs.com/images/cards/foster.jpg"
						height="125px"
						contain
				></v-img>
			</v-flex>
			<v-flex xs7>
				<v-card-title primary-title>
					<div>
						<div class="headline" v-if="this.survey.title">{{survey.title}}</div>
						<div class="headline" v-else>제목 없음</div>
						<div>{{survey.description}}</div>
						<div v-if="this.survey.questions">{{survey.questions.length}}</div>
						<div v-else>0</div>
					</div>
				</v-card-title>
			</v-flex>
		</v-layout>
		<v-divider light></v-divider>

		<v-card-actions class="pa-3">
			<v-list-tile-avatar color="grey darken-3">
				<v-img
						class="elevation-6"
						:src="survey.user.imageUrl"
				></v-img>
			</v-list-tile-avatar>

			<v-list-tile-content>
				<v-list-tile-title>{{survey.user.name}}</v-list-tile-title>
			</v-list-tile-content>

			<v-spacer></v-spacer>
		</v-card-actions>
	</v-card>
</template>

<script>
	import SurveyCard from "../src/survey-card"

	export default SurveyCard
</script>

<style scoped>

</style>
