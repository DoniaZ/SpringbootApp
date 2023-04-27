package edu.zmander.demo.controller;

import edu.zmander.demo.dao.PaysDao;
import edu.zmander.demo.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PaysController {
    @Autowired
private PaysDao paysDao;

    @GetMapping("/liste-pays")
    public List<Pays> getListePays() {
        return paysDao.findAll();
    }


    @GetMapping("/admin/pays/{id}")
    public ResponseEntity<Pays>  getPays(@PathVariable int id) {

        Optional<Pays> optional = paysDao.findById(id);
        // return optional.orElse(null);
        //return paysDao.findById(id).orElse(null);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/admin/pays")
    public ResponseEntity<Pays> ajoutPays(@RequestBody Pays nouvelPays) {

        //si l'pays fournit possède un id
        if(nouvelPays.getId() != null) {

            Optional<Pays> optional = paysDao.findById(nouvelPays.getId());

            //si c'est un update
            if(optional.isPresent()) {
                paysDao.save(nouvelPays);
                return new ResponseEntity<>(nouvelPays,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un pays avec un id qui n'existait pas
            return new ResponseEntity<>(nouvelPays,HttpStatus.BAD_REQUEST);

        }

        paysDao.save(nouvelPays);
        return new ResponseEntity<>(nouvelPays,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/pays/{id}")
    public ResponseEntity<Pays> supprimePays(@PathVariable int id) {

        Optional<Pays> paysAsupprimer = paysDao.findById(id);

        if(paysAsupprimer.isPresent()) {
            paysDao.deleteById(id);
            return new ResponseEntity<>(paysAsupprimer.get(),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }






}


