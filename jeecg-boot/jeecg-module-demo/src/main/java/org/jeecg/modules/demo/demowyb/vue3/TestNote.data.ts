import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '用户名',
    align:"center",
    sorter: true,
    dataIndex: 'name'
   },
   {
    title: '年龄',
    align:"center",
    sorter: true,
    dataIndex: 'age'
   },
   {
    title: '性别',
    align:"center",
    dataIndex: 'sex_dictText'
   },
   {
    title: '生日',
    align:"center",
    dataIndex: 'birthday',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '请假原因',
    align:"center",
    dataIndex: 'contents',
   },
   {
    title: '地区',
    align:"center",
    dataIndex: 'sheng',
   },
   {
    title: '年',
    align:"center",
    dataIndex: 'year',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      if(text) {
        return getWeekMonthQuarterYear(text)['year'];
      } else {
        return text;
      }
    },
   },
   {
    title: '月',
    align:"center",
    dataIndex: 'month',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      if(text) {
        return getWeekMonthQuarterYear(text)['month'];
      } else {
        return text;
      }
    },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "用户名",
      field: 'name',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
        const {setFieldsValue} = formActionType;
        return{
            setFieldsValue:setFieldsValue,
            code:"tj_user_report",
            fieldConfig: [
                { source: 'username', target: 'name' },
            ],
            multi:true
        }
    },

      //colProps: {span: 6},
 	},
     {
      label: "年龄",
      field: "age",
      component: 'JRangeNumber',
      //colProps: {span: 6},
	},
	{
      label: "性别",
      field: 'sex',
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"sex"
      },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '用户名',
    field: 'name',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
        const {setFieldsValue} = formActionType;
        return{
            setFieldsValue:setFieldsValue,
            code:"tj_user_report",
            fieldConfig: [
                { source: 'username', target: 'name' },
            ],
            multi:true
        }
    },

  },
  {
    label: '年龄',
    field: 'age',
    component: 'InputNumber',
  },
  {
    label: '性别',
    field: 'sex',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sex"
     },
  },
  {
    label: '生日',
    field: 'birthday',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
  },
  {
    label: '请假原因',
    field: 'contents',
    component: 'JEditor',
  },
  {
    label: '地区',
    field: 'sheng',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
  },
  {
    label: '年',
    field: 'year',
    component: 'DatePicker',
    componentProps: {
      picker: 'year',
      valueFormat: 'YYYY-MM-DD'
    },
  },
  {
    label: '月',
    field: 'month',
    component: 'DatePicker',
    componentProps: {
      picker: 'month',
      valueFormat: 'YYYY-MM-DD'
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
  name: {title: '用户名',order: 0,view: 'popup', type: 'string',code: 'tj_user_report', orgFields: 'username', destFields: 'name', popupMulti: false,},
  age: {title: '年龄',order: 1,view: 'number', type: 'number',},
  sex: {title: '性别',order: 2,view: 'list', type: 'string',dictCode: 'sex',},
  birthday: {title: '生日',order: 3,view: 'date', type: 'string',},
  contents: {title: '请假原因',order: 4,view: 'umeditor', type: 'string',},
  sheng: {title: '地区',order: 5,view: 'pca', type: 'string',},
  year: {title: '年',order: 6,view: 'date', type: 'string',},
  month: {title: '月',order: 7,view: 'date', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}