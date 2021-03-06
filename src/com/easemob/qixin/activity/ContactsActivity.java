package com.easemob.qixin.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.qixin.DemoApplication;
import com.easemob.qixin.R;
import com.easemob.qixin.activity.contact.adapter.DepartmentAdapter;
import com.easemob.qixin.parse.DepartmentEntity;

public class ContactsActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = ContactsActivity.class.getSimpleName();

	private ListView deptListView;

	private DepartmentAdapter adapter;

	private InputMethodManager manager;
	private ImageButton btnBack;
	HXContactSyncListener contactSyncListener;
	private LinearLayout progressBar;
	
	class HXContactSyncListener implements HXSDKHelper.HXSyncListener{

		@Override
		public void onSyncSucess(final boolean success) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if(success){
						progressBar.setVisibility(View.GONE);
                        refresh();
					}else{
						 String s1 = getResources().getString(R.string.get_failed_please_check);
	                     Toast.makeText(ContactsActivity.this, s1, Toast.LENGTH_SHORT).show();
	                     progressBar.setVisibility(View.GONE);
					}
					
				}
			});
			
		}
		
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.contact_list);
		btnBack = (ImageButton) findViewById(R.id.leftBtn);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(this);
		progressBar = (LinearLayout)findViewById(R.id.progress_bar);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		deptListView = (ListView) findViewById(R.id.list);

		contactSyncListener = new HXContactSyncListener();
		HXSDKHelper.getInstance().addSyncContactListener(contactSyncListener);
		if (!HXSDKHelper.getInstance().isContactsSyncedWithServer()) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
		
		
		deptListView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getCurrentFocus() != null) {
						manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}
				}
				return false;
			}
		});
		DepartmentEntity entity = null;
		List<DepartmentEntity> entities = DemoApplication.getInstance().getAllDepartments();
		for (DepartmentEntity departmentEntity : entities) {
			if (TextUtils.isEmpty(departmentEntity.getDepartmentSupId())) {
				entity = departmentEntity;
			}
		} 
		adapter = new DepartmentAdapter(this,entity,entities);
		deptListView.setAdapter(adapter);

	}

	public void refresh() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftBtn:
			finish();
			break;
		default:
			break;
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(contactSyncListener != null){
			HXSDKHelper.getInstance().removeSyncContactListener(contactSyncListener);
			contactSyncListener = null;
		}
	}
	
}
