package edu.zmander.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.dao.EntrepriseDao;
import edu.zmander.demo.model.Entreprise;
import edu.zmander.demo.view.VueEntreprise;
import edu.zmander.demo.view.VueUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@RestController
public class EntrepriseController {
    //    @Autowired :ca dire le controleur a besoin de entrepriseDao(on a besoin de cette dépendance)
    @Autowired
private  EntrepriseDao entrepriseDao;
    @JsonView(VueEntreprise.class)
    @GetMapping("/entreprises")
   public List<Entreprise> getListeEntreprise(){
        return entrepriseDao.findAll() ;
}
    @JsonView(VueEntreprise.class)
    @GetMapping("/entreprise/{id}")
//retourne moi le json entier de Entreprise
public Entreprise getEntreprise(@PathVariable int id){
Entreprise entreprise = entrepriseDao.findById(id).orElse (null);
    return  entreprise;
}
@DeleteMapping("/admin//entreprise/{id}")
public boolean supprimerEntreprise (@PathVariable int id){
    entrepriseDao.deleteById(id);
    return true;

}
//pour la mise a jour de l'entreprise de type Entreprise
@PostMapping("/admin/entreprise")
public  Entreprise EnregistrerEntreprise(@RequestBody Entreprise entrepriseAenregistrer){
      entrepriseDao.save(entrepriseAenregistrer);

        return entrepriseAenregistrer;



}


}
