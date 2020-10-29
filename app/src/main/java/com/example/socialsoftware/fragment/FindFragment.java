package com.example.socialsoftware.fragment;

import android.app.Activity;
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
import com.example.socialsoftware.adapter.FindAdapter;
import com.example.socialsoftware.model.FindFakeUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mContext = null;

    public FindFragment() {
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
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
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
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

    }

    private void initUI(View view) {
        //初始化一个控件（列表控件）  1
        RecyclerView rv = view.findViewById(R.id.rv_find);


        //给rv一个管理器  2
        rv.setLayoutManager(new LinearLayoutManager(mContext));


        List<FindFakeUser> FindFakeUserList = new ArrayList<>();


        FindFakeUser user1 = new FindFakeUser("1", "功能1", "http://www.baidu.com");

        FindFakeUser user2 = new FindFakeUser("2", "功能2", "http://www.baidu.com");

        FindFakeUser user3 = new FindFakeUser("3", "功能3", "http://www.baidu.com");




        FindFakeUserList.add(user1);
        FindFakeUserList.add(user2);
        FindFakeUserList.add(user3);



        //写一个适配器Adapter  3
        FindAdapter findAdapter = new FindAdapter(FindFakeUserList, mContext);

        //把适配器设置给rv  4
        rv.setAdapter(findAdapter);

    }


}