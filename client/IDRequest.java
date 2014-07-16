/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class IDRequest extends MainEntryPoint{
    
  private String url = "";
  private Request request = null;
  private String data = "";
  private final int timeoutMillis = 300000;
  private RequestBuilder requestBuilder = null;
  private static String rs = ".";
  //private String dataUsr = "";
  //private String Msg = null;
  
  public IDRequest(IDFunction function){
      this.preparar(MainEntryPoint.getURL(), function);
  }
  
  public IDRequest(String url,IDFunction function){
      this.preparar(url, function);
  }
  
  private void preparar(String url,final IDFunction usrFunction){
    this.url = url;
    final IDFunction function = new IDFunction(){
      @Override
      public void execute(Response response){
          try{
              JSONObject JSON = (JSONObject)JSONParser.parse(response.getText());
              usrFunction.execute(response);
          }catch(Exception e){ usrFunction.execute(response); }
      };
    };
    this.requestBuilder = new RequestBuilder(RequestBuilder.POST,url);
    this.requestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded");
    this.requestBuilder.setTimeoutMillis(this.timeoutMillis);
    this.requestBuilder.setCallback(new RequestCallback() {
          @Override
          public void onResponseReceived(com.google.gwt.http.client.Request request, final Response response) {
              if (response.getStatusCode() == Response.SC_OK){
                  JSONObject JSON = null;
                  try{
                      JSON = (JSONObject)JSONParser.parse(response.getText());
                      if(MainEntryPoint.getBoolean(JSON.get("STDOUT"))){
                          final String JSONRespuesta = MainEntryPoint.getString(JSON.get("data"));
                          final Response tmp = new Response(){
                            @Override
                            public String getHeader(String arg0){return response.getHeader(arg0);}
                            @Override
                            public Header[] getHeaders(){return response.getHeaders();}
                            @Override
                            public String getHeadersAsString(){return response.getHeadersAsString();}
                            @Override
                            public int getStatusCode(){return response.getStatusCode();}
                            @Override
                            public String getStatusText(){return response.getStatusText();}
                            @Override
                            public String getText(){return JSONRespuesta;}
                          };
                          function.execute(tmp);
                      }else
                          JSON = null;
                  }catch(Exception e){ function.execute(response); }
              }
          }
          @Override
          public void onError(Request request, Throwable exception) {
              Window.alert("Error: " + exception.getMessage());
          }
      });
  }
  /** 
   * @param name
   * @param value
   */
  public void addParam(String name,String value){
    this.data = this.data + name + "=" + value + "&";
  }
  
  public boolean send(){
    try{
        this.requestBuilder.setRequestData(this.data);
        this.request = this.requestBuilder.send();
    }catch(RequestException e){ return false; }
    return true;
  }
  
  public void cancelar(){
    if(this.request != null)
      this.request.cancel();
  }
  
  public static String getAjaxData(String param){
        final String result = "-";
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, MainEntryPoint.getURL());
        builder.setRequestData(param);
        //final SynchronousQueue resultQueue = new SynchronousQueue();
        try{
            builder.sendRequest(null, new RequestCallback() {
              @Override
              public void onResponseReceived(Request request, Response response) {
                  JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                  if (MainEntryPoint.getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                      rs = MainEntryPoint.getString(json.get("modo"));
                  }
              }
              @Override
              public void onError(Request request, Throwable exception) { }
          });
          return rs;
        }catch (RequestException ex) { }
        return result;
  }
  
  public static void getAjaxData(String param,final String campo, final Callback<String, String> callback){  
    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, MainEntryPoint.getURL());
    builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
    builder.setRequestData(param);
    try {
      builder.sendRequest(param, new RequestCallback() {
        @Override
        public void onResponseReceived(Request request, Response response) {
          JSONObject json = (JSONObject) JSONParser.parse(response.getText());
          if (campo.isEmpty())
            callback.onSuccess(response.getText());
          else
            callback.onSuccess(MainEntryPoint.getString(json.get(campo)));
        }
        @Override
        public void onError(Request request, Throwable exception) {}
      });
    } catch (RequestException e) {}  
  }
}