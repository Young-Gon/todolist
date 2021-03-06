import Vue from 'vue';
import Router from 'vue-router';
import auth from '../libs/authentication';
import Home from '../components/home';
import Profile from '../components/profile';
import NotFound from '../components/notFound';
import Login from '../components/user/login';
import Signup from '../components/user/signup';
import TodoList from '../components/todolist/todoList';
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
      path: '/profile',
      name: 'Profile',
      component: Profile,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/oauth2/redirect',
      name: 'Oauth2redirect',
      component: Oauth2redirect,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/todolist/list',
      name: 'TodoList',
      component: TodoList,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '*',
      name: '/NotFound',
      component: NotFound
    }
  ],
});
