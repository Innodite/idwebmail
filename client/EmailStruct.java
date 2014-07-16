/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.user.client.ui.HTML;
import java.util.Date;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class EmailStruct {
    
    public final String asunto;
    public final Date fecha;
    public final String remitente;
    public final HTML body;
    public final String headAsunto;
    public final String headRemitente;
    public final String headDestinatario;
    public final String headfecha;
    public final int headSise;
    /**
     * Read flag.
     */
    public boolean read;
    
    public EmailStruct(String asunto, Date fecha, String remitente, HTML body,
                        int headSise, String headfecha,String headDestinatario) {
        this.asunto = asunto;
        this.fecha = fecha;
        this.remitente = remitente;
        this.body = body;
        this.headAsunto = asunto;
        this.headRemitente = remitente;
        this.headDestinatario = headDestinatario;
        this.headfecha = headfecha;
        this.headSise = headSise;
    }   
}