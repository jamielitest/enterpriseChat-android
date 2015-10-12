package com.easemob.qixin.activity;

import com.easemob.qixin.DemoApplication;
import com.easemob.qixin.R;
import com.easemob.qixin.parse.QXUser;
import com.easemob.qixin.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
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
	private QXUser user;

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
		
		
		for (QXUser qxuser : DemoApplication.getInstance().getAllUsers()) {
			if (qxuser.getHXid().equals(userid)) {
				user = qxuser;
			}
		}
		
		usernameText.setText(user.getNick());
		userText.setText(user.getNick());
		phoneText.setText(user.getMobile());
//		departmentText.setText(user.getOrganization());
		Picasso.with(PersonalInfoActivity.this).load(user.getAvatorUrl()).placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(avatarImage);

	
		
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
			if (!TextUtils.isEmpty(user.getNick())) {
				startActivity(new Intent(PersonalInfoActivity.this,ChatActivity.class).putExtra("userId", userid).putExtra("nick", user.getNick()));
			}else {
				startActivity(new Intent(PersonalInfoActivity.this,ChatActivity.class).putExtra("userId", userid).putExtra("nick", user.getUsername()));
			}
			break;

		default:
			break;
		}
	}
	
}
