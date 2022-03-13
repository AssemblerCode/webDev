package com.itheima.spring.controller;

import com.itheima.spring.entity.request.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
        System.out.println(u.getName());
    }

    @GetMapping("/getParamsToArray")
    public void getParamsToArray(String str[]) {
        List<String> strs = Arrays.asList(str);
    }

    @GetMapping("/getParamsToList")
    public void getParamsToList(@RequestParam(value = "list", required = false) List<User> list) {
        System.out.println(list);
    }

    @GetMapping("/getPathVar/{id}/name/{userName}")
    public void getPathVar( @PathVariable(value = "id", required = true) Integer id, @PathVariable(value = "userName", required = true) String userName,
                           @RequestHeader("User-Agent") String userAgent) {
    }

    @GetMapping("/getMult")
    public  void getMult(MultipartFile mf){}

    @GetMapping("getMults")
    public void getMults(MultipartFile mf[]) throws IOException {
        File f = new File("web");
        for (int i = 0; i < mf.length; i++) {
            MultipartFile m = mf[i];
            m.transferTo(f);
        }
    }
}
