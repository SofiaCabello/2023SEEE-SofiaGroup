package com.example.wechatproject.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.FileUtil;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;

import org.json.JSONObject;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        ImageView buttonSettings = view.findViewById(R.id.imageView2);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动设置界面的Activity
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        ImageView imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        TextView textViewUsername = view.findViewById(R.id.textViewUsername);
        TextView textViewSignature = view.findViewById(R.id.textViewSignature);
        TextView textview = view.findViewById(R.id.textView);

        Client.SendJSONTask task = new Client.SendJSONTask(getContext(), new Client.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {
                    CurrentUserInfo.getInstance().setSignature(jsonObject.optString("signature"));
                    CurrentUserInfo.getInstance().setAvatarFilePath(FileUtil.base64ToFile(getContext(),jsonObject.optString("photoId"),"1"));
                    textViewSignature.setText(CurrentUserInfo.getSignature());
                    imageViewAvatar.setImageURI(Uri.parse(CurrentUserInfo.getAvatarFilePath()));
                }
            }
        });

        task.execute(JSONHandler.generateRequestUserInfoJSON(CurrentUserInfo.getUsername()));

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if(CurrentUserInfo.getSignature()!=null){
            textViewSignature.setText(CurrentUserInfo.getSignature());
        }
        if(CurrentUserInfo.getAvatarFilePath() != null) {
            imageViewAvatar.setImageURI(Uri.parse(CurrentUserInfo.getAvatarFilePath()));
        }
        textViewUsername.setText(CurrentUserInfo.getUsername());
        textview.setText(CurrentUserInfo.getIPAddress());
        return view;
    }
}