package com.easemob.qixin.activity;

import java.io.ByteArrayOutputStream;

import com.easemob.qixin.R;
import com.easemob.qixin.parse.ParseManager;
import com.easemob.qixin.parse.QXUser;
import com.easemob.qixin.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends BaseActivity implements
		OnClickListener {

	private ImageButton btnBack;
	private TextView txtTitle;
	private RelativeLayout relativeAvatar;
	private RelativeLayout relativeNick;
	private RelativeLayout relativePhone;
	private CircleImageView imageAvatar;
	private TextView txtNick;
	
	private static final int REQUESTCODE_PICK = 1;
	private static final int REQUESTCODE_CUTTING = 2;
	private static final int REQUESTCODE_MODIFY_NICK = 3;
	
	private QXUser user;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_profile);
		user = (QXUser) QXUser.getCurrentUser();

		relativeAvatar = (RelativeLayout) findViewById(R.id.rl_avatar);
		relativeNick = (RelativeLayout) findViewById(R.id.rl_nick);
		relativePhone = (RelativeLayout) findViewById(R.id.rl_telphone);
		imageAvatar = (CircleImageView)findViewById(R.id.iv_avatar);
		txtNick = (TextView)findViewById(R.id.tv_nick);
		btnBack = (ImageButton) findViewById(R.id.leftBtn);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(this);
		relativeAvatar.setOnClickListener(this);
		relativeNick.setOnClickListener(this);
		relativePhone.setOnClickListener(this);
		txtTitle.setText("我的资料");
		txtNick.setText(user.getNick());
		Picasso.with(this).load(user.getAvatorUrl()).placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(imageAvatar);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.rl_avatar:
			Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
			pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(pickIntent, REQUESTCODE_PICK);
			break;
		case R.id.rl_nick:
			startActivityForResult(new Intent(UserProfileActivity.this, ModifyUserInfoActivity.class).putExtra("nick", user.getNick()), REQUESTCODE_MODIFY_NICK);
			break;
			
		case R.id.rl_telphone:
			
			break;
		case R.id.leftBtn:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_PICK:
			if (data == null || data.getData() == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
		case REQUESTCODE_CUTTING:
			if (data != null) {
				setPicToView(data);
			}
			break;
			
		case REQUESTCODE_MODIFY_NICK:
			if (data != null) {
				txtNick.setText(data.getExtras().getString("nickname"));
			}
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}
	
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(getResources(), photo);
			imageAvatar.setImageDrawable(drawable);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			uploadUserAvatar(bos.toByteArray());
		}

	}
	
	private void uploadUserAvatar(final byte[] data) {
		final ProgressDialog dialog = ProgressDialog.show(this, "update...", "wating...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				final String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						if (avatarUrl != null) {
							Toast.makeText(UserProfileActivity.this, "success",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(UserProfileActivity.this, "error",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

			}
		}).start();

	}
	
}
