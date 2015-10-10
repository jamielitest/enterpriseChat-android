package com.easemob.qixin.parse;

import java.util.List;

import android.content.Context;
import cn.qixin.utils.MD5Util;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.easemob.qixin.DemoApplication;
import com.easemob.util.EMLog;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseManager {

	private static final String TAG = ParseManager.class.getSimpleName();
	private static final String ParseAppID = "7DLXNCIyH8VEF6viPJpV0UFxF22TuG8X7YYmZl7k";
	private static final String ParseClientKey = "zXjrAFkCg4kSKkFKFRBl7kV0YEVqK7MQ9SbOugCK";
	private Context appContext;
	private static ParseManager instance = new ParseManager();
	
	private static final String CONFIG_NICK = "nick";
	private static final String CONFIG_AVATAR = "avatar";
	private static final String CONFIG_HXID = "eid";

	private ParseManager() {
	}

	public static ParseManager getInstance() {
		return instance;
	}

	public void onInit(Context context) {
		this.appContext = context.getApplicationContext();
		ParseUser.registerSubclass(QXUser.class);
		ParseObject.registerSubclass(DepartmentEntity.class);
		Parse.enableLocalDatastore(appContext);
		Parse.initialize(context, ParseAppID, ParseClientKey);
	}
	
	
	 
	
	
	public void registerUser(String username,String password) throws ParseException, EaseMobException{
		QXUser user = new QXUser();
		user.setUsername(username);
		user.setPassword(password);
		user.signUp();
		String objectId = user.getObjectId();
		updateParseHXid(objectId);
		String lowObjectId = objectId.toLowerCase();
		EMChatManager.getInstance().createAccountOnServer(lowObjectId, MD5Util.string2MD5(lowObjectId));
	}
	 
	public void loginParse(String username,String password,final EMValueCallBack<QXUser> callback){
		QXUser.logInInBackground(username, password, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(user!=null){
					if(callback!=null){
						callback.onSuccess((QXUser)user);
					}
				}else{
					if(callback!=null){
						callback.onError(e.getCode(), e.getMessage());
					}
				}
			}
		});
	}
	
	private boolean updateParseHXid(String hxid){
		QXUser pUser = (QXUser) QXUser.getCurrentUser();
		try {
			pUser.put(CONFIG_HXID, hxid);
			pUser.save();
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			EMLog.e(TAG, "parse register eid error " + e.getMessage());
		}
		return false;
	}
	
	public boolean updateParseNickName(final String nickname) {
		QXUser pUser = (QXUser) QXUser.getCurrentUser();
		try {
			pUser.put(CONFIG_NICK, nickname);
			pUser.save();
			DemoApplication.getInstance().getQXManager().updateQXUser(nickname);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			EMLog.e(TAG, "parse error " + e.getMessage());
		}
		return false;
	}
	
	public String uploadParseAvatar(byte[] data) {
		QXUser pUser = (QXUser) QXUser.getCurrentUser();
		try {
			ParseFile pFile = new ParseFile(data);
			pFile.saveInBackground();
			pUser.put(CONFIG_AVATAR, pFile);
			pUser.save();
			return pFile.getUrl();
		} catch (ParseException e) {
			e.printStackTrace();
			EMLog.e(TAG, "parse error " + e.getMessage());
		}
		return null;
	}
	
	
	public void logout(){
		QXUser.logOut();
	}
	public void asyncLogout(){
		QXUser.logOutInBackground();
	}
	
	
	public List<QXUser> getAllQXUserFromServer() throws ParseException {
		ParseQuery<QXUser> qxUserQuery = ParseQuery.getQuery(QXUser.class);
		return qxUserQuery.find();
	}

	public List<DepartmentEntity> getAllDepartsFromServer() throws ParseException {
		ParseQuery<DepartmentEntity> query = ParseQuery.getQuery(DepartmentEntity.class);
		return query.find();
	}
}
