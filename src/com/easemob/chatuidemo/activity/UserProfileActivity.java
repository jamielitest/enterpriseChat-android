package com.easemob.chatuidemo.activity;

import com.easemob.qixin.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserProfileActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton btnBack;
	private TextView txtTitle;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}


	private void initView() {
		setContentView(R.layout.activity_profile);
		btnBack = (ImageButton) findViewById(R.id.leftBtn);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(this);
		txtTitle.setText("我的资料");
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

}
