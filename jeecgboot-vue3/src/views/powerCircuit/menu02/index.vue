<template>
  <PageWrapper style="background: #ffffff" :dense="true" :contentFullHeight="true" fixedHeight>
    <template #headerContent>
      <label style="font-size: 20px; font-weight: 600">
        <Icon icon="ant-design:dashboard-filled" size="22" style="margin-right: 5px" />
        安全校正控制计算
        <a-tooltip placement="right">
          <template #title>手动更新</template>
          <Icon icon="ant-design:sync-outlined" size="24" style="margin-left: 20px; cursor: pointer" @click="refresh" /> </a-tooltip
      ></label>
    </template>
    <div style="width: 100%; height: 100%; display: flex; justify-content: space-around; padding: 10px">
      <div style="height: inherit; width: 20%; box-shadow: 0px 0px 2px 2px #e4e4e4; display: flex; flex-direction: column">
        <BasicTree
          ref="tree"
          style="font-size: 16px; padding: 10px; flex: 1"
          title="数据列表"
          checkable
          toolbar
          :treeData="treeData"
          :search="true"
          :expandedKeys="expandedKeys"
          @check="onCheck"
          @toolbar-call="toolbarCall"
        />
        <div style="margin: 10px; display: flex; justify-content: space-evenly">
          <a-button type="primary" size="small" preIcon="ant-design:edit-outlined" :disabled="!isUse" @click="openModal(true, {})">矫正计算</a-button>
          <a-button type="primary" size="small" preIcon="ant-design:calculator-outlined" @click="calculation">潮流计算</a-button>
        </div>
      </div>
      <div style="height: 100%; width: 78%; border: 2px solid; text-align: center; overflow: hidden">
        <v3-drag-zoom-container ref="zoom" style="height: 100%; width: 100%">
          <div id="svgcontainer" class="wrap"></div>
        </v3-drag-zoom-container>
      </div>
    </div>
    <BasicModal
      width="700px"
      :dialogStyle="{ left: '-200px' }"
      destroyOnClose
      :mask="false"
      :canFullscreen="false"
      :maskClosable="false"
      :minHeight="400"
      :title="'安全矫正控制计算'"
      @register="registerModal"
      @ok="handleSubmit"
    >
      <template #footer></template>
      <CorrectCalculate :dataArr="checkedArr" :overloadIds="overloadIds" @remove-load="removeLoad" />
    </BasicModal>
  </PageWrapper>
