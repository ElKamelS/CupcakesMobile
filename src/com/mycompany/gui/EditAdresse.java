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

/**
 *
 * @author berrahal
 */
public class EditAdresse {

    Form f;
    Button btnaff = new Button("Modifier");
    
    public EditAdresse(com.mycompany.Entities.Adresses a) {
                f = new Form("Modifier adresse");

        TextField nom = new TextField(a.getNom(), "Nom") ;
        TextField prenom = new TextField(a.getPrenom(), "Prenom") ;
        TextField adresse = new TextField(a.getAdresse(), "Adresse") ;
        TextField tel = new TextField(a.getTelephone(), "Telephone") ;
        TextField cp = new TextField(a.getCp(), "Code postal") ;
        TextField pays = new TextField(a.getPays(), "Pays") ;
        TextField ville = new TextField(a.getVille(), "Ville") ;
        TextField complement = new TextField(a.getComplement(), "complement") ;
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
            Adresses ad = new Adresses();
            a.setNom(nom.getText());
            a.setPrenom(prenom.getText());
            a.setAdresse(adresse.getText());
            a.setComplement(complement.getText());
            a.setCp(cp.getText());
            a.setTelephone(tel.getText());
            a.setPays(pays.getText());
            a.setVille(ville.getText());
            as.updateAdresse(a);
            Adresses r = new Adresses();
          r.getF().show();
            }
           
        });
        f.add(btnaff);
        f.showBack();
        
    }
    
      public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
     public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }
}
