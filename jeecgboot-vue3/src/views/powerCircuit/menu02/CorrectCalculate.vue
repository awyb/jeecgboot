<template>
  <a-card :bordered="false" style="height: 100%">
    <a-tabs v-model:activeKey="activeKey" @change="tabChange">
      <a-tab-pane v-for="item in dataArr" :key="item.key">
        <template #tab>
          <span>
            {{ item.title }}
            <Icon
              v-if="overloadIds.includes(item.key + '') || removeKeys.includes(item.key + '')"
              :icon="`ant-design:${removeKeys.includes(item.key + '') ? 'check-circle' : 'warning'}-outlined`"
              :style="{ color: removeKeys.includes(item.key + '') ? 'lime' : 'black' }"
            />
          </span>
        </template>
        <main style="height: 400px"> {{ item }} </main>
        <footer>
          <a-button
            v-if="overloadIds.includes(item.key + '') && !removeKeys.includes(item.key + '')"
            type="primary"
            size="small"
            preIcon="ant-design:close-circle-outlined"
            @click="removeLoad(item)"
            >切除</a-button
          >
        </footer>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>
<script lang="ts">
  import { defineComponent, ref, onMounted, reactive } from 'vue';
  interface MenuItem {
    key: string | number; // 假设key可以是字符串或数字
    title: string; // 必须有title属性且类型为字符串
    // 可以添加其他属性
  }
  export default defineComponent({
    name: 'CorrectCalculate',
    props: {
      dataArr: {
        type: Array as PropType<MenuItem[]>,
        default: () => [],
      },
      overloadIds: {
        type: Array as PropType<string[]>,
        default: () => [],
      },
    },
    emits: ['click', 'remove-load'],
    setup(props, { expose, emit }) {
      const activeKey = ref(props.dataArr.length ? props.dataArr[0].key : '');
      const removeKeys: Array<string> = reactive([]);
      //使用component动态切换tab
      function tabChange(key) {
        activeKey.value = key;
      }
      function removeLoad(item) {
        removeKeys.push(item.key);
        emit('remove-load', item);
      }
      onMounted(() => {});
      expose({});
      return { activeKey, removeKeys, tabChange, removeLoad };
    },
  });
</script>
