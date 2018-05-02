/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Services.AdressesService;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berrahal
 */
public class Adresses {
         Form f;
    Button btnaff = new Button("Passer la commande");
    
    public Adresses()
    {
                 f = new Form("Modifier adresse",new BorderLayout());
                   AdressesService sb = new AdressesService();
        ArrayList<com.mycompany.Entities.Adresses> ls = new ArrayList<com.mycompany.Entities.Adresses>();
        ls = sb.getAdressesbyUserId(MyApplication.user.getId());
        System.err.println(ls.toString());
                 Container c3 = new Container(BoxLayout.y());
                           Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

  Button add = new Button("Ajouter");
        for (com.mycompany.Entities.Adresses l : ls) {
                    Button edit = new Button("Modifier");

             Label nom = new Label(l.getNom());
              Label prenom = new Label(l.getPrenom());
               Label adresse = new Label("Adresse:" +l.getAdresse()+", "+l.getVille()+","+l.getPays());
                Label tel = new Label("Tel:"+l.getTelephone());
                             adresse.getUnselectedStyle().setFont(font);
             tel.getUnselectedStyle().setFont(font);
             edit.addActionListener((evt) -> {
                 EditAdresse r = new EditAdresse(l);
          r.getF().show();
             });

                Container c1 = new Container(BoxLayout.y());
            Container c2 = new Container(BoxLayout.x());
            c2.add(nom);
            c2.add(prenom);
            c2.add(edit);
            c1.add(c2);
            c1.add(adresse);
            c1.add(tel);
              c3.add(c1);
              c3.setScrollableY(true);
        }
                 Container ajout = new Container(new BorderLayout());
         ajout.add(BorderLayout.NORTH,add);

         add.addActionListener((evt) -> {
              AddAdresse r = new AddAdresse();
          r.getF().show();
         });
        f.add(BorderLayout.CENTER,c3);
                 f.add(BorderLayout.SOUTH,ajout);

        
        
    }
    
       public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
