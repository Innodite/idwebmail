/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class ContentMail extends ScrollPanel{
    
    private String initialHTML = "<div style='text-align:center;'>" +
                                  "<img src='org.yournamehere.Main/imgs/LogoID.gif' style='opacity:0.15; margin:5em auto' width='350px' height='115px'/>" +
                                  "</div>";
    
    public ContentMail(){
        this.setWidth("100%");
        this.setHeight("370px");
        this.setStyleName("innodite-scroll-content-mail");
        this.scrollToRight();
        this.setWidget(new HTML(initialHTML));
    }
    
    public void setContent(String html){
        this.setWidget(new HTML(html));
    }
    
    public void setCWidget(Widget w){
        this.setWidget(w);
    }
}