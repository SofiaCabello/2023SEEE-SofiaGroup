package com.example.wechatproject.message;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wechatproject.R;
import com.example.wechatproject.util.DBHelper;
import com.example.wechatproject.util.MessageAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 刷新列表
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListView messageListView = getView().findViewById(R.id.listViewMessages);
                            DBHelper dbHelper = new DBHelper(getContext());
                            List<MessageItem> messageItemList = dbHelper.getLatestMessage();
                            MessageAdapter messageAdapter = new MessageAdapter(getContext(), R.layout.message_list_item, messageItemList);
                            messageListView.setAdapter(messageAdapter);
                        }
                    });
                }
            }
        }, 0, 1000);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View messageView = inflater.inflate(R.layout.fragment_message_list, container, false);
        ListView messageListView = messageView.findViewById(R.id.listViewMessages);
        DBHelper dbHelper = new DBHelper(getContext());
        List<MessageItem> messageItemList = dbHelper.getLatestMessage();
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), R.layout.message_list_item, messageItemList);
        messageListView.setAdapter(messageAdapter);
        return messageView;
    }
}