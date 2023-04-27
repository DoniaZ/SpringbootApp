package edu.zmander.demo.security;

import edu.zmander.demo.dao.UtilisateurDao;
import io.jsonwebtoken.Claims;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//JwtFilter : pour intercepter les requetes avant quel arrive
//on peut mettre a la place de component @service
@Component
public class JwtFilter extends OncePerRequestFilter {
@Autowired
JwtUtils jwtUtils;
@Autowired
MonUserDetailsService userDetailsService;
    //doFilterInternal: permet de vérifier l'authorisation
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      //extraire le token en se basant sur l'authorization
        String jwt = request.getHeader("Authorization");
        if (jwt !=null && jwt.startsWith("Bearer")){
            //substring: enleve les espaces et les virgules
            String token =jwt.substring(7);
            if(jwtUtils.isTokenValide(token)) {
                Claims donnes = jwtUtils.getData(token);
                // System.out.println(donnes.getSubject());
                //on récupere l'utilisateur ds la BDD en fonction de l'eamil du jwt
              UserDetails userDetails =   userDetailsService.loadUserByUsername(donnes.getSubject());
              //On ajoute l'utilisateur au processus d'identification de spring security (on faitr copier coller de la doc): on aura tous ce qu'il faut pour identifier l'utilisateur
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request,response);


    }
}
