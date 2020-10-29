package com.example.socialsoftware.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialsoftware.R;
import com.example.socialsoftware.adapter.ContactAdapter;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;

import java.util.List;

public class ContactFragment extends Fragment  {

    private Activity mContext = null;
    private DBOpenHelper db;
    private String name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        db = new DBOpenHelper(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sp.getString("name","");
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

        List<User> userList = db.getFriendListByName(name);
        //写一个适配器Adapter  3
        ContactAdapter contactAdapter = new ContactAdapter(userList, mContext);
        //把适配器设置给rv  4
        rv.setAdapter(contactAdapter);
    }






}