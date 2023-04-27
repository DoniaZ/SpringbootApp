package edu.zmander.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.dao.UtilisateurDao;
import edu.zmander.demo.model.Utilisateur;
import edu.zmander.demo.security.JwtUtils;
import edu.zmander.demo.security.MonUserDetails;
import edu.zmander.demo.security.MonUserDetailsService;
import edu.zmander.demo.view.VueUtilisateur;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {
    //AuthenticationManager:declencher le mécanisme de l'auth: l'objet envoyé qui a été ds le formulaire

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UtilisateurDao utilisateurDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MonUserDetailsService userDetailsService;
    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur) {

        MonUserDetails userDetails;
        try {


            //le traitement qu'on va lui demander de vérifier est ce que c le bon email ou pas par exple c'est un design pattern stratégie avec la methode  authenticate
            userDetails=(MonUserDetails)  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    utilisateur.getEmail(),
                    utilisateur.getMotDePasse()
            )).getPrincipal();
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            // return "utilisteur n'existe pas";
        }
        return new ResponseEntity<>( jwtUtils.generateJwt(userDetails), HttpStatus.OK);

    }
    @PostMapping("/inscription")
    @JsonView(VueUtilisateur.class)
   public ResponseEntity<Utilisateur>  inscription(@RequestBody Utilisateur utilisateur) {
       if(utilisateur.getId()!=null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       if(utilisateur.getMotDePasse().length()<=3){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(utilisateur.getEmail()).matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());

        if(optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String passwordHache = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(passwordHache);

        utilisateurDao.save(utilisateur);
            return  new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
       }






}
