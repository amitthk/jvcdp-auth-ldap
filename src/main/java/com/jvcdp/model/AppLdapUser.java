package com.jvcdp.model;

public class AppLdapUser {

    private String userName, firstName, lastName, password, organisationUnit;

    public AppLdapUser(){

    }

    public AppLdapUser(String userName, String firstName, String lastName,
                                String password, String organisationUnit) {

        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.organisationUnit = organisationUnit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganisationUnit() {
        return organisationUnit;
    }

    public void setOrganisationUnit(String organisationUnit) {
        this.organisationUnit = organisationUnit;
    }
}
