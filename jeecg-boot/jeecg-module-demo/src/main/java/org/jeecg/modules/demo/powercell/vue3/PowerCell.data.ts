import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '元件类型',
    align:"center",
    dataIndex: 'cellType_dictText'
   },
   {
    title: '元件名称',
    align:"center",
    dataIndex: 'cellName'
   },
   {
    title: '元件编号',
    align:"center",
    dataIndex: 'cellCode'
   },
   {
    title: '当前载流量',
    align:"center",
    dataIndex: 'curLoadFlow'
   },
   {
    title: '预测值',
    align:"center",
    dataIndex: 'pv'
   },
   {
    title: '更新日期',
    align:"center",
    dataIndex: 'updateTime'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "元件类型",
      field: 'cellType',
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"cell_type"
      },
      //colProps: {span: 6},
 	},
	{
      label: "元件名称",
      field: 'cellName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "当前载流量",
      field: 'curLoadFlow',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '元件类型',
    field: 'cellType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"cell_type"
     },
  },
  {
    label: '元件名称',
    field: 'cellName',
    component: 'Input',
  },
  {
    label: '元件编号',
    field: 'cellCode',
    component: 'Input',
  },
  {
    label: '当前载流量',
    field: 'curLoadFlow',
    component: 'Input',
  },
  {
    label: '预测值',
    field: 'pv',
    component: 'Input',
  },
  {
    label: '更新日期',
    field: 'updateTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  cellType: {title: '元件类型',order: 0,view: 'list', type: 'string',dictCode: 'cell_type',},
  cellName: {title: '元件名称',order: 1,view: 'text', type: 'string',},
  cellCode: {title: '元件编号',order: 2,view: 'text', type: 'string',},
  curLoadFlow: {title: '当前载流量',order: 3,view: 'text', type: 'string',},
  pv: {title: '预测值',order: 4,view: 'text', type: 'string',},
  updateTime: {title: '更新日期',order: 5,view: 'datetime', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}