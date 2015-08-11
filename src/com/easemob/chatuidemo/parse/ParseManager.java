package com.easemob.chatuidemo.parse;

import java.util.List;

import android.content.Context;
import cn.qixin.utils.MD5Util;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseManager {

	private static final String TAG = ParseManager.class.getSimpleName();
	private static final String ParseAppID = "nq42ird0okwWDv5njbN8Ik1kx2GFtVPjdAobqu0Q";
	private static final String ParseClientKey = "gFpw2kLLjE5Tpw16olqCF1zdYtR6mMoalUXarCJi";
	private Context appContext;
	private static ParseManager instance = new ParseManager();

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
		user.setOrganization("/根");
		user.signUp();
		String objectId = user.getObjectId();
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
