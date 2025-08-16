import HomeView from '@/views/HomeView.vue'
import AdminView from '@/views/AdminView.vue'
import NoAuth from '@/views/NoAuth.vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'

export  const routes= [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/noAuth',
    name: 'auth',
    component: NoAuth,
    meta: {
      hideInMenu: true,
    }

  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    }
  },
  {
    path: '/about',
    name: 'about',

    // route level code-splitting
    // this generates a separate chunk (About.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/AboutView.vue'),

  },
]