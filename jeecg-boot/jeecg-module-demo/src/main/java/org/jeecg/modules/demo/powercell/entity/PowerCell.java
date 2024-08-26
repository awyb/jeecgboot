package org.jeecg.modules.demo.powercell.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 电力元件表
 * @Author: jeecg-boot
 * @Date:   2024-08-15
 * @Version: V1.0
 */
@Data
@TableName("power_cell")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="power_cell对象", description="电力元件表")
public class PowerCell implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**元件类型*/
	@Excel(name = "元件类型", width = 15, dicCode = "cell_type")
	@Dict(dicCode = "cell_type")
    @ApiModelProperty(value = "元件类型")
    private java.lang.String cellType;
	/**元件名称*/
	@Excel(name = "元件名称", width = 15)
    @ApiModelProperty(value = "元件名称")
    private java.lang.String cellName;
	/**元件编号*/
	@Excel(name = "元件编号", width = 15)
    @ApiModelProperty(value = "元件编号")
    private java.lang.String cellCode;
	/**当前载流量*/
	@Excel(name = "当前载流量", width = 15)
    @ApiModelProperty(value = "当前载流量")
    private java.lang.String curLoadFlow;
	/**预测值*/
	@Excel(name = "预测值", width = 15)
    @ApiModelProperty(value = "预测值")
    private java.lang.String pv;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
}
