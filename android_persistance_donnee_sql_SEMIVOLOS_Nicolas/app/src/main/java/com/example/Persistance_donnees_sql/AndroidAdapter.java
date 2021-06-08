package com.example.Persistance_donnees_sql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AndroidAdapter extends ArrayAdapter<User> {
    private int viewRes;
    private Context context;
    private ArrayList<User> UserList;

    public AndroidAdapter(Context context, int textViewResourceId, ArrayList<User> versions) {
        super(context,textViewResourceId, versions);
        this.UserList = versions;
        this.context = context;
        this.viewRes = textViewResourceId;
    }

    static class ViewHolder {
        TextView username;
        TextView email;

    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup parent) {
        View view = ConvertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);
            holder = new ViewHolder();
            holder.username = (TextView)view.findViewById(R.id.UsernameItem);
            holder.email = (TextView)view.findViewById(R.id.EmailItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        final User User = UserList.get(position);
        if (User != null) {
            holder.username.setText(User.getUsername());
            holder.email.setText(User.getEmail());
            }
        return view;
    }

    public int getCount() {
        return UserList.size();
    }
}
