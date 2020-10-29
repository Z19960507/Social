package com.example.socialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialsoftware.model.FindFakeUser;
import com.example.socialsoftware.R;

import java.util.List;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.MyViewHolder> {
    //data随便起
    private List<FindFakeUser> data;
    private Context context;

    /*构造方法 接收数据*/
    public FindAdapter(List<FindFakeUser> strList, Context context) {
        this.data = strList;
        this.context = context;
    }

    /*填充子项布局*/
    @NonNull
    @Override
    public FindAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //在Java中使用xml，需要把xml转换成
        View view = LayoutInflater.from(context).inflate(R.layout.rv_find,parent,false);
        return new FindAdapter.MyViewHolder(view);
    }


    /*声明并初始化 UI控件*/
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumber;
        ImageView ivAvatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_find_name);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_find);


        }
    }


    /*把数据绑定到UI上*/
    @Override
    public void onBindViewHolder(@NonNull FindAdapter.MyViewHolder holder, final int position) {
        holder.tvNumber.setText(String.valueOf(data.get(position).getAge()));
        switch (position){
            case 0:
                holder.ivAvatar.setImageResource(R.drawable.groupchat);
                break;
            case 1:
                holder.ivAvatar.setImageResource(R.drawable.publicnum);
                break;
            default:
                holder.ivAvatar.setImageResource(R.drawable.sign);
        }

        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(data.get(position)), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}
