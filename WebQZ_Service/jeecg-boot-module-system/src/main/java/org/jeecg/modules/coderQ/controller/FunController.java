package org.jeecg.modules.coderQ.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.coderQ.entity.*;
import org.jeecg.modules.coderQ.service.*;
import org.jeecg.modules.coderQ.util.DownLoadUtil;
import org.jeecg.modules.coderQ.util.ScreenUtil;
import org.jeecg.modules.coderQ.util.SessionUser;
import org.jeecg.modules.coderQ.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("coderQ/prefun")
public class FunController {

    @Autowired
    private INewsService newsService;

    @Autowired
    private IYewuService yewuService;

    @Autowired
    private IZixunService zixunService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IApplyService applyService;

    @Autowired
    private IQuzhenService quzhenService;


    @GetMapping("getAllNews")
    public Result<?> getAllNews() {
        LambdaQueryWrapper<News> query = new LambdaQueryWrapper<>();
        query.orderByDesc(News::getCreateTime);
        List<News> list = newsService.list(query);
        return Result.OK(list);
    }


    @GetMapping("getNewsDetail")
    public Result<?> getNewsDetail(String id) {
        News news = newsService.getById(id);
        return Result.OK(news);
    }

    @GetMapping("getAllYewu")
    public Result<?> getAllYewu() {
        LambdaQueryWrapper<Yewu> query = new LambdaQueryWrapper<>();
        query.orderByDesc(Yewu::getCreateTime);
        List<Yewu> list = yewuService.list(query);
        return Result.OK(list);
    }


    @GetMapping("getYeWuDetail")
    public Result<?> getYeWuDetail(String id) {
        Yewu byId = yewuService.getById(id);
        return Result.OK(byId);
    }

    @GetMapping("getAllZiXun")
    public Result<?> getAllZiXun() {
        LambdaQueryWrapper<Zixun> query = new LambdaQueryWrapper<>();
        query.orderByDesc(Zixun::getCreateTime);
        List<Zixun> list = zixunService.list(query);
        return Result.OK(list);
    }


    @GetMapping("getZiXunDetail")
    public Result<?> getZiXunDetail(String id) {
        Zixun news = zixunService.getById(id);
        return Result.OK(news);
    }

    @GetMapping("getMyComment")
    public Result<?> getMyComment(String id) {
        LambdaQueryWrapper<Comment> query = new LambdaQueryWrapper<>();
        query.eq(Comment::getUserid, id);
        query.orderByDesc(Comment::getCreateTime);
        List<Comment> list = commentService.list(query);
        return Result.OK(list);
    }


    @GetMapping("doComment")
    public Result<?> doComment(String id, String content, String name) {
        Comment comment = new Comment();
        comment.setUserid(id);
        comment.setContent(content);
        comment.setUsername(name);
        comment.setCommenttime(new Date());
        commentService.save(comment);
        return Result.OK();
    }


    @PostMapping("doApply")
    public Result<?> doApply(@RequestBody Apply apply) {
        applyService.save(apply);
        return Result.OK();
    }


    @GetMapping("getMyApply")
    public Result<?> getMyApply(String id) {
        LambdaQueryWrapper<Apply> query = new LambdaQueryWrapper<>();
        query.eq(Apply::getUserid, id);
        query.orderByDesc(Apply::getCreateTime);
        List<Apply> list = applyService.list(query);
        return Result.OK(list);
    }

    @GetMapping("doQuZhen")
    public Result<?> doQuZhen(String url, Integer time, HttpServletRequest request) {
        SessionUser sessionUser = SessionUtil.getSessionUser(request);
        Quzhen quzhen = new Quzhen();
        quzhen.setUserid(sessionUser.getId());
        quzhen.setUsername(sessionUser.getName());
        quzhen.setTime(time);
        quzhen.setQzzt(1);
        quzhen.setUrl(url);
        quzhenService.save(quzhen);
        for (int i = 0; i < time; i++) {
            new Thread(() -> {
                ScreenUtil.doScreen(url, sessionUser.getName(), quzhen.getId());
            }).start();
        }
        String fileUrl = "D:\\opt\\upFiles\\" + sessionUser.getName() + quzhen.getId() + "\\";

    //    new Thread(() -> {
            try {
                new DownLoadUtil().download(url, fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
      //  });

        return Result.OK();
    }

    @GetMapping("getMyQuZhen")
    public Result<?> getMyQuZhen(String id) {
        LambdaQueryWrapper<Quzhen> query = new LambdaQueryWrapper<>();
        query.eq(Quzhen::getUserid, id);
        query.orderByDesc(Quzhen::getCreateTime);
        List<Quzhen> list = quzhenService.list(query);
        return Result.OK(list);
    }

    @GetMapping("checkQz")
    public Result<?> checkQz(String id) {
        Quzhen quzhen = quzhenService.getById(id);
        String username = quzhen.getUsername();
        String fileUrl = "D:\\opt\\upFiles\\" + username + quzhen.getId();
        File fileDir = new File(fileUrl);
        if (!fileDir.exists()) {
            return Result.error("暂未完成取证，请稍后重试！");
        } else {
            return Result.OK();
        }
    }


    @GetMapping("downloadQz")
    public void downloadQz(String id, HttpServletResponse response) {
        try {
            Quzhen quzhen = quzhenService.getById(id);
            String username = quzhen.getUsername();
            String fileUrl = "D:\\opt\\upFiles\\" + username + quzhen.getId();
            File fileDir = new File(fileUrl);
            if (!fileDir.exists()) {
                throw new Exception("无文件生成");
            }
            String downloadName = username + quzhen.getId() + ".zip";
            // 将文件进行打包下载

            OutputStream os = response.getOutputStream();
            // 接收压缩包字节
            byte[] data = createZip(fileUrl);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "*");
            // 下载文件名乱码问题
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadName, "UTF-8"));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            IOUtils.write(data, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建zip文件
     *
     * @param sourceFilePath 文件路径
     * @return byte[]
     */
    private static byte[] createZip(String sourceFilePath) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 将目标文件打包成zip导出
        File file = new File(sourceFilePath);
        handlerFile(zip, file, "");
        // 无异常关闭流，它将无条件的关闭一个可被关闭的对象而不抛出任何异常。
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 打包处理
     *
     * @param zip  压缩
     * @param file 文件
     * @param dir  路径
     */
    private static void handlerFile(ZipOutputStream zip, File file, String dir) throws Exception {
        // 如果当前的是文件夹，则循环里面的内容继续处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] fileArray = file.listFiles();
            if (fileArray == null) {
                return;
            }
            //将文件夹添加到下一级打包目录
            zip.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            // 递归将文件夹中的文件打包
            for (File f : fileArray) {
                handlerFile(zip, f, dir + f.getName());
            }
        } else {
            // 如果当前的是文件，打包处理
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(dir);
            zip.putNextEntry(entry);
            zip.write(FileUtils.readFileToByteArray(file));
            IOUtils.closeQuietly(bis);
            zip.flush();
            zip.closeEntry();
        }
    }

}

