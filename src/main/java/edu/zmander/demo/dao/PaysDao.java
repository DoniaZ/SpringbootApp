package edu.zmander.demo.dao;

import edu.zmander.demo.model.Pays;
import edu.zmander.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysDao extends JpaRepository<Pays, Integer> {



}
