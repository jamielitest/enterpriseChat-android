package com.easemob.chatuidemo.activity.enterprise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easemob.qixin.R;

public class EnterpriseFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.enterprise, container,false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView tvTitle = (TextView) getView().findViewById(R.id.txtTitle);
		tvTitle.setText(R.string.enterprise);
	}
	
	
}
