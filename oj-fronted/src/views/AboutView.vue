<template>
  <CodeEditor :value="code" :language="'javascript'" />
</template>

<script setup lang="ts">import CodeEditor from '@/components/CodeEditor.vue'
import { ref } from 'vue'

const code = ref(`function hello() {
  alert('Hello world!');
}`)
</script>