import HomeView from '@/views/HomeView.vue'
import AdminView from '@/views/AdminView.vue'
import NoAuth from '@/views/NoAuth.vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'
import UserLayout from '@/layouts/UserLayout.vue'
import UserLoginView from '@/views/user/UserLoginView.vue'
import UserRegisterView from '@/views/user/UserRegisterView.vue'
import AddQuestionView from '@/views/question/AddQuestionView.vue'
import ManageQuestionView from '@/views/question/ManageQuestionView.vue'
import QuestionsView from '@/views/question/QuestionsView.vue'
import ViewQuestionView from '@/views/question/ViewQuestionView.vue'

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
    path: '/questions',
    name: '浏览题目',
    component: QuestionsView,
  },
  {
    path: '/add/question',
    name: '创建题目',
    component: AddQuestionView,
    meta: {
      access: ACCESS_ENUM.ADMIN
    }
  },
  {
    path: '/',
    name: '主页',
    component: QuestionsView,
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
    path: '/view/question/:id',
    name: '在线做题',
    props: true,
    component: ViewQuestionView,
    meta: {
      access: ACCESS_ENUM.USER
    }
  },
  {
    path: '/update/question',
    name: '修改题目',
    component: AddQuestionView,
    meta: {
      hideInMenu: true
    }
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
    component: HomeView
    // meta: {
    //   access: ACCESS_ENUM.ADMIN
    // }
  }
]