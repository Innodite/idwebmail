/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client.grids;

import java.util.Date;
import java.util.List;
 
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import java.util.ArrayList;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class ContactDatabase {
    
    public static class EmailInfo implements Comparable<EmailInfo> {
        
        private static int nextId = 0;
        private final int id;
        
        final String asunto;
        final Date fecha;
        final String remitente;
        public final HTML body;
        public final String headAsunto;
        public final String headRemitente;
        public final String headDestinatario;
        public final String headfecha;
        public final int headSise;
        
        public static final ProvidesKey<EmailInfo> KEY_PROVIDER = new ProvidesKey<EmailInfo>() {
            @Override
            public Object getKey(EmailInfo item) {
                return item == null ? null : item.getId();
            }
        };
        
        public EmailInfo(String asunto, Date fecha, String remitente, HTML body,
                          int headSise, String headfecha,String headDestinatario) {
            this.id = nextId;
            nextId++;
            
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
        
        @Override
        public int compareTo(EmailInfo o) {
            return (o == null || o.remitente == null) ? -1 : -o.remitente.compareTo(remitente);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof EmailInfo) {
                return id == ((EmailInfo) o).id;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return id;
        }

        public int getId() {
            return this.id;
        }
        
        /*
            @SuppressWarnings("deprecation")
            public int getAge() {
                Date today = new Date();
                int age = today.getYear() - birthday.getYear();
                if (today.getMonth() > birthday.getMonth()
                        || (today.getMonth() == birthday.getMonth() && today
                                .getDate() > birthday.getDate())) {
                    age--;
                }
                return age;
            }
        */
    }
    
    
 
    static interface DatabaseConstants extends Constants {
        String[] contactDatabaseCategories();
    }
 
    private static ContactDatabase instance;
 
    public static ContactDatabase get() {
        if (instance == null) {
            instance = new ContactDatabase();
        }
        return instance;
    }
 
    private final ListDataProvider<EmailInfo> dataProvider = new ListDataProvider<EmailInfo>();
 
    private ContactDatabase() {
        //generateContacts(15);
    }
 
    public void addContact(EmailInfo contact) {
        List<EmailInfo> contacts = dataProvider.getList();
        contacts.remove(contact);
        contacts.add(contact);
    }
 
    public void addDataDisplay(HasData<EmailInfo> display) {
        dataProvider.addDataDisplay(display);
    }
 
    public final List generateEmails(int count) {
        List<EmailInfo> email = dataProvider.getList();
        for (int i = 0; i < count; i++) {
            email.add(createEmailInfo());
        }
        return email;
    }
    
    public List agregarMail(ArrayList store){
        List<EmailInfo> email = dataProvider.getList();
        email.addAll(store);
        return email;
    }
    
    public List setStore(ArrayList store){
        dataProvider.getList().clear();
        List<EmailInfo> email = dataProvider.getList();
        email.addAll(store);
        return email;
    }
 
    public ListDataProvider<EmailInfo> getDataProvider() {
        return dataProvider;
    }
 
    public void refreshDisplays() {
        dataProvider.refresh();
    }
 
    @SuppressWarnings("deprecation")
    private EmailInfo createEmailInfo() {
        EmailInfo email = new EmailInfo("Asunto", new Date(2014, 5, 1), "Remitente",
                                         new HTML("Body"), 1, "HEAD FECHA", "Destinatario");
        return email;
    }
 
    private <T> T nextValue(T[] array) {
        return array[Random.nextInt(array.length)];
    }
}