/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class LeftPanel extends StackPanel{
    
    private final LeftPanel instance = this;
    private final Arbol arbol;
    
    public LeftPanel(){
        this.setSize("200px", "100%");
        
        final Label labelMessage = new Label(){{ setWidth("300");}};
        arbol = new Arbol(){{
            addSelectionHandler(new SelectionHandler(){
                @Override
                public void onSelection(SelectionEvent event) {
                    labelMessage.setText("Selección: " + getSelectedItem().getText());
                    
                    if (!getSelectedItem().getText().isEmpty()){
                        if (getSelectedItem().getText().toUpperCase().compareTo("NUEVO") == 0){
                            onNuevo();
                        }
                        if (getSelectedItem().getText().toUpperCase().compareTo("RECIBIDOS") == 0){
                            onRecibidos();
                        }
                        if (getSelectedItem().getText().toUpperCase().compareTo("BORRADORES") == 0){
                            onBorradores();
                        }
                        if (getSelectedItem().getText().toUpperCase().compareTo("ENVIADOS") == 0){
                            onEnviados();
                        }
                        if (getSelectedItem().getText().toUpperCase().compareTo("PAPELERA") == 0){
                            onPapelera();
                        }
                    }
                }
            });
        }};
        
        VerticalPanel vpanel_00 = new VerticalPanel(){{ 
            add(arbol); 
            add(labelMessage);
        }};
        
        add(vpanel_00, "Correo "  ,false);
        add(new Label("Mis Contactos"), "Contactos",false);
        this.addStyleName("demo-panel");
    }
    
    public void onNuevo(){}
    public void onRecibidos(){}
    public void onEnviados(){}
    public void onBorradores(){}
    public void onPapelera(){}
    
    public void setRootName(String str){ 
        arbol.setRootName(str); 
        String p = "import=users.Users.getProgramData";
        IDRequest.getAjaxData(p, "modo", new Callback<String, String>() {
            @Override
            public void onFailure(String reason) {}
            @Override
            public void onSuccess(String result) {
                if (result.toLowerCase().compareTo("standard") == 0){
                    instance.add(new Label("mis Tareas"), "Tareas",false);
                    //instance.add(new Label("Mis Contactos"), "Contactos",false);
                }
            }
        });
    }
}