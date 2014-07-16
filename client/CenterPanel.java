/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.idwebmail.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.idwebmail.client.grids.MyPaginationDataGrid;
import org.idwebmail.client.grids.ContactDatabase;
import org.idwebmail.client.grids.ContactDatabase.EmailInfo;
import static org.idwebmail.client.MainEntryPoint.getString;
import java.util.ArrayList;
import java.util.Date;
import org.idwebmail.client.components.IdToolTip;


/**
 *
 * @author merma158 <jurbano@innodite.com en Innodite, C.A.>
 */
public class CenterPanel extends VerticalPanel{
    
    private int xaux, yaux, i, j;
    private String strBs;
    private boolean sw = false;
    final ArrayList<String[]> datosContacts = new ArrayList<String[]>();
    private final VerticalPanel vppopup = new VerticalPanel();
    
    /**************************************************************************/
    TextBox txtTo = new TextBox(){{ 
        setTitle("Inserte Dirección de Correo");
        setWidth("99%"); 
        getElement().setPropertyString("placeholder", "Inserte Dirección de Correo");
        
        addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                xaux = event.getClientX();
                yaux = event.getClientY();
            }
        });
        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                MailsControlKey(getText(),"to");
            }
        });
    }};
    /**************************************************************************/
    TextBox txtCc = new TextBox(){{ 
        setTitle("Inserte Dirección de Correo");
        setWidth("99%");
        getElement().setPropertyString("placeholder", "Inserte Dirección de Correo");
        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                MailsControlKey(getText(),"cc");
            }
        });
    }};
    /**************************************************************************/
    TextBox txtCo = new TextBox(){{ 
        setTitle("Inserte Dirección de Correo");
        setWidth("99%");
        getElement().setPropertyString("placeholder", "Inserte Dirección de Correo");
        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                MailsControlKey(getText(),"co");
            }
        });
    }};
    /**************************************************************************/
    TextBox txtSg = new TextBox(){{ 
        setTitle("Inserte Asunto del Mensaje de Correo");
        setWidth("99%");
        getElement().setPropertyString("placeholder", "Inserte Asunto del Mensaje de Correo");
    }};
    
    private final HorizontalPanel hpCc = new HorizontalPanel(){{
        setVisible(false);
        setWidth("100%");
        setHorizontalAlignment(ALIGN_RIGHT);
        Label lbCc =  new Label("Cc:"){{ setWidth("50px"); }};
        add(lbCc); 
        setHorizontalAlignment(ALIGN_LEFT);
        add(txtCc);
        lbCc.getElement().getParentElement().setAttribute("width", "200px");
    }};
    private final HorizontalPanel hpCo = new HorizontalPanel(){{
        setVisible(false);
        setWidth("100%");
        setHorizontalAlignment(ALIGN_RIGHT);
        Label lbCo = new Label("Cco:"){{ setWidth("50px"); }};
        add(lbCo); 
        setHorizontalAlignment(ALIGN_LEFT);
        add(txtCo);
        lbCo.getElement().getParentElement().setAttribute("width", "200px");
    }};
    
    final TextArea text        = new TextArea(){{ getElement().setId("ceditor_00"); setVisible(false); }};
    final ContentMail bodyMail = new ContentMail();
    final VerticalPanel vpSend = new VerticalPanel(){{
        setWidth("100%");
        add(getVpSender());
        add(text);
        setVisible(false);
    }};
    /**************************************************************************/
    MyPaginationDataGrid<EmailInfo> grid = new MyPaginationDataGrid<EmailInfo>(){
        @Override
        public void onRowSelected(final EmailInfo selected) {
            super.onRowSelected(selected); //To change body of generated methods, choose Tools | Templates.
            showBodyMail();
            bodyMail.setCWidget(new VerticalPanel(){{
                        setWidth("100%");
                        setHorizontalAlignment(ALIGN_LEFT);
                        add(new HorizontalPanel(){{ 
                            setWidth("100%"); 
                            setStyleName("innodite-scroll-info-header-mail");  
                            add(new Label(selected.headAsunto)); 
                        }});
                        add(new HorizontalPanel(){{
                            setWidth("100%");
                            setStyleName("innodite-scroll-info-header-mail");
                            setSpacing(3);
                            setHorizontalAlignment(ALIGN_LEFT);
                            setVerticalAlignment(ALIGN_MIDDLE);
                            add(getImgPerfil());
                            setVerticalAlignment(ALIGN_TOP);
                            add(getHeadInfoMail(selected.headRemitente, selected.headDestinatario, selected.headfecha, selected.headSise));
                        }});
                        add(getMailBody(selected.body));
                    }});
        }
    };
    /**************************************************************************/
    public CenterPanel(){
        this.CargarContactos();
        this.setWidth("100%");
        this.setHeight("100%");
        
        grid.setHeight("250px");
        grid.setStyleName("innodite-grid");
        
        this.add(grid);
        this.add(bodyMail);
        this.add(vpSend);
    }
    private void CargarContactos(){
        datosContacts.clear();
        
        IDRequest res = new IDRequest(new IDFunction() {
             @Override
             public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                JSONArray array = json.get("data").isArray(); 
                
                for(int i=0;i<array.size();i++){
                    String v[] = new String[2];
                    v[0] = getString(array.get(i).isObject().get("nombre"));
                    v[1] = getString(array.get(i).isObject().get("correo"));
                    
                    datosContacts.add(v);
                }
             }
        });
        res.addParam("import", "mail.Mails.getContactsForTtip");
        res.send();
    }
    /**
     * @param strtxt
     * @param campo
     */
    public void MailsControlKey(final String strtxt, final String campo){
        if (strtxt.length() > 2){
                    j=0;
                    if (strtxt.contains(";")){
                        for (int k=strtxt.length();k>0;k--){
                            if (strtxt.charAt(k) == ';') break;
                            j++;
                        }
                        strBs = strtxt.substring(strtxt.length()-(j-1), strtxt.length());
                    }else{
                        strBs = strtxt;
                    }
                    
                    sw      = false;
                    vppopup.clear();
                    vppopup.add(new HorizontalPanel(){{
                        add(new Label("Nombre"){{ setWidth("180px"); }});
                        add(new Label("Correo"));
                    }});
                    
                    
                    for (i=0;i<datosContacts.size();i++){
                        if (datosContacts.get(i)[0].toLowerCase().contains(strBs.toLowerCase()) ||
                            datosContacts.get(i)[1].toLowerCase().contains(strBs.toLowerCase()) ||    
                            datosContacts.get(i)[0].toLowerCase().contains(strBs.toLowerCase().trim()) ||
                            datosContacts.get(i)[1].toLowerCase().contains(strBs.toLowerCase().trim())){
                            final String v[] = new String[2];
                            v[0] = datosContacts.get(i)[0];
                            v[1] = datosContacts.get(i)[1];
                            vppopup.add(new HorizontalPanel(){{
                                        add(new Label(datosContacts.get(i)[0]));
                                        add(new Anchor(datosContacts.get(i)[1]){{
                                            addClickHandler(new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    if (j == 0){
                                                        if (campo.toLowerCase().compareTo("to") == 0)
                                                            txtTo.setText("\""+v[0]+"\" | <"+v[1]+">;");
                                                        if (campo.toLowerCase().compareTo("cc") == 0)
                                                            txtCc.setText("\""+v[0]+"\" | <"+v[1]+">;");
                                                        if (campo.toLowerCase().compareTo("co") == 0)
                                                            txtCo.setText("\""+v[0]+"\" | <"+v[1]+">;");
                                                    }else{
                                                        if (campo.toLowerCase().compareTo("to") == 0)
                                                            txtTo.setText(strtxt.substring(0, (strtxt.length()-(j))) + ";\""+v[0] +"\" | <"+v[1]+">;");
                                                        if (campo.toLowerCase().compareTo("cc") == 0)
                                                            txtCc.setText(strtxt.substring(0, (strtxt.length()-(j))) + ";\""+v[0] +"\" | <"+v[1]+">;");
                                                        if (campo.toLowerCase().compareTo("co") == 0)
                                                            txtCo.setText(strtxt.substring(0, (strtxt.length()-(j))) + ";\""+v[0] +"\" | <"+v[1]+">;");
                                                    }
                                                }
                                            });
                                        }});
                                    }});
                                    if (strBs.length() > 2)
                                        sw = true;
                        }
                    }
                    if (sw){
                        IdToolTip tip = new IdToolTip(false, vppopup, xaux, yaux);
                    }
        }
    }
    
    public VerticalPanel getVpSender(){
        VerticalPanel vpSder = new VerticalPanel(){{ setWidth("100%"); }};
        vpSder.add(new HorizontalPanel(){{
                    setWidth("100%");
                    setHorizontalAlignment(ALIGN_RIGHT);
                    add(new HorizontalPanel(){{
                        add(new Button("Cc..", new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) { 
                                if (hpCc.isVisible())
                                    hpCc.setVisible(false);
                                else
                                    hpCc.setVisible(true);
                            }
                        }));
                        add(new Button("Co..", new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                if (hpCo.isVisible())
                                    hpCo.setVisible(false);
                                else
                                    hpCo.setVisible(true);
                            }
                        }));
                        setHorizontalAlignment(ALIGN_RIGHT);
                        add(new Label("Para:"){{ setWidth("50px"); }});
                    }});
                    setHorizontalAlignment(ALIGN_LEFT);
                    add(txtTo);
        }
            @Override
            protected void onLoad() {
                    super.onLoad(); //To change body of generated methods, choose Tools | Templates.
                    getElement().getElementsByTagName("td").getItem(0).setAttribute("width", "200px");
            }
        });
        vpSder.add(hpCc);
        vpSder.add(hpCo);
        vpSder.add(new HorizontalPanel(){{
                    setWidth("100%");
                    setHorizontalAlignment(ALIGN_RIGHT);
                    Label lbAs = new Label("Asunto:"){{ setWidth("50px"); }};
                    add(lbAs); 
                    setHorizontalAlignment(ALIGN_LEFT);
                    add(txtSg);
                    lbAs.getElement().getParentElement().setAttribute("width", "200px");
        }});
        return vpSder;
    }
    
    public Image getImgPerfil(){
        Image perfil = new Image();
        perfil.setWidth("60px");
        perfil.setUrl("org.yournamehere.Main/imgs/contactpic_48px.png");
        return perfil;
    }
    
    public VerticalPanel getHeadInfoMail(final String r, final String d, final String f, final int t){
        VerticalPanel vp = new VerticalPanel(){{ setHorizontalAlignment(ALIGN_LEFT); }};
        vp.add(new HorizontalPanel(){{
            add(new Label("Remitente:"){{ setWidth("90px"); }});
            add(new Label(r.trim()));
        }});
        vp.add(new HorizontalPanel(){{
            add(new Label("Destinatario:"){{ setWidth("90px"); }});
            add(new Label(d.trim()));
        }});
        vp.add(new HorizontalPanel(){{
            add(new Label("Fecha:"){{setWidth("90px"); }});
            add(new Label(f.trim()));
        }});
        vp.add(new HorizontalPanel(){{ 
            add(new Label("Tamaño:"){{ setWidth("90px"); }});
            add(new Label(Integer.toString(t)));
        }});
        return vp;
    }
    
    public ScrollPanel getMailBody(HTML html){
        ScrollPanel sp = new ScrollPanel(html){{ setStyleName("innodite-scroll-content-mail"); }};
        return sp;
    }
    
    public void showBodyMail(){
        if (text.isVisible())
            text.setVisible(false);
        if (vpSend.isVisible()){
            vpSend.setVisible(false);
            prepareDisableSend(false);
        }
        
        MainEntryPoint.hideCKE(this.getIdTextarea());
        
        if (!bodyMail.isVisible())
            bodyMail.setVisible(true);
    }
    
    public void setContentBodyMail(String html){
        showBodyMail();
        bodyMail.setContent(html);
    }
    
    public void setContentWidget(Widget w){
        bodyMail.setCWidget(w);
    }
    
    public void setEnableText(){
        if (bodyMail.isVisible())
            bodyMail.setVisible(false);
        if (!vpSend.isVisible()){
            vpSend.setVisible(true);
            prepareDisableSend(true);
        }
        MainEntryPoint.callCKEditor(this.getIdTextarea());
        MainEntryPoint.showCKE(this.getIdTextarea());
    }
    
    public void prepareDisableSend(boolean b){}
    
    public String getTextTo(){ return this.txtTo.getValue().trim(); }
    public String getTextCc(){ return this.txtCc.getValue().trim(); }
    public String getTextCo(){ return this.txtCo.getValue().trim(); }
    public String getTextSg(){ return this.txtSg.getValue().trim(); }
    public void setTextTo(String str){ this.txtTo.setText(str); }
    public void setTextCc(String str){ this.txtCc.setText(str); }
    public void setTextCo(String str){ this.txtCo.setText(str); }
    public void setTextSg(String str){ this.txtSg.setText(str); }
    
    public void clearHeadMail(){
        this.setTextTo("");
        this.setTextCc("");
        this.setTextCo("");
        this.setTextSg("");
    }
    
    public String getIdTextarea(){ return text.getElement().getId(); }
    
    public void setListBox(String box){
        
        final ArrayList<EmailInfo> datos = new ArrayList<EmailInfo>();
        IDRequest res = new IDRequest(new IDFunction() {
             @Override
             public void execute(Response response) {
                JSONObject json = (JSONObject) JSONParser.parse(response.getText());
                if (getString(json.get("msg")).toLowerCase().compareTo("exito") == 0){
                    JSONArray array = json.get("data").isArray(); 
                    for(int i=0;i<array.size();i++){
                        datos.add(new EmailInfo(getString(array.get(i).isObject().get("asunto")),
                                                    new Date(Date.parse(getString(array.get(i).isObject().get("fecha")))),
                                                    getString(array.get(i).isObject().get("from")), 
                                                    new HTML(getString(array.get(i).isObject().get("body"))),
                                                    Integer.parseInt(getString(array.get(i).isObject().get("size"))),
                                                    getString(array.get(i).isObject().get("date")),
                                                    getString(array.get(i).isObject().get("to")) ));
                    }
                    
                    grid.setDataList(ContactDatabase.get().setStore(datos));
                }
                
             }
        });
        res.addParam("import", "mail.Mails.getImapInbox");
        res.addParam("folder", box);
        res.send();
    }
    
    public void Enviar(){}
}