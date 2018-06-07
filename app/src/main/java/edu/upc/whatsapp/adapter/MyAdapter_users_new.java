/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.upc.whatsapp.R;
import entity.User;
import entity.UserInfo;

/**
 *
 * @author upcnet
 */
public class MyAdapter_users_new extends RecyclerView.Adapter<MyAdapter_users_new.ViewHolder>{

    Context context;
    List<UserInfo> users;
    UserAdapterCallbacks mCallbacks;

    public interface UserAdapterCallbacks {
       void OnUserClick(UserInfo userInfo);
    }

    public MyAdapter_users_new(List<UserInfo> users, UserAdapterCallbacks listener) {
      this.users = users;
      this.mCallbacks = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_twotextviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserInfo mUserInfo = users.get(position);
        holder.name.setText(mUserInfo.getName());
        holder.surname.setText(mUserInfo.getSurname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.OnUserClick(mUserInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users==null?0:users.size();
    }

    public void swapUsers(List<UserInfo> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView name;
        @BindView(R.id.user_lastname)
        TextView surname;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
