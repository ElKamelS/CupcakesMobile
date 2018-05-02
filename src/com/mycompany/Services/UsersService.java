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
import com.codename1.io.rest.Rest;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Date;


import com.mycompany.Entities.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;





/**
 *
 * @author berrahal
 */
public class UsersService {
    ArrayList<Users> listEvents = new ArrayList<>();
    Users u = new Users();
    
     
    public ArrayList<Users> getAllUsers(){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/users/"); 
        con.setPost(false);
        con.setHttpMethod("GET");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                UsersService es = new UsersService();
                listEvents = es.getUsers(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }
    
    
    public Users getUserbyEmail(String email){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/users?_where=(email,eq,"+email+")"); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 UsersService es = new UsersService();
                listEvents = es.getUsers(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        if (listEvents.isEmpty()) {
            return null;
        }
        else
        {
                    return listEvents.get(0);

        }
    }
    
    public Users getUserbyId(int id){    
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/users/"+id); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 UsersService es = new UsersService();
                listEvents = es.getUsers(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents.get(0);
    }
    
    public Users getUserInfoFB(String token)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("https://graph.facebook.com/v2.12/me?fields=first_name,last_name,birthday,email&access_token="+token); 
        con.setPost(false);
         
        con.setHttpMethod("GET");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 UsersService es = new UsersService();
                u= es.getFBGraph(new String(con.getResponseData()));
           
              


            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return u;
    }
    
    
     public Users getFBGraph(String json) {


       
            System.out.println(json);
            JSONParser j = new JSONParser();

         
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

//System.out.println(jsonObject.get("email").getAsString()); //John
         


          
                Users u = new Users();
               
                if (jsonObject.get("first_name").getAsString() != null) {
                    u.setFirstname(jsonObject.get("first_name").getAsString());
                }
                if (jsonObject.get("last_name").getAsString() != null) {
                    u.setLastname(jsonObject.get("last_name").getAsString());
                }
                
                if (jsonObject.get("birthday").getAsString() != null) {
                 Date thedate;
                try {
                    thedate = new SimpleDateFormat("MM/dd/yyyy").parse(jsonObject.get("birthday").getAsString());
                u.setBirthday(thedate);
                } catch (ParseException ex) {
                    System.err.println(ex.getMessage());
                }
                 
                }
               
                if (jsonObject.get("email").getAsString() != null) {
                    u.setEmail(jsonObject.get("email").getAsString());
                   
                }
                u.setUsername(jsonObject.get("first_name").getAsString()+jsonObject.get("last_name").getAsString());

                u.setPassword(u.getFirstname()+u.getLastname());
                
                
               return u;

         

    }
    
    public ArrayList<Users> getUsers(String json) {

        ArrayList<Users> listEvt = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evt = j.parseJSON(new CharArrayReader(json.toCharArray()));
            //System.out.println(evt);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) evt.get("root");

            for (Map<String, Object> obj : list) {
                
                Users u = new Users();
                float id = Float.parseFloat(obj.get("id").toString());
            

                u.setId((int) id);
                u.setUsername(obj.get("username").toString());
                u.setEmail(obj.get("email").toString());
                u.setEnabled((int) Float.parseFloat(obj.get("enabled").toString()));
                if (obj.get("FirstName") != null) {
                    u.setFirstname(obj.get("FirstName").toString());
                }
                if (obj.get("LastName") != null) {
                    u.setLastname(obj.get("LastName").toString());
                }
                if (obj.get("PhoneNumber") != null) {
                    u.setPhonenumber((long) Float.parseFloat(obj.get("PhoneNumber").toString()));
                }
               
                if (obj.get("roles") != null) {
                    u.setRoles(obj.get("roles").toString());
                }
                
              
                u.setPassword(obj.get("password").toString());
                
                
                System.out.println(u.getUsername());
                listEvt.add(u);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEvt);
        return listEvt;

    }
    
    
    public void addUser(Users user)
    {
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/users/"); 
        con.setPost(true);
        
        con.setHttpMethod("POST");
        con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        con.addArgument("username", user.getUsername());
        con.addArgument("username_canonical", user.getUsername());
        con.addArgument("email", user.getEmail());
        con.addArgument("email_canonical", user.getEmail());
        con.addArgument("enabled", "1");
        con.addArgument("password", genpass(user.getPassword()));
        con.addArgument("Roles", "ROLE_USER");
        con.addArgument("FirstName", user.getFirstname());
        con.addArgument("LastName", user.getLastname());
        con.addArgument("Birthday", user.getBirthday().toString());
        con.addArgument("PhoneNumber",Long.toString(user.getPhonenumber()));
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    public void updatepasswordUser(Users user)
    {
        /* ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:3000/api/users/"+user.getId()); 
        con.setPost(true);
         


        con.setHttpMethod("POST");

        con.addRequestHeader("X-HTTP-Method-Override", "PATCH");
              //con.setHttpMethod("POST");
        con.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        con.addArgument("password", genpass(user.getPassword()));
        
        NetworkManager.getInstance().addToQueueAndWait(con);*/
        ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/editpass.php?id="+user.getId()+"&password="+user.getPassword());
                r.setPost(false);
                NetworkManager.getInstance().addToQueueAndWait(r);
    }
    
    
    public void updateUser(Users user)
    {
     
        ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/edituser.php?id="+user.getId()+"&nom="+user.getFirstname()+"&prenom="+user.getLastname()
                +"&username="+user.getUsername()+"&tel="+user.getPhonenumber()+"&email="+user.getEmail());
                r.setPost(false);
                NetworkManager.getInstance().addToQueueAndWait(r);
                System.err.println(r.getResponseCode());
    }
    
      public String pass;
    public  String genpass(String password)
    {
        ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/genpass.php?password="+password);
                r.setPost(false);
                r.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                       /* try {
                            NetworkEvent event = (NetworkEvent) ev;
                            byte[] data = (byte[]) event.getMetaData();
                            String decodedData = new String(data, "UTF-8");
                            System.out.println(decodedData);
                            if (decodedData.equals("nice")) {
                                LoginController.status="good";

                            } else {
                                LoginController.status="notgood";
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }*/
                       pass =new String(r.getResponseData());
                        //System.err.println(status);
                       

                    }

                });

                NetworkManager.getInstance().addToQueueAndWait(r);
                return pass;
    }
    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
}
