package com.example.socialsoftware.fragment.chaui.util;

import android.net.Uri;
/*
不在使用
 */
public interface IAudioPlayListener {
    void onStart(Uri var1);

    void onStop(Uri var1);

    void onComplete(Uri var1);
}