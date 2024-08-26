package org.jeecg.modules.demo.demowyb1.controller;

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
import org.jeecg.modules.demo.demowyb1.entity.TestTable;
import org.jeecg.modules.demo.demowyb1.service.ITestTableService;

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
 * @Description: 测试表6789
 * @Author: jeecg-boot
 * @Date:   2024-08-12
 * @Version: V1.0
 */
@Api(tags="测试表6789")
@RestController
@RequestMapping("/demowyb1/testTable")
@Slf4j
public class TestTableController extends JeecgController<TestTable, ITestTableService> {
	@Autowired
	private ITestTableService testTableService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testTable
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "测试表6789-分页列表查询")
	@ApiOperation(value="测试表6789-分页列表查询", notes="测试表6789-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestTable>> queryPageList(TestTable testTable,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestTable> queryWrapper = QueryGenerator.initQueryWrapper(testTable, req.getParameterMap());
		Page<TestTable> page = new Page<TestTable>(pageNo, pageSize);
		IPage<TestTable> pageList = testTableService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testTable
	 * @return
	 */
	@AutoLog(value = "测试表6789-添加")
	@ApiOperation(value="测试表6789-添加", notes="测试表6789-添加")
	@RequiresPermissions("demowyb1:test_table:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestTable testTable) {
		testTableService.save(testTable);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testTable
	 * @return
	 */
	@AutoLog(value = "测试表6789-编辑")
	@ApiOperation(value="测试表6789-编辑", notes="测试表6789-编辑")
	@RequiresPermissions("demowyb1:test_table:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestTable testTable) {
		testTableService.updateById(testTable);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "测试表6789-通过id删除")
	@ApiOperation(value="测试表6789-通过id删除", notes="测试表6789-通过id删除")
	@RequiresPermissions("demowyb1:test_table:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testTableService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "测试表6789-批量删除")
	@ApiOperation(value="测试表6789-批量删除", notes="测试表6789-批量删除")
	@RequiresPermissions("demowyb1:test_table:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testTableService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "测试表6789-通过id查询")
	@ApiOperation(value="测试表6789-通过id查询", notes="测试表6789-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestTable> queryById(@RequestParam(name="id",required=true) String id) {
		TestTable testTable = testTableService.getById(id);
		if(testTable==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testTable);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testTable
    */
    @RequiresPermissions("demowyb1:test_table:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestTable testTable) {
        return super.exportXls(request, testTable, TestTable.class, "测试表6789");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("demowyb1:test_table:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestTable.class);
    }

}
