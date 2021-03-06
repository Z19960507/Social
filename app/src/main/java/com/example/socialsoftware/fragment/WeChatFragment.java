package com.example.socialsoftware.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialsoftware.adapter.ChatAdapter;
import com.example.socialsoftware.model.ChatFakeUser;


import com.example.socialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class WeChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mContext = null;

    public WeChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeChatFragment newInstance(String param1, String param2) {
        WeChatFragment fragment = new WeChatFragment();
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
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wechat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

    }

    private void initUI(View view) {
        //初始化一个控件（列表控件）  1
        RecyclerView rv = view.findViewById(R.id.rv_chat);


        //给rv一个管理器  2
        rv.setLayoutManager(new LinearLayoutManager(mContext));


        List<ChatFakeUser> ChatFakeUserList = new ArrayList<>();


        ChatFakeUser user1 = new ChatFakeUser("1", "对话1", "http://www.baidu.com");

        ChatFakeUser user2 = new ChatFakeUser("2", "对话2", "http://www.baidu.com");

        ChatFakeUser user3 = new ChatFakeUser("3", "对话3", "http://www.baidu.com");




        ChatFakeUserList.add(user1);
        ChatFakeUserList.add(user2);
        ChatFakeUserList.add(user3);



        //写一个适配器Adapter  3
        ChatAdapter ChatAdapter = new ChatAdapter(ChatFakeUserList, mContext);

        //把适配器设置给rv  4
        rv.setAdapter(ChatAdapter);

    }

}
