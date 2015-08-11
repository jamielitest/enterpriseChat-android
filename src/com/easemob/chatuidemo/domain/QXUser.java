package com.easemob.chatuidemo.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.easemob.exceptions.EMResourceNotExistException;
import com.easemob.util.HanziToPinyin;

public class QXUser {

	public static final String PROP_USERNAME = "username";
    public static final String PROP_NICK = "nick";
    public static final String PROP_SIGNATURE = "signature";
    public static final String PROP_PICTURE = "picture";
    public static final String PROP_DEPARTMENT = "department";
    public static final String PROP_TELPHONE = "mobile";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_HXID = "hxid";
    
    String username = null;
    String nick = null;
    String hxid = null;
    String header = null;
    String avatorUrl = null;
    String avatorPath = null;
    String signature = null;
    String email = null;
    String mobile = null;
    String telephone = null;
    String organization = null;
    String permissions = null;
    boolean actived = true;
    
    HashMap<String,Object> attributes = new HashMap<String,Object>();
    
    public QXUser(){
    	
    }
    
    public QXUser(String username){
    	this.username = username;
    }
    
    public String getUsername(){
        return username;
    }
    public void setUserName(String username){
        this.username = username;
    }
    
    
    public String getHXid(){
        return hxid;
    }
    public void setHXid(String hxid){
        this.hxid = hxid;
    }
    
    public String getNick(){
        if(nick==null)
        	return username;
        return nick;
    }
    
    public void setNick(String nick){
        this.nick = nick;
        if(nick!=null&&!nick.trim().equals("")){
            header=HanziToPinyin.getInstance().get(nick.trim().substring(0, 1)).get(0).target
                    .substring(0, 1).toUpperCase();
                    char charHeader=header.toLowerCase().charAt(0);
                    if(charHeader<'a'||charHeader>'z')
                    {
                        header="#";
                    }
        }
    }
    
    public String getSignature(){
        return signature;
    }
    
    public void setSignature(String signature){
        this.signature = signature;
    }
    
    public String getAvatorPath(){
        return avatorPath;
    }
    
    
    public void setAvatorPath(String avatorPath){
        this.avatorPath = avatorPath;
    }
    
    public String getAvatorUrl(){
        return avatorUrl;
    }
    
    public void setAvatorUrl(String avatorUrl){
        this.avatorUrl = avatorUrl;
    }
    public String getPermissions(){
        return permissions;
    }
    
    public void setPermissions(String permissions){
        this.permissions = permissions;
    }
    
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getMobile(){
        return mobile;
    }
    
    
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    
    public String getTelephone(){
        return telephone;
    }
    
    public void setTelephone(String value){
        this.telephone = value;
    }
    public void setOrganization(String value){
        this.organization = value;
    }
    
    public String getOrganization(){
        return organization;
    }
    
    public void setProperty(String key, int value){
        attributes.put(key, value);
    }
    
    public int getIntProperty(String key) throws EMResourceNotExistException{
        Object intVal = attributes.get(key);
        if(intVal == null){
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
        try{
            return ((Integer)intVal).intValue();
        }catch(Exception e){
           throw new EMResourceNotExistException("cant find attribute:"+key); 
        }
    }
    
    public int getIntProperty(String key,int defaultParam){
        Object intVal = attributes.get(key);
        if(intVal == null){
            return defaultParam;
        }
        try {
            return ((Integer)intVal).intValue();
        } catch (Exception e) {
            return defaultParam;
        }
    }

    public void setProperty(String key,String value){
        attributes.put(key, value);
    }
    
    public String getStringProperty(String key) throws EMResourceNotExistException{
        Object val = attributes.get(key);
        if(val == null){
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
        try {
            return (String)val;
        } catch (Exception e) {
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
    }
    
    
    public String getStringProperty(String key,String defaultParam){
        Object val = attributes.get(key);
        if(val == null){
            return defaultParam;
        }
        try {
            return (String)val;
        } catch (Exception e) {
            return defaultParam;
        }
    }
    

    public boolean isExist(String key){
        return attributes.containsKey(key);
    }
    
    public void setProperty(String key,boolean value){
        attributes.put(key, value);
    }
    
    public boolean getBoolProperty(String key) throws EMResourceNotExistException{
        
        Object val = attributes.get(key);
        if(val == null){
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
       return ((Boolean)val).booleanValue();
        
    }
    
    public boolean getBoolProperty(String key, boolean defaultValue){
        Object val = attributes.get(key);
        if(val == null){
            return defaultValue;
        }
        return ((Boolean)val).booleanValue();
    }
    
    public void setProperty(String key,long value){
        attributes.put(key, value);
    }
    
    public long getLongProperty(String key) throws EMResourceNotExistException{
        Object val = attributes.get(key);
        if(val == null){
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
        return ((Long)val).longValue();
    }
    
    public void setProperty(String key, double value){
        attributes.put(key, value);
    }
    
    public double getDoubleProperty(String key) throws EMResourceNotExistException{
        Object val = attributes.get(key);
        if(val == null){
            throw new EMResourceNotExistException("cant find attribute:"+key);
        }
        return ((Double)val).doubleValue();
    }

    
    public void setProperty(String key,Object value){
        attributes.put(key, value);
    }
    public Object getObjectValue(String key){
        return attributes.get(key);
    }
    
    public void setProperties(HashMap<String, Object> properties){
        attributes = properties;
    }
    
    public Map<String, Object> getProperties(){
        return attributes;
    }
    
    public String getPropertiesJson(){
        
        try {
            JSONObject json = new JSONObject();
            
            for (String  key : attributes.keySet()) {
                Object obj = attributes.get(key);
                json.put(key, obj);
            }
            String ret = json.toString();
            System.out.println("emuser getproperties json return str:" + ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    
    public void setPropertiesJson(JSONObject json){
        try {
            Iterator<String> itor = json.keys();
            while (itor.hasNext()) {
                String key = itor.next();
                Object obj = json.get(key);
                attributes.put(key, obj);
            }
            System.out.println("emuser set properties json:"+json+" map:"+attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public Map<String,Object> getAllProperties(){
        HashMap<String,Object> allAttr = new HashMap<String, Object>(attributes);
        allAttr.put(PROP_USERNAME, username);
        allAttr.put(PROP_NICK, nick);
        allAttr.put(PROP_SIGNATURE, signature);
        allAttr.put(PROP_PICTURE, avatorUrl);
        allAttr.put(PROP_DEPARTMENT, this.organization);
        allAttr.put(PROP_EMAIL, email);
        allAttr.put(PROP_TELPHONE, telephone);
        allAttr.put(PROP_HXID, hxid);
        return allAttr;
    }
    
    
    public String getHeader(){
        return header;
    }
    public void setHeader(String value){
        header = value;
    }
    
    public int compare(QXUser other){
        if(this.getHeader().equals(other.getHeader())){
            String thisName = nick;
            if(thisName == null){
                thisName = username;
            }
            String otherName = other.nick;
            if(otherName == null){
                otherName = username;
            }
            return thisName.compareTo(otherName);
        } else {
            return this.getHeader().compareTo(other.getHeader());
        }
    }
    
    public String toString(){
        if(nick != null){
            return nick;
        }
        return username;
    }
    
    
}
