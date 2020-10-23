package com.example.socialsoftware;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    //data随便起
    private List<ChatFakeUser> data;
    private Context context;

    /*构造方法 接收数据*/
    public ChatAdapter(List<ChatFakeUser> strList, Context context) {
        this.data = strList;
        this.context = context;
    }

    /*填充子项布局*/
    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //在Java中使用xml，需要把xml转换成
        View view = LayoutInflater.from(context).inflate(R.layout.rv_chat,parent,false);
        return new ChatAdapter.MyViewHolder(view);
    }




    /*声明并初始化 UI控件*/
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumber;
        ImageView ivAvatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_chat_name);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_chat);


        }
    }


    /*把数据绑定到UI上*/
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, final int position) {
        holder.tvNumber.setText(String.valueOf(data.get(position).getAge()));
        switch (position){
            case 0:
                holder.ivAvatar.setImageResource(R.drawable.kobe);
                break;
            case 1:
                holder.ivAvatar.setImageResource(R.drawable.pony);
                break;
            default:
                holder.ivAvatar.setImageResource(R.drawable.kun);
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
