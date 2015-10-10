package com.easemob.qixin.parse;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("DEPARTMENT")
public class DepartmentEntity extends ParseObject{

	private String DEPARTMENT_ID = "departmentId";
	private String DEPARTMENT_NAME = "departmentName";
	private String DEPARTMENT_MEMBERS = "departmentMembers";
	private String DEPARTMENT_SUB_ID = "departmentSubIds";
	private String DEPARTMENT_SUP_ID = "departmentSupId";
	
	public DepartmentEntity(){
		
	}
	
	public void setDepartmentId(String departmentId){
		put(DEPARTMENT_ID, departmentId);
	}
	
	public String getDepartmentId(){
		return getString(DEPARTMENT_ID);
	}
	
	public void setDepartmentName(String departmentName){
		put(DEPARTMENT_NAME, departmentName);
	}
	
	public String getDepartmentName(){
		return getString(DEPARTMENT_NAME);
	}
	
	public void setDepartmentMembers(String departmentMembers){
		put(DEPARTMENT_MEMBERS, departmentMembers);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDepartmentMembers(){
		List<String> list = new ArrayList<String>();
		if (getList(DEPARTMENT_MEMBERS) == null) {
			return list;
		}else {
			list.addAll((List<String>) get(DEPARTMENT_MEMBERS));
		}
		return list;
	}
	
	public void setDepartmentSubId(String departmentList){
		put(DEPARTMENT_SUB_ID, departmentList);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDepartmentSubId(){
		List<String> list = new ArrayList<String>();
		if (getList(DEPARTMENT_SUB_ID) == null) {
			return list;
		}else {
			list.addAll((List<String>) get(DEPARTMENT_SUB_ID));
		}
		return list;
	}
	
	public void setDepartmentSupId(String departmentSupId){
		put(DEPARTMENT_SUP_ID, departmentSupId);
	}
	
	public String getDepartmentSupId(){
		return getString(DEPARTMENT_SUP_ID);
	}
}
