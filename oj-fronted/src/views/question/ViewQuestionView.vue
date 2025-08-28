<template>
  <div id="viewQuestionView" style="height: 100vh">
    <a-row :gutter="[24,24]">
      <a-col :md="12" :xs="24">
        <a-tabs default-active-key="content">
          <a-tab-pane key="content" title="内容">
            <a-card v-if="questionVO" :title="questionVO.title">
              <template #extra>
                <a-space>
                  <a-tag v-for="(tag, index) of questionVO.tags" :key="index" color="green">{{ tag }}</a-tag>
                </a-space>
              </template>
              <!--              judge信息-->
              <a-descriptions title="判题条件" :column="{xs:1, md:2,lg:3}">
                <a-descriptions-item label="时间限制">
                  <a-tag>{{ questionVO.judgeConfig?.timeLimit ?? 0 }}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="内存限制">
                  <a-tag>{{ questionVO.judgeConfig?.memoryLimit ?? 0 }}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="堆栈限制">
                  <a-tag>{{ questionVO.judgeConfig?.stackLimit ?? 0 }}</a-tag>
                </a-descriptions-item>
              </a-descriptions>
              <MdViewer :value="questionVO?.content"></MdViewer>
            </a-card>
          </a-tab-pane>
          <a-tab-pane key="comment" title="评论" disabled>
          </a-tab-pane>
          <a-tab-pane key="answer" title="题解">
            <a-card>
              <MdViewer :value="questionVO?.answer"></MdViewer>
            </a-card>
          </a-tab-pane>
        </a-tabs>
      </a-col>
      <a-col :md="12">
        <a-form :model="form" layout="inline">
          <a-form-item field="language" label="选择语言" style="min-width: 240px">
            <a-select :style="{width:'320px'}" placeholder="Please select ..." v-model="form.language">
              <a-option>cpp</a-option>
              <a-option>java</a-option>
              <a-option>go</a-option>
            </a-select>
          </a-form-item>
        </a-form>

        <CodeEditor :value="form.code" :language="form.language" :handleChange="onCodeChange"></CodeEditor>
        <a-divider size=0></a-divider>
        <a-button type="primary" style="min-width: 100px" @click="handleSubmit">提交代码</a-button>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { getQuestionVoByIdUsingGet, listQuestionVoByPageUsingPost } from '@/api/questionController.ts'
import { Message } from '@arco-design/web-vue'
import MdEditor from '@/components/MdEditor.vue'
import CodeEditor from '@/components/CodeEditor.vue'
import MdViewer from '@/components/MdViewer.vue'
import { doQuestionSubmitUsingPost } from '@/api/questionSubmitController.ts'

interface props {
  id: string
}

const props = withDefaults(defineProps<props>(), {
  id: () => ''
})

const questionVO = ref<API.QuestionVO>()

const loadData = async () => {
  const res = await getQuestionVoByIdUsingGet({ id: props.id } as any)
  if (res.data.code === 0) {
    questionVO.value = res?.data?.data
  } else {
    Message.error('获取数据失败，' + res.data.message)
  }
}
const form = ref<API.QuestionSubmitAddRequest>({
  language: 'java',
  code: '',
  questionId: props.id as any
})
watch((form.value), () => {
  console.log("form.value"+form.value.language)
})

const handleSubmit = async () => {
  const res = await doQuestionSubmitUsingPost(form.value)
  console.log(form.value.language)
  if (res.data.code === 0) {
    Message.success('提交成功')

  } else {
    Message.error('提交失败，' + res.data.message)
  }
}
const onCodeChange = (v: string) => {
  console.log('父组件----------'+form.value.code)

  form.value.code = v
  // form.value.language
}
onMounted(() => {
  loadData()
})
</script>

<style scoped>
#viewQuestionView {
}
</style>
