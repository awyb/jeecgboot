<template>
  <PageWrapper style="background: #ffffff" :dense="true" :contentFullHeight="true" fixedHeight>
    <template #headerContent>
      <label style="font-size: 20px; font-weight: 600; display: flex; align-items: center; height: 30px">
        <Icon icon="ant-design:line-chart-outlined" size="24" style="margin-right: 5px" />
        短期负荷预测曲线
        <a-tooltip placement="right">
          <template #title>更新状态：{{ state ? '正常' : '异常' }}</template>
          <div class="state" :style="{ background: state ? '#2eff00aa' : 'red' }"></div>
        </a-tooltip>
        <a-tooltip placement="right">
          <template #title>手动更新</template>
          <Icon icon="ant-design:sync-outlined" size="24" style="margin-left: 20px; cursor: pointer" @click="refresh" />
        </a-tooltip>
      </label>
      <p style="color: #00000099; margin-left: 30px">预测负荷：科五线</p>
    </template>
    <div style="width: 100%; height: 100%; display: flex; justify-content: space-around; padding: 10px; background: #ffffff">
      <div style="height: 100%; width: 98%; text-align: center; overflow: hidden; box-shadow: 0px 0px 1px 1px #f0f0f0">
        <LinePredict ref="lineChart" />
      </div>
    </div>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import LinePredict from './LinePredict.vue';
  import { PageWrapper } from '/@/components/Page';
  import { onMounted, ref, onBeforeMount } from 'vue';

  const lineChart = ref(null);
  const state = ref(true);

  let inData: any[] = [];
  let i = 0;
  let checked = { title: '科五线', type: 'cable' };
  let timer;
  const predictLoad = () => {
    if (!lineChart.value) return;
    const list = [
      { time: '00:00', value: 0, predict: 0 },
      { time: '01:00', value: 0, predict: 0 },
      { time: '02:00', value: 0, predict: 0 },
      { time: '03:00', value: 0, predict: 0 },
      { time: '04:00', value: 0, predict: 0 },
      { time: '05:00', value: 0, predict: 0 },
      { time: '06:00', value: 0, predict: 0 },
      { time: '07:00', value: 0, predict: 0 },
      { time: '08:00', value: 0, predict: 0 },
      { time: '09:00', value: 0, predict: 0 },
      { time: '10:00', value: 0, predict: 0 },
      { time: '11:00', value: 0, predict: 0 },
      { time: '12:00', value: 0, predict: 0 },
      { time: '13:00', value: 0, predict: 0 },
      { time: '14:00', value: 0, predict: 0 },
      { time: '15:00', value: 0, predict: 0 },
      { time: '16:00', value: 0, predict: 0 },
      { time: '17:00', value: 0, predict: 0 },
      { time: '18:00', value: 0, predict: 0 },
      { time: '19:00', value: 0, predict: 0 },
      { time: '20:00', value: 0, predict: 0 },
      { time: '21:00', value: 0, predict: 0 },
      { time: '22:00', value: 0, predict: 0 },
      { time: '23:00', value: 0, predict: 0 },
    ];
    (lineChart.value as any)?.initChart([], '预测负荷：' + checked.title);
    i = 0;
    inData.length = 0;
    let timer = setInterval(() => {
      if (i > 23 || !lineChart.value) return clearInterval(timer);
      inData.push({
        time: list[i].time,
        value: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
        predict: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
      });
      i++;
      (lineChart.value as any)?.initChart(inData, '预测负荷：' + checked.title);
      state.value = Boolean(i % 2);
    }, 1000 * 1);
  };
  onMounted(() => {
    predictLoad();
  });
  onBeforeMount(() => {
    clearInterval(timer);
  });
  const refresh = () => {
    predictLoad();
  };
</script>
<style scoped>
  .state {
    width: 15px;
    height: 15px;
    border-radius: 50%;
    margin-left: 5px;
    cursor: pointer;
  }
</style>
