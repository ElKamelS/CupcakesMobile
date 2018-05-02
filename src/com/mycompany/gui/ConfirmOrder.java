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
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entities.Line_Order;
import com.mycompany.Entities.Orders;
import com.mycompany.Services.AdressesService;
import com.mycompany.Services.LineOrderService;
import com.mycompany.Services.OrdersService;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;

/**
 *
 * @author berrahal
 */
public class ConfirmOrder {

    Form f;
    Button btnaff = new Button("Confirmer la commande");

    public ConfirmOrder(float amount) {
        f = new Form("Adresse de livraison");
        AdressesService sb = new AdressesService();
        ArrayList<com.mycompany.Entities.Adresses> ls = new ArrayList<com.mycompany.Entities.Adresses>();
        ls = sb.getAdressesbyUserId(MyApplication.user.getId());
        System.err.println(ls.toString());
        Container c3 = new Container(BoxLayout.y());
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        for (com.mycompany.Entities.Adresses l : ls) {
            Button edit = new Button("Confirmer");

            Label nom = new Label(l.getNom());
            Label prenom = new Label(l.getPrenom());
            Label adresse = new Label("Adresse:" + l.getAdresse() + ", " + l.getVille() + "," + l.getPays());
            Label tel = new Label("Tel:" + l.getTelephone());
            adresse.getUnselectedStyle().setFont(font);
            tel.getUnselectedStyle().setFont(font);
            edit.addActionListener((evt) -> {
                OrdersService os = new OrdersService();
                Orders o = new Orders();
                o.setAdresse_id(l.getId());
                o.setAmount(amount);
                int primkey = os.addOrder(o);

                LineOrderService lines = new LineOrderService();
                for (com.mycompany.Entities.Panier p : MyApplication.Panier) {
                    Line_Order line = new Line_Order();
                    line.setCommande_id(primkey);
                    line.setProduct_id(p.getProduit().getId());
                    line.setQte(p.getQte());
                    lines.addLineOrder(line);
                    o.setId(primkey);
                    MyApplication.Panier.clear();
                    PaiementOrder r = new PaiementOrder(o);
                    r.getF().show();
                }

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

        f.add(c3);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
