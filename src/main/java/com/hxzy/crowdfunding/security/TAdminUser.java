package com.hxzy.crowdfunding.security;

import com.hxzy.crowdfunding.bean.TAdmin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class TAdminUser extends User {

    private TAdmin tAdmin; //保留我们自己的用户信息

    public TAdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities, TAdmin tAdmin) {
        super(username, password, authorities);
        this.tAdmin = tAdmin;
    }

    public TAdminUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, TAdmin tAdmin) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.tAdmin = tAdmin;
    }

}
