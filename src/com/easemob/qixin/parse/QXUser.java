package com.easemob.qixin.parse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.easemob.exceptions.EMResourceNotExistException;
import com.easemob.util.HanziToPinyin;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class QXUser extends ParseUser{

	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICK = "nick";
	public static final String PROP_SIGNATURE = "signature";
	public static final String PROP_AVATAR_URL = "avatarurl";
	public static final String PROP_MOBILE = "mobile";
	public static final String PROP_TELEPHONE = "telephone";
	public static final String PROP_EMAIL = "email";
	public static final String PROP_HXID = "eid";
	public static final String PROP_HEADER = "header";
	public static final String PROP_PERMISSIONS = "permissions";
	public static final String PROP_ATTRIBUTES = "attributes";
	
	HashMap<String,Object> attributes = new HashMap<String,Object>();
	public QXUser(){
	}
	
	public QXUser(String username){
		put(PROP_USERNAME, username);
	}
	
	
	public String getUsername() {
		return getString(PROP_USERNAME);
	}

	public void setUserName(String username) {
		put(PROP_USERNAME, username);
	}

	public String getHXid() {
		if (getObjectId()  != null) {
			return getObjectId().toLowerCase();
		}
		return getString(PROP_HXID);
	}

	public void setHXid(String hxid) {
		if(hxid == null){
			return;
		}
		put(PROP_HXID, hxid);
	}

	public String getNick() {
		String nick = getString(PROP_NICK);
		if (nick == null)
			return getString(PROP_USERNAME);
		return nick;
	}
	    
	public void setNick(String nick) {
		if(nick == null){
			return;
		}
		put(PROP_NICK, nick);
		if (nick != null && !nick.trim().equals("")) {
			String header = HanziToPinyin.getInstance().get(nick.trim().substring(0, 1)).get(0).target.substring(0, 1)
					.toUpperCase();
			char charHeader = header.toLowerCase().charAt(0);
			if (charHeader < 'a' || charHeader > 'z') {
				header = "#";
			}
			put(PROP_HEADER, header);
		}
	}

	public String getSignature() {
		return getString(PROP_SIGNATURE);
	}

	public void setSignature(String signature) {
		if(signature != null)
			put(PROP_SIGNATURE, signature);
	}

	public String getAvatorUrl() {
		if (getString(PROP_AVATAR_URL) != null) {
			return getString(PROP_AVATAR_URL);
		}else {
			ParseFile file = getParseFile("avatar");
			if (file != null) {
				return file.getUrl();
			}
		}
		return null;
	}

	public void setAvatorUrl(String avatarUrl) {
		if(avatarUrl != null)
			put(PROP_AVATAR_URL, avatarUrl);
	}

	public String getPermissions() {
		return getString(PROP_PERMISSIONS);
	}

	public void setPermissions(String permissions) {
		if(permissions != null)
			put(PROP_PERMISSIONS, permissions);
	}

	public String getEmail() {
		return getString(PROP_EMAIL);
	}

	public void setEmail(String email) {
		if(email != null)
			put(PROP_EMAIL, email);
	}

	public String getMobile() {
		return getString(PROP_MOBILE);
	}

	public void setMobile(String mobile) {
		if(mobile != null)
			put(PROP_MOBILE, mobile);
	}

	public String getTelephone() {
		return getString(PROP_TELEPHONE);
	}

	public void setTelephone(String value) {
		if(value != null)
			put(PROP_TELEPHONE, value);
	}

	public void setProperty(String key, int value) {
		attributes.put(key, value);
	}

	public int getIntProperty(String key) throws EMResourceNotExistException {
		Object intVal = attributes.get(key);
		if (intVal == null) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
		try {
			return ((Integer) intVal).intValue();
		} catch (Exception e) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
	}

	public int getIntProperty(String key, int defaultParam) {
		Object intVal = attributes.get(key);
		if (intVal == null) {
			return defaultParam;
		}
		try {
			return ((Integer) intVal).intValue();
		} catch (Exception e) {
			return defaultParam;
		}
	}

	public void setProperty(String key, String value) {
		attributes.put(key, value);
	}

	public String getStringProperty(String key) throws EMResourceNotExistException {
		Object val = attributes.get(key);
		if (val == null) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
		try {
			return (String) val;
		} catch (Exception e) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
	}

	public String getStringProperty(String key, String defaultParam) {
		Object val = attributes.get(key);
		if (val == null) {
			return defaultParam;
		}
		try {
			return (String) val;
		} catch (Exception e) {
			return defaultParam;
		}
	}

	public boolean isExist(String key) {
		return attributes.containsKey(key);
	}

	public void setProperty(String key, boolean value) {
		attributes.put(key, value);
	}

	public boolean getBoolProperty(String key) throws EMResourceNotExistException {

		Object val = attributes.get(key);
		if (val == null) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
		return ((Boolean) val).booleanValue();

	}

	public boolean getBoolProperty(String key, boolean defaultValue) {
		Object val = attributes.get(key);
		if (val == null) {
			return defaultValue;
		}
		return ((Boolean) val).booleanValue();
	}

	public void setProperty(String key, long value) {
		attributes.put(key, value);
	}

	public long getLongProperty(String key) throws EMResourceNotExistException {
		Object val = attributes.get(key);
		if (val == null) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
		return ((Long) val).longValue();
	}

	public void setProperty(String key, double value) {
		attributes.put(key, value);
	}

	public double getDoubleProperty(String key) throws EMResourceNotExistException {
		Object val = attributes.get(key);
		if (val == null) {
			throw new EMResourceNotExistException("cant find attribute:" + key);
		}
		return ((Double) val).doubleValue();
	}

	public void setProperty(String key, Object value) {
		attributes.put(key, value);
	}

	public Object getObjectValue(String key) {
		return attributes.get(key);
	}

	public void setProperties(HashMap<String, Object> properties) {
		attributes = properties;
	}

	public Map<String, Object> getProperties() {
		return attributes;
	}

	public String getPropertiesJson() {

		try {
			JSONObject json = new JSONObject();

			for (String key : attributes.keySet()) {
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

	public void setPropertiesJson(JSONObject json) {
		try {
			Iterator<String> itor = json.keys();
			while (itor.hasNext()) {
				String key = itor.next();
				Object obj = json.get(key);
				attributes.put(key, obj);
			}
			System.out.println("emuser set properties json:" + json + " map:" + attributes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	    
	    
	    public Map<String,Object> getAllProperties(){
	        HashMap<String,Object> allAttr = new HashMap<String, Object>(attributes);
	        allAttr.put(PROP_USERNAME, getUsername());
	        allAttr.put(PROP_NICK, getNick());
	        allAttr.put(PROP_SIGNATURE, getSignature());
	        allAttr.put(PROP_AVATAR_URL, getAvatorUrl());
	        allAttr.put(PROP_EMAIL, getEmail());
	        allAttr.put(PROP_TELEPHONE, getTelephone());
	        allAttr.put(PROP_HXID, getHXid());
	        return allAttr;
	    }
	    
	    
	public String getHeader() {
		return getString(PROP_HEADER);
	}

	public void setHeader(String value) {
		put(PROP_HEADER, value);
	}

	public int compare(QXUser other) {
		if (this.getHeader().equals(other.getHeader())) {
			String thisName = getNick();
			if (thisName == null) {
				thisName = getUsername();
			}
			String otherName = other.getNick();
			if (otherName == null) {
				otherName = getUsername();
			}
			return thisName.compareTo(otherName);
		} else {
			return this.getHeader().compareTo(other.getHeader());
		}
	}

	public String toString() {
		String nick = getString(PROP_NICK);
		if (nick != null) {
			return nick;
		}
		return getString(PROP_USERNAME);
	}
	    
	
//	private static final String HXID = "hxid";
//	private static final String PHONE = "phone";
//	private static final String NICKNAME = "nickname";
//	private static final String ORGANIZATION = "department";
//
//	public void setHXid(String hxId) {
//		put(HXID, hxId);
//	}
//
//	public String getHXid() {
//		return getString(HXID);
//	}
//
//	public void setPhone(String phone) {
//		put(PHONE, phone);
//	}
//
//	public String getPhone() {
//		return getString(PHONE);
//	}
//	
//	public void setNickname(String nickname){
//		put(NICKNAME, nickname);
//	}
//	
//	public String getNickname(){
//		return getString(NICKNAME);
//	}
//	
//	public String getOriganization(){
//		return getString(ORGANIZATION);
//	}
//	
//	public void setOriganization(String department){
//		put(ORGANIZATION, department);
//	}
//	
//	
//	public QXUser(){
//		super();
//	}
//	
//	public QXUser(String username){
//		super();
//		setUsername(username);
//	}
}
