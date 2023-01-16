import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
// for docker deployment:
Vue.prototype.$apiUrl = 'https://localhost:443/api'
// for local deployment (entwicklung)
// Vue.prototype.$apiUrl = 'http://localhost:8080/api'

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
