/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Controllers.RegistrationController;
import com.mycompany.Entities.Users;
import com.mycompany.Services.UsersService;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author berrahal
 */
public class Registration {
    Form f;

    Button btnaff;
    
    
    public Registration()
    {
         f = new Form("Inscription");

        TextField nom = new TextField("", "Nom") ;
        TextField prenom = new TextField("", "Prenom") ;
        TextField email = new TextField("", "Email") ;
        TextField tel = new TextField("", "Telephone") ;
        TextField utilisateur = new TextField("", "Nom d'utilisateur") ;
        TextField password = new TextField("", "Mot de passe") ;
        Picker birthday = new Picker();
birthday.setType(Display.PICKER_TYPE_DATE);

        
        btnaff = new Button("S'inscrire");
        f.add(nom);
        f.add(prenom);
        f.add(email);
        f.add(tel);
        f.add(birthday);
        f.add(utilisateur);
        f.add(password);
        f.add(btnaff);
        
        btnaff.addActionListener((evt) -> {
            
            if (nom.getText().equals("") || prenom.getText().equals("") || email.getText().equals("") || tel.getText().equals("")
                    || birthday.equals("") || utilisateur.getText().equals("") || password.getText().equals("")) 
            {
                   Dialog.show("Erreur", "Veillez remplir tout les champs" , "OK", null);
    
            }
            else if (tel.getText().trim().length() > 8 || tel.getText().trim().length() < 8 || (!isInteger(tel.getText()))) {
               Dialog.show("Erreur", "Veillez vérifier votre numéro de téléphone" , "OK", null); 
            }
            else if (!email.getText().contains("@")) {
             Dialog.show("Erreur", "Veillez vérifier votre email" , "OK", null); 

            }
            else
            {
                
            UsersService us = new UsersService();
            Users u = new Users(utilisateur.getText(), email.getText(), password.getText(), birthday.getDate(), "ROLE_USER", nom.getText(), prenom.getText(),Long.parseLong(tel.getText()));
            us.addUser(u);
            }
            
            
           
        });
        
    }
    
 
    
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }
    
    
    
      public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
