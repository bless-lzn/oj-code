<template>
  <div id="globalHeader">
    <a-row :wrap=false align="center">
      <a-col flex="160px">
        <div class="title-bar">
          <img src="../assets/logo.png" alt="#" style="width:32px">
          <div class="title">OJ判题系统</div>
        </div>
      </a-col>
      <a-col flex="auto">
        <div>
          <a-menu mode="horizontal" :selected-keys="current" @menu-item-click="doMenuClick">
            <a-menu-item v-for="item in visibleRouter" :key="item.path">
              {{ item.name }}
            </a-menu-item>
          </a-menu>
        </div>
      </a-col>
      <a-col flex="100px">
        <div>
          {{ store.loginUser.userName ?? '未登录' }}
        </div>
      </a-col>
    </a-row>


  </div>
</template>
<style scoped>
#globalHeader .title-bar {
  margin-left: 8px;
  display: flex;
  align-items: center
}

#globalHeader .title {
  color: black;
  font-size: 18px;
  margin-left: 8px;
}
</style>
<script setup lang="ts">
import { routes } from '@/router/routes.ts'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'
import { UserLoginUserStore } from '@/stores/userLoginUserStore.ts'
import checkAccess from '@/access/checkAccess.ts'
//进行路由跳转
const doMenuClick = (key: string) => {
  router.push({
    path: key
  })
}
//获取全局状态管理
const store = UserLoginUserStore()
const visibleRouter = computed(() => {
  return routes.filter(item => {
    if (item.meta?.hideInMenu) {
      return false
    }//store.loginUser.userRole
    if (!checkAccess(store.loginUser, item.meta?.access)) {
      // console.log(store.loginUser.userRole, item.meta?.access)
      return false
    }
    return true
  })
})

// 当前要高亮的菜单项
const current = ref<string[]>([])
const router = useRouter()
//解决是刷新后，路由丢失的问题
router.afterEach((to) => {
  current.value = [to.path]
})


store.fetchLoginUser()


</script>