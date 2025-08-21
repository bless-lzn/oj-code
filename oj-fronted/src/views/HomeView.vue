<template>
  <div>
    <!--    <CodeEditor :value="codeValue" :handle-change="onCodeChange"/>-->
    <MdEditor :value="mdValue"  :handle-change="onMdChange" />
    <!--    父组件可以进行传值-->
  </div>

</template>
<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
import MdEditor from '@/components/MdEditor.vue'
import { ref } from 'vue'
import CodeEditor from '@/components/CodeEditor.vue'

/*const codeValue = ref('')
const onCodeChange = (v: string) => {
  console.log(codeValue.value)
  codeValue.value = v
}*/
const mdValue = ref('你好')
const onMdChange = (v: string) => {
  console.log(mdValue)
  mdValue.value = v
}


</script>
