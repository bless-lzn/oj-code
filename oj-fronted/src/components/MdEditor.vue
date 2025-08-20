<template>
  <Editor :value="value" :plugins="plugins" @change="handleChange" />
</template>

<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Editor,Viewer } from '@bytemd/vue-next'
import { ref } from 'vue'

const plugins = [
  gfm(),
  highlight()
  // Add more plugins here
]

//子组件
//给子组件传值
interface Props {
  value: string;
  handleChange: (v: string) => void;
}
const props = withDefaults(defineProps<Props>(), {
  value: '',
  handleChange: () => {}
})

const value = ref('')

const handleChange = (v: string) => {
  value.value = v
}
</script>

<style scoped></style>

