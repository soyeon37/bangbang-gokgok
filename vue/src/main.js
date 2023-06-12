import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Flicking from "@egjs/vue-flicking";
import "@egjs/vue-flicking/dist/flicking.css";

import draggable from "vuedraggable";
import { BootstrapVueIcons } from 'bootstrap-vue'

Vue.use(BootstrapVueIcons)
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
Vue.use(Flicking);
Vue.use(draggable);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
