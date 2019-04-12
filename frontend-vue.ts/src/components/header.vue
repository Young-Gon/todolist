<template>
	<v-toolbar >
		<v-toolbar-title >
			<router-link to="/">Analysum</router-link>
		</v-toolbar-title>
		<v-spacer></v-spacer>
		<v-toolbar-items v-if="this.$store.getters.authenticated">
			<v-flex align-center layout py-2>
			<router-link v-ripple class="toolbar-items" to="/profile">Profile</router-link>
			<router-link v-ripple class="toolbar-items" to="/mySurveyList">Survey</router-link>
			<v-btn flat class="toolbar-items" @click="logout">Logout</v-btn>
			</v-flex>
		</v-toolbar-items>
		<v-toolbar-items v-else>
			<v-flex align-center layout py-2>
			<router-link v-ripple class="toolbar-items" to="/login">Login</router-link>
			<router-link v-ripple class="toolbar-items" to="/signup">Signup</router-link>
			</v-flex>
		</v-toolbar-items>
	</v-toolbar>
</template>

<script lang="ts">
	import {Component, Vue} from "vue-property-decorator"

	@Component
export default class Header extends Vue {
	public logout() {
		this.$emit("logout")
	}
}
</script>

<style scoped>

	.container {
		height: 60px;
		position: relative;
		z-index: 10;
	}

	.app-title {
		line-height: 60px;
		vertical-align: middle;
		font-size: 1.35em;
	}

	.app-branding {
		float: left;
	}

	.app-options {
		float: right;
	}

	.app-nav ul {
		list-style-position: unset;
		margin: 0;
		padding: 0;
	}

	.app-nav ul li {
		list-style-type: none;
		display: inline-block;
	}

	.app-nav ul li a {
		display: inline-block;
		line-height: 60px;
		vertical-align: middle;
		padding-left: 15px;
		padding-right: 15px;
		color: rgba(0,0,0,0.65);
	}

	.app-nav ul li a:hover {
		color: #0f96f6;
	}

	.app-nav ul li a.active {
		color: #0f96f6;
	}
	.v-btn {
		color: #216cf3 !important;
	}

	.v-btn--active:before,
	.v-btn:focus:before,
	.v-btn:hover:before {
		color: #40a9ff !important;
		background-color: transparent;
	}

</style>
