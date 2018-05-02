/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.mycompany.Entities.Users;
import com.mycompany.Services.UsersService;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author berrahal
 */
public class User {
    Form f;

    Button btnaff;
    public User()
    {
        f = new Form("Mon profil");
        Label l = new Label();
        TextField nom = new TextField("", "Nom") ;
        TextField prenom = new TextField("", "Prenom") ;
        TextField email = new TextField("", "Email") ;
        TextField tel = new TextField("", "Telephone") ;
        TextField utilisateur = new TextField("", "Nom d'utilisateur") ;
        btnaff = new Button("Modifier");
         f.add(nom);
        f.add(prenom);
        f.add(email);
        f.add(tel);
        f.add(utilisateur);
     f.add(btnaff);
        System.err.println(MyApplication.user.getPassword());
     nom.setText(MyApplication.user.getFirstname());
        prenom.setText(MyApplication.user.getLastname());
        email.setText(MyApplication.user.getEmail());
        tel.setText(""+MyApplication.user.getPhonenumber());
        utilisateur.setText(""+MyApplication.user.getUsername());
     btnaff.addActionListener((evt) -> {
         UsersService us= new UsersService();
         Users u = new Users();
         u.setFirstname(nom.getText());
         u.setLastname(prenom.getText());
         u.setEmail(email.getText());
         u.setPhonenumber(Long.parseLong(tel.getText()));
         u.setUsername(utilisateur.getText());
         u.setId(MyApplication.user.getId());
         us.updateUser(u);
         System.out.println(MyApplication.user.getId());
     });
        
    }
    
        public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
