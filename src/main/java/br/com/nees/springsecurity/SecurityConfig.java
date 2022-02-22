package br.com.nees.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.nees.services.UsuarioService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService userService;
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
		  .antMatchers( "/*").permitAll()
		  .anyRequest().authenticated()
		  .and()
		  .formLogin()
		  	.permitAll()
		  	.loginPage("/login")
		  	.defaultSuccessUrl("/membro/")
		  	.usernameParameter("email")
		  	.passwordParameter("senha")
//		  	.and()
//		  	.rememberMe().key("uniqueAndSecret")
		  .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and().csrf().disable();
		  
		  
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(  "/webjars/**", "/img/**", "/css/**",  "/js/**",  "/summernote/**");
	}

	//	 http
//     .authorizeRequests()
//         .antMatchers("/*").permitAll()
//         .anyRequest().authenticated()
//         .and()
//     .formLogin()
//         .loginPage("/login")
//         .permitAll()
//         .and()
//     .logout()
//         .permitAll();
	

}
