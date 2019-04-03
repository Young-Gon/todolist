<template>
	<div class="signup-container">
		<div class="signup-content">
			<h1 class="signup-title">Signup with SpringSocial</h1>
			<div class="social-signup">
				<a class="btn btn-block social-btn google" v-bind:href="GOOGLE_AUTH_URL">
					<img src="../../assets/google-logo.png" alt="Google" /> Sign up with Google</a>
				<a class="btn btn-block social-btn facebook" v-bind:href="FACEBOOK_AUTH_URL">
					<img src="../../assets/fb-logo.png" alt="Facebook" /> Sign up with Facebook</a>
				<a class="btn btn-block social-btn facebook" v-bind:href="NAVER_AUTH_URL">
					<img src="../../assets/naver-logo.png" alt="Naver" /> Sign up with naver</a>
				<a class="btn btn-block social-btn facebook" v-bind:href="KAKAO_AUTH_URL">
					<img src="../../assets/kakao-logo.png" alt="Kakao" /> Sign up with kakao</a>
			</div>
			<div class="or-separator">
				<span class="or-text">OR</span>
			</div>
			<form @submit.prevent="signup">
				<div class="form-item">
					<input type="text" name="name"
					       class="form-control" placeholder="Name"
					       v-model="user.name"/>
				</div>
				<div class="form-item">
					<input type="email" name="email"
					       class="form-control" placeholder="Email"
					       v-model="user.email"/>
				</div>
				<div class="form-item">
					<input type="password" name="password"
					       class="form-control" placeholder="Password"
					       v-model="user.password"/>
				</div>
				<div class="form-item">
					<button type="submit" class="btn btn-block btn-primary">Sign Up</button>
				</div>
			</form>
			<span class="login-link">Already have an account? <router-link to="/login">Login!</router-link></span>
		</div>
	</div>
</template>

<script lang="ts">
	import Signup from "@/src/auth/signup"

	export default Signup
</script>

<style scoped>
	.signup-container {
		text-align: center;
	}

	.signup-content {
		background: #fff;
		box-shadow: 0 1px 11px rgba(0, 0, 0, 0.27);
		border-radius: 2px;
		width: 500px;
		display: inline-block;
		margin-top: 30px;
		vertical-align: middle;
		position: relative;
		padding: 35px;
	}

	.social-btn {
		margin-bottom: 15px;
		font-weight: 400;
		font-size: 16px;
	}

	.social-btn img {
		height: 32px;
		float: left;
		margin-top: 10px;
	}

	.social-btn.google {
		margin-top: 7px;
	}

	.social-btn.facebook img {
		height: 24px;
		margin-left: 3px;
	}

	.social-btn.github img {
		height: 24px;
		margin-left: 3px;
	}

	.login-link {
		color: rgba(0, 0, 0, 0.65);
		font-size: 14px;
	}

	.signup-title {
		font-size: 1.5em;
		font-weight: 500;
		margin-top: 0;
		margin-bottom: 30px;
		color: rgba(0, 0, 0, 0.65);
	}
</style>
