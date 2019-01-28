package com.angusyg.winecellar.core.security.configuration;

import com.angusyg.winecellar.core.security.jwt.filter.CustomBasicAuthenticationEntryPoint;
import com.angusyg.winecellar.core.security.jwt.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public class SecurityConfiguration {
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
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
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
      }

      @Autowired
      private UserDetailsService userDetailsService;

      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // make sure we use stateless session; session won't be used to store user's state.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // handle an authorized attemptsActionListener
            .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            // Add a filter to validate the tokens with every request
            .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // authorization requests config
            .authorizeRequests()
            // allow all who are accessing "auth" service
            .antMatchers(HttpMethod.POST, jwtConfiguration.getUri()).permitAll()
            // must be an admin if trying to access admin area (authentication is also required here)
            // .antMatchers("/admin/**").hasRole("ADMIN")
            // Any other request must be authenticated
            .antMatchers("/h2-console").permitAll()
            .anyRequest().authenticated();
      }
    };
  }

  @Bean
  @Profile("dev")
  public WebSecurityConfigurerAdapter devSecurity() {
    return new WebSecurityConfigurerAdapter() {
      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic().authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint());
      }
    };
  }
}
