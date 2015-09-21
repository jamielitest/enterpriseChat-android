package com.easemob.chatuidemo.activity;

import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.parse.QXUser;
import com.easemob.chatuidemo.widget.CircleImageView;
import com.easemob.qixin.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalInfoActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton backBtn;
	private TextView infoText;
	private Button moreBtn;
	private CircleImageView avatarImage;
	private TextView usernameText;
	private TextView userText;
	private TextView phoneText;
	private TextView departmentText;
	private LinearLayout sendLayout;
	private String userid;
	private ParseQuery<ParseObject> query;
	private QXUser user;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_info);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		userid = getIntent().getExtras().getString("userId");
		
		backBtn = (ImageButton)findViewById(R.id.leftBtn);
		infoText = (TextView)findViewById(R.id.txtTitle);
		moreBtn = (Button)findViewById(R.id.btn_more);
		avatarImage = (CircleImageView)findViewById(R.id.iv_avatar);
		usernameText = (TextView)findViewById(R.id.tv_username);
		userText = (TextView)findViewById(R.id.tv_user);
		phoneText = (TextView)findViewById(R.id.tv_phone);
		departmentText = (TextView)findViewById(R.id.tv_department);
		sendLayout = (LinearLayout)findViewById(R.id.ll_send_msg);
		
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setImageResource(R.drawable.icon_btn_back_white);
		infoText.setVisibility(View.VISIBLE);
		infoText.setTextColor(getResources().getColor(R.color.common_top_bar_white));
		infoText.setText("personal infomation");
		moreBtn.setVisibility(View.VISIBLE);
		
		backBtn.setOnClickListener(this);
		moreBtn.setOnClickListener(this);
		sendLayout.setOnClickListener(this);
		
		
		dialog = ProgressDialog.show(this, "loading...", "");
		loadUser();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftBtn:
			finish();
			break;
		case R.id.btn_more:
			
			break;
	
		case R.id.ll_send_msg:
			startActivity(new Intent(PersonalInfoActivity.this,ChatActivity.class).putExtra("userId", userid).putExtra("nick", user.getNick()));
			break;

		default:
			break;
		}
	}
	
	private void loadUser(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				user = DemoApplication.getInstance().getQXManager().loadSingleUser(userid);
				runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();
						usernameText.setText(user.getNick());
						userText.setText(user.getNick());
						phoneText.setText(user.getMobile());
						departmentText.setText(user.getOrganization());

					}
				});
			}
		}).start();
	}
}
