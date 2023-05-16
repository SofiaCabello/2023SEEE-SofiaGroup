package com.example.wechatproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechatproject.R;
import com.example.wechatproject.message.MessageItem;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<MessageItem> messageList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView messageTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView  = itemView.findViewById(R.id.avatarImageView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }

    public RecyclerAdapter(List<MessageItem> messageList) {
        this.messageList = messageList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageItem item = messageList.get(position);
        holder.messageTextView.setText(item.getMessage());
        holder.avatarImageView.setImageResource(item.getAvatarResId());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
