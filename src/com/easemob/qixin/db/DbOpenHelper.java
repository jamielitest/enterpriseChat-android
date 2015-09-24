/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.qixin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.qixin.DemoApplication;

public class DbOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 5;
	private static DbOpenHelper instance;

	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessgeDao.TABLE_NAME + " ("
			+ InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";
	
	private static final String DEPART_TABLE_CREATE = "CREATE TABLE "
            + Contract.DepartTable.TABLE_NAME + " ("
            + Contract.DepartTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Contract.DepartTable.COLUMN_NAME_DEPART_NAME + " TEXT, "
            + Contract.DepartTable.COLUMN_NAME_ADMIN + " TEXT, "
            + Contract.DepartTable.COLUMN_NAME_PARENT + " TEXT); ";	
	
	private static final String CONTRACTUSER_TABLE_CREATE = "CREATE TABLE "
	        + Contract.ContractUserTable.TABLE_NAME + " ("
	        + Contract.ContractUserTable.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
	        + Contract.ContractUserTable.COLUMN_NAME_NICK + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_SIGNATURE + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_HXID + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_AVATORURL + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_EMAIL + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_MOBILE + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_TELEPHONE + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_ORGANIZATION + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_PERMISSION + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_HEADER + " TEXT, "
	        + Contract.ContractUserTable.COLUMN_NAME_ATTRIBUTES + " TEXT);";
	
	
	private DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
        return  HXSDKHelper.getInstance().getHXId() + "_demo.db";
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		onCreateDB(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    db.execSQL("drop table "+InviteMessgeDao.TABLE_NAME);
		    db.execSQL("drop table "+Contract.DepartTable.TABLE_NAME);
		    db.execSQL("drop table "+Contract.ContractUserTable.TABLE_NAME);
		    onCreateDB(db);
	}
	
	private void onCreateDB(SQLiteDatabase db){
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(DEPART_TABLE_CREATE);
        db.execSQL(CONTRACTUSER_TABLE_CREATE);
	}
	
	
	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}
	
}
