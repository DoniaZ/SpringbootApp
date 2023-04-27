package edu.zmander.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.view.VueEntreprise;
import edu.zmander.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Table(name = "utilisateur")
@Entity
@Getter
@Setter
public class Utilisateur {
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Column(name = "name")
    //@Column(length = 80, nullable = false)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

    //@Column(length = 50, nullable = true)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String prenom;


    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String email;

    private String motDePasse;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @ManyToOne
    private  Role role;
    //on a changger private boolean admin par role

    @JsonView(VueUtilisateur.class)
    @ManyToOne
    private Pays pays;

    //    @ManyToOne: entreprise id qui va etre rajouter ds la bdd
    @JsonView(VueUtilisateur.class)
    @ManyToOne
    private Entreprise entreprise;


    //@JoinTable pour la jointure
    @JsonView(VueUtilisateur.class)
    @ManyToMany
    @JoinTable(name = "recherche_emploi_utilisateur",inverseJoinColumns = @JoinColumn(name= "emploi_id"))
    private Set<Emploi> emploisRecherche = new HashSet<>();

    @JsonView(VueUtilisateur.class)
    @CreationTimestamp
    private LocalDate createdAt;

    @JsonView(VueUtilisateur.class)
    @UpdateTimestamp
    private LocalDateTime updatedAt;





    }

