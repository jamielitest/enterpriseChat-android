package com.easemob.chatuidemo.activity.contact;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.activity.contact.adapter.DepartmentAdapter;
import com.easemob.chatuidemo.domain.Depart;
import com.easemob.chatuidemo.domain.Sub_Depart;
import com.easemob.chatuidemo.parse.QXUser;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.chatuidemo.utils.LoadingDialogShow;
import com.easemob.qixin.R;

public class DepartmentListFragment extends Fragment {

	protected static final String TAG = DepartmentListFragment.class.getSimpleName();
	protected ListView deptListView;

	protected DepartmentAdapter departmentAdapter;
	protected List<QXUser> deptUserlist;
	private InputMethodManager manager;
	private LoadingDialogShow dialog;
	List<Sub_Depart> listDeparts;
	private List<Depart> listd;
	private static final int MSG_REG_SUCC = 0;
	private static final int MSG_REG_FAIL = 1;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REG_SUCC:

				break;
			case MSG_REG_FAIL:

				dialog.setResultStatusDrawable(false, "密码错误");
				break;

			default:
				break;
			}
		}

	};

	public DepartmentListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.contact_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

		dialog = new LoadingDialogShow(getActivity());

		deptListView = (ListView) getView().findViewById(R.id.list);
//		DemoApplication.getInstance().getQXManager().saveDepartmentData();
		DemoApplication.getInstance().getQXManager().saveUserAndDepart(new EMCallBack() {
			
			@Override
			public void onSuccess() {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(getActivity(), "getUser Success:", Toast.LENGTH_SHORT).show();
						refresh();
					}
				});
				
			}
			
			@Override
			public void onProgress(int progress, String status) {
				
			}
			
			@Override
			public void onError(int code,final String message) {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(getActivity(), "getUser Fail:" + message, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		
		departmentAdapter = new DepartmentAdapter(getActivity(), deptListView, "/",
				CommonUtils.getChildDepartments("/"), CommonUtils.getUsersWithDepartment(DemoApplication.getInstance()
						.getAllUsers(), "/"));

		deptListView.setAdapter(departmentAdapter);
		deptListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, android.view.MotionEvent event) {
				if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getActivity().getCurrentFocus() != null)
						manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
	}

	public void refresh() {
		departmentAdapter = new DepartmentAdapter(getActivity(), deptListView, "/",
				CommonUtils.getChildDepartments("/"), CommonUtils.getUsersWithDepartment(DemoApplication.getInstance()
						.getAllUsers(), "/"));
		deptListView.setAdapter(departmentAdapter);
	}

}
