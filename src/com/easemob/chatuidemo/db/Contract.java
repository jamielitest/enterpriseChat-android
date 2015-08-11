package com.easemob.chatuidemo.db;

import android.provider.BaseColumns;

public class Contract {
	public static abstract class DepartTable implements BaseColumns {
		public static final String TABLE_NAME = "depart";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_DEPART_NAME = "name";
		public static final String COLUMN_NAME_PARENT = "parent";
		public static final String COLUMN_NAME_ADMIN = "admin";

	}

	public static abstract class ContractUserTable implements BaseColumns {

		public static final String TABLE_NAME = "contactuser";
		public static final String COLUMN_NAME_ID = "username";
		public static final String COLUMN_NAME_NICK = "nick";
		public static final String COLUMN_NAME_SIGNATURE = "signature";
		public static final String COLUMN_NAME_HXID = "hxid";
		public static final String COLUMN_NAME_AVATORURL = "avatorurl";
		public static final String COLUMN_NAME_AVATORPATH = "avatorpath";
		public static final String COLUMN_NAME_EMAIL = "email";
		public static final String COLUMN_NAME_MOBILE = "mobile";
		public static final String COLUMN_NAME_TELEPHONE = "telephone";
		public static final String COLUMN_NAME_ORGANIZATION = "organization";
		public static final String COLUMN_NAME_PERMISSION = "permission";
		public static final String COLUMN_NAME_HEADER = "header";
		public static final String COLUMN_NAME_ATTRIBUTES = "attributes";

	}

}
