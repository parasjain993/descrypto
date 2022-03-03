package deshaw.dae.descrypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
               http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                       .antMatchers(HttpMethod.POST,"/order/**/**").permitAll()
                       .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").permitAll()
<<<<<<< HEAD
                 .and()
=======
                .antMatchers(HttpMethod.GET, "/trade/**").permitAll()
                .antMatchers(HttpMethod.POST, "/trade/**").permitAll()
                .antMatchers(HttpMethod.GET, "/order/**").permitAll()
                .antMatchers(HttpMethod.POST, "/order/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
>>>>>>> 5708b8481ffe09ff8e56cbeabd22e8aebdf3beab
                .httpBasic();
    }
}
