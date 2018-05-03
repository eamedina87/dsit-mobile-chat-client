package edu.upc.whatsapp.REST_API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by juanluis.
 */

public interface Comms {
  String WhatsApp_server = "10.0.2.2";
  //String WhatsApp_server = "127.0.0.1";
  String url_rpc = "http://"+WhatsApp_server+":8080/WhatsAppServer/webresources";
//  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss+01:00").create();
  Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializerDeserializer()).create();
}
