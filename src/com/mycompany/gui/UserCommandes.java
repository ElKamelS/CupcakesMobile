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
import com.mycompany.Entities.Orders;
import com.mycompany.Services.AdressesService;
import com.mycompany.Services.OrdersService;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;

/**
 *
 * @author berrahal
 */
public class UserCommandes {

    Form f;
    Button btnaff = new Button("");

    public UserCommandes() {
        f = new Form("Mes commandes", new BorderLayout());
        OrdersService sb = new OrdersService();
        ArrayList<Orders> ls = new ArrayList<Orders>();
        ls = sb.getOrdersbyUserid(MyApplication.user.getId());
        System.err.println(ls.toString());
        Container c3 = new Container(BoxLayout.y());
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        for (Orders l : ls) {
            Button pay = new Button("Payer");

            if (l.getPaymentState().equals("paid")) {
                pay.setVisible(false);
            }
            
            pay.addActionListener((evt) -> {
                 PaiementOrder r = new PaiementOrder(l);
          r.getF().show();
            });

            Label nom = new Label(l.getReference());

            Container c1 = new Container(BoxLayout.y());
            Container c2 = new Container(BoxLayout.x());

            c2.add(nom);
            c2.add(pay);

            c1.add(c2);

            c3.add(c1);
            c3.setScrollableY(true);
        }
        Container ajout = new Container(new BorderLayout());
        
        f.add(BorderLayout.CENTER, c3);
        f.add(BorderLayout.SOUTH, ajout);

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
