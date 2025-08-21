<template>
  <div id="addQuestionView">
    <h2>创建题目</h2>
    <a-form :model="form" @submit="handleSubmit" label-align="left">
      <a-form-item field="title" label="标题">
        <a-input v-model="form.title" placeholder="请输入标题"></a-input>
      </a-form-item>

      <a-form-item field="tags" label="标签">
        <a-input-tag v-model="form.tags" placeholder="请输入标签" allow-clear />
      </a-form-item>
      <a-form-item field="content" label="内容">
        <MdEditor :value="form.content"
                  :handle-change="onContentChange" />
      </a-form-item>
      <a-form-item field="answer" label="答案">
        <MdEditor :value="form.answer" :handle-change="onAnswerChange"></MdEditor>
      </a-form-item>
      <a-form-item label="判题配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" style="min-width: 480px">
          <a-form-item field="judgeConfig.timeLimit" label="时间限制">
            <a-input-number
              v-model="form.judgeConfig.timeLimit"
              placeholder="请输入时间消耗"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
            <a-input-number
              v-model="form.judgeConfig.memoryLimit"
              placeholder="请输入内存限制"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
            <a-input-number
              v-model="form.judgeConfig.stackLimit"
              placeholder="请输入堆栈限制"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <a-form-item
        label="测试用例配置"
        :content-flex="false"
        :merge-props="false"
        no-style
      >
        <a-form-item
          v-for="(judgeCaseItem, index) of form.judgeCase"
          :key="index"
        >
          <a-space direction="vertical" style="min-width: 640px">
            <a-form-item
              :field="`form.judgeCase[${index}].input`"
              :label="`输入样例-${index}`"
              :key="index"
            >
              <a-input
                v-model="judgeCaseItem.input"
                placeholder="请输入测试输入样例"
              />
            </a-form-item>
            <a-form-item
              :field="`form.judgeCase[${index}].output`"
              :label="`输出样例-${index}`"
              :key="index"
            >
              <a-input
                v-model="judgeCaseItem.output"
                placeholder="请输入测试输出样例"
              />
            </a-form-item>
            <a-button @click="handleDelete(index)" status="danger">删除</a-button>
          </a-space>
        </a-form-item>
        <div style="margin-top: 32px">
          <a-button @click="handleAdd()" type="outline" status="success"
          >新增测试样例
          </a-button>
        </div>
      </a-form-item>
      <div style="margin-top: 16px"></div>
      <a-form-item>
        <a-button type="primary" style="min-width: 200px" @click="doSubmit()"
        >提交
        </a-button>
      </a-form-item>
    </a-form>
  </div>

</template>
<script setup lang="ts">
import { reactive } from 'vue'
import MdEditor from '@/components/MdEditor.vue'
import { Message } from '@arco-design/web-vue'
import { addQuestionUsingPost } from '@/api/questionController.ts'

const form = reactive({
  answer: '答案',
  content: '题目内容',
  judgeCase: [
    {
      input: '1 2',
      output: '3 4'
    }
  ],
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000
  },
  tags: ['栈', '简单'],
  title: 'A + B'
})
const doSubmit =async () =>  {
  // console.log(form.content)
  // console.log(form.answer)
  const res = await addQuestionUsingPost(form);
  if (res.data.code === 0) {
    Message.success("创建成功");
  } else {
    Message.error("创建失败" + res.data.message);
  }
}
const handleAdd = () => {
  form.judgeCase.push({
    input: '',
    output: ''
  })
}
const handleDelete = (index: number) => {
  form.judgeCase.splice(index, 1)
}
const handleSubmit = () => {
  console.log(1)
}
const onContentChange = (value: string) => {
  form.content = value
}
const onAnswerChange = (value: string) => {
  form.answer = value
}
</script>
<style scoped>


</style>