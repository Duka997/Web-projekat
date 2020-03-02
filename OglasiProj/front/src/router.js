import Vue from 'vue'
import Router from 'vue-router'

import Home from './components/Home'
import Login from './components/Login'
import Register from './components/Register'

import Ad from './components/OpendAd'
import OrderedAds from './components/OrderedAds'
import DeliveredAds from './components/DeliveredAds'
import FavouriteAds from './components/FavouriteAds'
import User from './components/User'
import UserAds from './components/UserAds'
import AddAd from './components/AddAd'

import AdminCategories from './components/AdminCategories'
import AdminUsers from './components/AdminUsers'
import AdminAds from './components/AdminAds'

import SendMessage from './components/SendMessage'
import Inbox from './components/Inbox'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Home
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/oglas/:naziv',
      component: Ad
    },
    {
      path: '/orderedAds',
      component: OrderedAds
    },
    {
      path: '/deliveredAds',
      component: DeliveredAds
    },
    {
      path: '/favouriteAds',
      component: FavouriteAds
    },
    {
      path: '/addAd',
      component: AddAd
    },
    {
      path: '/userAds',
      component: UserAds
    },
    {
      path: '/userProfile/:naziv',
      component: User
    }
    ,
    {
      path: '/userProfile/:naziv/:nazivOglasa',
      component: User
    }
    ,
    {
      path: '/adminCategories',
      component: AdminCategories
    },
    {
      path: '/adminUsers',
      component: AdminUsers
    },
    {
      path: '/adminAds',
      component: AdminAds
    } ,
    {
      path: '/sendMessage',
      component: SendMessage
    },
    {
      path: '/sendMessage/:korisnickoIme/:oglas',
      component: SendMessage
    },
    {
      path: '/sendMessage/:korisnickoIme/',
      component: SendMessage
    },
    {
      path: '/inbox',
      component: Inbox
    }
  ]
})
