/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class LogIn extends VerticalPanel{
    
    private final Label txtAlert          = new Label("");
    private final TextBox txtUser         = new TextBox(){{ 
        addFocusListener(new FocusListenerAdapter() {
            @Override
            public void onFocus(Widget sender) {
                super.onFocus(sender);
                txtAlert.setText("");
            }
        }); 
    }};
    private final PasswordTextBox txtPass = new PasswordTextBox(){{
        addFocusListener(new FocusListenerAdapter() {
            @Override
            public void onFocus(Widget sender) {
                super.onFocus(sender);
                txtAlert.setText("");
            }
        });
    }};
    private Button submit                 = new Button("Acceder", new ClickListener(){
            @Override
            public void onClick(Widget sender) {
                onSubmit();
            }
    });
    
    public LogIn(){
        this.setWidth("100%");
        this.setHeight("100%");
        this.setSpacing(3);
        this.setHorizontalAlignment(ALIGN_CENTER);
        this.setVerticalAlignment(ALIGN_MIDDLE);
        this.add(hpUser());
        this.add(hpPass());
        this.add(hpSubt());
        this.add(hpMsga());
    }
    
    public void onSubmit(){}
    public String getUser(){ return this.txtUser.getValue(); }
    public String getPass(){ return this.txtPass.getValue(); }
    public void setUser(String str){ this.txtUser.setText(str); }
    public void setPass(String str){ this.txtPass.setText(str); }
    public void setAlertMsg(String msg){
        this.txtAlert.setText(msg);
    }
    
    private HorizontalPanel hpUser(){
        HorizontalPanel hp = new HorizontalPanel(){{ setHorizontalAlignment(ALIGN_CENTER); }};
        hp.add(new Label("Usuario "){{ setWidth("80px"); }});
        txtUser.setText("jurbano@innodite.com");
        hp.add(txtUser);
        return hp;
    }
    
    private HorizontalPanel hpPass(){
        HorizontalPanel hp = new HorizontalPanel(){{ setHorizontalAlignment(ALIGN_CENTER); }};
        hp.add(new Label("Password"){{ setWidth("80px"); }});
        txtPass.setText("jut.120388");
        hp.add(txtPass);
        return hp;
    }
    
    private HorizontalPanel hpSubt(){
        HorizontalPanel hp = new HorizontalPanel(){{ setHorizontalAlignment(ALIGN_CENTER); }};
        hp.add(submit);
        return hp;
    }
    
    private HorizontalPanel hpMsga(){
        HorizontalPanel hp = new HorizontalPanel(){{ setHorizontalAlignment(ALIGN_CENTER); }};
        hp.add(txtAlert);
        return hp;
    }
}