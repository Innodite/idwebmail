/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client.grids;

import java.util.Comparator;
 
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import org.idwebmail.client.grids.ContactDatabase.EmailInfo;

/**
 * MyPaginationDataGrid extends PagingDataGrid to add Columns into Grid by implementation of 
 * initTableColumns() method
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 * @param <T>
 */
public class MyPaginationDataGrid<T> extends PagingDataGrid<T> {
    @Override
    public void initTableColumns(DataGrid<T> dataGrid,ListHandler<T> sortHandler) {
            Column<T, String> asunto = new Column<T, String>(new TextCell()) {
                @Override
                public String getValue(T object) {
                    return ((EmailInfo) object).headAsunto;
                }
            };
            asunto.setSortable(true);
            sortHandler.setComparator(asunto, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((EmailInfo) o1).headAsunto.compareTo(((EmailInfo) o2).headAsunto);
                }
            });
            dataGrid.addColumn(asunto, "Asunto");
            dataGrid.setColumnWidth(asunto, 20, Unit.PCT);
            /******************************************************************/
            Column<T, String> fecha = new Column<T, String>(new TextCell()) {
                @Override
                public String getValue(T object) {
                    return ((EmailInfo) object).headfecha;
                }
            };
            fecha.setSortable(true);
            sortHandler.setComparator(fecha, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((EmailInfo) o1).headfecha.compareTo(((EmailInfo) o2).headfecha);
                }
            });
            dataGrid.addColumn(fecha, "Fecha");
            dataGrid.setColumnWidth(fecha, 20, Unit.PCT);
            /******************************************************************/
            Column<T, String> lastNameColumn = new Column<T, String>(new TextCell()) {
                @Override
                public String getValue(T object) {
                    return ((EmailInfo) object).headRemitente;
                }
            };
            lastNameColumn.setSortable(true);
            sortHandler.setComparator(lastNameColumn, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((EmailInfo) o1).headRemitente.compareTo(((EmailInfo) o2).headRemitente);
                }
            });
            dataGrid.addColumn(lastNameColumn, "Remitente");
            dataGrid.setColumnWidth(lastNameColumn, 20, Unit.PCT);
    }   
}