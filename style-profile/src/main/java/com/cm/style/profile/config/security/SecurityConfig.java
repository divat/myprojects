package com.cm.style.profile.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration
 * @author DHIVAKART
 *
 */
@Configuration
@ComponentScan(value="com.cm.style.profile.config.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean(){
		return new CustomAuthenticationManager();
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/errors").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/public/**").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/addStyle", "/getStyles", "/postString", "/updateStyleExecution", "/getStyleExecutionStatus", "/addStyles", "/getClientList", "/getPublisherList", "/getPublicationList", "/getStyleProfileData").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login").permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new CustomAuthenticationSuccessHandler())
				.failureHandler(new CustomAuthenticationFailureHandler())
		  		.and()
		  	.logout()
		  		.logoutUrl("/logout").invalidateHttpSession(true)
		  		.logoutSuccessUrl("/login?logout=success").permitAll()
		  		.and()
			.exceptionHandling().accessDeniedPage("/accessDenied");
	}
	
}
