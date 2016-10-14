// VUE
import Vue from 'vue';
import Router from 'vue-router'
import VueResource from 'vue-resource'
import vueForm from 'vue-form'

// PAGES
import App from './App';
import LoginView from './pages/LoginView'
import MainView from './pages/MainView'
import DashboardView from './pages/DashboardView'
import DevicesView from './pages/DevicesView'
import ProductsView from './pages/ProductsView'
import SettingView from './pages/SettingView'
import PageNotFound from './pages/PageNotFound'

Vue.use(VueResource)
Vue.use(Router)
Vue.use(vueForm);
Vue.http.options.emulateJSON = true
Vue.http.options.root = 'http://boss.sport.net'

var router = new Router()

router.map({
  '/dashboard': {
    component : DashboardView
  },
  '/devices':{
    component : DevicesView
  },

  '/products':{
    component : ProductsView
  },
  '/setting':{
    component : SettingView
  },
  //EORROR pages
  '/404':{
    component: PageNotFound
  }
})

router.beforeEach(function() {
  window.scrollTo(0, 0)

})

router.alias({
  '/': '/dashboard',
  '/404': '/home/404',

})

router.redirect({
  '*':'/404'
})


router.start(App, '#app')
