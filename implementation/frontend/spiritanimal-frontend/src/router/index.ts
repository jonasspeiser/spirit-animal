import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
//import Login from '../views/Login.vue'
//import SoulSearch from '../views/SoulSearch.vue'

Vue.use(VueRouter)

export default new VueRouter({
  mode: "history",
  routes: [
    {
      path: '/',
      redirect: {
        name: "login"
      }
    },
    {
      path: "/login",
      name: "login",
      component: () => import( "@/views/Login.vue")
    },
    {
      path: "/inserieren",
      name: "Inserieren",
      component: () => import(/* webpackChunkName: "home" */ '@/views/Inserieren.vue')
    },
    {
      path: "/soulsearch",
      name: "Soul Search",
      component: () => import(/* webpackChunkName: "home" */ '@/views/SoulSearch.vue')
    },
    {
      path: "/mydarlings",
      name: "My Darlings",
      component: () => import(/* webpackChunkName: "home" */ '@/views/MyDarlings.vue')
    },
    {
      path: "/apitest",
      name: "apitest",
      component: ()=> import("@/views/APItestview.vue")
    },
    {
      path: "/neuesinserat",
      name: "neues Inserat",
      component: ()=> import("@/views/Inserieren.vue")
    }
  ]
})

// const routes: Array<RouteConfig> = [
//   {
//     path: '/',
//     name: 'home',
//     component: HomeView
//   },
//   {
//     path: '/about',
//     name: 'about',
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
//   }
// ]
//
// const router = new VueRouter({
//   mode: 'history',
//   base: process.env.BASE_URL,
//   routes
// })
//
// export default router
