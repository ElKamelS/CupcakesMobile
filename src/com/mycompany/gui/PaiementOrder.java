/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.Entities.Orders;
import com.mycompany.Entities.PaiementStripe;
import com.mycompany.Services.OrdersService;
import com.stripe.model.Charge;
import com.stripe.model.Token;

/**
 *
 * @author berrahal
 */
public class PaiementOrder {
Form f;
    Button btnaff = new Button("Payer");
    public PaiementOrder(Orders order) {
        f = new Form("Paiement  "+order.getAmount()+"DT");
   
        TextField num = new TextField("", "Numero carte") ;
        TextField mois = new TextField("", "mois") ;
        TextField annee = new TextField("", "annee") ;
        TextField cvv = new TextField("", "cvv") ;
                TextField email = new TextField("", "email") ;
               
                btnaff.addActionListener((evt) -> {
                    if (num.getText()==null || mois.getText()==null || annee.getText()==null || email.getText()==null ) {
                        Dialog.show("Erreur", "Merci de vérifier vos informations" , "OK", null); 
                    }
                    else{
                        
                  
 
                    int mois0 = Integer.parseInt(mois.getText());
        int annee0 = Integer.parseInt(annee.getText());
                
                                    Token token = PaiementStripe.getToken("pk_test_AuAMdXwE57NnBcd4Xld65Ez4", num.getText(), mois0, annee0, cvv.getText(), email.getText());
 if(token !=null){
            Charge ch= PaiementStripe.ChargePayement("rk_test_oGfrFNOjpnRPklUVzjelPHgf", "usd", "tok_visa", order.getAmount(),"sk_test_yIqEVjLUzA1vwKhr1PjhnS9I", num.getText(), mois0, annee0, cvv.getText(), email.getText());
     OrdersService os = new OrdersService();
     os.PayOrder(order);

                        Dialog.show("Succès", "Le paiement a été effectué avec succès" , "OK", null); 
                         UserCommandes r = new UserCommandes();
          r.getF().show();

     
     
 }
 else
 {
     Dialog.show("Erreur", "Merci de vérifier vos informations" , "OK", null); 
 }
                 } });
                f.add(num);
                f.add(mois);
                f.add(annee);
                f.add(cvv);
                f.add(email);
                f.add(btnaff);

        

    }
         public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}
