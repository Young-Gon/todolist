<template>
    <div class="container">
        <div class="app-branding">
            <router-link to="/" class="app-title">Spring Social</router-link>
        </div>
        <div class="app-options">
            <nav class="app-nav">
                <ul v-if="this.$store.getters.authenticated">
                    <li>
                        <router-link to="/profile">Profile</router-link>
                    </li>
                    <li>
                        <router-link to="/todolist/list">TODO List</router-link>
                    </li>
                    <li>
                        <a @click="logout">Logout</a>
                    </li>
                </ul>
                <ul v-else>
                    <li>
                        <router-link to="/login">Login</router-link>
                    </li>
                    <li>
                        <router-link to="/signup">Signup</router-link>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</template>

<script>
    export default {
        name: "aHeader",
        methods: {
            logout() {
                this.$emit('logout');
            },
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

</style>