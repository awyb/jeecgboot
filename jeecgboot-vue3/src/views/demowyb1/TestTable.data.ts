import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '字段1',
    align: 'center',
    dataIndex: 'colname1',
  },
  {
    title: '字段2',
    align: 'center',
    dataIndex: 'colname2',
  },
  {
    title: '字段3',
    align: 'center',
    dataIndex: 'colname3',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '创建日期',
    align: 'center',
    dataIndex: 'createTime',
  },
  {
    title: '更新日期',
    align: 'center',
    dataIndex: 'updateTime',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '字段1',
    field: 'colname1',
    component: 'Input',
    //colProps: {span: 6},
  },
  {
    label: '字段2',
    field: 'colname2',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
    //colProps: {span: 6},
  },
  {
    label: '字段3',
    field: 'colname3',
    component: 'JSwitch',
    componentProps: {
      query: true,
    },
    //colProps: {span: 6},
  },
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
    //colProps: {span: 6},
  },
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '字段1',
    field: 'colname1',
    component: 'Input',
  },
  {
    label: '字段2',
    field: 'colname2',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
  },
  {
    label: '字段3',
    field: 'colname3',
    component: 'JSwitch',
    componentProps: {},
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入字段3!' }];
    },
  },
  {
    label: '创建日期',
    field: 'createTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  colname1: { title: '字段1', order: 0, view: 'text', type: 'string' },
  colname2: { title: '字段2', order: 1, view: 'pca', type: 'string' },
  colname3: { title: '字段3', order: 2, view: 'switch', type: 'string' },
  createTime: { title: '创建日期', order: 3, view: 'datetime', type: 'string' },
  updateTime: { title: '更新日期', order: 4, view: 'datetime', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
