package pyg.shang.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbBrand;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pyg.com.utis.PageResult;
import pyg.com.utis.PygResult;
import pyg.shang.Service.BranService;

import java.util.HashMap;
import java.util.List;
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