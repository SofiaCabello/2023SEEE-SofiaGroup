package com.example.wechatproject.message;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechatproject.R;
import com.example.wechatproject.message.ChatItem;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<ChatItem> chatItemList;
    private LayoutInflater inflater;
    private static final int TYPE_SENT = 0;
    private static final int TYPE_RECEIVED = 1;

    public ChatAdapter(Context context, List<ChatItem> objects) {
        chatItemList = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatItem chatItem = chatItemList.get(position);
        if (chatItem.isMeSend()) {
            return TYPE_SENT;
        } else {
            return TYPE_RECEIVED;
        }
    }

    public void setData(List<ChatItem> chatItemList) {
        this.chatItemList = chatItemList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int viewType = getItemViewType(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (viewType == TYPE_SENT) {
                convertView = inflater.inflate(R.layout.message_sent_item, parent, false);
                viewHolder.avatarImageView = convertView.findViewById(R.id.ivAvatarSent);
                viewHolder.messageTextView = convertView.findViewById(R.id.tvMessageSent);
            } else if (viewType == TYPE_RECEIVED) {
                convertView = inflater.inflate(R.layout.message_received_item, parent, false);
                viewHolder.avatarImageView = convertView.findViewById(R.id.ivAvatarReceived);
                viewHolder.messageTextView = convertView.findViewById(R.id.tvMessageReceived);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前位置的消息项
        ChatItem messageItem = chatItemList.get(position);

        // 在视图中显示消息数据
        viewHolder.avatarImageView.setImageURI(Uri.parse(messageItem.getAvatarFilePath()));
        if (messageItem.getType().equals("0")) {
            viewHolder.messageTextView.setText(messageItem.getMessage());
        } else {
            viewHolder.messageTextView.setText("[其他文件类型，请点击查看]");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //通过Intent传递文件路径，打开文件
                    String filePath = messageItem.getMessage();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + filePath), "*/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        v.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "未找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView avatarImageView;
        TextView messageTextView;
    }
}
