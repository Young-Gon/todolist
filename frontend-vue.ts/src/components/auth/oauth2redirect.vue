<template>
	<div>

	</div>
</template>

<script lang="ts">
	import {Component, Vue} from "vue-property-decorator"

	@Component
export default class Oauth2Redirect extends Vue {
	public created() {
		console.log("create oauth2redirect");
		const token = this.$route.query.token;
		if (token) {
			this.$store.commit("setToken", token);

			// this.$router.push("/profile")
			// push를 사용할 경우 history에 남아서 back을 누를 경우 이 페이지로 돌아올 수 있다
			this.$store.dispatch("getUserDetail")
			this.$router.replace("/profile")
		} else {
			this.$router.replace("/")
		}
	}
}
</script>

<style scoped>

</style>
