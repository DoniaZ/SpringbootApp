package edu.zmander.demo.dao;

import edu.zmander.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@Repository c une dépendance qd doit l'injecter ds MonUserDetailsService

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
  Utilisateur findByPrenom(String prenom);
  Optional<Utilisateur>findByEmail(String email);




}
