package com.itheima.spring.controller;

import com.itheima.spring.entity.request.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/save")
    public String save() {
        System.out.println("save");
        return "save";
    }

    @GetMapping("/getUser")
    public ModelAndView getUser() {
        ModelAndView mav = new ModelAndView();

//        设置视图名称(必须)
        String viewName = "save";
        mav.setViewName(viewName);

//        设置回写数据
        mav.addObject("deep", "dark");

        return mav;
    }

    @GetMapping("/createModelAndView")
    public ModelAndView createModelAndView(ModelAndView mav) {
        String viewName = "";
        mav.setViewName(viewName);
        return mav;
    }

    @GetMapping("/createModelAndViewToStr")
    public String createModelAndViewToStr(Model m) {
        m.addAttribute("boy", "next door");
        return "success";
    }

    @GetMapping("/getAttributeByHttpServlet")
    public void getAttributeByHttpServlet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("", "");
    }

    @GetMapping("/getParams")
    public void getParams(String userName, int age) {

    }

    @GetMapping("/getParamsToPojo")
    public void getParamToPojo(User u) {

    }

    @GetMapping("/getParamsToArray")
    public void getParamsToArray(String str[]) {
        List<String> strs = Arrays.asList(str);
    }

    @GetMapping("/getParamsToList")
    public void getParamsToList(@RequestParam(value = "list",required = false) List<User> list) {
        System.out.println(list);
    }
}
