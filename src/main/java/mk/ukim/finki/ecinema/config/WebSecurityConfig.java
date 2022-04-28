package mk.ukim.finki.ecinema.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import mk.ukim.finki.ecinema.service.impl.FacebookConnectionSignUp;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    @Autowired
    private FacebookConnectionSignUp facebookConnectionSignup;

    @Value("29a26ee274ea221268a1ed0459e7f383")
    String appSecret;

    @Value("360327136118560")
    String appId;

    //so providerSignInController dozvolvuame da se logiraat preku fb
    @Bean
    public ProviderSignInController providerSignInController() {
        ConnectionFactoryLocator connectionFactoryLocator = //vo ova imame parametri, klinetot so ova vospostavuva vrska
                connectionFactoryLocator();
        UsersConnectionRepository usersConnectionRepository =
                getUsersConnectionRepository(connectionFactoryLocator); //za userot se pravi userconnectionrepository
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)////zacuvua vo memorija konekcija na korisnik
                .setConnectionSignUp(facebookConnectionSignup);//za tie so prv pat se logiraat
        return new ProviderSignInController(connectionFactoryLocator, //so ovoj provider se pravi post baranje do signin/facebook
                usersConnectionRepository, new FacebookSignInAdapter()); //sign in adapterot ja pravi login logikata vo nasata app
    }

    private ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
        return registry;
    }
    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator
                                                                           connectionFactoryLocator) {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
    }

    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2**"); // do not remove this line
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()//кои страници да ни бидат достапни
                .antMatchers( "/assets/**", "/home", "/register", "/signin/**","/signup/**", "/api/**", "/header.html", "footer.html", "/login").permitAll()//koi stranici na koi url treba da bidat dozvoleni od korisnici
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
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

}
