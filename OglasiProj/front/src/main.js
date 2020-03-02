import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import 'bootstrap'; 
import 'bootstrap/dist/css/bootstrap.min.css';

import Toasted from 'vue-toasted'

Vue.use(Toasted)

new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store
})
