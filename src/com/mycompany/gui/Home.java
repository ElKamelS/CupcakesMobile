/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.Entities.Panier;
import com.mycompany.myapp.MyApplication;
import java.util.Iterator;

/**
 *
 * @author berrahal
 */
public class Home extends Form {
     public static Form f;

    Button btnaff;

    
    public Home(){
        
        f = new Form("Acceuil");
        Label l = new Label();
        if (MyApplication.Panier.isEmpty()) {
           // Label l= new Label("Aucun produit");
            l.setText("Aucun Produit");
                    f.add(l);

        }
        else {
             Iterator<Panier> it = MyApplication.Panier.iterator();
            int nbproduit = 0;
            double prixtotal = 0;
            while (it.hasNext()) {
                Panier next = it.next();
                nbproduit++;
                prixtotal = next.getProduit().getPrice() * next.getQte() + prixtotal;

            }
           l.setText(nbproduit+" produits. ("+prixtotal+" DT)");
                    f.add(l);

        }
        
        
    }
    
        public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
      public static void installSidemenu() {

                  Toolbar tb = f.getToolbar();

Container topBar = BorderLayout.east(new Label("Menu"));
topBar.add(BorderLayout.SOUTH, new Label("Cool App Tagline...", "SidemenuTagline")); 
topBar.setUIID("SideCommand");

tb.addComponentToSideMenu(topBar);

tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {}); 
tb.addMaterialCommandToSideMenu("Website", FontImage.MATERIAL_WEB, e -> {});
tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {});
tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {});
       
    }
      
      
}
