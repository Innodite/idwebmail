/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client.grids;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract PagingDataGrid class to set initial GWT DataGrid and Simple Pager with ListDataProvider
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 * @param <T>
 */
public abstract class PagingDataGrid<T> extends Composite {
    private final DataGrid<T> dataGrid;
    private final SimplePager pager;
    private String height;
    private ListDataProvider<T> dataProvider;
    private List<T> dataList;
    private final DockPanel dock = new DockPanel();
    
    public PagingDataGrid() {
        initWidget(dock);
        dataGrid = new DataGrid<T>();
        dataGrid.setWidth("100%");
 
        SimplePager.Resources pagerResources = GWT
                .create(SimplePager.Resources.class);
        pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,true);
        pager.setDisplay(dataGrid);
        dataProvider = new ListDataProvider<T>();
        dataProvider.setList(new ArrayList<T>());
        dataGrid.setEmptyTableWidget(new HTML("No hay datos para mostrar."));
        ListHandler<T> sortHandler = new ListHandler<T>(dataProvider.getList());
        
        initTableColumns(dataGrid, sortHandler);
 
        dataGrid.addColumnSortHandler(sortHandler);
 
        dataProvider.addDataDisplay(dataGrid);
        pager.setVisible(true);
        dataGrid.setVisible(true);
        // Limitando la cantidad de registros por pagina
        pager.setPageSize(25);
        // Add a selection model to handle user selection.
        final SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>();
        dataGrid.setSelectionModel(selectionModel);
        
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
           @Override
           public void onSelectionChange(SelectionChangeEvent event) {
               T selected = selectionModel.getSelectedObject();
               onRowSelected(selected);
           }
        });
 
        dock.add(dataGrid, DockPanel.CENTER);
        dock.setHorizontalAlignment(ALIGN_CENTER);
        dock.add(pager, DockPanel.SOUTH);
        dock.setHorizontalAlignment(ALIGN_LEFT);
        dock.setWidth("100%");
        dock.setCellWidth(dataGrid, "100%");
        dock.setCellWidth(pager, "100%");
    }
    
    public void setEmptyTableWidget() {
        dataGrid.setEmptyTableWidget(new HTML(
                "The current request has taken longer than the allowed time limit. Please try your report query again."));
    }
    
    public void onRowSelected(T selected) {}
 
    /**
     * 
     * Abstract Method to implements for adding Column into Grid
     * 
     * @param dataGrid
     * @param sortHandler
     */
    public abstract void initTableColumns(DataGrid<T> dataGrid,   ListHandler<T> sortHandler);
 
    public String getHeight() {
        return height;
    }
 
    @Override
    public void setHeight(String height) {
        this.height = height;
        dataGrid.setHeight(height);
    }
 
    public List<T> getDataList() {
        return dataList;
    }
 
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        dataProvider.getList().clear();
        List<T> list = dataProvider.getList();
        list.addAll(this.dataList);
        dataProvider.refresh();
    }
 
    public ListDataProvider<T> getDataProvider() {
        return dataProvider;
    }
 
    public void setDataProvider(ListDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
    }
}