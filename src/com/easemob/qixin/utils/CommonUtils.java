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
package com.easemob.qixin.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.qixin.Constant;
import com.easemob.qixin.DemoApplication;
import com.easemob.qixin.R;
import com.easemob.qixin.parse.DepartmentEntity;
import com.easemob.qixin.parse.QXUser;
import com.easemob.util.EMLog;

public class CommonUtils {
	private static final String TAG = "CommonUtils";
	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	

	/**
     * 根据消息内容和消息类型获取消息内容提示
     * 
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
        case LOCATION: // 位置消息
            if (message.direct == EMMessage.Direct.RECEIVE) {
                //从sdk中提到了ui中，使用更简单不犯错的获取string方法
//              digest = EasyUtils.getAppResourceString(context, "location_recv");
                digest = getString(context, R.string.location_recv);
                digest = String.format(digest, message.getFrom());
                return digest;
            } else {
//              digest = EasyUtils.getAppResourceString(context, "location_prefix");
                digest = getString(context, R.string.location_prefix);
            }
            break;
        case IMAGE: // 图片消息
            digest = getString(context, R.string.picture);
            break;
        case VOICE:// 语音消息
            digest = getString(context, R.string.voice);
            break;
        case VIDEO: // 视频消息
            digest = getString(context, R.string.video);
            break;
        case TXT: // 文本消息
            if(!message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL,false)){
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = txtBody.getMessage();
            }else{
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = getString(context, R.string.voice_call) + txtBody.getMessage();
            }
            break;
        case FILE: //普通文件消息
            digest = getString(context, R.string.file);
            break;
        default:
            EMLog.e(TAG, "error, unknow type");
            return "";
        }

        return digest;
    }
    
    static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }
	
	
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

	
	
	
	

	 public static List<String> getChildDepartments(String path){
	        List<String> resultList = new ArrayList<String>();
	        List<DepartmentEntity> departments = DemoApplication.getInstance().getAllDepartments(); 
	        for (DepartmentEntity targetDepartment : departments) {
	            if(path.equals(targetDepartment.getParent())){
	                String departmentFullPath = targetDepartment.getParent() + (targetDepartment.getParent().endsWith("/") ? targetDepartment.getName() : "/" + targetDepartment.getName());
	                resultList.add(departmentFullPath);
	            }
	        }
	        return resultList;
	 }
	    
	
	public static int getUserCountWithDepartment(List<QXUser> allUsers, String departmentPath) {
		int count = 0;
		for (QXUser user : allUsers) {
			String[] departs;
			String strDepartment = user.getOrganization();
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

	public static List<QXUser> getUsersWithDepartment(List<QXUser> allUsers, String departmentPath) {
		List<QXUser> resultList = new ArrayList<QXUser>();

		for (QXUser user : allUsers) {
			String[] departs;
			String strDepartment = user.getOrganization();
			if (strDepartment != null && strDepartment.contains(",")) {
				departs = strDepartment.trim().split(",");
			} else {
				departs = new String[] { strDepartment };
			}
			for (String depart : departs) {
				if (departmentPath.equals(depart)) {
					resultList.add(user);
					break;
				}
			}
		}
		return resultList;
	}

	public static String getDepartmentName(String path) {
		return path.substring(path.lastIndexOf("/") + 1, path.length());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
