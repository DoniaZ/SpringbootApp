package edu.zmander.demo.security;

import edu.zmander.demo.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MonUserDetails implements UserDetails {

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    private Utilisateur utilisateur;
public MonUserDetails(Utilisateur utilisateur) {

    this.utilisateur = utilisateur;
}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<GrantedAuthority> roles = new ArrayList<>();
//
//        if(utilisateur.isAdmin()) {
//            roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
//        } else {
//            roles.add(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
//        }
//        return roles;

//        return utilisateur.isAdmin()
//                ? List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"))
//                : List.of(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));

       // return List.of(new SimpleGrantedAuthority(utilisateur.isAdmin() ? "ROLE_ADMINISTRATEUR" : "ROLE_UTILISATEUR"));
        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));

    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
//Credentials:ces identifiants
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
