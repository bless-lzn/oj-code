import HomeView from '@/views/HomeView.vue'
import AdminView from '@/views/AdminView.vue'
import NoAuth from '@/views/NoAuth.vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'
import UserLayout from '@/layouts/UserLayout.vue'
import UserLoginView from '@/views/user/UserLoginView.vue'
import UserRegisterView from '@/views/user/UserRegisterView.vue'
import AddQuestionView from '@/views/question/AddQuestionView.vue'
import ManageQuestionView from '@/views/question/ManageQuestionView.vue'

export const routes = [
  {
    path: '/user',
    name: '用户',
    component: UserLayout,
    meta: {
      hideInMenu: true
    },
    children: [
      {
        path: '/user/login',
        name: '用户登录',
        component: UserLoginView
      },
      {
        path: '/user/register',
        name: '用户注册',
        component: UserRegisterView
      }
    ]
  },
  {
    path: '/add/question',
    name: '创建题目',
    component: AddQuestionView,
    // meta: {
    //   access: ACCESS_ENUM.ADMIN
    // }
  },
  {
    path: '/',
    name: '浏览题目',
    component: HomeView
    // meta: {
    //   access: ACCESS_ENUM.USER
    // }
  },
  {
    path: '/manage/question',
    name: '管理题目',
    component: ManageQuestionView
    // meta: {
    //   access: ACCESS_ENUM.USER
    // }
  },
  {
    path: '/noAuth',
    name: 'auth',
    component: NoAuth,
    meta: {
      hideInMenu: true
    }

  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
    // meta: {
    //   access: ACCESS_ENUM.ADMIN
    // }
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (About.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/AboutView.vue'),
    // meta: {
    //   access: ACCESS_ENUM.USER
    // }
  }
]