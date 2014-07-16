/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.idwebmail.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;  
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Main entry point.
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class MainEntryPoint implements EntryPoint {
    
    private static final String url = "/idfwmail/index.php";
    private static Cuerpo body;
    private static Cabecera head;
    private LogIn acceso;
    private final VerticalPanel verticalPanel   = new VerticalPanel(){{ 
          setWidth("100%");
          setHorizontalAlignment(ALIGN_JUSTIFY);
          setVerticalAlignment(ALIGN_MIDDLE);
          //setHeight(Integer.toString(com.google.gwt.user.client.Window.getClientHeight()-2)); 
    }};
    
    public static String userMail = "";
    public static String userNick = "";
    public static String programVersion = "";
    public static String programMode = "";
    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {}

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad(){
      //  Estable - Code
      DecoratorPanel decoratorPanel = new DecoratorPanel(){{ setWidth("100%"); setHeight("730px"); }};
      head = new Cabecera(){
          @Override
          public void actionSend() {
              super.actionSend();
              //"^([\"]([^.'\"])+[\"])[ ]{0,}\\|[ ]{0,}<[_a-z-A-Z-0-9-]+(\\.[_a-z-A-Z-0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})>[;]?$"
              //"^[_a-z-A-Z-0-9-]+(\\.[_a-z-A-Z-0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$"
              RegExp reStandar = RegExp.compile("(^([\"]([^.'\"])+[\"])[ ]{0,}\\|[ ]{0,}<[_a-z-A-Z-0-9-]+(\\.[_a-z-A-Z-0-9-]+)*@[a-z-A-Z-0-9-]+(\\.[a-z-A-Z-0-9-]+)*(\\.[a-z-A-Z]{2,3})>[;]?$)|"+
                                                 "(^[_a-z-A-Z-0-9-]+(\\.[_a-z-A-Z-0-9-]+)*@[a-z-A-Z-0-9-]+(\\.[a-z-A-Z-0-9-]+)*(\\.[a-z-A-Z]{2,3})[;]?$)");
              if (body.getTo().matches(reStandar.getSource())){
                  //prepareToSend(body.getTo(), body.getCc(), body.getCo(), body.getSg());
                  Window.alert("Pase");
              }else
                  Window.alert("No Paso");
              
          }
          @Override
          public void actionClose() {
              super.actionClose();
              logout();
          }
      };
      body = new Cuerpo(){
          @Override
          public void disableSend(boolean b) {
              super.disableSend(b);
              head.setEnableSend(b);
          }
      };
      
      acceso = new LogIn(){
          @Override
          public void onSubmit() {
              super.onSubmit();
              login(getUser(), getPass());
          }
      };
      
      verticalPanel.add(acceso);
      isLogged();
      decoratorPanel.add(verticalPanel);
      
      // Add the widgets to the root panel.
      RootPanel.get().add(decoratorPanel);
    }
    
    private void login(final String user, final String pass){
        IDRequest res = new IDRequest(new IDFunction() {
            @Override
            public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if (getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                    ScreenIn();
                }else{
                    acceso.setAlertMsg("Intente Nuevamente");
                }
            }
        });
        res.addParam("import", "users.Users.logIn");
        res.addParam("username", user);
        res.addParam("password", pass);
        res.send();
    }
    
    private void logout(){
        IDRequest res = new IDRequest(new IDFunction() {
            @Override
            public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if (getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                    verticalPanel.remove(1);
                    verticalPanel.remove(0);
                    verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
                    verticalPanel.add(acceso);
                }
            }
        });
        res.addParam("import", "users.Users.logOut");
        res.send();
    }
    
    private void isLogged(){
        String param = "import=users.Users.isLogged";
        IDRequest.getAjaxData(param, "log", new Callback<String, String>() {
            @Override
            public void onFailure(String reason) {}
            @Override
            public void onSuccess(String result) {
                if (result.toUpperCase().compareTo("S") == 0){
                    ScreenIn();
                }
            }
        });
    }
    
    private void ScreenIn(){
        acceso.setUser("");
        acceso.setPass("");
        verticalPanel.remove(0);
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        verticalPanel.add(head);
        verticalPanel.add(body);
        getUserMail();
    }
    
    public static void EnviarBySMTP(String to, String cc, String co, String sg){
        IDRequest res = new IDRequest(new IDFunction() {
            @Override
            public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if (getString(json.get("msg")).toLowerCase().compareTo("exito") == 0 &&
                    getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                    body.setMsgSend("Tu mensaje ha sido enviado..!");
                    body.clearHeadMail();
                    setCKEData(body.getIdTxtar(), "");
                }
            }
        });
        res.addParam("import", "mail.Mails.sendSmtpMail");
        res.addParam("txtto", to);
        res.addParam("txtcc", cc);
        res.addParam("txtco", co);
        res.addParam("txtsg", sg);
        res.addParam("txtmghtml", getCKEData(body.getIdTxtar()));
        res.addParam("txtmgtext", getCKEPlainText(body.getIdTxtar()));
        res.send();
    }
    
    public static String getURL(){
        return url;
    }
    
    public static void getUserMail(){
        IDRequest res = new IDRequest(new IDFunction() {
            @Override
            public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if(getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                    userMail = getString(json.get("correo"));
                    userNick = getString(json.get("user"));
                    head.setUserLogIn(userMail);
                    body.setRootName(userNick);
                }
            }
        });
        res.addParam("import", "users.Users.getUserMail");
        res.send();
    }
    
    public static void getProgramData(){
        IDRequest res = new IDRequest(new IDFunction() {
            @Override
            public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if(getString(json.get("data")).toLowerCase().compareTo("exito") == 0){
                    programVersion = getString(json.get("version"));
                    programMode = getString(json.get("modo"));
                }
            }
        });
        res.addParam("import", "users.Users.getProgramData");
        res.send();
    }
    
    public static boolean getBoolean(JSONValue jsonValue){
        if(jsonValue == null)
          return false;
        return ((JSONBoolean) jsonValue).booleanValue();
    }
    
    public static String getString(String value){
        return value.replace(".", "").replace(",",".");
    }
    /**
    * Retorna el string de un elemento del tipo JSONValue
    * @param jsonValue Objeto del cual se quiere el String
    * @return String que se obtuvo del objeto
    */
    public static String getString(JSONValue jsonValue){
      if(jsonValue == null || jsonValue.isString() == null || jsonValue.getClass().getName().equals("com.google.gwt.json.client.JSONNull"))
        return "null";
      return ((JSONString)jsonValue).stringValue();
    }
    /**
     * Retorna el int de un elemento del tipo JSONValue
     * @param jsonValue Objeto del cual se quiere el String
     * @return int que se obtuvo del objeto
     */
    public static int getInt(JSONValue jsonValue){
        if(jsonValue == null)
          return 0;
        return (int) Math.floor(((JSONNumber)jsonValue).doubleValue());
    } 
    
    public static native String getParameter(String name,String url)/*-{
        var regex = new RegExp ("[\\?&]"+name+"=([^&#]*)");
        var results = regex.exec(url);
        return results == null?"":results[1];
    }-*/;
    
    public static native void msg(String cad)/*-{
        $wnd.alert(cad);
    }-*/;
    
    /* Hack CKEditor JS */
    public static native void callCKEditor(String idCKE)/*-{
        $wnd.iniciaCKE(idCKE);
    }-*/;
    
    public static native void hideCKE(String idCKE)/*-{
        $wnd.hideCKE(idCKE);
    }-*/;
    
    public static native void showCKE(String idCKE)/*-{
        $wnd.showCKE(idCKE);
    }-*/;
    public static native String setCKEData(String idCKE,String valor)/*-{
        return $wnd.setCkeValue(idCKE,valor);
    }-*/;
    public static native String getCKEData(String idCKE)/*-{
        return $wnd.getCkeValue(idCKE);
    }-*/;
    public static native String getCKEPlainText(String idCKE)/*-{
        return $wnd.getCkePlainText(idCKE);
    }-*/;
}