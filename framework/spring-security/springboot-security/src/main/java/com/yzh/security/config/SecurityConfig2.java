package com.yzh.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author simple
 */
@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注入一个 UserDetailsService 的实现类，在类中去查找数据库来实现认证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出
        http.logout().logoutUrl("/logout").
            logoutSuccessUrl("/test/hello").permitAll();

        //配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        //配置url的访问权限
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/**update**").permitAll()
            .antMatchers("/login/**").permitAll()
            .anyRequest().authenticated();

        //关闭csrf保护功能
        //http.csrf().disable();
//
        //使用自定义的登录窗口
        http.formLogin()
            .loginPage("/userLogin").permitAll()
            .usernameParameter("username").passwordParameter("password")
            .defaultSuccessUrl("/")
            .failureUrl("/userLogin?error");

//        http.formLogin()   //自定义自己编写的登录页面
//            .loginPage("/login.html")  //登录页面设置
//            .loginProcessingUrl("/user/login")   //登录访问路径
//            .defaultSuccessUrl("/success.html").permitAll()  //登录成功之后，跳转路径
//            .failureUrl("/unauth.html")
//            .and().authorizeRequests()
//            .antMatchers("/","/test/hello","/user/login","/user/page").permitAll() //设置哪些路径可以直接访问，不需要认证
            //当前登录用户，只有具有admins权限才可以访问这个路径
            //1 hasAuthority方法
            // .antMatchers("/test/index").hasAuthority("admins")
            //2 hasAnyAuthority方法
            // .antMatchers("/test/index").hasAnyAuthority("admins,manager")
            //3 hasRole方法   ROLE_sale
//            .antMatchers("/test/index").hasRole("sale")

//            .anyRequest().authenticated()
//            .and().rememberMe().tokenRepository(persistentTokenRepository())
//            .tokenValiditySeconds(60)//设置有效时长，单位秒
//            .userDetailsService(userDetailsService);
        // .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // .and().csrf().disable();  //关闭csrf防护, 默认开启
    }

    //配置对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
