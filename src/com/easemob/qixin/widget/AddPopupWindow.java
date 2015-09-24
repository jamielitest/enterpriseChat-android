package com.easemob.qixin.widget;

import com.easemob.qixin.R;
import com.easemob.qixin.activity.ContactsActivity;
import com.easemob.qixin.activity.GroupsActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class AddPopupWindow  extends PopupWindow{

	private View contentView;
	
	public AddPopupWindow(final Context context){
		contentView = LayoutInflater.from(context).inflate(R.layout.session_pull_down_popupwindow, null);
		
		this.setContentView(contentView);
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		ColorDrawable cDraw = new ColorDrawable(0000000000);
		this.setBackgroundDrawable(cDraw);
		this.setAnimationStyle(R.style.AnimPreview);
		
		View chatSession = contentView.findViewById(R.id.chatSession);
		View groupSession = contentView.findViewById(R.id.groupSession);
		chatSession.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPopupWindow.this.dismiss();
				context.startActivity(new Intent(context, ContactsActivity.class));
				
			}
		});
		groupSession.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddPopupWindow.this.dismiss();
				context.startActivity(new Intent(context, GroupsActivity.class));
			}
		});
		
		
		
	}
	
	
	
	public void showPopupWindow(View parent){
		if(!this.isShowing()){
			this.showAsDropDown(parent, 0 , 5);
		}else{
			this.dismiss();
		}
	}
	
	
}
