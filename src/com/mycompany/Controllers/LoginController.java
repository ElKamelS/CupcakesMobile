/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Controllers;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entities.Users;
import com.mycompany.Services.UsersService;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.Oauth2;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Dialog;
import com.mycompany.gui.Home;

/**
 *
 * @author berrahal
 */
public class LoginController {

     String status;
    Users usertest = new Users();

    public String login(String username, String password) {
        ArrayList<Users> users = new ArrayList<>();
        UsersService us = new UsersService();
        users = us.getAllUsers();
        for (Users user : users) {
            if (user.getUsername().equals(username)) {
                MyApplication.user = user;
                String p;

              
               

            }

        }
        if (MyApplication.user!=null) {
            
       
         ConnectionRequest r = new ConnectionRequest();
                r.setUrl("http://localhost/testpass.php?user=" + MyApplication.user.getId() + "&password=" + password);
                r.setPost(false);
                r.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
               
                       status =testpass(new String(r.getResponseData()));
                       

                    }

                });

                NetworkManager.getInstance().addToQueueAndWait(r);
        return status;
          }
        else
        {
            return "nouser";
        }
      
    }
    
    
    public String testpass(String status)
    {
        if (status.equals("nice")) {
                            return "good";
                        }
                        else 
                        {
                            return "notgood";
                        }
    }
     public static String TOKEN;
     
     private static void signIn() {
       /* FaceBookAccess.setClientId("344309509416204");
        FaceBookAccess.setClientSecret("ce2ad147b8ac69cb16df8c90c8c62990");
        FaceBookAccess.setRedirectURI("http://www.google.com/");
        //public_profile,user_birthday,email
        FaceBookAccess.setPermissions(new String[]{"public_profile", "user_birthday", "email"});
                        Login fb = FacebookConnect.getInstance();
                        fb.doLogin();
        FaceBookAccess.getInstance().showAuthentication(new ActionListener() {
           
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() instanceof String) {
                    String token = (String) evt.getSource();
                    String expires = Oauth2.getExpires();
                    TOKEN = token;
                    System.out.println("recived a token " + token + " which expires on " + expires);
                    //store token for future queries.
                    Storage.getInstance().writeObject("token", token);
                   
                } else {
                    //Exception err = (Exception) evt.getSource();
                    //err.printStackTrace();
                    Dialog.show("Error", "An error occurred while logging in: " , "OK", null);
                    System.err.println(evt.getSource().toString());
                }
            }
        });*/
       
        String clientId = "344309509416204";
                String redirectURI = "http://www.google.com/";
                String clientSecret = "ce2ad147b8ac69cb16df8c90c8c62990";
                Login fb = FacebookConnect.getInstance();
                fb.setClientId(clientId);
                fb.setRedirectURI(redirectURI);
                fb.setClientSecret(clientSecret);
               
                //Sets a LoginCallback listener
               fb.setCallback(new LoginCallback() {
                    @Override
    public void loginSuccessful() {
            String token = fb.getAccessToken().getToken();
                                        System.err.println(token);
                                        UsersService us = new UsersService();
                                        Users u =new Users();
                                        u=us.getUserInfoFB(token);
                                        System.err.println(u.getFirstname()+u.getEmail());
                                        
                                        if (us.getUserbyEmail(u.getEmail())!=null) {
                                            MyApplication.user=us.getUserbyEmail(u.getEmail());
                                            
                    }
                                        else{
                                           us.addUser(u);
                                           MyApplication.user=u;
                                           
                                        }
                          Home r = new Home();
          r.getF().show();             

    }

    @Override
    public void loginFailed(String errorMessage) {
        Dialog.show("No!", "it did not work!", "sad", null);
    }
                   
});
                //trigger the login if not already logged in
                if(!fb.isUserLoggedIn()){
                    fb.doLogin();
                    
                   
                }else{
                    //get the token and now you can query the facebook API
                    String token = fb.getAccessToken().getToken();
                                        System.err.println(token);
                                        UsersService us = new UsersService();
                                        Users u =new Users();
                                        u=us.getUserInfoFB(token);
                                        System.err.println(u.getFirstname()+u.getEmail());
                                        
                                        if (us.getUserbyEmail(u.getEmail())!=null) {
                                            MyApplication.user=us.getUserbyEmail(u.getEmail());
                                            
                    }
                                        else{
                                           us.addUser(u);
                                           MyApplication.user=u;
                                           
                                        }
                                        

                }
              
}
      public static boolean firstLogin() {
        return Storage.getInstance().readObject("token") == null;
}
    public static void login() {
        if (firstLogin()) {
            signIn();
        } else {
            //token exists no need to authenticate
            TOKEN = (String) Storage.getInstance().readObject("token");
            FaceBookAccess.setToken(TOKEN);
            //in case token has expired re-authenticate
            FaceBookAccess.getInstance().addResponseCodeListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent evt) {
                    NetworkEvent ne = (NetworkEvent) evt;
                    int code = ne.getResponseCode();
                    //token has expired
                    if (code == 400) {
            System.err.println("400");
                    }                    
                }
            });
        }
}
    
}
