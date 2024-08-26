package org.jeecg.modules.demo.powercell.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.powercell.entity.PowerCell;
import org.jeecg.modules.demo.powercell.service.IPowerCellService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 电力元件表
 * @Author: jeecg-boot
 * @Date:   2024-08-15
 * @Version: V1.0
 */
@Api(tags="电力元件表")
@RestController
@RequestMapping("/powercell/powerCell")
@Slf4j
public class PowerCellController extends JeecgController<PowerCell, IPowerCellService> {
	@Autowired
	private IPowerCellService powerCellService;
	
	/**
	 * 分页列表查询
	 *
	 * @param powerCell
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "电力元件表-分页列表查询")
	@ApiOperation(value="电力元件表-分页列表查询", notes="电力元件表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PowerCell>> queryPageList(PowerCell powerCell,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PowerCell> queryWrapper = QueryGenerator.initQueryWrapper(powerCell, req.getParameterMap());
		Page<PowerCell> page = new Page<PowerCell>(pageNo, pageSize);
		IPage<PowerCell> pageList = powerCellService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param powerCell
	 * @return
	 */
	@AutoLog(value = "电力元件表-添加")
	@ApiOperation(value="电力元件表-添加", notes="电力元件表-添加")
	@RequiresPermissions("powercell:power_cell:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PowerCell powerCell) {
		powerCellService.save(powerCell);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param powerCell
	 * @return
	 */
	@AutoLog(value = "电力元件表-编辑")
	@ApiOperation(value="电力元件表-编辑", notes="电力元件表-编辑")
	@RequiresPermissions("powercell:power_cell:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PowerCell powerCell) {
		powerCellService.updateById(powerCell);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电力元件表-通过id删除")
	@ApiOperation(value="电力元件表-通过id删除", notes="电力元件表-通过id删除")
	@RequiresPermissions("powercell:power_cell:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		powerCellService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电力元件表-批量删除")
	@ApiOperation(value="电力元件表-批量删除", notes="电力元件表-批量删除")
	@RequiresPermissions("powercell:power_cell:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.powerCellService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "电力元件表-通过id查询")
	@ApiOperation(value="电力元件表-通过id查询", notes="电力元件表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PowerCell> queryById(@RequestParam(name="id",required=true) String id) {
		PowerCell powerCell = powerCellService.getById(id);
		if(powerCell==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(powerCell);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param powerCell
    */
    @RequiresPermissions("powercell:power_cell:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PowerCell powerCell) {
        return super.exportXls(request, powerCell, PowerCell.class, "电力元件表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("powercell:power_cell:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PowerCell.class);
    }

}
