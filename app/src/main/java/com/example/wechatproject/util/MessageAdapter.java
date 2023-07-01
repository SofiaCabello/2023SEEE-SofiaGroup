package com.example.wechatproject.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechatproject.R;
import com.example.wechatproject.message.ChatActivity;
import com.example.wechatproject.message.MessageItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MessageAdapter extends ArrayAdapter<MessageItem> {
    private List<MessageItem> messageItemList;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, int resource, List<MessageItem> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        messageItemList = new ArrayList<>(objects);
        filterMessageList();
    }

    @Override
    public int getCount() {
        return messageItemList.size();
    }

    @Override
    public MessageItem getItem(int position) {
        return messageItemList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatarImageView = convertView.findViewById(R.id.img1);
            viewHolder.nameTextView = convertView.findViewById(R.id.title);
            viewHolder.messageTextView = convertView.findViewById(R.id.content);
            viewHolder.timeTextView = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前位置的消息项
        MessageItem messageItem = getItem(position);

        if (messageItem != null) {
            // 在视图中显示消息数据
            viewHolder.avatarImageView.setImageURI(Uri.parse(messageItem.getAvatarFilePath()));
            viewHolder.nameTextView.setText(messageItem.getName());
            if (Objects.equals(messageItem.getType(), "0")) {
                viewHolder.messageTextView.setText(messageItem.getLatestMessage());
            } else {
                viewHolder.messageTextView.setText("[非文本消息]");
            }
            String time = messageItem.getTime();
            // 将时间戳转换为时间
            String date = TimeStamp2Date(time, "yyyy-MM-dd HH:mm:ss");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("name", messageItem.getName());
                    intent.putExtra("avatarFilePath", messageItem.getAvatarFilePath());
                    getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }

    // 将时间戳转换为时间
    private String TimeStamp2Date(String time, String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        long lt = new Long(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    private static class ViewHolder {
        ImageView avatarImageView;
        TextView nameTextView;
        TextView messageTextView;
        TextView timeTextView;
    }

    private void filterMessageList() {
        List<MessageItem> filteredList = new ArrayList<>();
        for (MessageItem messageItem : messageItemList) {
            if (!messageItem.getName().equals(CurrentUserInfo.getUsername())) {
                filteredList.add(messageItem);
            }
        }
        messageItemList = filteredList;
    }

}

