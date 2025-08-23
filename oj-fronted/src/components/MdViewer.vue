<template>
  <Viewer :value="value"  :plugins="plugins" />
</template>

<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Viewer } from '@bytemd/vue-next'
import { ref,withDefaults,defineProps } from 'vue'
//子组件
//给子组件传值
interface Props {
  value: string;
}
const props = withDefaults(defineProps<Props>(), {
  value: '',
})

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
]

</script>

<style>
.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child {
  display: none;
}
</style>

