<template>
  <div id="userLayOut">
    <div class="title-bar">
      <img src="../assets/logo.png" alt="#" style="width:32px">
      <div class="title">OJ判题系统</div>
    </div>
    <a-layout style="height: 400px;">
      <a-layout-header class="header">
      </a-layout-header>
      <a-layout-content class="content">

        <RouterView></RouterView>
      </a-layout-content>
    </a-layout>
  </div>
</template>
<script setup lang="ts">

import GlobalHeader from '@/components/GlobalHeader.vue'
</script>
<style scoped>
#userLayOut .footer {
  text-align: center;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: #efefef;
  padding: 16px;
}

#userLayOut .content {
  /*background: linear-gradient(to right, #efefef, #fff);*/
  background: #fff;
  margin-bottom: 28px;
  padding: 20px;
}
#userLayOut .title-bar {
  display: flex;
  align-items: center;
  margin-left: 20px;
  /*justify-content: center;*/
}

</style>