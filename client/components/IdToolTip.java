/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client.components;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class IdToolTip extends PopupPanel{
    
    final int VISIBLE_DELAY = 2000;
    Timer removeDelay;
    boolean hideOnClick = false;
    /**
     * @param hideonclick
     * @param message
     * @param x
     * @param y
     */
    public IdToolTip(boolean hideonclick, String message, int x, int y){
        super(true);
        this.hideOnClick = hideonclick;
        this.setPopupPosition(x, y);
        this.add(new Label(message));
        
        removeDelay = new Timer(){
          @Override
          public void run() {
              IdToolTip.this.setVisible(false);
              IdToolTip.this.hide();
          }        
        };
        removeDelay.schedule(VISIBLE_DELAY);
        this.addPopupListener(new PopupListener(){
            @Override
            public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
              removeDelay.cancel();
            }
        });
        this.setStyleName("id-toolTip");
        this.show();
    }
    /**
     * @param hideonclick
     * @param w
     * @param x
     * @param y
     */
    public IdToolTip(boolean hideonclick, Widget w, int x, int y){
        super(true);
        this.hideOnClick = hideonclick;
        this.setPopupPosition(y, y);
        this.add(w);
        
        removeDelay = new Timer(){
          @Override
          public void run() {
              IdToolTip.this.setVisible(false);
              IdToolTip.this.hide();
          }        
        };
        removeDelay.schedule(VISIBLE_DELAY);
        this.addPopupListener(new PopupListener(){
            @Override
            public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
              removeDelay.cancel();
            }
        });
        this.setStyleName("id-toolTip");
        this.show();
    }
    /**
     * @param event
     * @return boolean
     */
    @Override
    public boolean onEventPreview(Event event){
        int type = DOM.eventGetType(event);
        switch(type){
           case Event.ONMOUSEDOWN:
           case Event.ONCLICK:{
             if (this.hideOnClick)
                this.hide();
             return true;
           }
        }
        return false;
    }   
}