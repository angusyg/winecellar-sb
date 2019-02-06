package com.angusyg.winecellar.core.security.configuration;

import com.angusyg.winecellar.core.security.jwt.configuration.JwtConfiguration;
import com.angusyg.winecellar.core.security.jwt.filter.JwtTokenAuthenticationFilter;
import com.angusyg.winecellar.core.security.web.AuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public class SecurityConfiguration {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Profile("prod")
  public WebSecurityConfigurerAdapter prodSecurity() {
    return new WebSecurityConfigurerAdapter() {
      @Autowired
      private JwtConfiguration jwtConfiguration;

      @Autowired
      private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            // Disable csrf protection for REST API.
            .csrf().disable()
            // Disable security headers.
            .headers().disable()
            // Make sure we use stateless session, session won't be used to store user's state.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Handle unauthenticated accesses.
            .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
            .and()
            // Add a filter to validate the tokens with every request.
            .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            // Allow requests to "auth" endpoints.
            .antMatchers(HttpMethod.POST, jwtConfiguration.getUri()).permitAll()
            // Protect all endpoints
            .anyRequest().authenticated();
      }
    };
  }

  @Bean
  @Profile("dev")
  public WebSecurityConfigurerAdapter devSecurity() {
    return new WebSecurityConfigurerAdapter() {
      @Autowired
      private JwtConfiguration jwtConfiguration;

      @Autowired
      private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            // Disable csrf protection for REST API.
            .csrf().disable()
            // Disable security headers.
            .headers().disable()
            // Make sure we use stateless session, session won't be used to store user's state.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Handle unauthenticated accesses.
            .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
            .and()
            // Add a filter to validate the tokens with every request.
            .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/h2-console/*").permitAll()
            // Allow requests to "auth" endpoints.
            .antMatchers(HttpMethod.POST, jwtConfiguration.getUri()).permitAll()
            // Protect all endpoints
            .anyRequest().authenticated();
      }
    };
  }
}
