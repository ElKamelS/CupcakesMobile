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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entities.Orders;
import com.mycompany.Entities.Planning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author berrahal
 */
public class PlanningService {

    ArrayList<Planning> listEvents = new ArrayList<>();
    
     public ArrayList<Planning> getAllPlannings(){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/planning"); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 PlanningService es = new PlanningService();
                listEvents = es.getPlannings(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }

    public ArrayList<Planning> getPlannings(String json) {

        ArrayList<Planning> listEvt = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evt = j.parseJSON(new CharArrayReader(json.toCharArray()));
            //System.out.println(evt);

            List<Map<String, Object>> list = (List<Map<String, Object>>) evt.get("root");

            for (Map<String, Object> obj : list) {

                Planning u = new Planning();
                float id = Float.parseFloat(obj.get("id").toString());
                if (obj.get("lineorder_id") != null) {
                    float line = Float.parseFloat(obj.get("lineorder_id").toString());
                    u.setLine_order((int) line);

                }

                float uid = Float.parseFloat(obj.get("utilisateur_id").toString());

                   String dd= obj.get("DateStart").toString();
                //String dt= dd.substring(0,10);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                
                Date d = new Date();
                
                try {
                    d= formatter.parse(dd);
                } catch (ParseException ex) {
                }
                
                   String dd2= obj.get("DateEnd").toString();
                //String dt= dd.substring(0,10);
                SimpleDateFormat formatter2= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                
                Date d2 = new Date();
                
                try {
                    d2= formatter.parse(dd2);
                } catch (ParseException ex) {
                }
                u.setId((int) id);
                u.setUtilisateur_id((int)uid);
                u.setDatestart(d);
                u.setDateend(d2);
                listEvt.add(u);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEvt);
        return listEvt;

    }

}
