package edu.zmander.demo.model;


import com.fasterxml.jackson.annotation.JsonView;
import edu.zmander.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VueUtilisateur.class)
    private Integer id;
    @JsonView(VueUtilisateur.class)
    private String nom;




    }

