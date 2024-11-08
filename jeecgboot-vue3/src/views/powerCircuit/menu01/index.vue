<template>
  <PageWrapper style="" :dense="true" :contentFullHeight="true" fixedHeight>
    <template #headerContent>
      <label style="font-size: 20px; font-weight: 600"><Icon icon="ant-design:control-outlined" size="22" style="margin-right: 5px" />设备管理</label>
    </template>
    <div style="width: 100%; height: 100%; display: flex; justify-content: space-around; padding: 10px">
      <div style="height: inherit; width: 20%; box-shadow: 0px 0px 2px 2px #e4e4e4">
        <BasicTree :treeData="treeData" style="font-size: 16px; padding: 10px" title="数据列表" :search="true" :expandedKeys="expandedKeys" />
      </div>
      <div style="height: 100%; width: 78%; text-align: center; overflow: hidden">
        <PowerCellList style="height: 100%" />
      </div>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { PageWrapper } from '/@/components/Page';
  import { TreeItem } from '/@/components/Tree/index';
  import { BasicTree } from '/@/components/Tree/index';
  import TestTableList from '../../demowyb1/TestTableList.vue';
  import PowerCellList from '../../powercell/PowerCellList.vue';
  export default defineComponent({
    components: { PageWrapper, BasicTree, TestTableList, PowerCellList },
    setup() {
      const treeData: TreeItem[] = [
        {
          title: 'parent 1',
          key: '0-0',
          icon: 'ant-design:folder-outlined',
          children: [
            { title: 'leaf1', key: '0-0-0', icon: 'ant-design:file-outlined' },
            {
              title: 'leaf2',
              key: '0-0-1',
              icon: 'ant-design:folder-outlined',
              children: [
                { title: 'leaf2-1', key: '0-0-0-0', icon: 'ant-design:file-outlined' },
                { title: 'leaf2-2', key: '0-0-0-1', icon: 'ant-design:file-outlined' },
              ],
            },
          ],
        },
        {
          title: 'parent 2',
          key: '1-1',
          icon: 'ant-design:folder-outlined',
          children: [
            { title: 'leaf3', key: '1-1-0', icon: 'ant-design:file-outlined' },
            { title: 'leaf4', key: '1-1-1', icon: 'ant-design:file-outlined' },
          ],
        },
        {
          title: 'parent 3',
          key: '2-2',
          icon: 'ant-design:folder-outlined',
          children: [
            { title: 'leaf5', key: '2-2-0', icon: 'ant-design:file-outlined' },
            { title: 'leaf6', key: '2-2-1', icon: 'ant-design:file-outlined' },
          ],
        },
      ];
      const expandedKeys = ['0-0-0', '0-0-1'];
      const checked = ref(true);
      return { treeData, expandedKeys, checked };
    },
  });
</script>
