package org.jeecg.modules.coderQ.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.coderQ.entity.Yonghu;
import org.jeecg.modules.coderQ.service.IYonghuService;
import org.jeecg.modules.coderQ.util.SessionUser;
import org.jeecg.modules.coderQ.util.SessionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 *
 */
@Controller
@RequestMapping("coderQ")
public class DoLoginController {

    @Autowired
    private IYonghuService yonghuService;

    @GetMapping("login")
    public String login() {
        return "login";
    }


    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute(SessionUtil.SESSION_USER, null);
        return "redirect:index";
    }


    @PostMapping("toLogin")
    @ResponseBody
    public Result<?> toLogin(@RequestBody JSONObject object, HttpServletRequest request) {
        String username = object.getString("username");
        String password = object.getString("password");
        // check login
        LambdaQueryWrapper<Yonghu> query = new LambdaQueryWrapper<>();
        query.eq(Yonghu::getAccount, username);
        query.eq(Yonghu::getPwd, password);
        Yonghu one = yonghuService.getOne(query);
        if (one != null) {
            SessionUser sessionUser = new SessionUser();
            BeanUtils.copyProperties(one, sessionUser);
            request.getSession().setAttribute(SessionUtil.SESSION_USER, sessionUser);
            return Result.OK();
        } else {
            return Result.error("");
        }
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("toRegister")
    @ResponseBody
    public Result<?> toRegister(@RequestBody JSONObject object) {
        Yonghu yonghu = JSONObject.toJavaObject(object, Yonghu.class);
        yonghuService.save(yonghu);
        return Result.OK(object);
    }


    @GetMapping("doUpdateInfo")
    @ResponseBody
    public Result<?> doUpdateInfo(String name, String phone, HttpServletRequest request) {
        SessionUser sessionUser = SessionUtil.getSessionUser(request);
        Yonghu yonghu = new Yonghu();
        yonghu.setId(sessionUser.getId());
        yonghu.setName(name);
        yonghu.setPhone(phone);
        yonghuService.updateById(yonghu);
        sessionUser.setName(name);
        sessionUser.setPhone(phone);
        request.getSession().setAttribute(SessionUtil.SESSION_USER, sessionUser);
        return Result.OK();
    }

    @GetMapping("doUpdatePwd")
    @ResponseBody
    public Result<?> doUpdatePwd(String oldpwd, String newpwd, HttpServletRequest request) {
        //check oldpwd
        SessionUser sessionUser = SessionUtil.getSessionUser(request);
        //update newpwd
        if (!sessionUser.getPwd().equals(oldpwd)) {
            return Result.error("");
        } else {
            LambdaUpdateWrapper<Yonghu> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Yonghu::getPwd, newpwd);
            updateWrapper.eq(Yonghu::getId, sessionUser.getId());
            yonghuService.update(updateWrapper);
            return Result.OK();
        }
    }
}
