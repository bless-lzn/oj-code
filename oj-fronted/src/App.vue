

<template>

<BasicLayout></BasicLayout>
</template>
<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
import { UserLoginUserStore } from '@/stores/userLoginUserStore.ts'
import { useRouter } from 'vue-router'

const router = useRouter();
const store = UserLoginUserStore();

router.beforeEach((to, from, next) => {
  // 仅管理员可见，判断当前用户是否有权限
  console.log("to----", to.meta?.access)

  console.log("---",store.loginUser?.role)
  if (to.meta?.access === "canAdmin") {
    if (store.loginUser?.role !== "admin") {
      next("/noAuth");
      return;
    }
  }
  next();
});

</script>
<style scoped>

</style>
