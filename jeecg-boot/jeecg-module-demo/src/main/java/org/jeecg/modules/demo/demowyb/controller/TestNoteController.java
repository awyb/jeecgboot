package org.jeecg.modules.demo.demowyb.controller;

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
import org.jeecg.modules.demo.demowyb.entity.TestNote;
import org.jeecg.modules.demo.demowyb.service.ITestNoteService;

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
 * @Description: 请假单@JS增强示例
 * @Author: jeecg-boot
 * @Date:   2024-08-12
 * @Version: V1.0
 */
@Api(tags="请假单@JS增强示例")
@RestController
@RequestMapping("/demowyb/testNote")
@Slf4j
public class TestNoteController extends JeecgController<TestNote, ITestNoteService> {
	@Autowired
	private ITestNoteService testNoteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testNote
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "请假单@JS增强示例-分页列表查询")
	@ApiOperation(value="请假单@JS增强示例-分页列表查询", notes="请假单@JS增强示例-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestNote>> queryPageList(TestNote testNote,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestNote> queryWrapper = QueryGenerator.initQueryWrapper(testNote, req.getParameterMap());
		Page<TestNote> page = new Page<TestNote>(pageNo, pageSize);
		IPage<TestNote> pageList = testNoteService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testNote
	 * @return
	 */
	@AutoLog(value = "请假单@JS增强示例-添加")
	@ApiOperation(value="请假单@JS增强示例-添加", notes="请假单@JS增强示例-添加")
	@RequiresPermissions("demowyb:test_note:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestNote testNote) {
		testNoteService.save(testNote);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testNote
	 * @return
	 */
	@AutoLog(value = "请假单@JS增强示例-编辑")
	@ApiOperation(value="请假单@JS增强示例-编辑", notes="请假单@JS增强示例-编辑")
	@RequiresPermissions("demowyb:test_note:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestNote testNote) {
		testNoteService.updateById(testNote);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "请假单@JS增强示例-通过id删除")
	@ApiOperation(value="请假单@JS增强示例-通过id删除", notes="请假单@JS增强示例-通过id删除")
	@RequiresPermissions("demowyb:test_note:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testNoteService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "请假单@JS增强示例-批量删除")
	@ApiOperation(value="请假单@JS增强示例-批量删除", notes="请假单@JS增强示例-批量删除")
	@RequiresPermissions("demowyb:test_note:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testNoteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "请假单@JS增强示例-通过id查询")
	@ApiOperation(value="请假单@JS增强示例-通过id查询", notes="请假单@JS增强示例-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestNote> queryById(@RequestParam(name="id",required=true) String id) {
		TestNote testNote = testNoteService.getById(id);
		if(testNote==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testNote);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testNote
    */
    @RequiresPermissions("demowyb:test_note:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestNote testNote) {
        return super.exportXls(request, testNote, TestNote.class, "请假单@JS增强示例");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("demowyb:test_note:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestNote.class);
    }

}
