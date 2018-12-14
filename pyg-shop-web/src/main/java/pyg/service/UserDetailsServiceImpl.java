package pyg.service;

import com.pyg.pojo.TbSeller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pyg.shang.Service.SellerService;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public SellerService getSellerService() {
        return sellerService;
    }

     //使用set方法把对象注入到容器中
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                   //根据用户名查询密码
        TbSeller tbSeller = sellerService.findOne(username);
        //设置角色集合
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
       if(tbSeller!=null){
           //判断他是否审核通过
           if(tbSeller.getStatus().equals("1")){


               User user = new User(username, tbSeller.getPassword(), authorities);

               return user;
           }
       }
        return null;
    }


}
