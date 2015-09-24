package com.easemob.qixin.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.qixin.DemoApplication;
import com.easemob.qixin.db.Contract;
import com.easemob.qixin.db.DbOpenHelper;
import com.easemob.qixin.parse.DepartmentEntity;
import com.easemob.qixin.parse.ParseManager;
import com.easemob.qixin.parse.QXUser;
import com.easemob.util.EMLog;
import com.parse.ParseException;
import com.parse.ParseFile;

public class QiXinManager {
	
	private DbOpenHelper dbHelper;
	
	public QiXinManager(Context context){
		dbHelper = DbOpenHelper.getInstance(context);
	}
	
	
	public void saveDepartment(DepartmentEntity department){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(Contract.DepartTable.COLUMN_NAME_DEPART_NAME, department.getName());
            values.put(Contract.DepartTable.COLUMN_NAME_PARENT,department.getParent());
            values.put(Contract.DepartTable.COLUMN_NAME_ADMIN, department.getAdmin());
            db.insert(Contract.DepartTable.TABLE_NAME, null, values);
        }else{
            throw new RuntimeException("db is not open");
        }
	}
	
	public void deleteAllDepartment(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(Contract.DepartTable.TABLE_NAME, null, null);
		}else{
			throw new RuntimeException("db is not open");
		}
	}
	
	public void deleteAllQXUser(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(Contract.ContractUserTable.TABLE_NAME, null, null);
		}else{
			throw new RuntimeException("db is not open");
		}
	}
	
	public void saveQXUser(QXUser user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(Contract.ContractUserTable.COLUMN_NAME_ATTRIBUTES, user.getPropertiesJson());
            
			values.put(Contract.ContractUserTable.COLUMN_NAME_AVATORURL, user.getAvatorUrl());
            values.put(Contract.ContractUserTable.COLUMN_NAME_EMAIL, user.getEmail());
            values.put(Contract.ContractUserTable.COLUMN_NAME_HEADER, user.getHeader());
            values.put(Contract.ContractUserTable.COLUMN_NAME_HXID, user.getHXid());
            values.put(Contract.ContractUserTable.COLUMN_NAME_ID, user.getUsername());
            values.put(Contract.ContractUserTable.COLUMN_NAME_MOBILE, user.getMobile());
            values.put(Contract.ContractUserTable.COLUMN_NAME_NICK, user.getNick());
            values.put(Contract.ContractUserTable.COLUMN_NAME_ORGANIZATION, user.getOrganization());
            values.put(Contract.ContractUserTable.COLUMN_NAME_PERMISSION, user.getPermissions());
            values.put(Contract.ContractUserTable.COLUMN_NAME_SIGNATURE, user.getSignature());
            values.put(Contract.ContractUserTable.COLUMN_NAME_TELEPHONE, user.getTelephone());
            db.insert(Contract.ContractUserTable.TABLE_NAME, null, values);
        }else{
            throw new RuntimeException("db is not open");
        }
	}
	
	
	
	public List<DepartmentEntity> loadDepartments(){
		List<DepartmentEntity> departments = new ArrayList<DepartmentEntity>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = null;
			try {
				cursor = db.rawQuery("select * from "+Contract.DepartTable.TABLE_NAME, null);
				if(cursor.moveToFirst()){
					do{
						DepartmentEntity department = new DepartmentEntity();
						department.setName(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_DEPART_NAME)));
						department.setParent(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_PARENT)));
						department.setAdmin(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_ADMIN)));
						departments.add(department);
					}while(cursor.moveToNext());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(cursor != null){
					cursor.close();
					cursor = null;
				}
			}
		}
		return departments;
	}
	
	public List<QXUser> loadAllUsers(){
		List<QXUser> qxUsers = new ArrayList<QXUser>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = null;
			try {
                cursor = db.rawQuery("select * from "
                        + Contract.ContractUserTable.TABLE_NAME, null);
                if (cursor.moveToFirst()) {
                    do{
                        QXUser user = new QXUser();
                        user.setEmail(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_EMAIL)));
                        try {
                        	user.setHeader(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_HEADER)));
						} catch (Exception e) {
							EMLog.e("###", e.getMessage());
						}
                        user.setAvatorUrl(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_AVATORURL)));
                        user.setHXid(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_HXID)));
                        user.setMobile(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_MOBILE)));
                        user.setNick(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_NICK)));
                        user.setOrganization(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ORGANIZATION)));
                        user.setPermissions(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_PERMISSION)));
                        user.setSignature(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_SIGNATURE)));
                        user.setTelephone(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_TELEPHONE)));
                        user.setUserName(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ID)));
                        JSONObject attrs = null;
                        try {
                            attrs = new JSONObject(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ATTRIBUTES)));
                        } catch (Exception e) {
                        }
                        if(attrs!=null)
                            user.setPropertiesJson(attrs);
                        qxUsers.add(user); 
                        Log.i("info", "count:"+qxUsers.size());
                    }while(cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
		}
		return qxUsers;
	}
	public QXUser loadSingleUser(String objectId){
		QXUser user = new QXUser();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = null;
			try {
				cursor = db.rawQuery("select * from "
						+ Contract.ContractUserTable.TABLE_NAME + " where " + Contract.ContractUserTable.COLUMN_NAME_HXID + " = ?" , new String[]{objectId});
				if (cursor.moveToFirst()) {
					user.setAvatorUrl(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_AVATORURL)));
					user.setEmail(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_EMAIL)));
					try {
						user.setHeader(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_HEADER)));
					} catch (Exception e) {
						EMLog.e("###", e.getMessage());
					}
					user.setMobile(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_MOBILE)));
					user.setNick(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_NICK)));
					user.setOrganization(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ORGANIZATION)));
					user.setPermissions(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_PERMISSION)));
					user.setSignature(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_SIGNATURE)));
					user.setTelephone(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_TELEPHONE)));
					user.setUserName(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ID)));
					JSONObject attrs = null;
					try {
						attrs = new JSONObject(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_ATTRIBUTES)));
					} catch (Exception e) {
					}
					if(attrs!=null)
						user.setPropertiesJson(attrs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
					cursor = null;
				}
			}
		}
		return user;
	}
	
	public void saveDepartmentData(){
		String strData = "{\"data\":{\"sub_departs\":[{\"sub_departs\":[{\"sub_departs\":[],\"managers\":[],\"name\":\"\u73af\u4fe1\",\"users\":[{\"user_id\":\"54c34182ea0e821bc81a2f65\",\"name\":\"\u9ece\u5fd7\u5e73\",\"huanxin\":\"lzp:123456\"}]}],\"managers\":[],\"name\":\"\u5916\u90e8\",\"users\":[]},{\"sub_departs\":[{\"sub_departs\":[],\"managers\":[],\"name\":\"\u670d\u52a1\u5668\",\"users\":[{\"user_id\":\"54c33fccea0e821bc81a2f5e\",\"name\":\"\u5b8b\u9e4f\u98de\",\"huanxin\":\"spf:123456\"}]},{\"sub_departs\":[],\"managers\":[],\"name\":\"\u5b89\u5353\",\"users\":[{\"user_id\":\"54c340b2ea0e821bc81a2f60\",\"name\":\"\u9648\u632f\u5174\",\"huanxin\":\"czx:123456\"},{\"user_id\":\"54c3415bea0e821bc81a2f64\",\"name\":\"\u97e9\u5a01\",\"huanxin\":\"hw:123456\"}]},{\"sub_departs\":[],\"managers\":[],\"name\":\"ios\",\"users\":[{\"user_id\":\"54c34103ea0e821bc81a2f62\",\"name\":\"\u5f20\u632f\u680b\",\"huanxin\":\"zzd:123456\"},{\"user_id\":\"54c34081ea0e821bc81a2f5f\",\"name\":\"\u7f57\u884e\",\"huanxin\":\"lk:123456\"}]}],\"managers\":[],\"name\":\"\u8f6f\u4ef6\",\"users\":[]},{\"sub_departs\":[],\"managers\":[],\"name\":\"\u7ba1\u7406\u90e8\",\"users\":[{\"user_id\":\"54c341a0ea0e821bc81a2f66\",\"name\":\"\u9ad8\u9f50\",\"huanxin\":\"gq:123456\"},{\"user_id\":\"54c34132ea0e821bc81a2f63\",\"name\":\"\u9ad8\u7adf\u6ea2\",\"huanxin\":\"gjy:123456\"},{\"user_id\":\"54c341b6ea0e821bc81a2f67\",\"name\":\"\u4e01\u6c38\u5f3a\",\"huanxin\":\"dyq:123456\"}]},{\"sub_departs\":[],\"managers\":[],\"name\":\"\u8bbe\u8ba1\",\"users\":[{\"user_id\":\"54c340e4ea0e821bc81a2f61\",\"name\":\"\u738b\u535a\",\"huanxin\":\"wb:123456\"}]}],\"managers\":[],\"name\":\"\u6839\",\"users\":[]},\"error_description\":\"\",\"error\":0}";
        try {
            JSONObject jsonObject = new JSONObject(strData);
            JSONObject jsonRoot = jsonObject.getJSONObject("data");
            deleteAllQXUser();
            deleteAllDepartment();
            parseJsonSaveUser(jsonRoot,"/");
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	
	public void saveUserAndDepart(final EMCallBack callback){
		deleteAllQXUser();
        deleteAllDepartment();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<DepartmentEntity> allDepartsFromServer = ParseManager.getInstance().getAllDepartsFromServer();
					List<QXUser> qxUsers = ParseManager.getInstance().getAllQXUserFromServer();
					if (allDepartsFromServer != null) {
						DemoApplication.getInstance().setDeparts(allDepartsFromServer);
						for (DepartmentEntity departmentEntity : allDepartsFromServer) {
							saveDepartment(departmentEntity);
						}
					}
					if (qxUsers != null) {
						DemoApplication.getInstance().setQXUsers(qxUsers);
						for (QXUser qxUser : qxUsers) {
							saveQXUser(qxUser);
						}
					}
					if (callback != null) {
						callback.onSuccess();
					}
				} catch (ParseException e) {
					e.printStackTrace();
					if (callback != null) {
						callback.onError(e.getCode(), e.getMessage());
					}
				}
			}
		}).start();
	}
	
	
	
	public void parseJsonSaveUser(JSONObject jsonRoot,String parent){
		try {
            JSONArray  jsonRootManagers = jsonRoot.getJSONArray("managers");
            String rootManagerStr="";
            rootManagerStr= jsonRootManagers.join(",");
            String name = jsonRoot.getString("name");
            String departname = parent + (parent.endsWith("/")?name:"/"+name);
            JSONArray jsonArrUsers = jsonRoot.getJSONArray("users");
            if(jsonRoot.has("sub_departs")){
                JSONArray jsonArr=jsonRoot.getJSONArray("sub_departs");
                parseJsonArraySaveUser(jsonArr,departname);
            }
            for (int i = 0; i < jsonArrUsers.length() ; i++) {
                JSONObject jsonUser = jsonArrUsers.getJSONObject(i);
                QXUser user = new QXUser();
                user.setUserName(jsonUser.getString("user_id"));
                user.setNick(jsonUser.getString("name"));
                user.setHXid(jsonUser.getString("huanxin"));
                user.setOrganization(departname);
                saveQXUser(user);
            }
            DepartmentEntity departEntity = new DepartmentEntity();
            departEntity.setAdmin("");
            departEntity.setName(name);
            departEntity.setParent(parent);
            saveDepartment(departEntity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
	}
	
	public void parseJsonArraySaveUser(JSONArray jsonArrSub, String departname) {
		try {
			for (int i = 0; i < jsonArrSub.length(); i++) {
				JSONObject jsonRoot = jsonArrSub.getJSONObject(i);
				parseJsonSaveUser(jsonRoot, departname);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getDepartmentName(String path){
		return path.substring(path.lastIndexOf("/")+1, path.length());
	}
	
	public List<QXUser> getUsersWithDepartment(List<QXUser> allUsers, String departmentPath) {
		List<QXUser> resultList = new ArrayList<QXUser>();
		for (QXUser qxUser : allUsers) {
			String[] departs;
			String strDepartment = qxUser.getOrganization();
			if (strDepartment != null && strDepartment.contains(",")) {
				departs = strDepartment.trim().split(",");
			} else {
				departs = new String[] { strDepartment };
			}
			for (String depart : departs) {
				if (departmentPath.equals(depart)) {
					resultList.add(qxUser);
					break;
				}
			}

		}
		return resultList;
	}
	
	
	public static int getUserCountWithDepartment(List<QXUser> allUsers, String departmentPath) {
		int count = 0;
		for (QXUser qxUser : allUsers) {
			String[] departs;
			String strDepartment = qxUser.getOrganization();
			if (strDepartment != null && strDepartment.contains(",")) {
				departs = strDepartment.trim().split(",");
			} else {
				departs = new String[] { strDepartment };
			}

			for (String depart : departs) {
				if (depart != null && depart.startsWith(departmentPath)) {
					count++;
				}
			}

		}
		return count;
	}
	
	
	
	
	 public List<String> getChildDepartments(String path){
	        List<String> resultList = new ArrayList<String>();
	        List<DepartmentEntity> departments = null; 
	        for (DepartmentEntity targetDepartment : departments) {
	            if(path.equals(targetDepartment.getParent())){
	                String departmentFullPath = targetDepartment.getParent() + (targetDepartment.getParent().endsWith("/") ? targetDepartment.getName() : "/" + targetDepartment.getName());
	                resultList.add(departmentFullPath);
	            }
	        }
	        return resultList;
	    }
	
	
	
	
	
	

}
