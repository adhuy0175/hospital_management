package com.hospitalmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(value = "accountService")
	private UserDetailsService userDetailService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/public/**", "/error").permitAll()
				.antMatchers("/account**").hasRole(UserPrincipal.ADMIN)
				.anyRequest().authenticated()
			.and()
			.authorizeHttpRequests() // For API
				.antMatchers("/api/**/delete**").hasRole(UserPrincipal.ADMIN)  
				.antMatchers("/api/doctor/update**", "/api/doctor/create**").hasRole(UserPrincipal.ADMIN) 
				.antMatchers("/api/department/update**", "/api/department/create**").hasRole(UserPrincipal.ADMIN)
				.antMatchers("/api/medicine/update**", "/api/medicine/create**").hasRole(UserPrincipal.ADMIN)
				.antMatchers("/api/illness/update**", "/api/illness/create**").hasRole(UserPrincipal.ADMIN)
				.antMatchers("/api/account**").hasRole(UserPrincipal.ADMIN)
			.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login").permitAll()
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()
				.authenticationProvider(authProvider())
				.httpBasic();
	}
}
