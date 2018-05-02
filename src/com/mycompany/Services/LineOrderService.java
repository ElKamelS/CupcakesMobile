/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.mycompany.Entities.Line_Order;
import com.mycompany.Entities.Orders;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author berrahal
 */
public class LineOrderService {
      ArrayList<Line_Order> listEvents = new ArrayList<>();
       public void addLineOrder(Line_Order l)
    {
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/line_order/"); 
        con.setPost(true);
    

        
        con.setHttpMethod("POST");
        con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        con.addArgument("qte", ""+l.getQte());
        con.addArgument("commande_id", ""+l.getCommande_id());
        con.addArgument("product_id", ""+l.getProduct_id());
        con.addArgument("affected", "");
        
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
