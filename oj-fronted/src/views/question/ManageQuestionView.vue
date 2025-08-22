<template>
  <div id="manageQuestionView">
    <a-table :columns="columns" :data="dataList"
             :pagination="{
      showTotal: true,
      pageSize: searchParams.pageSize,
      total: total,
      current:searchParams.current }"
      @page-change="onPageChange">
      <template #optional="{ record }">
        <a-space>
          <a-button type="outline" @click="handleUpdate(record)">修改</a-button>
          <a-button status="danger" @click="handleDelete(record)">删除</a-button>
        </a-space>
      </template>
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag v-for="(tag, index) of record.tags" :key="index" color="green">{{ tag }}</a-tag>
        </a-space>
      </template>
<!--      <template #judgeConfig="{ record }">-->
<!--        <a-space wrap>-->
<!--         {{JSON.parse(record.judgeConfig)}}-->
<!--        </a-space>-->
<!--      </template>-->
      <template #judgeCase="{ record }">
        <a-space wrap>
         {{JSON.parse(record.judgeCase)}}
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
  listQuestionByPageUsingPost
} from '@/api/questionController.ts'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'
const router = useRouter()

const show = ref(true)
const columns = [{
  title: 'id',
  dataIndex: 'id'
},
  {
    title: '标题',
    dataIndex: 'title'
  },
  {
    title: '内容',
    dataIndex: 'content'
  },
  {
    title: '标签',
    slotName: 'tags'
  }, {
    title: '答案',
    dataIndex: 'answer'
  }, {
    title: '提交数',
    dataIndex: 'submitNum'
  }, {
    title: '通过数',
    dataIndex: 'acceptedNum'
  }, {
    title: '判题配置',
    dataIndex: 'judgeConfig'
  }, {
    title: '判题用例',
    slotName: 'judgeCase'
  }, {
    title: '用户id',
    dataIndex: 'userId'
  }, {
    title: '创建时间',
    dataIndex: 'createTime'
  },
  {
    title: 'Optional',
    slotName: 'optional'
  }]
//看接受到的数值
const dataList = ref<API.Question []>([])
const total = ref(0)
const searchParams = ref<API.QuestionQueryRequest>({
  current: 1,
  pageSize: 2
})
const getQuestionList = async () => {
  const res = await listQuestionByPageUsingPost(searchParams.value)
  if (res.data.code === 0) {
    dataList.value = res.data?.data?.records ?? []
    total.value = res.data?.data?.total ?? 0
    dataList.value.forEach(item => {
      item.createTime = dayjs(item.createTime).format('YYYY-MM-DD HH:mm:ss')
      if(item.tags)
        item.tags=JSON.parse(item.tags)
    })
    console.log(dataList)
  } else {
    Message.error('获取数据失败，' + res.data.message)
  }

}
onMounted(() => {
  getQuestionList()
  // console.log(dataList.value)
})
const onPageChange=(page:number)=>{
  searchParams.value={
    ...searchParams.value,
    current:page
  }
  // getQuestionList()
}
// watchEffect(()=>{
//   console.log("你好")
//   getQuestionList()
// })
const handleUpdate =(question: API.Question) => {
//发送请求到
  router.push({
    path: '/update/question',
    query: {
      id: question.id
    }
  })

}

const handleDelete = async (question: API.Question) => {
  const res = await deleteQuestionUsingPost({ id: question.id })
  if (res.data.code === 0) {
    Message.success('删除成功')
    getQuestionList()
  } else {
    Message.error('删除失败，' + res.data.message)
  }
}
</script>


<style scoped>
#manageQuestionView {
}
</style>
