package com.example.socialsoftware.fragment.chaui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.socialsoftware.R;
import com.example.socialsoftware.arouter.MyRoute;
import com.example.socialsoftware.fragment.chaui.adapter.ChatAdapter;
import com.example.socialsoftware.fragment.chaui.bean.Message;
import com.example.socialsoftware.fragment.chaui.bean.MsgSendStatus;
import com.example.socialsoftware.fragment.chaui.bean.MsgType;
import com.example.socialsoftware.fragment.chaui.bean.TextMsgBody;
import com.example.socialsoftware.fragment.chaui.util.ChatUiHelper;
import com.example.socialsoftware.fragment.chaui.widget.RecordButton;
import com.example.socialsoftware.fragment.chaui.widget.StateButton;

import java.util.ArrayList;
import java.util.UUID;


@Route(path = MyRoute.ACTIVITY_CHAT_DETAIL)
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mLlContent;
    RecyclerView mRvChat;
    EditText mEtContent;
    RelativeLayout mRlBottomLayout;//表情,添加底部布局
    ImageView mIvAdd;
    ImageView mIvEmo;
    StateButton mBtnSend;//发送按钮
    ImageView mIvAudio;//录音图片
    RecordButton mBtnAudio;//录音按钮
    LinearLayout mLlEmotion;//表情布局
    LinearLayout mLlAdd;//添加布局
//     SwipeRefreshLayout mSwipeRefresh;//下拉刷新
     private ChatAdapter mAdapter;
     public static final String 	  mSenderId="right";
     public static final String     mTargetId="left";
//     public static final int       REQUEST_CODE_IMAGE=0000;
//     public static final int       REQUEST_CODE_VEDIO=1111;SwipeRefreshLayout.OnRefreshListener,
//     public static final int       REQUEST_CODE_FILE=2222;








    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initContent();
    }

    private void initView(){
         mLlContent = findViewById(R.id.llContent);
         mRvChat = findViewById(R.id.rv_chat_list);
         mEtContent = findViewById(R.id.et_content);
         mRlBottomLayout = findViewById(R.id.bottom_layout);//表情,添加底部布局
//         mIvAdd = findViewById(R.id.ivAdd);
         mIvEmo = findViewById(R.id.ivEmo);
         mBtnSend = findViewById(R.id.btn_send);//发送按钮
         mLlEmotion = findViewById(R.id.rlEmotion);//表情布局
        //添加布局
//        mSwipeRefresh = findViewById(R.id.swipe_chat);
//        mSwipeRefresh.setOnRefreshListener(this);




        mLlEmotion.setOnClickListener(this);

        mBtnSend.setOnClickListener(this);




    }

//查詢方法
    protected void initContent() {
        mAdapter = new ChatAdapter(this, new ArrayList<Message>());
        LinearLayoutManager mLinearLayout=new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLinearLayout);//设置LinearLayoutManager在消息列表
        mRvChat.setAdapter(mAdapter);

        initChatUi();
     }


//下拉刷新模拟获取历史消息！！！SwipeRefreshLayout.OnRefreshListener,
//    @Override
//    public void onRefresh() {
//          //下拉刷新模拟获取历史消息
//          List<Message> mReceiveMsgList=new ArrayList<Message>();
//          //构建文本消息
//          Message mMessgaeText=getBaseReceiveMessage(MsgType.TEXT);
//          TextMsgBody mTextMsgBody=new TextMsgBody();
//          mTextMsgBody.setMessage("收到的消息");
//          mMessgaeText.setBody(mTextMsgBody);
//          mReceiveMsgList.add(mMessgaeText);
//          //构建图片消息
//          Message mMessgaeImage=getBaseReceiveMessage(MsgType.IMAGE);
//          ImageMsgBody mImageMsgBody=new ImageMsgBody();
//          mImageMsgBody.setThumbUrl("https://c-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.thumb.700_0.jpeg");
//          mMessgaeImage.setBody(mImageMsgBody);
//          mReceiveMsgList.add(mMessgaeImage);
//          //构建文件消息
//          Message mMessgaeFile=getBaseReceiveMessage(MsgType.FILE);
//          FileMsgBody mFileMsgBody=new FileMsgBody();
//          mFileMsgBody.setDisplayName("收到的文件");
//          mFileMsgBody.setSize(12);
//          mMessgaeFile.setBody(mFileMsgBody);
//          mReceiveMsgList.add(mMessgaeFile);
//          mAdapter.addData(0,mReceiveMsgList);//？？？？
//          mSwipeRefresh.setRefreshing(false);
//    }




    private void initChatUi(){
        //mBtnAudio
        final ChatUiHelper mUiHelper= ChatUiHelper.with(this);
        mUiHelper
                .bindContentLayout(mLlContent)
                .bindttToSendButton(mBtnSend)
                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindToEmojiButton(mIvEmo)
                .bindEmojiData()
        ;
//                .bindAddLayout(mLlAdd)
//                .bindToAddButton(mIvAdd)

//                .bindAudioBtn(mBtnAudio)


        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
        //

    }

   @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendTextMsg(mEtContent.getText().toString());//private void sendTextMsg方法獲取輸入框中的值 1
                mEtContent.setText("");//清空輸入框2
                break;
//            case R.id.rlPhoto:
//                PictureFileUtil.openGalleryPic(ChatActivity.this,REQUEST_CODE_IMAGE);
//                break;
//            case R.id.rlVideo:
//                PictureFileUtil.openGalleryAudio(ChatActivity.this,REQUEST_CODE_VEDIO);
//                break;
//            case R.id.rlFile:
//                PictureFileUtil.openFile(ChatActivity.this,REQUEST_CODE_FILE);
//                break;
//            case R.id.rlLocation:
//                break;
        }
    }





