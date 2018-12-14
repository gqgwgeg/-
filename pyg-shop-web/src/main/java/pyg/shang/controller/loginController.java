package pyg.shang.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login")
public class loginController {


@RequestMapping("/Landing")
public Map login(){
//获取用户名
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    Map loginName = new HashMap<>();
    loginName.put("loginName",name);
    return loginName;
}


}