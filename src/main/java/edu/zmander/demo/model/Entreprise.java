package edu.zmander.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.view.VueEntreprise;
import edu.zmander.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
//Entity : basé sur jpa
@Entity
public class Entreprise  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;
    @JsonView(VueEntreprise.class)
    @OneToMany(mappedBy = "entreprise")
    private Set<Utilisateur> listeEmploye = new HashSet<>();

}
