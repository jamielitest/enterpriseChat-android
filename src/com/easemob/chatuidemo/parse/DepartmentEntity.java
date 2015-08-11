package com.easemob.chatuidemo.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
@ParseClassName("depart")
public class DepartmentEntity extends ParseObject {

	private static final String PROP_NAME = "name";
	private static final String PROP_PARENT = "parent";
	private static final String PROP_ADMIN = "admin";
//	private static final String PROP_ID = "id";

	public DepartmentEntity() {
	}

	public void setName(String name) {
		if (name != null)
			put(PROP_NAME, name);
	}

	public String getName() {
		return getString(PROP_NAME);
	}

	public void setParent(String parent) {
		if (parent != null) {
			put(PROP_PARENT, parent);
		}
	}

	public String getParent() {
		return getString(PROP_PARENT);
	}

	public String getAdmin() {
		return getString(PROP_ADMIN);
	}

	public void setAdmin(String admin) {
		if (admin != null) {
			put(PROP_ADMIN, admin);
		}
	}

}
