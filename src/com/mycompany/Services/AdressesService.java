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
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entities.Adresses;
import com.mycompany.Entities.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author berrahal
 */
public class AdressesService {
    ArrayList<Adresses> listAdresses = new ArrayList<>();
       public ArrayList<Adresses> getAllAdresses(){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/adresses/"); 
        con.setPost(false);
        con.setHttpMethod("GET");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                AdressesService es = new AdressesService();
                listAdresses = es.getAdresses(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAdresses;
    }
       
        public ArrayList<Adresses> getAdresses(String json) {

        ArrayList<Adresses> listEvt = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evt = j.parseJSON(new CharArrayReader(json.toCharArray()));
            //System.out.println(evt);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) evt.get("root");

            for (Map<String, Object> obj : list) {
                
                Adresses u = new Adresses();
                float id = Float.parseFloat(obj.get("id").toString());
            float uid = Float.parseFloat(obj.get("utilisateur_id").toString());

                u.setId((int) id);
                u.setAdresse(obj.get("adresse").toString());
                u.setComplement(obj.get("complement").toString());
                u.setCp(obj.get("cp").toString());
                u.setNom(obj.get("nom").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setTelephone(obj.get("telephone").toString());
                u.setUtilisateur_id((int) uid);
                u.setVille(obj.get("ville").toString());
                u.setPays(obj.get("pays").toString());
               
                listEvt.add(u);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEvt);
        return listEvt;

    }
        
        
        
        public void addAdresses(Adresses a)
    {
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/adresses/"); 
        con.setPost(true);
        
        con.setHttpMethod("POST");
        con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        con.addArgument("utilisateur_id", Integer.toString(a.getUtilisateur_id()));
        con.addArgument("adresse", a.getAdresse());
        con.addArgument("cp", a.getCp());
        con.addArgument("nom", a.getNom());
        con.addArgument("prenom", a.getPrenom());
        con.addArgument("telephone", a.getTelephone());
        con.addArgument("ville", a.getVille());
        con.addArgument("pays", a.getPays());
        con.addArgument("complement", a.getComplement());
        
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
        
        public ArrayList<Adresses> getAdressesbyUserId(int id){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/adresses?_where=(utilisateur_id,eq,"+id+")"); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 AdressesService es = new AdressesService();
                listAdresses = es.getAdresses(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAdresses;
    }
    
        
         public void updateAdresse(Adresses a)
    {
     
        ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/editadresse.php?id="+a.getId()+"&nom="+a.getNom()+"&prenom="+a.getPrenom()+"&tel="+a.getTelephone()+"&adresse="+a.getAdresse()+""
                        + "&ville="+a.getVille()+"&pays="+a.getPays()+"&cp="+a.getCp()+"&complement="+a.getComplement());
                r.setPost(false);
                NetworkManager.getInstance().addToQueueAndWait(r);
                System.err.println(r.getResponseCode());
    }
    
}
