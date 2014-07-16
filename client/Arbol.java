/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class Arbol extends Tree{
    // Create a root tree item as Cta de Correo
    private TreeItem ctamail = new TreeItem(){{    setHTML("");    }};
    
    public Arbol(){  
      TreeItem nuevo    = new TreeItem(){{   setHTML("Nuevo");      }};
      TreeItem entrada  = new TreeItem(){{   setHTML("Recibidos");  }};
      TreeItem salida   = new TreeItem(){{   setHTML("Enviados");   }};
      TreeItem borrador = new TreeItem(){{   setHTML("Borradores"); }};
      TreeItem papelera = new TreeItem(){{   setHTML("Papelera");   }};
      
      ctamail.addItem(nuevo);
      ctamail.addItem(entrada);
      ctamail.addItem(salida);
      ctamail.addItem(borrador);
      ctamail.addItem(papelera);
      
      this.addItem(ctamail);
    }
    
    public void setRootName(String str){
        ctamail.setHTML(str);
    }
}