package com.indocyber.utility;

import java.util.LinkedList;
import java.util.List;

public class Dropdown {

    private String stringValue;

    private Long longValue;

    private String text;

    public Dropdown() {}

    public Dropdown(Long longValue, String text) {
        this.longValue = longValue;
        this.text = text;
    }

    public Dropdown(String stringValue, String text) {
        this.stringValue = stringValue;
        this.text = text;
    }

    public static List<Dropdown> getRoleDropdown() {

        List<Dropdown> roles = new LinkedList<>();

        roles.add(new Dropdown("Admin", "Admin"));
        roles.add(new Dropdown("Buyer", "Buyer"));
        roles.add(new Dropdown("Seller", "Seller"));

        return roles;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
