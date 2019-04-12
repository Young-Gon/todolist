<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
	<v-container
			id="lateral"
			style="max-width: 1200px; margin: auto;"
			class="py-5"
			grid-list-xl
	>
		<v-card>
			<v-img
					class="white--text"
					height="200px"
					src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
			>
			<v-container class="text-xs-right">
					<v-btn fab dark color="teal">
						<v-icon dark>image</v-icon>
					</v-btn>
					<v-btn fab dark color="red">
						<v-icon dark>close</v-icon>
					</v-btn>
			</v-container>
			</v-img>
			<v-card-title>
				<v-flex xs12>
					<v-text-field
							v-model="survey.title"
							label="제목"
							hint="설문지의 제목입니다"
							color="white"
					>
					</v-text-field>
				</v-flex>
			</v-card-title>
			<v-card-text>
				<v-flex xs12>
					<v-text-field
							v-model="survey.description"
							label="설명"
							hint="설문 참가자를 위해 이 설문지의 상세 내용을 적어 넣으세요"
					>
					</v-text-field>
				</v-flex>
			</v-card-text>
			<v-card-actions>
				<div class="container text-xs-right">
					<v-tooltip top>
						<template v-slot:activator="{ on }">
							<v-btn color="primary" fab dark small v-on="on" @click="onAddQuestion(1)">
								<v-icon dark>add</v-icon>
							</v-btn>
						</template>
						<span>질문 추가</span>
					</v-tooltip>
				</div>
			</v-card-actions>
		</v-card>

			<!--<v-card style="margin-top: 30px;" v-for>
				<v-img
						src="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
						aspect-ratio="2.75"
				></v-img>

				<v-card-title primary-title>
					<div>
						<h3 class="headline mb-0">Kangaroo Valley Safari</h3>
						<div> {{ card_text }} </div>
					</div>
				</v-card-title>

				<v-card-actions>
					<v-btn flat color="orange">Share</v-btn>
					<v-btn flat color="orange">Explore</v-btn>
				</v-card-actions>
			</v-card>-->
			<v-flex
					v-for="(question, index) in survey.questions"
					:key="index"
					xs4
			>
				<v-card>
					<div>{{question.no}}</div>
					<v-card-title primary-title>
						<div>
							<h3 class="headline mb-0">
								<v-text-field
										v-model="question.content"
										label="질문"
										hint="질문 내용을 입력 하세요"
								></v-text-field>
							</h3>
						</div>
					</v-card-title>
				</v-card>
			</v-flex>
	</v-container>
</template>

<script>
	import Survey from "../src/survey"

	export default Survey
</script>

<style scoped lang="scss">
	.v-text-field input {
	font-size: 20px;
}
	.theme--light.v-input:not(.v-input--is-disabled) input{
		color:#fff;
	}

	/*.theme--light.v-input:not(.v-input--is-disabled) input,
	.theme--light.v-input:not(.v-input--is-disabled) textarea {
		color: #ffffff !important;
		font-size: 24px;
	}

	.v-text-field__slot {
		input {
			color: #ffffff !important;
		}
	}*/
</style>
