<template>
    <div class="container">
        <img src='../assets/logo.png' alt="Vue.js LOGO" />
        <h1 class="home-title">Spring Boot Vue.js OAuth2 Social Login Demo</h1>
    </div>
</template>

<script>
    export default {
        name: "home"
    }
</script>

<style scoped>
    .container {
        text-align: center;
        min-height: calc(100vh - 60px);
        overflow: auto;
        position: relative;
        padding-top: 60px;
    }
    .home-title {
        font-size: 25px;
        font-weight: 400;
        color: rgba(0,0,0,0.70);
        margin-top: 50px;
    }

</style>