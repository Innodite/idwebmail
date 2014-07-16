/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class Cuerpo extends HorizontalPanel{
    
    private CenterPanel panelCentral = new CenterPanel(){ { setWidth("100%"); }
            @Override
            public void prepareDisableSend(boolean b) {
                super.prepareDisableSend(b);
                disableSend(b);
            }
    };
    
    private final LeftPanel panelIzq;
    
    public Cuerpo(){
        this.setHeight(Integer.toString(com.google.gwt.user.client.Window.getClientHeight()-3));
        
        panelIzq = new LeftPanel(){
            @Override
            public void onNuevo() {
                super.onNuevo();
                panelCentral.setEnableText();
            }
            @Override
            public void onRecibidos() {
                super.onRecibidos();
                panelCentral.setListBox("INBOX");
            }
            @Override
            public void onEnviados() {
                super.onEnviados();
                panelCentral.setListBox("INBOX.Sent");
            }
            @Override
            public void onBorradores() {
                super.onBorradores();
                panelCentral.setListBox("INBOX.Drafts");
            }
            @Override
            public void onPapelera() {
                super.onPapelera();
                panelCentral.setListBox("INBOX.Trash");
            }
        };
        
        
        add(panelIzq);
        add(panelCentral);
        panelCentral.getElement().getParentElement().setAttribute("width", "100%");
        panelIzq.getElement().getParentElement().setAttribute("height", "100%");
    }
    public void disableSend(boolean b){}
    public void setMsgSend(String html){ panelCentral.setContentBodyMail(html); }
    public void setRootName(String str){ panelIzq.setRootName(str); }
    public String getTo(){ return panelCentral.getTextTo(); }
    public String getCc(){ return panelCentral.getTextCc(); }
    public String getCo(){ return panelCentral.getTextCo(); }
    public String getSg(){ return panelCentral.getTextSg(); }
    public void clearHeadMail(){ panelCentral.clearHeadMail(); }
    public String getIdTxtar(){ return panelCentral.getIdTextarea(); }
}