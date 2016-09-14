package net.watermelon.config;

import net.watermelon.user.manager.UserDetailsServiceCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
@Override
protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// .antMatchers("/css/**", "/index","/plug-in/**").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/blank","/ui_colors","/user/**").hasRole("ADMIN").and().formLogin()
				.loginPage("/login").failureUrl("/login-error").successHandler(simpleLoginSuccessHandler()).and().rememberMe();
		// http.csrf().disable();
	}

	// @formatter:on

	// @formatter:off
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("123456")
	.roles("ADMIN");
//	auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	auth.authenticationProvider(authenticationProvider());  
	}
	// @formatter:on
@Bean  
public   LoginSuccessHandler simpleLoginSuccessHandler(){
	    	LoginSuccessHandler  simpleLoginSuccessHandler = new  LoginSuccessHandler();
		return simpleLoginSuccessHandler;
	}
	
	
@Bean  
public MessageDigestPasswordEncoder passwordEncoder(){  
		MessageDigestPasswordEncoder passwordEncoder=new MessageDigestPasswordEncoder("MD5");
	    return passwordEncoder;  
}  


public AuthenticationProvider authenticationProvider(){  
	    DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();  
	    authenticationProvider.setUserDetailsService(userDetailsService());  
	    authenticationProvider.setSaltSource(saltSource());
	    authenticationProvider.setPasswordEncoder(passwordEncoder());  
	    return authenticationProvider;  
	}  
	

   SaltSource saltSource(){
	ReflectionSaltSource rs = new ReflectionSaltSource();
	rs.setUserPropertyToUse("username");
	return rs;
}
  @Autowired
  UserDetailsServiceCustom userDetailsServiceCustom;

  
  @Override
protected UserDetailsService userDetailsService() {
	return userDetailsServiceCustom;
    
}  

}
