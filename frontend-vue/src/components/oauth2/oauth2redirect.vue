<template>
    <div>

    </div>
</template>

<script>
export default {
    name: "oauth2redirect",
    created() {
        console.log("create oauth2redirect");
        const token = this.$route.query.token;
        if (token) {
            this.$store.commit('setToken', token);

            //this.$router.push("/profile")
            // push를 사용할 경우 history에 남아서 back을 누를 경우 이 페이지로 돌아올수 있다
            this.$emit('getUserDetails');
            this.$router.replace('/profile')
        }
        else {
            this.$router.replace("/")
        }
    }
}
</script>

<style scoped>

</style>