package com.example.ssmdemo203.common.config;

import com.example.ssmdemo203.common.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.function.Consumer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeinedHandler jwtAccessDeinedHandler;

    @Resource
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private CustomConfig customConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception{
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            //自定义认证成功处理类
            .successHandler(jwtAuthenticationSuccessHandler)
            //自定义失败拦截器
            .failureHandler(loginFailureHandler)
            //自定义登录拦截地址
            .loginProcessingUrl("/login")
            .and()
            //自定义认证失败类
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            //自定义权限不足处理类
            .accessDeniedHandler(jwtAccessDeinedHandler)
            .and()
            //设置无状态，不创建session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated();

        //允许跨域
        http.cors().and().csrf().disable();

        //配置jwt校验过滤器
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        //缓存
        http.headers().cacheControl();
    }


    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code http.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity and = web.ignoring().and();

        // 忽略 GET
        customConfig.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        customConfig.getIgnores().getGet().forEach(new Consumer<String>() {
            @Override
            public void accept(String url) {
                and.ignoring().antMatchers(HttpMethod.GET, url);
            }
        });

        // 忽略 POST
        customConfig.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        customConfig.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        customConfig.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));

    }
}
