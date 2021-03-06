package com.jvcdp.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvcdp.model.AccountCredentials;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  public JWTLoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException, IOException, ServletException {
    AccountCredentials creds = new ObjectMapper()
        .readValue(req.getInputStream(), AccountCredentials.class);
    List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
    grantedAuths.add(new SimpleGrantedAuthority("USER"));

    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            creds.getUserName(),
            creds.getPassword(),
            grantedAuths
        )
    );
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req,
      HttpServletResponse res, FilterChain chain,
      Authentication ldapauth) throws IOException, ServletException {
    TokenAuthenticationService.addAuthentication(res, ldapauth.getName());
  }
}
