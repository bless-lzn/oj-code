<template>
  <div id="questionsView">
    <!--    搜索框-->
    <a-form :model="searchParams" layout="inline">
      <a-form-item field="title" label="名称" style="min-width: 240px">
        <a-input v-model="searchParams.title" />
      </a-form-item>

      <a-form-item field="tags" label="标签" style="min-width: 280px">
        <a-input-tag v-model="searchParams.tags" />
<!--        <a-input v-model="searchParams.tags" />-->
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="handleSubmit">搜索</a-button>
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
      <template #acceptance="{ record }">
        {{ `${record.submitNum ? record.acceptedNum / record.submitNum * 100 : '0'}%(${record.acceptedNum} / ${record.submitNum}) `
        }}
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
import { onMounted, ref, watchEffect } from 'vue'
import {
  deleteQuestionUsingPost,
  editQuestionUsingPost,
  listQuestionByPageUsingPost, listQuestionVoByPageUsingPost
} from '@/api/questionController.ts'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'

const router = useRouter()

const show = ref(true)
const columns = [
  {
    title: '题号',
    dataIndex: 'id'
  },
  {
    title: '题目名称',
    dataIndex: 'title'
  },
  {
    title: '标签',
    slotName: 'tags'
  }, {
    title: '通过率',
    slotName: 'acceptance'
  }, {
    title: '创建时间',
    slotName: 'createTime'
  },
  {
    title: 'Optional',
    slotName: 'optional'
  }]
//看接受到的数值
const dataList = ref<API.QuestionVO[]>([])
const total = ref(0)
const searchParams = ref<API.QuestionQueryRequest>({
  title: "",
  tags: [],
  current: 1,
  pageSize: 5
})
const getQuestionList = async () => {
  const res = await listQuestionVoByPageUsingPost(searchParams.value)
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
