/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entities.Orders;
import com.mycompany.Entities.Users;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author berrahal
 */
public class OrdersService {
        ArrayList<Orders> listEvents = new ArrayList<>();
        int insert_id;
        public int addOrder(Orders o)
    {
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/orders/"); 
        con.setPost(true);
        Date d = new java.util.Date();
        Random r = new Random();

        
        con.setHttpMethod("POST");
        con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        con.addArgument("Date", d.toString());
        con.addArgument("reference", "Cupcake-M-"+r.nextInt((999999 - 0) + 1) + 0+"-2018");
        con.addArgument("adresse_id", ""+o.getAdresse_id()); // change it
        con.addArgument("utilisateur_id", ""+MyApplication.user.getId());
        con.addArgument("amount", ""+o.getAmount());
        con.addArgument("PaymentState", "notpaid");
        
        
        con.addResponseListener((e) -> {

            String json = new String(con.getResponseData());
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evt;
            try {
                
                evt = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                //List<Map<String, Object>> list = (List<Map<String, Object>>) evt.get("root");
                
                for (Map.Entry<String, Object> i: evt.entrySet()) {
                    
                    System.out.println("hedha el key ***********"+i.getKey());
                    if("insertId".equals(i.getKey())){
                        
                       System.out.println("hedha el s**********" + i.getValue());
                       
                       
                       Double l = (double) i.getValue();
                        insert_id = (int) Math.round(l);

                    }
                  
                    

                    
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        });
        
                NetworkManager.getInstance().addToQueueAndWait(con);
                return insert_id;
    }
        
        
        public void PayOrder(Orders o)
    {
     
        ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/payorder.php?id="+o.getId());
                r.setPost(false);
                NetworkManager.getInstance().addToQueueAndWait(r);
                System.err.println(r.getResponseCode());
    }
        
        
      public ArrayList<Orders> getOrdersbyUserid(int id){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/orders?_where=(utilisateur_id,eq,"+id+")"); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 OrdersService es = new OrdersService();
                listEvents = es.getOrders(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }
        
        
        public ArrayList<Orders> getOrders(String json) {

        ArrayList<Orders> listEvt = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evt = j.parseJSON(new CharArrayReader(json.toCharArray()));
            //System.out.println(evt);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) evt.get("root");

            for (Map<String, Object> obj : list) {
                
                Orders u = new Orders();
                float id = Float.parseFloat(obj.get("id").toString());
                            float adresse = Float.parseFloat(obj.get("adresse_id").toString());

                            float uid = Float.parseFloat(obj.get("utilisateur_id").toString());

                u.setId((int) id);
                u.setAdresse_id((int) adresse);
               
                String dd= obj.get("Date").toString();
                //String dt= dd.substring(0,10);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                
                Date d = new Date();
               
                
                try {
                    d= formatter.parse(dd);
                } catch (ParseException ex) {
                }
                
                u.setDate(d);
                System.err.println(d);
                  u.setPaymentState(obj.get("PaymentState").toString());
                  u.setUtilisateur_id((int) uid);
                  u.setReference(obj.get("reference").toString());
                  u.setAmount(Float.parseFloat(obj.get("amount").toString()));
               
                listEvt.add(u);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEvt);
        return listEvt;

    }

    
}
