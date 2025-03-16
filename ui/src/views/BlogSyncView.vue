<script setup lang="ts">
import { ref } from 'vue';
import { Toast, VButton, VCard, VPageHeader } from "@halo-dev/components";
import axios from "axios";

// 创建带类型的接口
interface SyncRequest {
  type: number;
  url: string;
}

// 创建可复用的 axios 实例
const http = axios.create({
  baseURL: "/",
  timeout: 5000, // 适当延长超时时间
  headers: {
    'Content-Type': 'application/json'
  }
});

// 响应式数据
const formData = ref<SyncRequest>({
  type: 0,  // 默认选中第一个选项
  url: ""
});

// 提交状态
const isSubmitting = ref(false);

// 表单验证
const validateForm = () => {
  if (!formData.value.url) {
    Toast.error("请输入博客主页链接");
    return false;
  }
  try {
    new URL(formData.value.url);
  } catch {
    Toast.error("请输入有效的URL地址");
    return false;
  }
  return true;
};

// 提交逻辑
const sync = async () => {
  if (isSubmitting.value || !validateForm()) return;

  isSubmitting.value = true;
  try {
    const response = await http.post(
      "/apis/api.plugin.halo.run/v1alpha1/plugins/blog-sync/blogSync/sync",
      formData.value
    );

    console.log(response)
    if (response.status === 200) {
      if(response.data.code === 500){
        Toast.error("同步失败:请确保链接正确！");
        console.error("同步失败:", response.data.message);
        formData.value.url = ""; // 清空表单
      }else{
        Toast.success("同步成功");
        formData.value.url = ""; // 清空表单
      }
    }
  } catch (error) {
    console.error("同步失败:", error);
    Toast.error("同步失败，请检查网络连接");
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<template>
  <VPageHeader title="博客同步">
    <template #icon>
      <IconUpload class="mr-2 self-center" />
    </template>
  </VPageHeader>

  <div class="m-0 md:m-4">
    <VCard :body-class="['!p-0']">
      <div class="bg-white p-6">
        <form @submit.prevent="sync" class="space-y-6 max-w-md mx-auto">
          <!-- 单选按钮组 -->
          <div class="space-y-2">
            <label class="block text-sm font-medium text-gray-700">平台选择：</label>
            <div class="flex space-x-4">
              <label
                v-for="(platform, index) in [
                  { name: 'CSDN', value: 0 },
                  { name: '博客园', value: 1 },
                  { name: '简书', value: 2 },
                  { name: '开源中国', value: 3 },
                  // { name: '知乎', value: 4 },
                  { name: '华为云', value: 5 },
                  // { name: '阿里云', value: 6 },
                  { name: '腾讯云', value: 7 }
                ]"
                :key="index"
                class="flex items-center space-x-1 cursor-pointer"
              >
                <input
                  type="radio"
                  v-model="formData.type"
                  :value="platform.value"
                  class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
                >
                <span class="text-gray-700">{{ platform.name }}</span>
              </label>
            </div>
          </div>

          <!-- 输入框 -->
          <div>
            <label for="url" class="block text-sm font-medium text-gray-700">博客链接：</label>
            <input
              id="url"
              type="url"
              v-model.trim="formData.url"
              placeholder="https://example.com"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm"
              pattern="https?://.+"
            >
          </div>

          <!-- 提交按钮 -->
          <div>
            <VButton
              type="primary"
              :loading="isSubmitting"
              @click="sync"
              class="w-full justify-center bg-blue-600 hover:bg-blue-700 text-white"
            >
              {{ isSubmitting ? '同步中...' : '开始同步' }}
            </VButton>
          </div>
        </form>
      </div>
    </VCard>
  </div>
</template>

<style scoped>
/* 添加过渡动画 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
