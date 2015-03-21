package com.sundayStartUp.apps.localappsearch.Adapters;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sundayStartUp.apps.localappsearch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchListAdapter extends BaseAdapter{

    List<PackageInfo> packageList;
    List<PackageInfo> tempPackageList;
    Activity context;
    PackageManager packageManager;
 
    public SearchListAdapter(Activity context, List<PackageInfo> packageList,
            PackageManager packageManager) {
        super();
        this.context = context;
        this.packageList = new ArrayList<PackageInfo>();
        this.packageList.addAll(packageList);
        tempPackageList = new ArrayList<PackageInfo>();
        tempPackageList.addAll(packageList);
        this.packageManager = packageManager;
    }
 
    private class ViewHolder {
        TextView apkName;
    }
 
    public int getCount() {
        return packageList.size();
    }
 
    public Object getItem(int position) {
        return packageList.get(position);
    }
 
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.app_list_item, null);
            holder = new ViewHolder();
 
            holder.apkName = (TextView) convertView.findViewById(R.id.app_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        PackageInfo packageInfo = (PackageInfo) getItem(position);
        Drawable appIcon = packageManager
                .getApplicationIcon(packageInfo.applicationInfo);
        String appName = packageManager.getApplicationLabel(
                packageInfo.applicationInfo).toString();
        appIcon.setBounds(0, 0, 60, 60);
        holder.apkName.setCompoundDrawables(appIcon, null, null, null);
        holder.apkName.setCompoundDrawablePadding(15);
        holder.apkName.setText(appName);
 
        return convertView;
    }
    
    public void filter(String inputString) {
    	packageList.clear();
    	if(inputString.length() == 0) {
    		packageList.addAll(tempPackageList);
    	} else {
    		for(PackageInfo pi : tempPackageList) {
    			String appName = packageManager.getApplicationLabel(
    	                pi.applicationInfo).toString();
    			if(appName.toLowerCase(Locale.US).indexOf(inputString) >= 0) {
    				packageList.add(pi);
    			}
    		}
    	}
    	notifyDataSetChanged();
    }
 }
