package com.test.justauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author simple
 */
@Controller
public class FreemarkerController {
    @RequestMapping("/freemarker")
    public String testFreemarker(ModelMap modelMap) {
        modelMap.addAttribute("msg", "Hey you, this is freemarker");
        return "/freemarker";
    }
}
