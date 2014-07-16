/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client.grids;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.ArrayList;
import java.util.Date;
import org.idwebmail.client.EmailStruct;

/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class DataGrid extends CellTable{
   
    
   private DataGrid instance = this; 
   private HTML selectedBody;
   private String date;
   private String to;
   private String subject;
   private String from;
   private int tamanio;
   
    
   public DataGrid() {
       
       this.setWidth("100%", true);
       this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
       this.setPageSize(4);
       
       
       // Add a text column to show the name.
       TextColumn<EmailStruct> nameColumn = new TextColumn<EmailStruct>() {
           @Override
           public String getValue(EmailStruct object) {
               return object.asunto;
           }
       };
       this.addColumn(nameColumn, "Asunto");

       // Add a date column to show the birthday.
       DateCell dateCell = new DateCell();
       Column<EmailStruct, Date> dateColumn = new Column<EmailStruct, Date>(dateCell) {
           @Override
           public Date getValue(EmailStruct object) {
               return object.fecha;
           }
       };
       this.addColumn(dateColumn, "Fecha");

       // Add a text column to show the address.
       TextColumn<EmailStruct> addressColumn = new TextColumn<EmailStruct>() {
           @Override
           public String getValue(EmailStruct object) {
               return object.remitente;
           }
       };
       this.addColumn(addressColumn, "Remitente");

       // Add a selection model to handle user selection.
       final SingleSelectionModel<EmailStruct> selectionModel = new SingleSelectionModel<EmailStruct>();
       this.setSelectionModel(selectionModel);
       
       selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
           @Override
           public void onSelectionChange(SelectionChangeEvent event) {
               EmailStruct selected = selectionModel.getSelectedObject();
               selectedBody = selected.body;
               
               subject = selected.headAsunto;
               from = selected.headRemitente;
               to = selected.headDestinatario;
               date = selected.headfecha;
               tamanio = selected.headSise;
               onRowSelected();
           }
       });
       this.setStyleName("innodite-grid");
   }
   
   public HTML getHBody(){ return this.selectedBody; }
   
   public String getAsunto(){ return this.subject; }
   
   public String getFrom(){ return this.from; }
   
   public String getTo(){ return this.to; }
   
   public String getFecha(){ return this.date; }
   
   public int getSise(){ return this.tamanio; }
   
   public void setStore(ArrayList store){
       this.setRowCount(store.size(),true);
       this.setRowData(0, store);
   }
   
   public void onRowSelected(){}
}
