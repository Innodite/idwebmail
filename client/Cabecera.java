/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class Cabecera extends HorizontalPanel{
    
    final Label lb0  = getEtiqueta();
    final Button bt0 = getBtSend();
    final Button bt1 = getBtRSend();
    final Button bt2 = getBtCerrar();
    
    public Cabecera(){
        this.setWidth("100%");
        this.add(getLogo());
        this.setHorizontalAlignment(ALIGN_RIGHT);
        this.setVerticalAlignment(ALIGN_MIDDLE);
        
        bt0.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                actionSend();
            }
        });
        
        bt2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                actionClose();
            }
        });
        
        lb0.getElement().getStyle().setFloat(Style.Float.RIGHT);
        bt0.getElement().getStyle().setFloat(Style.Float.RIGHT);
        bt1.getElement().getStyle().setFloat(Style.Float.RIGHT);
        bt2.getElement().getStyle().setFloat(Style.Float.RIGHT);
        
        this.add(new HorizontalPanel(){{
            setWidth("300");
            setSpacing(3);
            add(lb0);
            add(bt0);
            add(bt1);
            add(bt2);
        }});
    }
    
    // Create a Image widget 
    private Image getLogo(){
        Image logo = new Image();
        //set image source
        logo.setUrl("org.idwebmail.Main/imgs/LogoID.gif");
        //logo.setPixelSize(250, 75);
        logo.setWidth("250px");
        logo.setHeight("75px");
        logo.setStyleName("innodite-logo-head", true);
        return logo;
    }
    
    private Label getEtiqueta(){
        Label lb = new Label("");
        return lb;
    }
    
    private Button getBtCerrar(){
        Button bt00 = new Button("Salir"){{ setWidth("55px"); }};
        return bt00;
    }
    
    private Button getBtSend(){
        Button bt01 = new Button("Enviar"){{ setWidth("60px"); setEnabled(false); }};
        return bt01;
    }
    
    private Button getBtRSend(){
        Button bt02 = new Button("Reenviar"){{ setWidth("80px"); setEnabled(false); }};
        return bt02;
    }
    
    public void setUserLogIn(String str){
        lb0.setText("Bienvenido, " + str);
    }
    
    public void prepareToSend(String to, String cc, String co, String sg){
        MainEntryPoint.EnviarBySMTP(to, cc, co, sg);
    }
    public void setEnableSend(boolean b){ this.bt0.setEnabled(b);  }
    public void setEnableRSend(boolean b){ this.bt1.setEnabled(b); }
    public void actionSend(){}
    public void actionClose(){}
}