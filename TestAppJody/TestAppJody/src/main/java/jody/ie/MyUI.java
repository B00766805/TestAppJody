package jody.ie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import com.microsoft.sqlserver.jdbc.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.*;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Connection connection= null;

        String connectionString = "jdbc:sqlserver://testjodydb.database.windows.net:1433;"+
        "database=TestJody;user=Jody@testjodydb;password=TestDBJo12;"+
        "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;"+
        "loginTimeout=30;";
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout hLayout = new HorizontalLayout();
        Grid<PersonalData> myGrid = new Grid<>();
                myGrid.setWidth("1200px");

        try{
            //connect jdbc driver to database
            connection = DriverManager.getConnection(connectionString);
            //layout.addComponent(new Label("Connected to Database" + connection.getCatalog()));
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM PERSONAL_DATA");
            List<PersonalData> pDatas = new ArrayList<PersonalData>();
            while (rs.next()){
                pDatas.add(new PersonalData(rs.getInt("id"),
                                 rs.getString("first_name"),
                                  rs.getString("last_name"),
                                      rs.getString("email"),
                            rs.getString("mobile_phone_no")) );

                // Set the items (List)
                
                myGrid.setItems(pDatas);
                // Configure the order and the caption of the grid
                myGrid.addColumn(PersonalData::getId).setCaption("Id");
                myGrid.addColumn(PersonalData::getFirstName).setCaption("First name");
                myGrid.addColumn(PersonalData::getLastName).setCaption("Surname");
                myGrid.addColumn(PersonalData::getEmail).setCaption("Email");
                myGrid.addColumn(PersonalData::getMobilePhoneNo).setCaption("Mobile phone no");
                // Configure the order and the caption of the grid  
                layout.addComponent(myGrid);
            }
            
            } 
            
        catch (Exception e){
            layout.addComponent(new Label(e.getMessage()));  
        }

        Label message = new Label();
        message.setValue("Please select the person from the below list");
        message.setContentMode (ContentMode.HTML);
 
        myGrid.setSelectionMode (SelectionMode.MULTI);
        MultiSelect<PersonalData> select = myGrid.asMultiSelect();
        myGrid.addSelectionListener(event -> {
        });
 
        layout.addComponent(message);
        layout.addComponent(myGrid);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
