<template>
  <div id="basicLayout">
    <a-layout style="height: 400px;">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
      </a-layout-header>
      <a-layout-content class="content">
        Content
        <RouterView></RouterView>
      </a-layout-content>
      <a-layout-footer class="footer">Footer</a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">

import GlobalHeader from '@/components/GlobalHeader.vue'
</script>
<style scoped>
#basicLayout .footer {
  text-align: center;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: #efefef;
  padding: 16px;
}

#basicLayout .content {
  background: linear-gradient(to right, #efefef, #fff);
  margin-bottom: 28px;
  padding: 20px;
}

#basicLayout .header {
  padding-inline: 20px;
  margin-bottom: 16px;
  color: unset;
  background: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}
</style>