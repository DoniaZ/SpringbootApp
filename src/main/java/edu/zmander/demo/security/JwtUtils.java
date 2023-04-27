package edu.zmander.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//Utils: utilitaire: ine classe contenant des fonctions
//@Service: ca injecte partout
@Service
public class JwtUtils {
    public  String generateJwt(MonUserDetails  userDetails){

        Map<String, Object> donnees = new HashMap<>();
            donnees.put("prenom", userDetails.getUtilisateur().getPrenom());
            donnees.put("nom", userDetails.getUtilisateur().getNom());
             donnees.put("role", userDetails.getUtilisateur().getRole().getNom());
//        ds le cas de plusieurs role donnees.put("role", userDetails.getUtilisateur().getRoles().getNom());


        return Jwts.builder()
                .setClaims(donnees)
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256,"azerty" )
                .compact();



    }
    //objet claims contient des donner de notre token(expl duree d'expiration) la methode va extraire les donnée du token (partie qui est en violet)
    public Claims getData(String jwt){
        return Jwts.parser()
                .setSigningKey("azerty")
                .parseClaimsJws(jwt)
                .getBody();


    }
    public boolean isTokenValide (String jwt) {
        try {
            Claims donnees = getData(jwt);

        }catch (SignatureException e){
            return false;
        }
        return true;

    }


}
