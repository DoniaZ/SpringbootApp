package edu.zmander.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.dao.UtilisateurDao;
import edu.zmander.demo.model.Role;
import edu.zmander.demo.model.Utilisateur;
import edu.zmander.demo.security.JwtUtils;
import edu.zmander.demo.services.FichierService;
import edu.zmander.demo.view.VueUtilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController
public class UtilisateurController {
    @Autowired
    private UtilisateurDao utilisateurDao;
    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    FichierService fichierService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/utilisateurs")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }


    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateurDonia(@PathVariable int id) {
        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Utilisateur> optional = utilisateurDao.findById(id);
         //return optional.orElse(null);
        //return utilisateurDao.findById(id).orElse(null);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profil")
    @JsonView(VueUtilisateur.class)

    public ResponseEntity<Utilisateur> getProfil(@RequestHeader("Authorization") String bearer) {
        String jwt = bearer.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        //System.out.println(donnees.getSubject());ici on recupere l'email sur la console, on teste avec postman avec "get"
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());
        if (utilisateur.isPresent()) {
            return new ResponseEntity(utilisateur.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



        @PostMapping("/admin/utilisateur")
        public ResponseEntity<Utilisateur> ajoutUtilisateur(@RequestPart("utilisateur") Utilisateur nouvelUtilisateur, @Nullable @RequestParam("fichier")MultipartFile fichier )

        {

        //si l'utilisateur fournit possède un id
        if(nouvelUtilisateur.getId() != null) {

            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId());

            //si c'est un update
            if(optional.isPresent()) {
                Utilisateur userToUpdate = optional.get();
                userToUpdate.setNom(nouvelUtilisateur.getNom());
                userToUpdate.setPrenom(nouvelUtilisateur.getPrenom());
                userToUpdate.setEmail(nouvelUtilisateur.getEmail());


                utilisateurDao.save(userToUpdate);
                return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas return bad request 404
            return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.BAD_REQUEST);

        }
//ajouter l'utilisateur ds la bdd avec save avec le code 201 de création et lui affecter  un role
            Role role = new Role();
            role.setId(1);
            nouvelUtilisateur.setRole(role);
            String passwordHache = passwordEncoder.encode("root");
            nouvelUtilisateur.setMotDePasse(passwordHache);



        utilisateurDao.save(nouvelUtilisateur);


        if(fichier != null){

            try {
                fichierService.transfertVersDossierUpload(fichier, "image-profil");
            } catch (IOException e) {
                return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.CREATED);

    }
    @JsonView(VueUtilisateur.class)
    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);
//si l'utilisateur existe supprime le
        if(utilisateurAsupprimer.isPresent()) {
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }






}


