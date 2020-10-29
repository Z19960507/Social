package com.example.socialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.socialsoftware.arouter.MyRoute;
import com.example.socialsoftware.model.ChatFakeUser;
import com.example.socialsoftware.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    //data随便起
    private List<ChatFakeUser> data;
    private final Context context;

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
        ConstraintLayout clChatParent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_chat_name);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_chat);
            clChatParent = itemView.findViewById(R.id.cl_chat_parent);
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
        holder.clChatParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(MyRoute.ACTIVITY_CHAT_DETAIL)
                        .navigation();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
