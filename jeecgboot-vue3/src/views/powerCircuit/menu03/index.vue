<template>
  <PageWrapper style="" :dense="true" :contentFullHeight="true" fixedHeight>
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
    <div style="width: 100%; height: 100%; display: flex; justify-content: space-around; padding: 10px">
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
  import { defHttp } from '/@/utils/http/axios';
  // import axios from 'axios';

  const lineChart = ref(null);
  const state = ref(true);

  let inData: any[] = [];
  let checked = { title: '科五线', type: 'cable' };

  const predictLoad = async () => {
    if (!lineChart.value) return;
    const actualLoad = await defHttp.get({
      url: '/online/cgreport/api/getColumnsAndData/1831577650211512321',
      params: { pageSize: 30 },
    });
    const predictLoad = await defHttp.get({
      url: '/online/cgreport/api/getColumnsAndData/1831575913648021505',
      params: { pageSize: 30 },
    });
    // const ga = await axios({
    //   url: '/kafka/test',
    //   data: { substationname: '科五线' },
    //   headers: { 'Access-Control-Allow-Origin': '*' },
    //   method: 'post',
    // });
    // const getDatas = await defHttp.post({
    //   url: '/kafka/test',
    //   params: { substationname: '科五线' },
    // });

    (lineChart.value as any)?.initChart([], '预测负荷：' + checked.title);
    inData.length = 0;
    // let i = 0;
    // let timer;
    // let timer = setInterval(() => {
    //   if (i > 23 || !lineChart.value) return clearInterval(timer);
    //   inData.push({
    //     time: list[i].time,
    //     value: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
    //     predict: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
    //   });
    //   i++;
    //   (lineChart.value as any)?.initChart(inData, '预测负荷：' + checked.title);
    //   state.value = Boolean(i % 2);
    // }, 1000 * 1);
    predictLoad.data.records.forEach((obj: any) => {
      inData.push({ time: obj.create_time, predict: obj.load_value });
    });

    actualLoad.data.records.forEach((obj: any) => {
      const f = inData.find((item) => item.time === obj.create_time);
      if (f) f.actual = obj.load_value;
      else inData.push({ time: obj.create_time, actual: obj.load_value });
    });
    inData.sort((a, b) => a.time - b.time);
    (lineChart.value as any)?.initChart(inData, '预测负荷：' + checked.title);
  };
  onMounted(() => {
    predictLoad();
  });
  onBeforeMount(() => {
    // clearInterval(timer);
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
