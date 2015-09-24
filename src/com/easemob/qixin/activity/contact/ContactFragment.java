package com.easemob.qixin.activity.contact;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.qixin.R;
import com.easemob.qixin.activity.ContactlistFragment;

public class ContactFragment extends Fragment{
	private ViewPager vPager;
	private InputMethodManager manager;
    private RelativeLayout titleLayout1, titleLayout2, titleLayout3;
    private TextView titleText1, titleText2, titleText3;
    private ImageView titleLine1, titleLine2, titleLine3;
    private boolean hidden;
    private List<Fragment> fragments;
    
    private int currentPagerIndex = 0;
    private LinearLayout refreshContanier;
    private ImageButton refreshBtn;
    private ProgressBar loadingBar;
    
    
//     private List<EMUser> contactList;
    private DepartmentListFragment departFragment;
//    private GroupListFragment oldGroupListFragment;
    private ContactlistFragment contactFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_tab_contacts, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 vPager = (ViewPager) getView().findViewById(R.id.vPager);
	        
	        refreshContanier = (LinearLayout) getActivity().findViewById(R.id.rl_fresh);
	        refreshBtn = (ImageButton) getActivity().findViewById(R.id.btn_refresh);
	        loadingBar = (ProgressBar) getActivity().findViewById(R.id.pb_loading);
	        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

	        titleLayout1 = (RelativeLayout) getView().findViewById(R.id.title_layout1);
	        titleLayout2 = (RelativeLayout) getView().findViewById(R.id.title_layout2);
	        titleLayout3 = (RelativeLayout) getView().findViewById(R.id.title_layout3);
	        titleText1 = (TextView) getView().findViewById(R.id.title1);
	        titleText2 = (TextView) getView().findViewById(R.id.title2);
	        titleText3 = (TextView) getView().findViewById(R.id.title3);
	        titleLine1 = (ImageView) getView().findViewById(R.id.iv_line1);
	        titleLine2 = (ImageView) getView().findViewById(R.id.iv_line2);
	        titleLine3 = (ImageView) getView().findViewById(R.id.iv_line3);
	        
	        fragments = new ArrayList<Fragment>();
	        contactFragment = new ContactlistFragment();    //联系人
	        departFragment = new DepartmentListFragment();  //部门
	        //工作组
//	        List<EMGroup> allGroups = EMGroupManager.getInstance().getAllGroups();
//	        if(allGroups==null)
//	        {
//	            allGroups=new ArrayList<EMGroup>();
//	        }
//	        oldGroupListFragment = new GroupListFragment(allGroups, new MyGroupFragmentListListener()); //工作组
		
	        fragments.add(contactFragment);
	        fragments.add(departFragment);
//	        fragments.add(oldGroupListFragment);
	        
	     // set viewpager adapter
	        vPager.setAdapter(new ContactFragmentPagerAdapter(getChildFragmentManager(), fragments));
	        vPager.setOnPageChangeListener(new OnPageChangeListener() {

	            @Override
	            public void onPageSelected(int arg0) {
	                switch (arg0) {
	                case 0:
	                    if (currentPagerIndex == 1) {
	                        setToNormalColor(titleText2);
	                        titleLine2.setVisibility(View.INVISIBLE);
	                    } else if (currentPagerIndex == 2) {
	                        setToNormalColor(titleText3);
	                        titleLine3.setVisibility(View.INVISIBLE);
	                    }
	                    setToSelectedColor(titleText1);
	                    titleLine1.setVisibility(View.VISIBLE);

	                    break;
	                case 1:
	                    if (currentPagerIndex == 0) {
	                        setToNormalColor(titleText1);
	                        titleLine1.setVisibility(View.INVISIBLE);
	                    } else if (currentPagerIndex == 2) {
	                        setToNormalColor(titleText3);
	                        titleLine3.setVisibility(View.INVISIBLE);
	                    }
	                    setToSelectedColor(titleText2);
	                    titleLine2.setVisibility(View.VISIBLE);

	                    break;
	                case 2:
	                    if (currentPagerIndex == 1) {
	                        setToNormalColor(titleText2);
	                        titleLine2.setVisibility(View.INVISIBLE);
	                    } else if (currentPagerIndex == 0) {
	                        setToNormalColor(titleText1);
	                        titleLine1.setVisibility(View.INVISIBLE);
	                    }
	                    setToSelectedColor(titleText3);
	                    titleLine3.setVisibility(View.VISIBLE);

	                    break;

	                }

	                currentPagerIndex = arg0;

	            }

	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	                // 隐藏软键盘
	                if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
	                    if (getActivity().getCurrentFocus() != null)
	                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
	                                InputMethodManager.HIDE_NOT_ALWAYS);
	                }

	            }

	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	                // TODO Auto-generated method stub

	            }
	        });
	        vPager.setCurrentItem(0);
	        
	        titleLayout1.setOnClickListener(new ContactTitleClickListener(0));
	        titleLayout2.setOnClickListener(new ContactTitleClickListener(1));
//	        titleLayout3.setOnClickListener(new ContactTitleClickListener(2));
	        
	        
	}
	
	protected class ContactTitleClickListener implements View.OnClickListener {
		private int index = 0;

		public ContactTitleClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			vPager.setCurrentItem(index);
		}
	};

	protected void setToSelectedColor(TextView textView) {
		textView.setTextColor(getResources().getColor(android.R.color.white));

	}

	protected void setToNormalColor(TextView textView) {
		textView.setTextColor(getResources().getColor(R.color.contacts_title_nomal_color));
	}

//	// Fragment点击事件
//
//	class MyGroupFragmentListListener implements GroupListFragmentListener {
//		@Override
//		public void onListItemClickListener(int position) {
//			System.out.println("onListItemClickListener:" + position);
//			if (position == oldGroupListFragment.groupAdapter.getCount() - 1) {
//
//				if (EMChatManager.getInstance().isConnected()) {
//					startActivityForResult(new Intent(getActivity(), NewGroupActivity.class), 0);
//				} else {
//					startActivity(new Intent(getActivity(), AlertDialog.class).putExtra("msg",
//							getActivity().getString(R.string.network_unavailable)));
//				}
//			} else {
//				Intent intent = new Intent(getActivity(), ChatActivity.class);
//				intent.putExtra("isChat", false);
//				intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
//				intent.putExtra("position", position - 1);
//				intent.putExtra("groupId", oldGroupListFragment.groupAdapter.getItem(position - 1).getGroupId());
//				startActivityForResult(intent, 0);
//			}
//		}
//
//	}
	class ContactFragmentPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments;

        public ContactFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
	
}
