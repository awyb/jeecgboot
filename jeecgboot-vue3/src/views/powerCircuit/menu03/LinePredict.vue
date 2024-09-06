<template>
  <div ref="chartRef" :style="{ height, width }" id="chart"></div>
</template>
<script lang="ts">
  import { defineComponent, PropType, ref, onMounted } from 'vue';
  import * as echarts from 'echarts';
  export default defineComponent({
    name: 'LinePredict',
    props: {
      chartData: {
        type: Array,
        default: () => [],
        required: true,
      },
      option: {
        type: Object,
        default: () => ({}),
      },
      type: {
        type: String as PropType<string>,
        default: 'line',
      },
      width: {
        type: String as PropType<string>,
        default: '100%',
      },
      height: {
        type: String as PropType<string>,
        default: 'calc(100vh - 300px)',
      },
    },
    emits: ['click'],
    setup(_, { expose }) {
      const unit = 'MW';
      const chartRef = ref<HTMLDivElement | null>(null);
      let chartInstance: echarts.ECharts;
      function initOption({ hData, pData, list }) {
        const option = {
          title: {
            // text: '短期负荷预测曲线',
            // subtext: subtext,
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
            },
            formatter: function (params: any) {
              const a = params[0];
              const h = `<div style="display:flex;flex-direction:column;align-items:start;">
              <label><span style='font-weight:600;'>计算时间</span>：${a.name}</label>`;
              if (params.length === 2) {
                const b = params[1];
                return `${h}
              <label><span style='font-weight:600;'>${a.seriesName}</span>：${a.value[1]} ${unit}</label>
              <label><span style='font-weight:600;'>${b.seriesName}</span>：${b.value[1]} ${unit}</label>
              </div>`;
              } else {
                return `${h}
              <label><span style='font-weight:600;'>${a.seriesName}</span>：${a.value[1]} ${unit}</label>
              </div>`;
              }
            },
          },
          legend: {
            top: 15,
            itemWidth: 20,
            data: ['实际负荷', '预测负荷'],
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none',
              },
              // dataView: { readOnly: false, show: true },
              magicType: { type: ['line', 'bar'] },
              restore: {},
              saveAsImage: {},
            },
          },
          xAxis: {
            name: '时间（月-日 时:分）',
            nameLocation: 'center',
            nameGap: 50,
            type: 'category',
            boundaryGap: false,
            axisLine: { show: true, lineStyle: { color: '#000' } },
            data: [...new Set(list.map((i) => i.time))],
            axisLabel: {
              fontSize: 10,
              formatter: function (params: any) {
                const d = params.split(' ')[0];
                const t = params.split(' ')[1];
                return d.split('-').slice(1).join('-') + ' ' + t.split(':').slice(0, 2).join(':');
              },
            },
          },
          yAxis: {
            name: `负荷（${unit}）`,
            nameLocation: 'center',
            nameGap: 100,
            min: 0,
            max: Math.floor(Math.max(...[...pData, ...hData].map((i) => i[1]))) + 1,
            // max: 100,
            axisLabel: {
              formatter: '{value}',
            },
            axisLine: { show: true, lineStyle: { color: '#000' } },
          },
          series: [
            {
              name: '实际负荷',
              type: 'line',
              data: hData,
              smooth: true,
              itemStyle: {
                normal: {
                  color: '#000', //改变折线点的颜色
                  lineStyle: {
                    color: '#565656', //改变折线颜色
                  },
                },
              },
              markPoint: {
                data: [
                  {
                    type: 'max',
                    name: '最大值',
                  },
                  {
                    type: 'min',
                    name: '最小值',
                  },
                ],
              },
              // markLine: {
              //   data: [{ type: 'average', name: '平均值' }],
              // },
            },
            {
              name: '预测负荷',
              type: 'line',
              smooth: true,
              itemStyle: {
                normal: {
                  color: '#034e86', //改变折线点的颜色
                  lineStyle: {
                    type: 'dashed',
                    color: '#018ffb', //改变折线颜色
                  },
                },
              },
              markPoint: {
                data: [
                  {
                    type: 'max',
                    name: '最大值',
                  },
                  {
                    type: 'min',
                    name: '最小值',
                  },
                ],
              },
              // markLine: {
              //   data: [{ type: 'average', name: '平均值' }],
              // },
              data: pData,
            },
          ],
        };
        return option;
      }

      function initChart(list: { time: string; actual?: number; predict?: number }[]) {
        const hData = list.filter((i) => i.actual).map((l) => [l.time, l.actual]);
        const pData = list.filter((i) => i.predict).map((l) => [l.time, l.predict]);

        if (!chartInstance) return;
        chartInstance.setOption(initOption({ hData, pData, list }), true);
      }
      onMounted(() => {
        chartInstance = echarts.init(chartRef.value);
      });
      expose({ initChart });
      return { chartRef };
    },
  });
</script>
