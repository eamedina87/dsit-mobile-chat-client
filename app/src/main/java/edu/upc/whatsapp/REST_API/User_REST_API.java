package edu.upc.whatsapp.REST_API;

import com.google.gson.Gson;
import entity.User;
import entity.UserInfo;
import java.io.*;
import java.net.*;

import static edu.upc.whatsapp.REST_API.Comms.gson;
import static edu.upc.whatsapp.REST_API.Comms.url_rpc;

public class User_REST_API {

  public static UserInfo createUser_return_UserInfo(User user) throws Exception{
      URL url = new URL(url_rpc+"/entity.user/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(user));

      ucon.connect();

      if (ucon.getResponseCode()==200){
          BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
          ucon.disconnect();
          return gson.fromJson(in, UserInfo.class);
      } else {
          ucon.disconnect();
          throw new RuntimeException("Server responded with code:"+ucon.getResponseCode());
      }



  }
  public static UserInfo loginUser(User user) throws Exception{
      URL url = new URL(url_rpc+"/entity.user/login");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      //ucon.setDoInput(true);
      //ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(user));



      ucon.connect();

      if (ucon.getResponseCode()==200){
          BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
          ucon.disconnect();
          return gson.fromJson(in, UserInfo.class);
      } else {
          ucon.disconnect();
          throw new RuntimeException("Server responded with code:"+ucon.getResponseCode());
      }


  }
}
