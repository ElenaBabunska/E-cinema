package mk.ukim.finki.ecinema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;


    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2**"); // do not remove this line
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()//кои страници да ни бидат достапни
                .antMatchers( "/assets/**", "/home", "/register", "/api/**", "/header.html", "footer.html").permitAll()//koi stranici na koi url treba da bidat dozvoleni od korisnici
                //.antMatchers("/admin/**").hasRole("ADMIN")//stranici dozvoleni samo za korisnici so uloga administrator
                .anyRequest()
                .authenticated() //site drugi url so ne se navedeni da se pristapat samo ako e avtenticiran korisnikot
                .and()
                .formLogin()
                .loginPage("/login").permitAll() //deka e dozvolena za site ovaa stranica
                .failureUrl("/login?error=BadCredentials") //ako se sluci exception, togas kazuvame na koe url da odi korisnikot
                .defaultSuccessUrl("/home",true)//ako uspee najavata
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)//da se trgne avtentikacijata
                .invalidateHttpSession(true)//invalidacija na sesijata
                .deleteCookies("JSESSIONID")//da se izbrise cookito
                .logoutSuccessUrl("/login") //ako e uspesno da ne odnese na login
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
    }
}
