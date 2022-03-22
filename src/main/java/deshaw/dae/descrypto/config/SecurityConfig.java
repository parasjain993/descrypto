package deshaw.dae.descrypto.config;


import deshaw.dae.descrypto.domain.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;

    @Autowired
    private CustomAuthenticationProvider authProvider;
    //    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//   @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth)
//           throws Exception
//    {
//        System.out.print("inside");
////        auth
////                .jdbcAuthentication()
////                .dataSource(dataSource)
////                .usersByUsernameQuery("select userName, password from userdb where userName = ?");
////        String userName =
////        auth.inMemoryAuthentication()
////                .withUser("admin")
////                .password(passwordEncoder().encode("password"))
////                .roles("USER");
//       // auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//   }
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}