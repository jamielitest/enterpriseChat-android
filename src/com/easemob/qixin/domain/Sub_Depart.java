package com.easemob.qixin.domain;

import java.io.Serializable;
import java.util.List;

public class Sub_Depart implements Serializable{

	private static final long serialVersionUID = -3566267812352399784L;

    private String name;
    private String managers;
    private List<Depart> listDeparts;

    
    public List<Depart> getListDeparts() {
        return listDeparts;
    }

    public void setListDeparts(List<Depart> listDeparts) {
        this.listDeparts = listDeparts;
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



    @Override
    public String toString() {
        return "Sub_Depart [name=" + name + ", managers=" + managers
                + ", listDeparts=" + listDeparts + "]";
    }

    public Sub_Depart() {
        super();
    }

    public Sub_Depart(String name, String managers, List<Depart> listDeparts) {
        super();
        this.name = name;
        this.managers = managers;
        this.listDeparts = listDeparts;
    }

	
	
}
