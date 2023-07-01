package com.example.wechatproject.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechatproject.R;
import com.example.wechatproject.message.ChatItem;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatItem> {
    private List<ChatItem> messageItemList;
    private LayoutInflater inflater;

    public ChatAdapter(Context context, int resource, List<ChatItem> objects) {
        super(context, resource, objects);
        messageItemList = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.userNameTextView);
            //viewHolder.messageTextView = convertView.findViewById(R.id.messageTextView);
            viewHolder.avatarImageView = convertView.findViewById(R.id.avatarImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前位置的消息项
        ChatItem messageItem = messageItemList.get(position);

        // 在视图中显示消息数据
        viewHolder.nameTextView.setText(messageItem.getName());
        viewHolder.messageTextView.setText(messageItem.getMessage());

        return convertView;
    }

    private static class ViewHolder {
        ImageView avatarImageView;
        TextView nameTextView;
        TextView messageTextView;
    }
}
