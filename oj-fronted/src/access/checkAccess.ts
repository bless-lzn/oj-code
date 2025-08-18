//进行权限检查
import ACCESS_ENUM from '@/access/accessEnum.ts'
import { UserLoginUserStore } from '@/stores/userLoginUserStore.ts'

//显示
const checkAccess = (loginUser: any, needAccess = ACCESS_ENUM.NOT_LOGIN) => {
  //如果无权限
  // console.log(`为负值之前方法内部${loginUser.userRole},需要的${needAccess}`)
  const loginUserAccess = loginUser?.userRole?? ACCESS_ENUM.NOT_LOGIN

  // console.log(`方法内部${loginUserAccess},需要的${needAccess}`)

  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true
  }
  //如果登录去除未登录
  if (needAccess === ACCESS_ENUM.USER) {
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false
    }
  }
  if (needAccess === ACCESS_ENUM.ADMIN) {
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false
    }
  }
  console.log('loginUserAccess,needAccess', loginUserAccess, needAccess)
  return true
}
export default checkAccess