//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_FILE:
//                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//                    sendFileMessage(mSenderId, mTargetId, filePath);
//                    break;
//                case REQUEST_CODE_IMAGE:
//                    // 图片选择结果回调
//                    List<LocalMedia> selectListPic = PictureSelector.obtainMultipleResult(data);
//                    for (LocalMedia media : selectListPic) {
//                        sendImageMessage(media);
//                    }
//                    break;
//                case REQUEST_CODE_VEDIO:
//                    // 视频选择结果回调
//                    List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
//                    for (LocalMedia media : selectListVideo) {
//                        sendVedioMessage(media);
//                    }
//                    break;
//            }
//        }
//    }




    //文本消息
    private void sendTextMsg(String hello)  {//hello獲取輸入框的值
        final Message mMessgae = getBaseSendMessage(MsgType.TEXT);//創建一個消息類型的 Message mMessgae
        TextMsgBody mTextMsgBody=new TextMsgBody();
        mTextMsgBody.setMessage(hello);//设置接收到的信息
//
        mMessgae.setBody(mTextMsgBody);
        //开始发送
        mAdapter.addData( mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);//更新数据
    }



    //图片消息
//    private void sendImageMessage(final LocalMedia media) {
//        final Message mMessgae=getBaseSendMessage(MsgType.IMAGE);
//        ImageMsgBody mImageMsgBody=new ImageMsgBody();
//        mImageMsgBody.setThumbUrl(media.getCompressPath());
//        mMessgae.setBody(mImageMsgBody);
//        //开始发送
//        mAdapter.addData( mMessgae);
//        //模拟两秒后发送成功
//        updateMsg(mMessgae);
//    }


    //视频消息
//    private void sendVedioMessage(final LocalMedia media) {
//        final Message mMessgae=getBaseSendMessage(MsgType.VIDEO);
//        //生成缩略图路径
//        String vedioPath=media.getPath();
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(vedioPath);
//        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
//        String imgname = System.currentTimeMillis() + ".jpg";
//        String urlpath = Environment.getExternalStorageDirectory() + "/" + imgname;
//        File f = new File(urlpath);
//        try {
//            if (f.exists()) {
//                f.delete();
//            }
//            FileOutputStream out = new FileOutputStream(f);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.flush();
//            out.close();
//        }catch ( Exception e) {
//            e.printStackTrace();
//        }
//        VideoMsgBody mImageMsgBody=new VideoMsgBody();
//        mImageMsgBody.setExtra(urlpath);
//        mMessgae.setBody(mImageMsgBody);
//        //开始发送
//        mAdapter.addData( mMessgae);
//        //模拟两秒后发送成功
//        updateMsg(mMessgae);
//
//    }

    //文件消息
//    private void sendFileMessage(String from, String to, final String path) {
//        final Message mMessgae=getBaseSendMessage(MsgType.FILE);
//        FileMsgBody mFileMsgBody=new FileMsgBody();
//        mFileMsgBody.setLocalPath(path);
//        mFileMsgBody.setDisplayName(FileUtils.getFileName(path));
//        mFileMsgBody.setSize(FileUtils.getFileLength(path));
//        mMessgae.setBody(mFileMsgBody);
//        //开始发送
//        mAdapter.addData( mMessgae);
//        //模拟两秒后发送成功
//        updateMsg(mMessgae);
//
//    }

    //语音消息
//    private void sendAudioMessage(  final String path,int time) {
//        final Message mMessgae=getBaseSendMessage(MsgType.AUDIO);
//        AudioMsgBody mFileMsgBody=new AudioMsgBody();
//        mFileMsgBody.setLocalPath(path);
//        mFileMsgBody.setDuration(time);
//        mMessgae.setBody(mFileMsgBody);
//        //开始发送
//        mAdapter.addData( mMessgae);
//        //模拟两秒后发送成功
//        updateMsg(mMessgae);
//    }


    private Message getBaseSendMessage(MsgType msgType){
        Message mMessgae=new Message();//new 一个public  class Message类
        mMessgae.setUuid(UUID.randomUUID()+"");
        mMessgae.setSenderId(mSenderId);//设置在右
        mMessgae.setTargetId(mTargetId);//设置在左
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


//    private Message getBaseReceiveMessage(MsgType msgType){
//        Message mMessgae=new Message();
//        mMessgae.setUuid(UUID.randomUUID()+"");
//        mMessgae.setSenderId(mTargetId);
//        mMessgae.setTargetId(mSenderId);
//        mMessgae.setSentTime(System.currentTimeMillis());
//        mMessgae.setSentStatus(MsgSendStatus.SENDING);//设置发送中
//        mMessgae.setMsgType(msgType);
//        return mMessgae;//返回到 mMessgae 中
//    }


    private void updateMsg(final Message mMessgae) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
         //模拟0.5秒后发送成功
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int position=0;
                mMessgae.setSentStatus(MsgSendStatus.SENT);
                //更新单个子条目
                for (int i=0;i<mAdapter.getData().size();i++){
                    Message mAdapterMessage=mAdapter.getData().get(i);
                    if (mMessgae.getUuid().equals(mAdapterMessage.getUuid())){
                        position=i;
                    }
                }
                mAdapter.notifyItemChanged(position);
            }
        }, 500);

    }



}
