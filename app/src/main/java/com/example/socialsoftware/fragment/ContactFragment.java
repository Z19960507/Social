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
import android.widget.ListView;
import android.widget.TextView;


import com.example.socialsoftware.ContactAdapter;
import com.example.socialsoftware.FakeUser;
import com.example.socialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment  {

    private Activity mContext = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_fragment, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

    }

    private void initUI(View view){
        //初始化一个控件（列表控件）  1
        RecyclerView rv = view.findViewById(R.id.fragment_rv);



        //给rv一个管理器  2
        rv.setLayoutManager(new LinearLayoutManager(mContext));



        List<FakeUser> FakeuserList = new ArrayList<>();


        FakeUser user1 = new FakeUser("1","好友1","http://www.baidu.com");

        FakeUser user2 = new FakeUser("2","好友2","http://www.baidu.com");

        FakeUser user3 = new FakeUser("3","好友3","http://www.baidu.com");

        FakeUser user4 = new FakeUser("4","好友4","http://www.baidu.com");

        FakeUser user5 = new FakeUser("5","好友5","http://www.baidu.com");

        FakeUser user6 = new FakeUser("6","好友6","http://www.baidu.com");

        FakeUser user7 = new FakeUser("7","好友7","http://www.baidu.com");

        FakeUser user8 = new FakeUser("8","好友8","http://www.baidu.com");


        FakeuserList.add(user1);
        FakeuserList.add(user2);
        FakeuserList.add(user3);
        FakeuserList.add(user4);
        FakeuserList.add(user5);
        FakeuserList.add(user6);
        FakeuserList.add(user7);
        FakeuserList.add(user8);



        //写一个适配器Adapter  3
        ContactAdapter contactAdapter = new ContactAdapter(FakeuserList, mContext);

        //把适配器设置给rv  4
        rv.setAdapter(contactAdapter);



    }






}