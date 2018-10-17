package com.codemantra.maestro.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan(value="com.codemantra.maestro.config.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new CustomAuthenticationManager();
    }
	
	/*@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}*/
	
	/*@Override
	public void configure(WebSecurity web) throws Exception {
		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
	    handler.setPermissionEvaluator(new CustomPermissionEvaluator());
	    web.expressionHandler(handler);
    }*/
	
	protected void configure(HttpSecurity http) throws Exception {
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
    			.antMatchers("/preEditValProcess", "/getValStageDetails","/getMaestroPath","/getCopyEditPath","/getCleanUpStage","/getJobList",
    					"/updateJobStatus", "/updateChapterTemplateStatus", "/getChapterTemplateStatus", "/updateStatus", "/getMailConfig",
    					"/updateTemplateStatus", "/getTemplateStatus", "/getTemplatePathDetails","/getGraphicsPath", "/getEquationsPath", "/updateChapterEquation",
    					"/getChapterEquation", "/updateWordExportMap", "/jobFeedDownload", "/updateJobPath","/sendMail").permitAll()
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
			/*.rememberMe()
				.key("lembrete")
				.useSecureCookie(true)
				.and()*/
			.logout()
				.logoutUrl("/logout").invalidateHttpSession(true)
				.logoutSuccessUrl("/login?logout=success").permitAll()
    			.and()
    		.exceptionHandling().accessDeniedPage("/accessDenied");
    }
	
	/*@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}*/
	
	
}
