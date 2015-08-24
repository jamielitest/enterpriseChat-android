package com.easemob.chatuidemo.activity.contact.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.parse.QXUser;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.qixin.R;

public class DepartmentAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context mContext;
	private EditText query;
	private ImageButton clearSearch;
	private List<String> mPaths;
	private List<QXUser> mUsers;
	private String mPath;
	private ListView mListView;
	private TextView txtTitle = null;
	
	public DepartmentAdapter(Context context, ListView listView, String path, List<String> paths, List<QXUser> list){
		mContext = context;
		mListView = listView;
		mPath = path;
		mPaths = paths;
		mUsers = list;
		inflater = LayoutInflater.from(context);
		
		txtTitle = (TextView) ((Activity)context).findViewById(R.id.txtTitle);
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0) return 0;
		if(position == 1) return 1;
		if(position < mPaths.size() + 2){
			return 2;
		}
		return 3;
	}

	
	private String signatureDigest(String raw){
		if(raw == null) return "";
		if(raw.length() > 16){
			return raw.substring(0, 15) + "...";
		}else {
			return raw;
		}
	}
	
	private static class ViewHolder {
		TextView header;
		TextView name;
		TextView signature;
		ImageView avatar;
	}
	
	
	@Override
	public int getCount() {
		return mPaths.size() + mUsers.size() + 2;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(position < 2){
			return new Object();
		}
		if(position < 2 + mPaths.size()){
			return mPaths.get(position - 2);
		}
		return mUsers.get(position - 2 - mPaths.size());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(position == 0){
			if(convertView == null){
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
			}
		}else if(position == 1){
			 if(convertView == null){
			        convertView = inflater.inflate(R.layout.horizontal_scroll_bar, parent,false);
			        String[] buttons = mPath.split("/");
			        int count = buttons.length;
			        LinearLayout container = (LinearLayout) convertView.findViewById(R.id.horizontal_bar);
			        Button root = (Button) inflater.inflate(R.layout.button_department, container,false);
			        root.setOnClickListener(new OnClickListener() {
	                    
	                    @Override
	                    public void onClick(View v) {
	                        List<QXUser> userlist = new ArrayList<QXUser>();
	                        userlist.addAll(DemoApplication.getInstance().getAllUsers());
	                        Collections.sort(userlist, new Comparator<QXUser>(){

	                            @Override
	                            public int compare(QXUser lhs, QXUser rhs) {
	                            	try {
	                            		return lhs.getHeader().compareTo(rhs.getHeader());
									} catch (Exception e) {
									}
	                                return 0;
	                            }
	                        });
	                     mListView.setAdapter(new DepartmentAdapter(mContext,mListView,"/",CommonUtils.getChildDepartments("/"),CommonUtils.getUsersWithDepartment(userlist, "/")));   
	                    }
	                });
			        root.setText("环信");
			        container.addView(root);
			        if(count>0){
			            final String[] segments = new String[count];
			            segments[0] = "";
			            for (int i = 0; i < count; i++) {
	                        if(buttons[i].length()>0){
	                            if(buttons[i].length() > 0){
	                                segments[i] = segments[i-1] + "/" + buttons[i];
	                            } else {
	                                segments[i] = segments[i-1];
	                            }
	                        }
	                    }
			            for(int i=1;i<count;i++){
			                String b = buttons[i];
			                if(b.length()>0){
							TextView textView = new TextView(mContext);
							textView.setText(">");
							container.addView(textView);

			                    Button tmpButton = (Button) inflater.inflate(R.layout.button_department, container, false);
	                            final String s = segments[i];
	                            tmpButton.setOnClickListener(new OnClickListener() {
	                                @Override
	                                public void onClick(View v) {
	                                    mListView.setAdapter(new DepartmentAdapter(mContext, mListView, s, CommonUtils.getChildDepartments(s),
	                                    		CommonUtils.getUsersWithDepartment(DemoApplication.getInstance().getAllUsers(), s)));
	                                }
	                            });
	                            tmpButton.setText(b);
	                            container.addView(tmpButton);
	                           
			                }
			            }
			        }
//			        container.getChildAt(container.getChildCount() - 1).setBackgroundResource(R.drawable.button_dept_highlighted);
			        if(container.getChildAt(container.getChildCount() - 1) instanceof Button){
			        	Button lastBtn = (Button) container.getChildAt(container.getChildCount() - 1);
			        	lastBtn.setTextColor(Color.BLUE);
			        	txtTitle.setText(lastBtn.getText());
			        }
			        
			        
			    }
		}else{
			 Object item = getItem(position);
			    if(item.getClass().equals(QXUser.class)){
			        final ViewHolder holder;
			        if (convertView == null) {
	                    holder=new ViewHolder();
	                    convertView = inflater.inflate(R.layout.row_contact, parent, false);    
//	                  holder.header = (TextView) convertView.findViewById(R.id.header);
	                    holder.name = (TextView) convertView.findViewById(R.id.name);
	                    holder.signature = (TextView) convertView.findViewById(R.id.signature);
	                    holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
	                    convertView.setTag(holder);
	                }else{
	                    holder= (ViewHolder) convertView.getTag();
	                }
			        final QXUser user = (QXUser)item;
			        holder.name.setText(user.getNick());
			        if(!TextUtils.isEmpty(user.getSignature())){
	                    holder.signature.setVisibility(View.VISIBLE);
	                    holder.signature.setText(user.getSignature());
	                }else{
	                    holder.signature.setVisibility(View.GONE);
	                }
			        holder.avatar.setImageResource(R.drawable.default_avatar);
			    }else{
			        if (convertView == null) {
	                    convertView = inflater.inflate(R.layout.row_department, parent, false); 
	                }
	                
	                TextView deptView = (TextView) convertView.getTag();
	                if(deptView == null){
	                    deptView = ((TextView)convertView.findViewById(R.id.dept));
	                }
	                final String dept = (String) item;
	                StringBuilder sb = new StringBuilder(CommonUtils.getDepartmentName(dept));
			        sb.append(" (");
			        sb.append(CommonUtils.getUserCountWithDepartment(DemoApplication.getInstance().getAllUsers(), dept));
			        sb.append(")");
			        deptView.setText(sb.toString());
			        convertView.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        mListView.setAdapter(new DepartmentAdapter(mContext, mListView, dept, CommonUtils.getChildDepartments(dept),
	                        		CommonUtils.getUsersWithDepartment(DemoApplication.getInstance().getAllUsers(), dept)));
	                    }
	                });
			    }
		}
		
		
		
		return convertView;
	}
	
	
	
}
