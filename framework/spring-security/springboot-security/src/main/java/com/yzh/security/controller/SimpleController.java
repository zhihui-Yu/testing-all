package com.yzh.security.controller;

import com.yzh.security.domain.Users;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author simple
 */
@RestController
@RequestMapping("test")
public class SimpleController {
    @GetMapping("hello")
    public String hello() {
        return "security";
    }

    //    @Secured({"ROLE_sale","ROLE_manager"})
//    @PreAuthorize("hasAnyAuthority('admins')")
    @PostAuthorize("hasAnyAuthority('admins')")
    @GetMapping("hello2")
    public String hello2() {
        System.out.println("hello2");
        return "security hello2";
    }

    @GetMapping("getAll")
    @PostAuthorize("hasAnyAuthority('admins')")
//    @PostFilter("filterObject.username == 'admin5'") // 查看返回值有无name是admin1的，有则返回，无则报错
    @PreFilter("filterObject.username == 'admin1'") // 查看入参 同上
    public List<Users> getAllUser() {
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(11, "admin1", "6666"));
        list.add(new Users(21, "admin2", "888"));
        System.out.println(list);
        return list;
    }
}
