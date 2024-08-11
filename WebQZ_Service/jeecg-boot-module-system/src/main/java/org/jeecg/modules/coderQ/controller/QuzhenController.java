package org.jeecg.modules.coderQ.controller;

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
import org.jeecg.modules.coderQ.entity.Quzhen;
import org.jeecg.modules.coderQ.service.IQuzhenService;

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

 /**
 * @Description: bk_quzhen
 * @Author: WEB取证
 * @Date:   2023-04-01
 * @Version: V1.0
 */
@Api(tags="bk_quzhen")
@RestController
@RequestMapping("/coderQ/quzhen")
@Slf4j
public class QuzhenController extends JeecgController<Quzhen, IQuzhenService> {
	@Autowired
	private IQuzhenService quzhenService;

	/**
	 * 分页列表查询
	 *
	 * @param quzhen
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-分页列表查询")
	@ApiOperation(value="bk_quzhen-分页列表查询", notes="bk_quzhen-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Quzhen quzhen,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Quzhen> queryWrapper = QueryGenerator.initQueryWrapper(quzhen, req.getParameterMap());
		Page<Quzhen> page = new Page<Quzhen>(pageNo, pageSize);
		IPage<Quzhen> pageList = quzhenService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param quzhen
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-添加")
	@ApiOperation(value="bk_quzhen-添加", notes="bk_quzhen-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Quzhen quzhen) {
		quzhenService.save(quzhen);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param quzhen
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-编辑")
	@ApiOperation(value="bk_quzhen-编辑", notes="bk_quzhen-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Quzhen quzhen) {
		quzhenService.updateById(quzhen);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-通过id删除")
	@ApiOperation(value="bk_quzhen-通过id删除", notes="bk_quzhen-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		quzhenService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-批量删除")
	@ApiOperation(value="bk_quzhen-批量删除", notes="bk_quzhen-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.quzhenService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bk_quzhen-通过id查询")
	@ApiOperation(value="bk_quzhen-通过id查询", notes="bk_quzhen-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Quzhen quzhen = quzhenService.getById(id);
		if(quzhen==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(quzhen);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param quzhen
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Quzhen quzhen) {
        return super.exportXls(request, quzhen, Quzhen.class, "bk_quzhen");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Quzhen.class);
    }

}
