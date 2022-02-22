package com.example.controller;

import com.example.utils.FileReadUtils;
import com.example.utils.MarkDown2HtmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author simple
 */
@Controller
@Slf4j
public class Md2HtmlController {
    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView getHtml(@PathVariable String id, ModelAndView modelAndView) throws IOException {
        log.info("id => {}", id);
        String html = MarkDown2HtmlUtils.markdown2Html(FileReadUtils.read("text.md"));
        modelAndView.setViewName("text");
        modelAndView.addObject("body", html);
        return modelAndView;
    }

//    @GetMapping("/text")
//    public ModelAndView view (ModelAndView modelAndView){
//        modelAndView.setViewName("text");
//        return modelAndView;
//    }
}
