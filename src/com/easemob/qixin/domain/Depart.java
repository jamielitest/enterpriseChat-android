package com.easemob.qixin.domain;

import java.io.Serializable;
import java.util.List;

public class Depart implements Serializable {

	private static final long serialVersionUID = -3566267812352399784L;

    private String name;
    private String managers;
    private List<ContactUser> listUsers;
    
    
    public List<ContactUser> getListUsers() {
        return listUsers;
    }
    public void setListUsers(List<ContactUser> listUsers) {
        this.listUsers = listUsers;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getManagers() {
        return managers;
    }
    public void setManagers(String managers) {
        this.managers = managers;
    }
    public Depart() {
        super();
    }

    
    @Override
    public String toString() {
        return "Depart [name=" + name + ", managers=" + managers
                + ", listUsers=" + listUsers + "]";
    }
    
    public Depart(String name, String managers, List<ContactUser> listUsers) {
        super();
        this.name = name;
        this.managers = managers;
        this.listUsers = listUsers;
    }
	
	
}
