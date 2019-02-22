import Vue from 'vue';
import Router from 'vue-router';
import auth from '../libs/authentication';
import Home from '../components/home';
import Profile from '../components/profile';
import NotFound from '../components/notFound';
import Login from '../components/user/login';
import Signup from '../components/user/signup';
import Oauth2redirect from '../components/oauth2/oauth2redirect';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup,
    },
    {
      path: '/oauth2/redirect',
      name: 'Oauth2redirect',
      component: Oauth2redirect
    },
    {
      path: '*',
      name: '/NotFound',
      component: NotFound
    }
  ],
});
