/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.mycompany.Entities.Panier;
import com.mycompany.myapp.MyApplication;
import java.util.Iterator;

/**
 *
 * @author berrahal
 */
public class Home {
      Form f;

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
}
