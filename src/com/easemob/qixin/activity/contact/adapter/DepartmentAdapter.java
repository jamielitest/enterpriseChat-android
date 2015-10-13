package com.easemob.qixin.activity.contact.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.qixin.DemoApplication;
import com.easemob.qixin.R;
import com.easemob.qixin.activity.PersonalInfoActivity;
import com.easemob.qixin.parse.DepartmentEntity;
import com.easemob.qixin.parse.QXUser;

public class DepartmentAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context mContext;
	private EditText query;
	private ImageButton clearSearch;
	private List<DepartmentEntity> entities;
	private TextView txtTitle = null;
	private List<String> departList;
	private DepartmentEntity entity;
	private List<String> subList;
	
	public DepartmentAdapter(Context context,DepartmentEntity departEntity,List<DepartmentEntity> list){
		mContext = context;
		entities = list;
		inflater = LayoutInflater.from(context);
		entity = departEntity;
		
		txtTitle = (TextView) ((Activity)context).findViewById(R.id.txtTitle);
		departList = new ArrayList<String>();
		departList.addAll(entity.getDepartmentSubId());
		departList.addAll(entity.getDepartmentMembers());
		subList = new ArrayList<String>();
	}

	private static class ViewHolder {
		TextView header;
		TextView name;
		TextView signature;
		ImageView avatar;
	}
	
	
	@Override
	public int getCount() {
		return departList.size() + 2;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		if(position == 0 || position == 1){
			return new Object();
		}
		return departList.get(position-2);
	}
	
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		if(position == 0){
				convertView = inflater.inflate(R.layout.search_bar_with_padding, parent, false);
				query = (EditText) convertView.findViewById(R.id.query);
				clearSearch = (ImageButton) convertView.findViewById(R.id.search_clear);
				query.addTextChangedListener(new TextWatcher() {
					public void onTextChanged(CharSequence s, int start, int before, int count) {
//						getContactFilter().filter(s);
						/*
						 * do custom search
						 */
						if(s.length() > 0){
							clearSearch.setVisibility(View.VISIBLE);
						} else {
							clearSearch.setVisibility(View.INVISIBLE);
						}
					}
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}
					public void afterTextChanged(Editable s) {
					}
				});
				clearSearch.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						query.getText().clear();
					}
				});
		}else if(position == 1){
			        convertView = inflater.inflate(R.layout.horizontal_scroll_bar, parent,false);
			        LinearLayout container = (LinearLayout) convertView.findViewById(R.id.horizontal_bar);
			        Button root = (Button) inflater.inflate(R.layout.button_department, container,false);
			        
			        root.setOnClickListener(new OnClickListener() {
	                    
	                    @Override
	                    public void onClick(View v) {
	                    	for (DepartmentEntity departmentEntity : entities) {
	                			if (TextUtils.isEmpty(departmentEntity.getDepartmentSupId())) {
	                				departList.clear();
	                				entity = departmentEntity;
	                				departList.addAll(departmentEntity.getDepartmentSubId());
	                				departList.addAll(departmentEntity.getDepartmentMembers());
									subList.clear();
									notifyDataSetChanged();
	                			}
	                		} 
	                    }
	                });
			        subList.add(entity.getDepartmentName());
			        root.setText(subList.get(0));
			        container.addView(root);
			        
			        
						for (int i = 1; i < subList.size(); i++) {
							TextView textView = new TextView(mContext);
							textView.setText(">");
							container.addView(textView);
							
						  final Button tmpButton = (Button) inflater.inflate(R.layout.button_department, container, false);
                            tmpButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                	for (DepartmentEntity departmentEntity : entities) {
										if (departmentEntity.getDepartmentName().equals(tmpButton.getText().toString())) {
											departList.clear();
			                				entity = departmentEntity;
			                				departList.addAll(departmentEntity.getDepartmentSubId());
			                				departList.addAll(departmentEntity.getDepartmentMembers());
											subList.subList(subList.lastIndexOf(tmpButton.getText().toString()), subList.size()).clear();
											notifyDataSetChanged();
										}
									}
                                }
                            });
                            tmpButton.setText(subList.get(i));
                            container.addView(tmpButton);
						}
			        
			        
			        if(container.getChildAt(container.getChildCount() - 1) instanceof Button){
			        	Button lastBtn = (Button) container.getChildAt(container.getChildCount() - 1);
			        	lastBtn.setTextColor(Color.BLUE);
			        	txtTitle.setText(lastBtn.getText());
			        }
			        
			        
		}else{
			 final String item = (String) getItem(position) ;
			 if(entity.getDepartmentSubId().size() > 0 && entity.getDepartmentSubId().contains(item)){
                 convertView = inflater.inflate(R.layout.row_department, parent, false); 
	                
	                TextView deptView = (TextView) convertView.getTag();
	                if(deptView == null){
	                    deptView = ((TextView)convertView.findViewById(R.id.dept));
	                }
	                
	                for (DepartmentEntity dentEntity : entities) {
						if (dentEntity.getDepartmentId().equals(item)) {
							int num = dentEntity.getDepartmentSubId().size() + dentEntity.getDepartmentMembers().size();
							deptView.setText(item + "(" + num +")");
						}
					}
					
			        convertView.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                    	departList.clear();
	                    	for (DepartmentEntity dentEntity : entities) {
								if (dentEntity.getDepartmentId().equals(item)) {
									departList.addAll(dentEntity.getDepartmentSubId());
									departList.addAll(dentEntity.getDepartmentMembers());
									entity = dentEntity;
								}
							}
	                    	notifyDataSetChanged();
	                    }
	                });
			    } else if(entity.getDepartmentMembers().size() > 0 && entity.getDepartmentMembers().contains(item)){
			        final ViewHolder holder;
//			        if (convertView == null) {
	                    holder=new ViewHolder();
	                    convertView = inflater.inflate(R.layout.row_contact, parent, false);    
//	                  holder.header = (TextView) convertView.findViewById(R.id.header);
	                    holder.name = (TextView) convertView.findViewById(R.id.name);
	                    holder.signature = (TextView) convertView.findViewById(R.id.signature);
	                    holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
	                    convertView.setTag(holder);
//	                }else{
//	                    holder= (ViewHolder) convertView.getTag();
//	                }
	                    for (QXUser user : DemoApplication.getInstance().getAllUsers()) {
	                    	if (user.getHXid().equals(item.toLowerCase())) {
	                    		if (!TextUtils.isEmpty(user.getNick())) {
	                    			holder.name.setText(user.getNick());
								}else {
									holder.name.setText(user.getUsername());
								}
	                    	}
							
	                    	if(!TextUtils.isEmpty(user.getSignature())){
	                    		holder.signature.setVisibility(View.VISIBLE);
	                    		holder.signature.setText(user.getSignature());
	                    	}else{
	                    		holder.signature.setVisibility(View.GONE);
	                    	}
						}
			        holder.avatar.setImageResource(R.drawable.default_avatar);
			        
			        convertView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mContext.startActivity(new Intent(mContext,PersonalInfoActivity.class).putExtra("userId", item.toLowerCase()));
						}
					});
			    } 
		}
		
		return convertView;
	}
	
}
