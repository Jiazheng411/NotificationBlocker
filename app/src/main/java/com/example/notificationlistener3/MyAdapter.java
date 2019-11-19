//package com.example.notificationlistener3;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
//    private String[] mDataset;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView textView;
//        public MyViewHolder(TextView v) {
//            super(v);
//            textView = v;
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                     int viewType) {
//        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.single_app_row_view, parent, false);
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.textView.setText(mDataset[position]);
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }
//}


package com.example.notificationlistener3;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private PackageManager packageManager;
    private LayoutInflater mLayoutInflater;
    private List<ApplicationInfo> apps;
    private SharedPreferences mSharedPreferences;
    private onCheckboxClicked checkedListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView icon;
        TextView name;
        CheckBox status;
        onCheckboxClicked listener;

        public MyViewHolder(View view, final onCheckboxClicked listener) {
            super(view);
            this.listener = listener;
            icon = view.findViewById(R.id.app_icon);
            name = view.findViewById(R.id.app_name);
            status = view.findViewById(R.id.app_status);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status.performClick();
                }
            });

            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MyViewHolder.this.listener.OnCheckboxClicked(getLayoutPosition(), isChecked);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, List<ApplicationInfo> apps, onCheckboxClicked listener) {
        this.apps = apps;
        this.checkedListener = listener;
        packageManager = context.getPackageManager();
        mLayoutInflater = LayoutInflater.from(context);
        mSharedPreferences = context.getSharedPreferences("setting", 0);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = mLayoutInflater.inflate(R.layout.single_app_row,parent,false);
        return new MyViewHolder(view, this.checkedListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ApplicationInfo app = apps.get(position);
        HashSet<String> notBlocked = new HashSet<>(Arrays.asList(mSharedPreferences.getString(Util_String.APPS_RECEIVING_NOTIFICATION, "").split(";")));

        holder.icon.setImageDrawable(app.loadIcon(packageManager));
        holder.name.setText(app.loadLabel(packageManager));
        holder.status.setChecked(notBlocked.contains(app.packageName));
    }

    // Return the size of your apps (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return apps.size();
    }

    public ApplicationInfo getItem(int position) {
        return apps.get(position);
    }

    public void setApps(List<ApplicationInfo> apps) {
        this.apps = apps;
        notifyDataSetChanged();
    }
}
