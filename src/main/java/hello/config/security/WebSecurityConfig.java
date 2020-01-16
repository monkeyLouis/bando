package hello.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import hello.config.security.handler.DenyAccessHandler;
import hello.config.security.voter.UrlVoter;
import hello.service.UserService;

@Configuration
@EnableGlobalMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private DenyAccessHandler handler;
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private UrlVoter urlVoter;
 
	@Bean
	public static PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
	    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
	    decisionVoters.add(new WebExpressionVoter());
	    decisionVoters.add(new RoleVoter());
	    decisionVoters.add(new AuthenticatedVoter());
	    decisionVoters.add(urlVoter);
	    
	    return new UnanimousBased(decisionVoters);
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
	    http
	    	.csrf().disable()			/* HTML FORM NEED CSRF TOKEN WHEN THIS LINE is COMMENTED. */
	    								/* POST ALSO GG */
			.authorizeRequests()
				.antMatchers("/signUp","/addMock").permitAll()
				.anyRequest().authenticated()						// all request to this app need to be authenticated
				.accessDecisionManager(accessDecisionManager())
//				.antMatchers("/hello", "/", "/rest/user/add").permitAll()
//				.antMatchers("/helloVip").hasAnyRole("USER", "ADMIN")
//				.antMatchers("/rest/**", "/menu").hasAnyRole("ADMIN")
			.and()
				.formLogin()										// spring basic login page
				.loginPage("/login")								// Assign Login Page to replace basic login page
				.successHandler(authenticationSuccessHandler)
//				.failureHandler(authenticationFailureHandler)
				.permitAll()										// authorize all user could access this
			.and()
				.logout()											// Provide logout support (default:/logout)
				.deleteCookies("JSESSIONID")
//				.clearAuthentication(true)
//		        .invalidateHttpSession(true)
//				.logoutUrl("/logout")								// Assign Logout url to replace default
//				.logoutSuccessUrl("/anyUrlWouldRedirect")			// Assign Logout Success page (default:/login?logout)
//				.addLogoutHandler(logoutHandler)					// could use logoutHandler
				.permitAll()
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)	// Deal with unauthorized user
				.accessDeniedHandler(handler)						// Deal with authorized user
//				.accessDeniedPage("/403")
	    	.and()
	    		.httpBasic();
//	    	.and()													// TODO
//	    		.rememberMe()
//	    		.tokenValiditySeconds(60*60*24*7);
//	    		.alwaysRemember(true)
//	    		.useSecureCookie(true);
	    
	  
    }

    /* bypass static file */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        	.ignoring()
        	.antMatchers("/403")
        	.antMatchers("/forgot")
            .antMatchers("/video/**")
            .antMatchers("/turnUp**")
            .antMatchers("/favicon.ico")
            .antMatchers("/zkau/**")
            .antMatchers("/webjars/**", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**");
    }
    
//    @Bean
//    public MessageSource initMessageSource() { 
//    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//
//    	messageSource.setBasename("classpath*:messages"); 
//    	messageSource.setDefaultEncoding("UTF-8"); 
//    	messageSource.setCacheMillis(30*60*1000);
//
//    	return messageSource; 
//    }
     
}
