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
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.upc.whatsapp.R;
import entity.Message;
import entity.UserInfo;

/**
 *
 * @author upcnet
 */
public class MyAdapter_messages_new extends RecyclerView.Adapter<MyAdapter_messages_new.ViewHolder>{

    private static final int VIEW_TYPE_RIGHT_BUBBLE = 1;
    private static final int VIEW_TYPE_LEFT_BUBBLE = 2;

    List<Message> messages;
    UserInfo user;

    public MyAdapter_messages_new(List<Message> messages, UserInfo user) {
        this.messages = messages;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==VIEW_TYPE_RIGHT_BUBBLE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_whatsapp_right_bubble, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_whatsapp_left_bubble, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message mMessage = messages.get(position);
        if (mMessage.getUserSender()==user){
            return VIEW_TYPE_RIGHT_BUBBLE;
        } else {
            return VIEW_TYPE_LEFT_BUBBLE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message mMessage = messages.get(position);
        holder.date.setText(mMessage.getDateString());
        holder.message.setText(mMessage.getContent());
    }

    @Override
    public int getItemCount() {
        return messages==null?0:messages.size();
    }

    public void swapMessage(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.texto)
        TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
