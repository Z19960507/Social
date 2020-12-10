package com.example.socialsoftware.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.socialsoftware.R;
import com.example.socialsoftware.activity.LoginActivity;
import com.example.socialsoftware.activity.MineRegisterPasswordActivity;
import com.example.socialsoftware.arouter.MyRoute;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;
import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxui.view.RxTitle;
import com.vondear.rxui.view.dialog.RxDialogChooseImage;
import com.vondear.rxui.view.dialog.RxDialogScaleView;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.vondear.rxui.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Route(path = MyRoute.MINE_FRAGMENT)
public class MineFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Uri resultUri;
    private RxTitle mRxTitle;
    private TextView mTvBg;
    private ImageView mIvAvatar;
    private LinearLayout mLlAnchorLeft;
    private RelativeLayout mRlAvatar;
    private TextView mTvName;
    private TextView mTvIdName;
    private TextView mTvConstellation;
    private TextView mTvBirthday;
    private TextView mTvAddress;
    private TextView mTvLables;
    private TextView mTextView2;
    private TextView mEditText2;
    private Button mBtnExit;
    private LinearLayout mActivityUser;
    private Activity mContext = null;
    private SharedPreferences sharedPreferences;
    private LinearLayout llMineInf;
    private LinearLayout llUpdatePassword;
    private String username;
    private String name;
    private Button mSave;
    private Button save;
    private DBOpenHelper dbOpenHelper;
    private TextView tvChatName;
    private EditText edChatName;
    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initUI(View view){
        mRxTitle = view.findViewById(R.id.rx_title);
        mTvBg = view.findViewById(R.id.tv_bg);
        mIvAvatar = view.findViewById(R.id.iv_avatar);
        mLlAnchorLeft = view.findViewById(R.id.ll_anchor_left);
        mRlAvatar = view.findViewById(R.id.rl_avatar);
        mTvName = view.findViewById(R.id.tv_name);
        mTvIdName = view.findViewById(R.id.tv_id_name);
        mTvConstellation = view.findViewById(R.id.tv_constellation);
        mTvBirthday = view.findViewById(R.id.tv_birthday);
        mTvAddress = view.findViewById(R.id.tv_address);
        mTvLables = view.findViewById(R.id.tv_lables);
        mTextView2 = view.findViewById(R.id.textView2);
        mEditText2 = view.findViewById(R.id.editText2);
        mBtnExit = view.findViewById(R.id.btn_exit);
        mActivityUser = view.findViewById(R.id.activity_user);
        mSave = view.findViewById(R.id.bt_revise_name);
        save = view.findViewById(R.id.bt_save_name);
        tvChatName = view.findViewById(R.id.tv_id);
        edChatName = view.findViewById(R.id.et_what_name);
        llMineInf = view.findViewById(R.id.ll_mine_inf);
        llUpdatePassword = view.findViewById(R.id.ll_update_password);

        llMineInf.setOnClickListener(this);
        llUpdatePassword.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);

        Bundle bundle = getArguments();

        username = bundle.getString("username");
        mTvName.setText(username);
        dbOpenHelper = new DBOpenHelper(mContext);
        sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        User user1 = dbOpenHelper.getUserByName(name);
        if (user1 != null) {
            mTvIdName.setText(user1.getReal_name());
        }

    }
    protected void initView() {
        Resources r = mContext.getResources();
        resultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.circle_elves_ball) + "/"
                + r.getResourceTypeName(R.drawable.circle_elves_ball) + "/"
                + r.getResourceEntryName(R.drawable.circle_elves_ball));

        mRxTitle.setLeftFinish(getActivity());

        // 这里才是正在的点击事件
        // 从这个点击开始往里找
        // 頭像點擊事件 在這裡
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 往裡走
                initDialogChooseImage();
            }
        });


        // 这里是  onLong  长按
        mIvAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                RxImageTool.showBigImageView(mContext, resultUri);
                RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(mContext);
                rxDialogScaleView.setImage(resultUri);
                rxDialogScaleView.show();
                return false;
            }
        });


    }




    private void initDialogChooseImage() {
        // 发现这里 new 了一个 Dialog  Dialog不知道啥意思就百度 对话，其实就是弹窗的意思
        // 继续往里走
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this, TITLE);

        dialogChooseImage.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(mi.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.circle_elves_ball)
                        //异常占位图(当加载异常的时候出现的图片)
                        .error(R.drawable.circle_elves_ball)
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(this).
                        load(RxPhotoTool.cropImageUri).
                        apply(options).
                        thumbnail(0.5f).
                        into(mIvAvatar);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, mIvAvatar);
                    RxSPTool.putContent(mContext, "AVATAR", resultUri.toString());
                    RxSPTool.putContent(getContext(), "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.circle_elves_ball)
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.drawable.circle_elves_ball)
                .transform(new CircleCrop())
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(this).
                load(uri).
                apply(options).
                thumbnail(0.5f).
                into(imageView);

        return (new File(RxPhotoTool.getImageAbsolutePath(getContext(), uri)));
    }

    private void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(mContext, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(mContext, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
//                .start(this)
                .start(getContext(), this)
        ;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_revise_name:
                showEditHideText();
                return;
            case R.id.bt_save_name:
                showTextHideEdit();
                return;
            case R.id.ll_mine_inf:
                ARouter.getInstance()
                        .build(MyRoute.MINE_INF_ACTIVITY)
                        .navigation(getContext());
                break;

            case R.id.ll_update_password:
                Intent intent = new Intent(mContext, MineRegisterPasswordActivity.class);
                intent.putExtra("username",username);

                getActivity().startActivity(intent);
                break;
            case R.id.btn_exit:
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(getContext());
                // qu xiao
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });

                // que ding
                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getActivity().finish();
                        //步骤1：创建一个SharedPreferences对象
                        SharedPreferences sharedPreferences= mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
                        //步骤2： 实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //步骤3：将获取过来的值放入文件
                        editor.putString("name","");


                        //步骤4：提交
                        editor.apply();

                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        mContext.finish();

                    }
                });
                rxDialogSureCancel.show();
        }
    }
    private void showEditHideText() {
        mSave.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);

        tvChatName.setVisibility(View.GONE);
        edChatName.setVisibility(View.VISIBLE);

    }
        private void showTextHideEdit() {
        mSave.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);

        tvChatName.setVisibility(View.VISIBLE);
        edChatName.setVisibility(View.GONE);
    }
}