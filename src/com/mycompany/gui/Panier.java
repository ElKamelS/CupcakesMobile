/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author berrahal
 */
public class Panier {
     Form f;
    Button btnaff = new Button("");
    float amount;
    public Panier()
    {
       
         f = new Form("Panier", new BorderLayout());
       
         Container c3 = new Container(BoxLayout.y());
          Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
         for (com.mycompany.Entities.Panier p : MyApplication.Panier) {
            Label nom = new Label("");
            Label prixp = new Label("");
            Label qte = new Label("");
            Label prixt = new Label("");
            Button addb = new Button("+");
        Button remove = new Button("-");
          prixp.getUnselectedStyle().setFont(smallPlainSystemFont);
          qte.getUnselectedStyle().setFont(smallPlainSystemFont);
          prixt.getUnselectedStyle().setFont(smallPlainSystemFont);

             if (p.getProduit()!=null) {
                            nom.setText(p.getProduit().getName()+": "+p.getProduit().getPrice()*p.getQte()+" DT");
                            prixp.setText("Prix :"+p.getProduit().getPrice());
                            qte.setText("Qte :"+p.getQte());
                            prixt.setText(""+p.getProduit().getPrice()*p.getQte());
                            remove.addActionListener((evt) -> {
                                removepanier(p);
                                nom.setText(p.getProduit().getName()+": "+p.getProduit().getPrice()*p.getQte()+" DT");
                            prixp.setText("Prix :"+p.getProduit().getPrice());
                            qte.setText("Qte :"+p.getQte());
                            prixt.setText(""+p.getProduit().getPrice()*p.getQte()); 
                                                        amount=amount- (float)p.getProduit().getPrice();

                                if (p.getQte()==0) {
                                    MyApplication.Panier.remove(p);
                                     nom.setText("");
                            prixp.setText("");
                            qte.setText("");
                            prixt.setText(""); 
                            remove.remove();
                            addb.remove();
                            f.refreshTheme();
                                }
                                
                            });
                            addb.addActionListener((evt) -> {
                                addpanier(p);
                                nom.setText(p.getProduit().getName()+": "+p.getProduit().getPrice()*p.getQte()+" DT");
                            prixp.setText("Prix :"+p.getProduit().getPrice());
                            qte.setText("Qte :"+p.getQte());
                            prixt.setText(""+p.getProduit().getPrice()*p.getQte()); 
                            amount=amount+ (float)p.getProduit().getPrice();
                            btnaff.setText("Confirmer la commande : "+amount+" DT");

                            });
                            
                            amount=amount+p.getQte()*(float) p.getProduit().getPrice();
                            
             }
            Container c1 = new Container(BoxLayout.y());
            Container c2 = new Container(BoxLayout.x());
                        Container cb = new Container(BoxLayout.x());
btnaff.setText("Confirmer la commande : "+amount+" DT");
            c1.add(nom);
            c1.add(prixp);
            c1.add(qte);
            c2.add(c1);
            cb.add(addb);
            cb.add(remove);
            c1.add(cb);

            c3.add(c2);
            
        }            
         
         Container commande = new Container(new BorderLayout());

         btnaff.addActionListener((evt) -> {
             ConfirmOrder r = new ConfirmOrder(amount);
          r.getF().show();
         });
         
         commande.add(BorderLayout.NORTH,btnaff);
         c3.setScrollableY(true);
         f.add(BorderLayout.CENTER,c3);
         f.add(BorderLayout.SOUTH,commande);
         
               

    }
    
        public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    public void addpanier(com.mycompany.Entities.Panier p){
     p.setQte(p.getQte()+1);
    }
    public void removepanier(com.mycompany.Entities.Panier p){
     p.setQte(p.getQte()-1);
    }
}
