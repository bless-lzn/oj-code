<template>
  <div id="globalHeader">
    <a-row  style="margin-bottom: 16px; " :wrap=false align="center">
      <a-col flex="160px">
        <div class="title-bar">
          <img src="../assets/logo.png" alt="#" style="width:32px">
          <div class="title">OJ判题系统</div>
        </div>
      </a-col>
      <a-col flex="auto">
        <div>
          <a-menu mode="horizontal" :selected-keys="current" @menu-item-click="doMenuClick">

            <a-menu-item v-for="item in routes" :key="item.path">
              {{item.name}}
            </a-menu-item>
          </a-menu>
        </div>
      </a-col>
      <a-col flex="100px">
        <div>李某</div>
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
#globalHeader .title{
  color: black;
  font-size: 18px;
  margin-left: 8px;
}
</style>
<script setup lang="ts">
import { routes } from '@/router/routes.ts'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
//进行路由跳转
const doMenuClick = (key:string) => {
  router.push({
    path: key
  })
}
const router = useRouter()
// 当前要高亮的菜单项
const current = ref<string[]>([])
//解决是刷新后，路由丢失的问题
router.afterEach((to)=>{
  current.value=[to.path]
})

</script>