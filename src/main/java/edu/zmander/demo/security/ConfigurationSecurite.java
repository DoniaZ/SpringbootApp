package edu.zmander.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.servlet.SecurityMarker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
public class ConfigurationSecurite extends WebSecurityConfigurerAdapter {
    //connexion a la BDD mysql
    @Autowired
    private DataSource dataSource;
    @Autowired
     private MonUserDetailsService monUserDetailsService;
    @Autowired
    JwtFilter filtre;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //authentification avec jwt
        auth.userDetailsService(monUserDetailsService);

//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                //le 1 de la requete: la personne est tjrs active
//                .usersByUsernameQuery("SELECT email, mot_de_passe, 1 FROM utilisateur WHERE email=?")
//                .authoritiesByUsernameQuery(
//                        " SELECT email, IF(admin,'ROLE_ADMINISTRATEUR','ROLE_UTILISATEUR') " +
//                                " FROM utilisateur" +
//                                " WHERE email = ?"
//                );
/*
auth
        .inMemoryAuthentication()
                .withUser("donia").password("root").roles("UTILISATEUR").and().withUser("admin").password("azerty").roles("ADMINISTRATEUR");

    }
    */
    }
    @Bean
    public PasswordEncoder creationPasswordEncoder(){
        //une instance singleton
        return new BCryptPasswordEncoder();

    }


    //@Bean pour le pouvoir injecter ds notre class apres
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//autorisation
    @Override
    protected void configure(HttpSecurity http) throws Exception {


                //cors(): on peut venir de n'importe que origine
        http.cors().configurationSource(httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(
                    Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                            "Accept", "Authorization","Access-Control-Allow-Origin"));
            return corsConfiguration;
        }).and()
            //csrf est une faille de sécurité:obligatoire il faut la mettre sinon ca marche pas
            .csrf().disable()
            .authorizeRequests()
 
            //.antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/connexion", "/inscription").permitAll()
            .antMatchers("/**").hasAnyRole("ADMINISTRATEUR", "UTILISATEUR")
            .anyRequest().authenticated()
                            .and().exceptionHandling()
                            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(filtre, UsernamePasswordAuthenticationFilter.class);


    }
}
