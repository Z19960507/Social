package com.vondear.rxui.view.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.R;


/**
 * @author vondear
 * @date 2017/3/20
 * 封装了从相册/相机 获取 图片 的Dialog.
 */
public class RxDialogChooseImage extends RxDialog {

    private LayoutType mLayoutType = LayoutType.TITLE;
    // 其实这个不用管，我们只知道他的id = mTvCamera 就行了
    public TextView mTvCamera;
    private TextView mTvFile;
    private TextView mTvCancel;

    public RxDialogChooseImage(Activity context) {
        super(context);
        initView(context);
    }

    public RxDialogChooseImage(Fragment fragment) {
        super(fragment.getContext());
        initView(fragment);
    }

    public RxDialogChooseImage(Activity context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, int themeResId) {
        super(fragment.getContext(), themeResId);
        initView(fragment);
    }

    public RxDialogChooseImage(Activity context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, float alpha, int gravity) {
        super(fragment.getContext(), alpha, gravity);
        initView(fragment);
    }

    // 发现这是个构造方法，里面最关键的就是  initView()
    //继续往里走
    public RxDialogChooseImage(Fragment fragment, LayoutType layoutType) {
        super(fragment.getContext());
        mLayoutType = layoutType;
        initView(fragment);
    }


    public RxDialogChooseImage(Activity context, LayoutType layoutType) {
        super(context);
        mLayoutType = layoutType;
        initView(context);
    }

    public RxDialogChooseImage(Activity context, int themeResId, LayoutType layoutType) {
        super(context, themeResId);
        mLayoutType = layoutType;
        initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, int themeResId, LayoutType layoutType) {
        super(fragment.getContext(), themeResId);
        mLayoutType = layoutType;
        initView(fragment);
    }

    public RxDialogChooseImage(Activity context, float alpha, int gravity, LayoutType layoutType) {
        super(context, alpha, gravity);
        mLayoutType = layoutType;
        initView(context);
    }

    public RxDialogChooseImage(Fragment fragment, float alpha, int gravity, LayoutType layoutType) {
        super(fragment.getContext(), alpha, gravity);
        mLayoutType = layoutType;
        initView(fragment);
    }

    public TextView getFromCameraView() {
        return mTvCamera;
    }

    public TextView getFromFileView() {
        return mTvFile;
    }

    public TextView getCancelView() {
        return mTvCancel;
    }

    public LayoutType getLayoutType() {
        return mLayoutType;
    }

    private void initView(final Activity activity) {
        View dialogView = null;
        switch (mLayoutType) {
            case TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_picker_pictrue, null);
                break;
            case NO_TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camero_show, null);
                break;
            default:
                break;
        }


        mTvCamera = dialogView.findViewById(R.id.tv_camera);
        mTvFile = dialogView.findViewById(R.id.tv_file);
        mTvCancel = dialogView.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancel();
            }
        });

        // 这个里面有点击事件
        mTvCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 原先这里只有这一句代码，没有动态申请权限
                //  RxPhotoTool.openCameraImage(activity);

                // 我们要做的就是动态申请权限
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 进入这儿表示没有权限
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                        // 提示已经禁止
                        RxToast.info("没有授予访问照相机权限，无法调起系统照相机");
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 100);
                    }
                } else {
                    RxPhotoTool.openCameraImage(activity);
                    cancel();
                }
            }
        });
        mTvFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                RxPhotoTool.openLocalImage(activity);
                cancel();
            }
        });
        setContentView(dialogView);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }


    // 发现这里有两个布局文件
    // 挨个看一下，发现是第一个
    // 我们要找的就是  照相机 对应的布局id
    private void initView(final Fragment fragment) {
        View dialogView = null;
        switch (mLayoutType) {
            case TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_picker_pictrue, null);
                break;
            case NO_TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camero_show, null);
                break;
            default:
                break;
        }

        mTvCamera =  dialogView.findViewById(R.id.tv_camera);
        mTvFile =  dialogView.findViewById(R.id.tv_file);
        mTvCancel =  dialogView.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancel();
            }
        });
        // 这个里面有点击事件
        mTvCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 原先这里只有这一句代码，没有动态申请权限
                //  RxPhotoTool.openCameraImage(activity);

                // 我们要做的就是动态申请权限
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 进入这儿表示没有权限
                    if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), Manifest.permission.CAMERA)) {
                        // 提示已经禁止
                        RxToast.info("没有授予访问照相机权限，无法调起系统照相机");
                    } else {
                        ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
                    }
                } else {
                    RxPhotoTool.openCameraImage(fragment);
                    cancel();
                }
            }
        });
        mTvFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                RxPhotoTool.openLocalImage(fragment);
                cancel();
            }
        });

        setContentView(dialogView);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }

    public enum LayoutType {
        TITLE, NO_TITLE
    }
}
