import router from '@/router'
import { UserLoginUserStore } from '@/stores/userLoginUserStore.ts'
import ACCESS_ENUM from './accessEnum.ts'
import checkAccess from './checkAccess.ts'
import { Message } from '@arco-design/web-vue'

// router.beforeEach(async (to, from, next) => {
//   const loginUserStore = UserLoginUserStore()
//   await UserLoginUserStore().fetchLoginUser()
//   let loginUser = loginUserStore.loginUser
//   //如果之前没有登录过自动登录
//   if (!loginUser || !loginUser.userRole) {
//     await UserLoginUserStore().fetchLoginUser()
//     loginUser = loginUserStore.loginUser
//     console.log('loginUser', loginUser)
//   }
//   console.log('登陆用户信息1', loginUser)
//   const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN
//   // 要跳转的页面必须要登陆
//   if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
//     // 如果没登陆，跳转到登录页面
//     if (!loginUser || !loginUser.userRole || loginUser.userRole === ACCESS_ENUM.NOT_LOGIN) {
//       next(`/user/login?redirect=${to.fullPath}`)
//       return
//     }
//     // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
//     if (!checkAccess(loginUser, needAccess)) {
//       next('/noAuth')
//       return
//     }
//   }
//   next()
// })
// 是否为首次获取登录用户
let firstFetchLoginUser = true
router.beforeEach(async (to, from, next) => {
  const loginUserStore = UserLoginUserStore()
  let loginUser = loginUserStore.loginUser
  // 确保页面刷新时，首次加载时，能等待后端返回用户信息后再校验权限
  if (firstFetchLoginUser) {
    await loginUserStore.fetchLoginUser()
    loginUser = loginUserStore.loginUser
    firstFetchLoginUser = false
  }
  // 可以自己定义权限校验逻辑，比如管理员才能访问 /admin 开头的页面
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN
//   // 要跳转的页面必须要登陆
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // 如果没登陆，跳转到登录页面
    if (!loginUser || !loginUser.userRole || loginUser.userRole === ACCESS_ENUM.NOT_LOGIN) {
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
    // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      next('/noAuth')
      return
    }
  }
  next()
})
