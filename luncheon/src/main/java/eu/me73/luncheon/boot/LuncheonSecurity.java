package eu.me73.luncheon.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class LuncheonSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    LuncheonAuthenticationProvider authenticationProvider;

    @Autowired
    LuncheonAuthenticationSuccessHandler luncheonAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home").access("hasAnyRole('USER','ADMIN')")
                .antMatchers("/users/**").access("hasAnyRole('USER','ADMIN')")
                .antMatchers("/lunches/**").access("hasAnyRole('ADMIN', 'USER')")
                .antMatchers("/orders/**").access("hasAnyRole('USER', 'ADMIN')")
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/daily/**").access("hasRole('ADMIN')")
                .antMatchers("/security/**").access("hasRole('SPECIAL')")
                .antMatchers("/securitydaily/**").access("hasRole('SPECIAL')")
                .and()
                .formLogin()
                .loginPage("/login")
//                .successHandler(luncheonAuthenticationSuccessHandler)
//                .defaultSuccessUrl("/")
//                .failureUrl("/login")
                .usernameParameter("ssoId").passwordParameter("password")
//                .and().exceptionHandling().accessDeniedPage("/Access_Denied")
                .and().csrf().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1)
                .expiredUrl("/expired")
                .and()
                .invalidSessionUrl("/login")
                .sessionFixation()
                .newSession();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/scripts/**", "/pictures/**", "/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

}
