package org.jeecg.modules.coderQ.controller;

import org.jeecg.modules.coderQ.util.SessionUser;
import org.jeecg.modules.coderQ.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
@RequestMapping("coderQ")
public class FrontController {

    @GetMapping("index")
    public String index(HttpServletRequest request) {
        SessionUser sessionUser = SessionUtil.getSessionUser(request);
        System.out.println(sessionUser);
        System.out.println(SessionUtil.checkLogin(request));
        return "index";
    }


    @GetMapping("personal")
    public String personal() {
        return "personal";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

    @GetMapping("updatepwd")
    public String updatepwd() {
        return "updatepwd";
    }

    @GetMapping("news")
    public String news() {
        return "news";
    }

    @GetMapping("newsdetail")
    public String newsdetail() {
        return "newsdetail";
    }

    @GetMapping("yewu")
    public String yewu() {
        return "yewu";
    }

    @GetMapping("yewudetail")
    public String yewudetail() {
        return "yewudetail";
    }

    @GetMapping("zixun")
    public String zixun() {
        return "zixun";
    }

    @GetMapping("zixundetail")
    public String zixundetail() {
        return "zixundetail";
    }

    @GetMapping("myapply")
    public String myapply() {
        return "myapply";
    }

    @GetMapping("mycomment")
    public String mycomment() {
        return "mycomment";
    }

    @GetMapping("quzhen")
    public String quzhen() {
        return "quzhen";
    }

    @GetMapping("myquzhen")
    public String myquzhen() {
        return "myquzhen";
    }

}
