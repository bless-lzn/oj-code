<template>
  <div id="questionsView">
    <!--    搜索框-->
      <a-form :model="searchParams" layout="inline" >
        <a-form-item field="questionId" label="题目Id" style="min-width: 240px">
          <a-input v-model="searchParams.questionId" placeholder="请输入题目Id"></a-input>
        </a-form-item>
        <a-form-item field="language" label="选择语言" style="min-width: 240px">
          <a-select :style="{width:'320px'}" placeholder="Please select ..." v-model="searchParams.language">
            <a-option>cpp</a-option>
            <a-option>java</a-option>
            <a-option>go</a-option>
          </a-select>
          <div style="margin-left: 10px"><a-button type="primary" @click="handleSubmit">搜索</a-button></div>

        </a-form-item>

      </a-form>

    <a-divider size="0"></a-divider>
    <a-table :columns="columns" :data="dataList"
             :pagination="{
      showTotal: true,
      pageSize: searchParams.pageSize,
      total: total,
      current:searchParams.current }"
             @page-change="onPageChange">
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag v-for="(tag, index) of record.tags" :key="index" color="green">{{ tag }}</a-tag>
        </a-space>
      </template>
      <template #title="{ record }">
        <a-button @click="handleUpdate(record)">{{ record.title }}</a-button>
      </template>
      <template #createTime="{ record }">
        {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
      </template>
      <template #judgeInfo="{ record }" >
<!--        {{JSON.stringify(record.judgeInfo)}}-->
       <div style="min-width: 65px"> message:{{record.judgeInfo. message?? ' '}}<br>
         time:{{record.judgeInfo.time?? ' '}}<br>
         memory:{{record.judgeInfo.memory?? ' '}}<br></div>
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="toQuestionPage(record)">做题</a-button>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch, watchEffect } from 'vue'
import {
  deleteQuestionUsingPost,
  editQuestionUsingPost,
  listQuestionByPageUsingPost, listQuestionVoByPageUsingPost
} from '@/api/questionController.ts'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'
import { listQuestionSubmitVoByPageUsingPost } from '@/api/questionSubmitController.ts'

const router = useRouter()

const show = ref(true)
const columns = [
  {
    title: '提交题号',
    dataIndex: 'id'
  },
  {
    title: '编程语言',
    dataIndex: 'language'
  },{
    title: '判题信息',
    slotName: 'judgeInfo'
  },
  {
    title: '题目Id',
    dataIndex: 'questionId'
  },
  {
    title: '判题状态',
    dataIndex: 'status'
  },
  {
    title: '创建用户',
    dataIndex: 'userId'
  },
 {
    title: '创建时间',
    slotName: 'createTime'
  }
]
//看接受到的数值
const dataList = ref<API.QuestionSubmitVO[]>([])
const total = ref(0)
const searchParams = ref<API.QuestionSubmitQueryRequest>({
  language: undefined,
  questionId: undefined,
  current: 1,
  pageSize: 5
})
watch(searchParams.value, () => {
  console.log(searchParams.value)
  handleSubmit()
  getQuestionList()
})
const getQuestionList = async () => {
  const res = await listQuestionSubmitVoByPageUsingPost({
      ...searchParams.value,
      sortField: 'createTime',
      sortOrder: 'descend',
  }
  )
  if (res.data.code === 0) {
    dataList.value = res.data?.data?.records ?? []
    total.value = res.data?.data?.total ?? 0
  } else {
    Message.error('获取数据失败，' + res.data.message)
  }

}
const handleSubmit = () => {
  console.log(searchParams.value)
  searchParams.value = {
    ...searchParams.value,
    current: 1
  }
  getQuestionList()
}
onMounted(() => {
  getQuestionList()
  // console.log(dataList.value)
})
const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page
  }
  getQuestionList()
}
// watchEffect(()=>{
//   console.log("你好")
//   getQuestionList()
// })
const handleUpdate = (question: API.Question) => {
//发送请求到
  router.push({
    path: '/update/question',
    query: {
      id: question.id
    }
  })
}
const toQuestionPage = (question: API.Question) => {
  router.push({
    path: `/view/question/${question.id}`
  })
}

const handleDelete = async (question: API.Question) => {
  const res = await deleteQuestionUsingPost({ id: question.id })
  if (res.data.code === 0) {
    Message.success('删除成功')
    await getQuestionList()
  } else {
    Message.error('删除失败，' + res.data.message)
  }
}
</script>


<style scoped>
#questionsView {
  margin: 0 auto;
  max-width: 1280px;
}
</style>
