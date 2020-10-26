package cn.zhengyk.authcenter.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

//        根据用户名去数据库查询用户是否存在
//        SysUser sysUser = userService.getSysUserByName(username);
//        if(sysUser == null){
//            //数据库不存在该用户
//            return null;
//        }

        //取出正确密码（hash值）
//        String password = sysUser.getPassword();
        //测试用，将用户登录密码写死为 123
        String password = new BCryptPasswordEncoder().encode("123");
        //用户权限，这里暂时使用静态数据，最终会从数据库读取
        //从数据库获取权限
        List<String> userPermission = new ArrayList<>();
        //赋予删除、添加权限
        userPermission.add("delete");
        userPermission.add("add");
        String userPermissionString  = StringUtils.join(userPermission.toArray(), ",");
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(userPermissionString));
    }
}
