package com.easemob.qixin.manager;

import java.util.ArrayList;
import java.util.List;

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

public class QiXinManager {
	
	private DbOpenHelper dbHelper;
	
	public QiXinManager(Context context){
		dbHelper = DbOpenHelper.getInstance(context);
	}
	
	
	public void saveDepartment(DepartmentEntity department){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(Contract.DepartTable.COLUMN_NAME_DEPART_NAME, department.getDepartmentName());
            values.put(Contract.DepartTable.COLUMN_NAME_DEPART_ID,department.getDepartmentId());
            try {
				values.put(Contract.DepartTable.COLUMN_NAME_MEMBERS, department.getDepartmentMembers().toString());
				values.put(Contract.DepartTable.COLUMN_NAME_DEPART_SUB_ID, department.getDepartmentSubId().toString());
				values.put(Contract.DepartTable.COLUMN_NAME_DEPART_SUP_ID, department.getDepartmentSupId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
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
            values.put(Contract.ContractUserTable.COLUMN_NAME_HXID, user.getHXid().toLowerCase());
            values.put(Contract.ContractUserTable.COLUMN_NAME_ID, user.getUsername());
            values.put(Contract.ContractUserTable.COLUMN_NAME_MOBILE, user.getMobile());
            values.put(Contract.ContractUserTable.COLUMN_NAME_NICK, user.getNick());
            values.put(Contract.ContractUserTable.COLUMN_NAME_PERMISSION, user.getPermissions());
            values.put(Contract.ContractUserTable.COLUMN_NAME_SIGNATURE, user.getSignature());
            values.put(Contract.ContractUserTable.COLUMN_NAME_TELEPHONE, user.getTelephone());
            db.insert(Contract.ContractUserTable.TABLE_NAME, null, values);
        }else{
            throw new RuntimeException("db is not open");
        }
	}
	
	
	public void updateQXUser(String nick){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(Contract.ContractUserTable.COLUMN_NAME_NICK, nick);
			db.update(Contract.ContractUserTable.TABLE_NAME, values, Contract.ContractUserTable.COLUMN_NAME_HXID + " = ? ", new String[]{((QXUser)QXUser.getCurrentUser()).getHXid().toLowerCase()});
		}else {
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
						department.setDepartmentName(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_DEPART_NAME)));
						department.setDepartmentId(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_DEPART_ID)));
						department.setDepartmentMembers(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_MEMBERS)));
						department.setDepartmentSubId(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_DEPART_SUB_ID)));
						department.setDepartmentSupId(cursor.getString(cursor.getColumnIndex(Contract.DepartTable.COLUMN_NAME_DEPART_SUP_ID)));
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
					user.setHXid(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_HXID)));
					user.setMobile(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_MOBILE)));
					user.setNick(cursor.getString(cursor.getColumnIndex(Contract.ContractUserTable.COLUMN_NAME_NICK)));
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
	
	
	
}
