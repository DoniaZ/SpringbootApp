package edu.zmander.demo.dao;


import edu.zmander.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//@Repository: au démarrage spring regarde toutes les interfaces et les classe il va crerr une instance de la classe anonyme
@Repository
public interface EntrepriseDao extends JpaRepository<Entreprise, Integer> {


}
