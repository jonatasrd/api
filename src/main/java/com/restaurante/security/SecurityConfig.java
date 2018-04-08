package com.restaurante.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password("{noop}admin").roles("USER", "ADMIN").and()
				.withUser("user").password("{noop}user").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
				.anyRequest()
					.authenticated()
				.and()
					.httpBasic()
				.and()
					.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.and()
						.sessionManagement()
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
