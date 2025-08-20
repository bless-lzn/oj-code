
<template>
  <div>
    <MdEditor :value="value" :handle-change="handleChange"/>
<!--    父组件可以进行传值-->
  </div>

</template>
<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
import MdEditor from '@/components/MdEditor.vue'
import { ref } from 'vue'
const value = ref("")
const handleChange = (v:string) => {
  value.value = v
}

</script>
