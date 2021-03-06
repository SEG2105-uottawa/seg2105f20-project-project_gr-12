package com.example.servicenovigrad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private String name;
    private HashMap<String, Boolean> requiredInfo;
    private HashMap<String, Boolean> requiredDocs;
    private double price;

    //Creates a service with no required docs or info (to be added through the add methods)
    Service(String name, HashMap<String, Boolean> infoFormFields, HashMap<String, Boolean> docsFormFields, double price) {
        this.name = name;
        this.requiredInfo = infoFormFields;
        this.requiredDocs = docsFormFields;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*public void setPrice(float price) {
        this.price = price;
    }*/

    public double getPrice() {
        return price;
    }

    public HashMap<String, Boolean> getRequiredDocs() {
        return requiredDocs;
    }

    //Adds a new required doc to the list of required docs if the list does not already contain it and returns the new list
    /*public List<String> addRequiredDoc(String doc) {
        if (!requiredDocs.contains(doc)) {
            requiredDocs.add(doc);
        }
        return requiredDocs;
    }*/

    //Attempts to remove the  doc from the list of required docs and returns true if successful, false if not
    /*public boolean removeDoc(String doc) {
        return requiredDocs.remove(doc);
    }*/

    public HashMap<String, Boolean> getRequiredInfo() {
        return requiredInfo;
    }

    //Adds a new required info to the list of required info if the list does not already contain it and returns the new list
/*    public String[][] addRequiredInfo(String info) {
        if (!requiredInfo.contains(info)) {
            //requiredInfo.add(info);
        }
        return requiredInfo;
    }*/

    //Attempts to remove the info from the list of required info and returns true if successful, false if not
/*    public boolean removeInfo(String info) {
        return requiredInfo.remove(info);
    }*/
    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Service service) {
        boolean sameName = name.equals(service.getName());
        boolean samePrice = price == service.getPrice();
        boolean sameRequiredInfo = requiredInfo.equals(service.getRequiredInfo());
        boolean sameRequiredDocs = requiredDocs.equals(service.getRequiredDocs());

        return (sameName && samePrice && sameRequiredDocs && sameRequiredInfo);
    }
}
