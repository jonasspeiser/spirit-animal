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
        name: "Login"
      }
    },
    {
      path: "/login",
      name: "Login",
      component: () => import( "@/views/Login.vue")
    },
    {
      path: "/register",
      name: "Registrierung",
      component: () => import( "@/views/Register.vue")
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
    },
    {
      path: "/inserate",
      name: "Meine Inserate",
      component: ()=> import("@/views/MeineInserate.vue")
    },
    {
      path: "/profil",
      name: "Mein Profil",
      component: ()=> import("@/views/MeinProfil.vue")
    },
    {
      path: "/kaeufe",
      name: "Meine Käufe",
      component: ()=> import("@/views/MeineKäufe.vue")
    },
    {
      path: "/anfragen",
      name: "Anfragencenter",
      component: ()=> import("@/views/Anfragencenter.vue")
    },
    {
      path: "/inserataendern/:inseratID",
      name: "Inserat Ändern",
      component: ()=> import("@/views/InseratÄndern.vue"),
      props: true
    },
    {
      path: "/adminspace",
      name: "AdminSpace",
      component: ()=> import("@/views/AdminSpace.vue")
    }
  ]
})

