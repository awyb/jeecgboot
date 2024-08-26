<template>
  <PageWrapper style="background: #ffffff" :dense="true" :contentFullHeight="true" fixedHeight>
    <template #headerContent>
      <label style="font-size: 20px; font-weight: 600">
        <Icon icon="ant-design:line-chart-outlined" size="24" style="margin-right: 5px" />
        短期负荷预测曲线
        <a-tooltip placement="right">
          <template #title>手动更新</template>
          <Icon icon="ant-design:sync-outlined" size="24" style="margin-left: 10px; cursor: pointer" @click="refresh" />
        </a-tooltip>
      </label>
      <p style="color: #00000099; margin-left: 30px">预测负荷：科五线</p>
    </template>
    <div style="width: 100%; height: 100%; display: flex; justify-content: space-around; padding: 10px; background: #ffffff">
      <div style="height: 100%; width: 98%; text-align: center; overflow: hidden; box-shadow: 0px 0px 1px 1px #f0f0f0">
        <LinePredict ref="lineChart" />
      </div>

      <!-- <div style="height: inherit; width: 20%; box-shadow: 0px 0px 2px 2px #e4e4e4; display: flex; flex-direction: column">
        <BasicTree
          ref="tree"
          style="font-size: 16px; padding: 10px; flex: 1"
          title="数据列表"
          :search="true"
          :treeData="treeData"
          :expandedKeys="expandedKeys"
          @select="onSelect"
        />
        <div style="margin: 10px">
          <a-button type="primary" size="small" preIcon="ant-design:edit-outlined" :disabled="!isUse" @click="predictLoad()">负荷预测</a-button>
        </div>
      </div>
      <div style="height: 100%; width: 78%; text-align: center; overflow: hidden">
        <LinePredict ref="lineChart" />
      </div> -->
    </div>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import LinePredict from './LinePredict.vue';
  import { PageWrapper } from '/@/components/Page';
  import { BasicTree } from '/@/components/Tree/index';
  import { TreeItem } from '/@/components/Tree/index';
  import { onMounted, ref, computed, onBeforeMount } from 'vue';
  const expandedKeys = ['transformer', 'cable'];
  const transformer1_ids = ['svg_220', 'svg_221', 'svg_222'];
  const transformer2_ids = ['svg_198', 'svg_199', 'svg_200'];
  const transformer3_ids = ['svg_176', 'svg_177', 'svg_178'];
  const cable_id1 = ['svg_140']; //外部电网
  const cable_id2 = ['svg_128']; //科秀线
  const cable_id3 = ['svg_116']; //科横线
  const cable_id4 = ['svg_100']; //科铁朝万线
  const cable_id5 = ['svg_86']; //科尧滨线
  const cable_id6 = ['svg_154']; //科五线
  const treeData: TreeItem[] = [
    {
      title: '变压器',
      key: 'transformer',
      icon: 'ant-design:folder-outlined',
      children: [
        {
          title: '变压器1',
          key: 'transformer1',
          icon: 'ant-design:file-outlined',
          ids: transformer1_ids,
          defaultColor: 'black',
          checkColor: 'blue',
          type: 'transformer',
        },
        {
          title: '变压器2',
          key: 'transformer2',
          icon: 'ant-design:file-outlined',
          ids: transformer2_ids,
          defaultColor: 'black',
          checkColor: 'blue',
          type: 'transformer',
        },
        {
          title: '变压器3',
          key: 'transformer3',
          icon: 'ant-design:file-outlined',
          ids: transformer3_ids,
          defaultColor: 'black',
          checkColor: 'blue',
          type: 'transformer',
        },
      ],
    },
    {
      title: 'GIS',
      key: 'gis',
      icon: 'ant-design:folder-outlined',
      children: [],
    },
    {
      title: '电缆',
      key: 'cable',
      icon: 'ant-design:folder-outlined',
      children: [
        {
          title: '外部电网',
          key: 'cable1',
          icon: 'ant-design:file-outlined',
          ids: cable_id1,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
        {
          title: '科秀线',
          key: 'cable2',
          icon: 'ant-design:file-outlined',
          ids: cable_id2,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
        {
          title: '科横线',
          key: 'cable3',
          icon: 'ant-design:file-outlined',
          ids: cable_id3,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
        {
          title: '科铁朝万线',
          key: 'cable4',
          icon: 'ant-design:file-outlined',
          ids: cable_id4,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
        {
          title: '科尧滨线',
          key: 'cable5',
          icon: 'ant-design:file-outlined',
          ids: cable_id5,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
        {
          title: '科五线',
          key: 'cable6',
          icon: 'ant-design:file-outlined',
          ids: cable_id6,
          defaultColor: 'black',
          checkColor: 'red',
          type: 'cable',
        },
      ],
    },
  ];
  const isUse = ref(false);
  const tree = ref(null);
  const lineChart = ref(null);
  let timer;
  let inData: any[] = [];
  let i = 0;
  let checked = { title: '科五线', type: 'cable' };
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
      { time: '19:00', value: 0, predict: 0 },
      { time: '20:00', value: 0, predict: 0 },
      { time: '21:00', value: 0, predict: 0 },
      { time: '22:00', value: 0, predict: 0 },
      { time: '23:00', value: 0, predict: 0 },
    ];
    (lineChart.value as any).initChart([], '预测负荷：' + checked.title);
    i = 0;
    inData.length = 0;
    timer = setInterval(() => {
      if (i > 23) return clearInterval(timer);
      inData.push({
        time: list[i].time,
        value: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
        predict: Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50,
      });
      i++;
      (lineChart.value as any).initChart(inData, '预测负荷：' + checked.title);
    }, 100);
    // list.forEach((i) => {
    //   i.value = Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50;
    //   i.predict = Math.floor(Math.random() * 10 * (Math.random() * 2 - 1 > 0 ? 1 : -1)) + 50;
    // });
    // (lineChart.value as any).initChart(list, '预测负荷：' + checked.title);
  };
  onMounted(() => {
    predictLoad();
  });
  onBeforeMount(() => {
    clearInterval(timer);
  });
  const onSelect = (ids) => {
    checked = (tree.value as any).getSelectedNode(ids[0]);
    isUse.value = checked.type === 'cable';
  };
  const refresh = () => {
    predictLoad();
  };
  // const onCheck = (checkedKeys) => {
  //   const filterNode = checkedKeys.filter((id) => (tree.value as any).getSelectedNode(id).type === 'cable');
  //   isUse.value = Boolean(filterNode.length);
  //   filterNode.forEach((id: string) => {
  //     const n = (tree.value as any).getSelectedNode(id);
  //     checked.push(n as never);
  //   });
  // };
</script>
