package com.hxzy.crowdfunding.config;

import com.hxzy.crowdfunding.bean.TAdmin;
import com.hxzy.crowdfunding.bean.TAdminExample;
import com.hxzy.crowdfunding.bean.TPermission;
import com.hxzy.crowdfunding.bean.TRole;
import com.hxzy.crowdfunding.exception.AdminNoExistException;
import com.hxzy.crowdfunding.mapper.TAdminMapper;
import com.hxzy.crowdfunding.mapper.TPermissionMapper;
import com.hxzy.crowdfunding.mapper.TRoleMapper;
import com.hxzy.crowdfunding.security.TAdminUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private TAdminMapper tAdminMapper;

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private TPermissionMapper tPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.info("登陆用户---{}", s);

        try {
            TAdminExample example = new TAdminExample();
            example.createCriteria().andLoginacctEqualTo(s);
            List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);

            if (tAdmins.isEmpty()){
                throw new RuntimeException("用户不存在");
            }

            TAdmin tAdmin = tAdmins.get(0);

            List<TRole> roles = tRoleMapper.selectAdminToRoles(tAdmin.getId());


            List<TPermission> permissions = tPermissionMapper.selectAdminToPermissions(tAdmin.getId());


            Set<GrantedAuthority> authorities = new HashSet<>();
            for (TRole role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            for (TPermission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
            return new TAdminUser(tAdmin.getLoginacct(),tAdmin.getUserpswd(),authorities,tAdmin);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            if (e instanceof AdminNoExistException){
                throw new AdminNoExistException(e.getMessage());
            }else {
                throw new RuntimeException(e.getMessage());
            }
        }

    }
}