</template>
<script lang="ts">
  import { Icon } from '/@/components/Icon';
  import power from '../../../../public/resource/img/电网.svg';
  import { defineComponent, onMounted, reactive, ref, h } from 'vue';
  import { PageWrapper } from '/@/components/Page';
  import { TreeItem } from '/@/components/Tree/index';
  import { BasicTree } from '/@/components/Tree/index';
  import { V3DragZoomContainer } from 'v3-drag-zoom';
  import { useModal, BasicModal } from '@/components/Modal';
  import CorrectCalculate from './CorrectCalculate.vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  let _checked = [];
  let _timer = {};
  export default defineComponent({
    components: { PageWrapper, BasicTree, Icon, V3DragZoomContainer, BasicModal, CorrectCalculate },
    setup() {
      const tree = ref(null);
      const checkedArr = reactive([]);
      const overloadIds = ref([]);
      const zoom = ref(null);
      const isUse = ref(false);
      const [registerModal, { openModal: openModal, closeModal }] = useModal();
      const { notification } = useMessage();
      const transformer1_ids = ['svg_220', 'svg_221', 'svg_222'];
      const transformer2_ids = ['svg_198', 'svg_199', 'svg_200'];
      const transformer3_ids = ['svg_176', 'svg_177', 'svg_178'];
      const generatrix_ids1 = ['svg_60'];
      const generatrix_ids2 = ['svg_50'];
      const generatrix_ids3 = ['svg_38'];
      const cable_id1 = ['svg_140']; //外部电网
      const cable_id2 = ['svg_128']; //科秀线
      const cable_id3 = ['svg_116']; //科横线
      const cable_id4 = ['svg_100']; //科铁朝万线
      const cable_id5 = ['svg_86']; //科尧滨线
      const cable_id6 = ['svg_154']; //科五线
      const overload_text = { cable1: 'svg_139', cable2: 'svg_59', cable3: 'svg_85', cable4: 'svg_99', cable5: 'svg_115', cable6: 'svg_127' };
      const treeData: TreeItem[] = [
        {
          title: '变压器',
          key: 'transformer',
          icon: 'ant-design:folder-outlined',
          children: [
            {
              title: '变压器#1',
              key: 'transformer1',
              icon: 'ant-design:file-outlined',
              ids: transformer1_ids,
              defaultColor: 'black',
              checkColor: '#3366ff',
              type: 'transformer',
              isLeaf: true,
            },
            {
              title: '变压器#2',
              key: 'transformer2',
              icon: 'ant-design:file-outlined',
              ids: transformer2_ids,
              defaultColor: 'black',
              checkColor: '#3366ff',
              type: 'transformer',
              isLeaf: true,
            },
            {
              title: '变压器#3',
              key: 'transformer3',
              icon: 'ant-design:file-outlined',
              ids: transformer3_ids,
              defaultColor: 'black',
              checkColor: '#3366ff',
              type: 'transformer',
              isLeaf: true,
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
          title: '母线',
          key: 'generatrix',
          icon: 'ant-design:folder-outlined',
          children: [
            {
              title: '母线#1',
              key: 'generatrix1',
              icon: 'ant-design:file-outlined',
              ids: generatrix_ids1,
              defaultColor: 'black',
              checkColor: '#00ff00',
              type: 'generatrix',
              isLeaf: true,
            },
            {
              title: '母线#2',
              key: 'generatrix2',
              icon: 'ant-design:file-outlined',
              ids: generatrix_ids2,
              defaultColor: 'black',
              checkColor: '#00ff00',
              type: 'generatrix',
              isLeaf: true,
            },
            {
              title: '母线#3',
              key: 'generatrix3',
              icon: 'ant-design:file-outlined',
              ids: generatrix_ids3,
              defaultColor: 'black',
              checkColor: '#00ff00',
              type: 'generatrix',
              isLeaf: true,
            },
          ],
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
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
            {
              title: '科秀线',
              key: 'cable2',
              icon: 'ant-design:file-outlined',
              ids: cable_id2,
              defaultColor: 'black',
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
            {
              title: '科横线',
              key: 'cable3',
              icon: 'ant-design:file-outlined',
              ids: cable_id3,
              defaultColor: 'black',
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
            {
              title: '科铁朝万线',
              key: 'cable4',
              icon: 'ant-design:file-outlined',
              ids: cable_id4,
              defaultColor: 'black',
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
            {
              title: '科尧滨线',
              key: 'cable5',
              icon: 'ant-design:file-outlined',
              ids: cable_id5,
              defaultColor: 'black',
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
            {
              title: '科五线',
              key: 'cable6',
              icon: 'ant-design:file-outlined',
              ids: cable_id6,
              defaultColor: 'black',
              checkColor: 'yellow',
              type: 'cable',
              isLeaf: true,
            },
          ],
        },
      ];
      const expandedKeys = ['transformer', 'cable', 'generatrix'];
      function getNodeByKey(key: string) {
        return treeData
          .map((a) => a.children)
          .flat(Infinity)
          .find((n) => (n as any).key === key) as any;
      }
      // 添加超载提示
      function onOverload(key: string, overColor = 'red') {
        const node = getNodeByKey(key);
        if (!node) return;
        const arr: any[] = [];
        const element_opacity = document.getElementById(overload_text[node.key]);
        if (element_opacity) {
          const timer_opacity = setInterval(() => {
            element_opacity?.setAttribute('opacity', element_opacity.getAttribute('opacity') === '1' ? '0' : '1');
          }, 500);
          arr.push(timer_opacity);
        }

        // 原来有闪烁线的，但是去选中冲突，去掉了
        // node.ids.forEach((id: string) => {
        //   const element = document.getElementById(id);
        //   const timer = setInterval(() => {
        //     element?.setAttribute('stroke', element.getAttribute('stroke') === overColor ? defaultColor : overColor);
        //   }, 500);
        //   arr.push(timer);
        // });
        _timer[node.key] = arr;
        (overloadIds.value as Array<string>).push(key);
      }
      // 取消超载提示
      function offOverload(key) {
        const node = getNodeByKey(key);
        if (!node) return;
        document.getElementById(overload_text[node.key])?.setAttribute('opacity', '0');
        if (_timer[node.key]) {
          _timer[node.key].forEach((timer: any) => {
            clearInterval(timer);
          });
          delete _timer[node.key];
        }
        overloadIds.value = overloadIds.value.filter((id: string) => id !== node.key);
      }
      // 选中
      function onCheck(checkedKeys) {
        const setColor = (id, key) => {
          const t = (tree.value as any).getSelectedNode(id);
          if (t && t.ids && t[key]) {
            t.ids.forEach((id: string) => {
              const element = document.getElementById(id);
              element?.setAttribute('stroke', t[key]);
            });
          }
        };
        isUse.value = Boolean(checkedKeys.length);
        // 新勾选的
        checkedKeys.forEach((id: string) => {
          if (!_checked.includes(id as never)) {
            setColor(id, 'checkColor');
          }
        });
        // 去掉勾选的
        _checked.forEach((id: string) => {
          if (!checkedKeys.includes(id as never)) {
            setColor(id, 'defaultColor');
          }
        });
        // 选中的节点
        checkedArr.length = 0;
        checkedKeys.forEach((id: string) => {
          const node = (tree.value as any).getSelectedNode(id);
          if (node && node.isLeaf) (checkedArr as unknown as any[]).push(node);
        });
        _checked = checkedKeys;
      }
      // 全部勾选回调
      function toolbarCall() {
        onCheck((tree.value as any).getCheckedKeys());
      }
      // 潮流文字添加
      function appendText(id: string, vals: Array<string>) {
        if (!vals.length) return;
        const source = document.getElementById(id);
        const createTspan = (val) => {
          const tspan = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
          tspan.textContent = val;
          return tspan;
        };
        if (source) {
          source.textContent = '';
          const x = source.getAttribute('x');
          source.appendChild(createTspan(vals[0]));
          for (let i = 1; i < vals.length; i++) {
            const tspan = createTspan(vals[i]);
            tspan.setAttribute('x', x + '');
            tspan.setAttribute('dy', 6 * i + '');
            source.appendChild(tspan);
          }
        }
      }
      // 弹窗点击提交
      function handleSubmit() {
        closeModal();
      }
      // 加载svg
      function loadSvg() {
        const svg = fetch('/resource/img/电网.svg');
        return new Promise<Boolean>((resolve, reject) => {
          svg
            .then((res) => {
              res
                .text()
                .then((svgContent) => {
                  let container = document.getElementById('svgcontainer');
                  if (container) container.innerHTML = svgContent;
                  resolve(true);
                })
                .catch(reject);
            })
            .catch(reject);
        });
      }
      function calculation() {
        appendText('svg_229', [Math.floor(Math.random() * 50 + 50) + 'MW', Math.floor(Math.random() * 10 + 10) + 'Mvar']);
        appendText('svg_230', [Math.floor(Math.random() * 50 + 50) + 'MW', Math.floor(Math.random() * 10 + 10) + 'Mvar']);
        appendText('svg_209', [Math.floor(Math.random() * 50 + 50) + 'MW', Math.floor(Math.random() * 10 + 10) + 'Mvar']);
      }
      function refresh() {
        const keys = ['cable3', 'cable6', 'cable1', 'cable2', 'cable4', 'cable5'];
        for (let i = 0; i < keys.length; i++) {
          if ((overloadIds.value as Array<string>).includes(keys[i])) continue;
          onOverload(keys[i]);
          notification.warning({
            message: '线路过载',
            description: h('label', null, [
              h('span', null, '线路'),
              h('span', { style: { 'font-weight': 'bold', color: '#000022' } }, getNodeByKey(keys[i])?.title),
              h('span', null, '超限'),
            ]),
            // description: `线路<i>${getNodeByKey(keys[i])?.title}</i>超限`,
          });
        }
      }
      function removeLoad(info) {
        offOverload(info.key);

        notification.success({
          message: '切除成功',
          description: h('label', null, [
            h('span', null, '设备'),
            h('span', { style: { 'font-weight': 'bold', color: '#000022' } }, info.title),
            h('span', null, '切除负荷量：20MW'),
          ]),
          // description: `设备${info.title}切除负荷量：20MW`,
        });
      }
      onMounted(async () => {
        await loadSvg();
      });

      return {
        treeData,
        expandedKeys,
        power,
        tree,
        zoom,
        checkedArr,
        isUse,
        overloadIds,
        toolbarCall,
        onCheck,
        registerModal,
        openModal,
        handleSubmit,
        refresh,
        calculation,
        removeLoad,
      };
    },
  });
</script>
<style scoped>
  .jeecg-page-wrapper {
    background: #fff;
  }
  .wrap {
    scale: 1.7;
  }
</style>
