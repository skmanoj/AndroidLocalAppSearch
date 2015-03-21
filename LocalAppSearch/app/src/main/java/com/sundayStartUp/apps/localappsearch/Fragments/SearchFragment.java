package com.sundayStartUp.apps.localappsearch.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.sundayStartUp.apps.localappsearch.Adapters.SearchListAdapter;
import com.sundayStartUp.apps.localappsearch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements OnItemClickListener {
	
	private EditText mSearchText;
	private PackageManager mPackageManager;
	private ListView mAppListView;
	private SearchListAdapter mSearchAdapter;
	
	public SearchFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mPackageManager = getActivity().getPackageManager();
        List<PackageInfo> packageList = mPackageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<PackageInfo> appPackageList = new ArrayList<PackageInfo>();
        Intent intent;
        
        for(PackageInfo pi : packageList) {
        	intent = mPackageManager.getLaunchIntentForPackage(pi.packageName);
            boolean b = intent == null || 
            		pi.packageName.equalsIgnoreCase(getActivity().getApplicationContext().getPackageName());
            if(!b) {
            	appPackageList.add(pi);
            }
        }
        
        mAppListView = (ListView) rootView.findViewById(R.id.app_list_view);
        mSearchAdapter = new SearchListAdapter(getActivity(), appPackageList, mPackageManager);
        mAppListView.setAdapter(mSearchAdapter);
        mAppListView.setOnItemClickListener(this);
        
		mSearchText = (EditText) rootView.findViewById(R.id.inputSearch);
		mAppListView.setOnScrollListener(new OnScrollListener() {
			
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				hideKeyBoard();
				
			}
			
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		mSearchText.addTextChangedListener(new TextWatcher() {
		
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String text = mSearchText.getText().toString().toLowerCase(Locale.getDefault());
				mSearchAdapter.filter(text);
			}
		});
        
		return rootView;
	}
	
	private void hideKeyBoard() {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
            long row) {
		hideKeyBoard();
        PackageInfo packageInfo = (PackageInfo) parent
                .getItemAtPosition(position);
        Intent intent;
        intent = mPackageManager.getLaunchIntentForPackage(packageInfo.packageName);
        if(null != intent) {
        	startActivity(intent);
        }
    }
}