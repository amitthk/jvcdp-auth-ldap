package com.jvcdp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    //Getting values from properties file
//    @Value("${ldap.urls}")
//    private String ldapUrls;
//    @Value("${ldap.base.dn}")
//    private String ldapBaseDn;
//    @Value("${ldap.username}")
//    private String ldapSecurityPrincipal;
//    @Value("${ldap.password}")
//    private String ldapPrincipalPassword;
//    @Value("${ldap.user.dn.pattern}")
//    private String ldapUserDnPattern;
//    @Value("${ldap.enabled}")
//    private String ldapEnabled;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8989/dc=amitthk,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .contextSource()
//                .url(ldapUrls + ldapBaseDn)
//                .managerDn(ldapSecurityPrincipal)
//                .managerPassword(ldapPrincipalPassword)
//                .and()
//                .userDnPatterns(ldapUserDnPattern);
//
//    }

//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.ldapAuthentication()
//                .contextSource()
//                .url("ldaps://amitthk.com:636/DC=fg,DC=local")
//                .managerDn("CN=amitthk,CN=amitthk,CN=com,DC=fg,DC=local")
//                .managerPassword("somepass")
//                .and()
//                .userSearchBase("")
//                .userSearchFilter("CN={0},CN=ProxyUsers,CN=fdam,DC=fg,DC=local");
//    }

}