package edu.zmander.demo.security;

import edu.zmander.demo.dao.UtilisateurDao;
import edu.zmander.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service: permet de creer une instance de l'interface UserDetailsService.on peut mettre aussi @compnent
//implements UserDetailsService pour récuperer
@Service
public class   MonUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //je vais retourner un Utilisateur
        Optional<Utilisateur> optional = utilisateurDao.findByEmail(email);
        if (optional.isEmpty()){
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");


        }
        return new MonUserDetails((optional.get()));
    }
}
