/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.mycompany.Services.AdressesService;
import static com.mycompany.gui.EditAdresse.isInteger;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author berrahal
 */
public class AddAdresse {
Form f;
    Button btnaff = new Button("Ajouter adresse");
    public AddAdresse() {
                        f = new Form("Ajouter adresse");

        TextField nom = new TextField("", "Nom") ;
        TextField prenom = new TextField("", "Prenom") ;
        TextField adresse = new TextField("", "Adresse") ;
        TextField tel = new TextField("", "Telephone") ;
        TextField cp = new TextField("", "Code postal") ;
        TextField pays = new TextField("", "Pays") ;
        TextField ville = new TextField("", "Ville") ;
        TextField complement = new TextField("", "complement") ;
         f.add(nom);
        f.add(prenom);
        f.add(adresse);
        f.add(tel);
        f.add(cp);
        f.add(pays);
        f.add(ville);
        f.add(complement);
        btnaff.addActionListener((evt) -> {
            if (nom.getText().equals("") || prenom.getText().equals("") || adresse.getText().equals("") || tel.getText().equals("")
                    || cp.getText().equals("") || pays.getText().equals("") || ville.getText().equals("") || complement.getText().equals("")
                    || !isInteger(tel.getText())) {
                Dialog.show("Erreur", "Merci de vérifier vos informations" , "OK", null); 
            }
            else 
            {
                 AdressesService as = new AdressesService();
            com.mycompany.Entities.Adresses a= new com.mycompany.Entities.Adresses();
            a.setNom(nom.getText());
            a.setPrenom(prenom.getText());
            a.setAdresse(adresse.getText());
            a.setComplement(complement.getText());
            a.setCp(cp.getText());
            a.setTelephone(tel.getText());
            a.setPays(pays.getText());
            a.setVille(ville.getText());
            a.setUtilisateur_id(MyApplication.user.getId());
            as.addAdresses(a);
            Adresses r = new Adresses();
          r.getF().show();  
            }
         
        });
        f.add(btnaff);
        
    }
    
     public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
     
    
}
