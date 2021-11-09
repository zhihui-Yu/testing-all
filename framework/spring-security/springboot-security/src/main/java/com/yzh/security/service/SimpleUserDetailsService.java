package com.yzh.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzh.security.domain.Users;
import com.yzh.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author simple
 */
@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> query = new QueryWrapper<Users>();
        query.eq("name", "name");
        Users users = usersMapper.selectOne(query);
        if (users == null)
            throw new UsernameNotFoundException(String.format("username not found, name = %s", username));
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale,ROLE_manager");
        return new User(users.getName(), new BCryptPasswordEncoder().encode(users.getPassword()), authorityList);
    }
}
