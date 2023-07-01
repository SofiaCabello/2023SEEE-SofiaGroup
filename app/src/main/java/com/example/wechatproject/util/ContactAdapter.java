package com.example.wechatproject.util;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechatproject.R;
import com.example.wechatproject.contact.ContactItem;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactItem> {
    private List<ContactItem> contactItemList;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, int resource, List<ContactItem> objects) {
        super(context, resource, objects);
        contactItemList = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatarImageView = convertView.findViewById(R.id.avatarImageView);
            viewHolder.userNameTextView = convertView.findViewById(R.id.userNameTextView);
            viewHolder.signatureTextView = convertView.findViewById(R.id.signatureTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前位置的联系人项
        ContactItem contactItem = contactItemList.get(position);

        // 在视图中显示联系人数据
        viewHolder.avatarImageView.setImageURI(Uri.parse(contactItem.getAvatarFilePath()));
        viewHolder.userNameTextView.setText(contactItem.getUserName());
        viewHolder.signatureTextView.setText(contactItem.getSignature());

        return convertView;
    }

    private static class ViewHolder {
        ImageView avatarImageView;
        TextView userNameTextView;
        TextView signatureTextView;
    }
}

