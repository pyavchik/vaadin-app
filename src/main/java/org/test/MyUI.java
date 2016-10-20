package org.test;

import javax.servlet.annotation.WebServlet;
//Adyw56ez
//adyw56ez
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.List;

@Theme("mytheme")
public class MyUI extends UI {

    private CustomerService customerService = CustomerService.getInstance();
    private Grid grid = new Grid();
    private TextField filterText = new TextField();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();
        filterText.setInputPrompt("filter by name...");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Customer.class,
                    customerService.findAll(e.getText())));
        });

        Button clearFieldsButton = new Button("Clear filter");

        clearFieldsButton.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        grid.setColumns("firstName", "lastName", "email");


        verticalLayout.addComponents(filterText, clearFieldsButton, grid);


        updateList();

        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        setContent(verticalLayout);
    }

    public void updateList() {
        List<Customer> custumers = customerService.findAll(filterText.getValue());
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, custumers));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
