package com.easemob.chatuidemo.domain;

import java.io.Serializable;

public class ContactUser implements Serializable{

	private static final long serialVersionUID = -1619915467746223125L;
	   
    /**
     *  "user_id": "54c341a0ea0e821bc81a2f66", 
        "name": "高齐", 
        "huanxin": "gq:123456"
     */
    
    private String user_id;
    private String name;
    private String huanxin_account;
    private String huanxin_pwd;
    
    
    public String getUser_id() {
        return user_id;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getHuanxin_account() {
        return huanxin_account;
    }



    public void setHuanxin_account(String huanxin_account) {
        this.huanxin_account = huanxin_account;
    }



    public String getHuanxin_pwd() {
        return huanxin_pwd;
    }



    public void setHuanxin_pwd(String huanxin_pwd) {
        this.huanxin_pwd = huanxin_pwd;
    }



    @Override
    public String toString() {
        return "ContactUser [user_id=" + user_id + ", name=" + name
                + ", huanxin_account=" + huanxin_account + ", huanxin_pwd="
                + huanxin_pwd + "]";
    }


    public ContactUser(String user_id, String name, String huanxin_account) {
        super();
        this.user_id = user_id;
        this.name = name;
        this.huanxin_account = huanxin_account;
    }
    
	
	
}
