package com.easemob.qixin.activity;

import com.easemob.qixin.R;
import com.easemob.qixin.parse.ParseManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ModifyUserInfoActivity extends BaseActivity {
	
	private ImageButton btnBack;
	private Button btnSave;
	private EditText etModifyUserInfo;
	private String nick;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_modify_user_info);
		
		nick = getIntent().getStringExtra("nick");
		
		btnBack = (ImageButton)findViewById(R.id.leftBtn);
		btnSave = (Button) findViewById(R.id.btn_more);
		etModifyUserInfo = (EditText)findViewById(R.id.et_modify_user_info);
		etModifyUserInfo.setText(nick);
		etModifyUserInfo.requestFocus();
		
		btnBack.setVisibility(View.VISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnSave.setText("save");
		btnSave.setTextColor(Color.BLACK);
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String nick = etModifyUserInfo.getText().toString();
				final ProgressDialog dialog = ProgressDialog.show(ModifyUserInfoActivity.this, "updating...", "waiting...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						final boolean modifySuccess = ParseManager.getInstance().updateParseNickName(nick);
						runOnUiThread(new Runnable() {
							public void run() {
								if (modifySuccess) {
									setResult(RESULT_OK, new Intent().putExtra("nickname", nick));
									Toast.makeText(ModifyUserInfoActivity.this, "success",
											Toast.LENGTH_SHORT).show();
									finish();
								}else {
									Toast.makeText(ModifyUserInfoActivity.this, "error",
											Toast.LENGTH_SHORT).show();
									
								}
								dialog.dismiss();
							}
						});
					}
				}).start();
			}
		});
	}

}
