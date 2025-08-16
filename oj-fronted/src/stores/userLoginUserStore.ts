import { defineStore } from 'pinia'
import { ref } from 'vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'

export const UserLoginUserStore = defineStore('loginUser', () => {
  const loginUser = ref({
    userName: '未登录',
    userRole:ACCESS_ENUM.NOT_LOGIN
  })
  /**
   * 远程获取登录用户信息
   */
  async function fetchLoginUser() {
    // const res = await getLoginUserUsingGet()
    // if (res.data.code === 0 && res.data.data) {
    //   loginUser.value = res.data.data
    // }
    // // 测试用户登录，3 秒后自动登录
    setTimeout(() => {
      loginUser.value = { userName: '测试用户', userRole: ACCESS_ENUM.USER }
    }, 3000)
  }

  /**
   * 设置登录用户
   * @param newLoginUser
   */
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }
  return {
    loginUser,
    fetchLoginUser,
    setLoginUser,
  }
})