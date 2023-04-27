package edu.zmander.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FichierService {
    @Value("${dossier.upload}")
    private String dossierUpload;



    public void transfertVersDossierUpload(MultipartFile fichier, String nomFichier) throws IOException {

        Path cheminDossierUpload = Paths.get(dossierUpload);
        if(!Files.exists( cheminDossierUpload)){
            Files.createDirectories(cheminDossierUpload);


        }
        Path destination = Paths.get(dossierUpload+"\\"+ nomFichier);
        Files.copy(fichier.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);

        }

    }




