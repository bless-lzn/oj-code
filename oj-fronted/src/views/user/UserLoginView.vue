<template>
  <div id="userLoginView">
    <h3 class="title">用户登录</h3>
    <a-form :model="form" label-align="left" auto-label-width :style="{ width: '480px', margin: '0 auto'}"
            @submit="handleSubmit">
      <a-form-item field="userAccount" label="用户名">
        <a-input
          v-model="form.userAccount"
          placeholder="请输入用户名"
        />
      </a-form-item>
      <a-form-item field="userPassword" label="密码">
        <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
      </a-form-item>
<!--      <a-form-item field="isRead">-->
<!--        <a-checkbox v-model="form.isRead"> I have read the manual</a-checkbox>-->
<!--      </a-form-item>-->
      <div class="tips">
        没有账号？
        <RouterLink to="/user/register">去注册</RouterLink>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="margin: 0 auto">提交</a-button>
<!--        <a-button @click="handleResult" type="primary" style="margin: 0 auto">提交test</a-button>-->
      </a-form-item>
    </a-form>
<!--    {{ form }}-->
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { UserLoginUserStore } from '@/stores/userLoginUserStore.ts'
import { userLoginUsingPost } from '@/api/userController.ts'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
const router = useRouter()  //循环依赖问题
const form = reactive({
  userAccount: '',
  userPassword: '',
})
const handleSubmit = async (data: any) => {
  const res = await userLoginUsingPost(data.values)
  if (res.data.code === 0 && res.data.data) {
    await UserLoginUserStore().fetchLoginUser()
    Message.success('登录成功')
    await router.push({
      path: '/',
      replace: true
    })
  } else {
    Message.error('登录失败' + res.data.message)
  }
}
</script>

<style scoped>
#userLoginView .title {
  text-align: center;
}

#userLoginView .tips {
  text-align: right;
}

/*#userLoginView {

}*/

</style>