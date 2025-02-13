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
import org.jeecg.modules.coderQ.entity.News;
import org.jeecg.modules.coderQ.service.INewsService;

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
 * @Description: bk_news
 * @Author:
 * @Date:   2023-03-25
 * @Version: V1.0
 */
@Api(tags="bk_news")
@RestController
@RequestMapping("/coderQ/news")
@Slf4j
public class NewsController extends JeecgController<News, INewsService> {
	@Autowired
	private INewsService newsService;

	/**
	 * 分页列表查询
	 *
	 * @param news
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "bk_news-分页列表查询")
	@ApiOperation(value="bk_news-分页列表查询", notes="bk_news-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(News news,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<News> queryWrapper = QueryGenerator.initQueryWrapper(news, req.getParameterMap());
		Page<News> page = new Page<News>(pageNo, pageSize);
		IPage<News> pageList = newsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param news
	 * @return
	 */
	@AutoLog(value = "bk_news-添加")
	@ApiOperation(value="bk_news-添加", notes="bk_news-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody News news) {
		newsService.save(news);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param news
	 * @return
	 */
	@AutoLog(value = "bk_news-编辑")
	@ApiOperation(value="bk_news-编辑", notes="bk_news-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody News news) {
		newsService.updateById(news);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bk_news-通过id删除")
	@ApiOperation(value="bk_news-通过id删除", notes="bk_news-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		newsService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bk_news-批量删除")
	@ApiOperation(value="bk_news-批量删除", notes="bk_news-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.newsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bk_news-通过id查询")
	@ApiOperation(value="bk_news-通过id查询", notes="bk_news-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		News news = newsService.getById(id);
		if(news==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(news);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param news
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, News news) {
        return super.exportXls(request, news, News.class, "bk_news");
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
        return super.importExcel(request, response, News.class);
    }

}
