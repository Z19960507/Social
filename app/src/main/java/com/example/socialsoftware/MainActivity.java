package com.example.socialsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.socialsoftware.arouter.MyRoute;
import com.example.socialsoftware.databinding.ActivityMain3Binding;
import com.example.socialsoftware.fragment.ContactFragment;
import com.example.socialsoftware.fragment.FindFragment;
import com.example.socialsoftware.fragment.MineFragment;
import com.example.socialsoftware.fragment.WeChatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vondear.rxtool.RxTool;

import java.util.ArrayList;
import java.util.List;


@Route(path = MyRoute.MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMain3Binding bind;
    private VpAdapter adapter;
    private String username;
    // collections
    private List<androidx.fragment.app.Fragment> fragments;// used for ViewPager adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxTool.init(this);
//        setContentView(R.layout.activity_with_view_pager);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main3);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        initData();
        initView();
        initEvent();
    }


    /**
     * create fragments
     */
    private void initData() {
        fragments = new ArrayList<>(4);

        Bundle bundle = new Bundle();
        // create music fragment and add it
//        BaseFragment musicFragment = new BaseFragment();
//        bundle.putString("title", getString(R.string.music));
//        musicFragment.setArguments(bundle);

        // create backup fragment and add it
//        BaseFragment backupFragment = new BaseFragment();
//        bundle = new Bundle();
//        bundle.putString("title", getString(R.string.backup));
//        backupFragment.setArguments(bundle);

        // create friends fragment and add it
//        BaseFragment favorFragment = new BaseFragment();
//        bundle = new Bundle();
//        bundle.putString("title", getString(R.string.favor));
//        favorFragment.setArguments(bundle);

//        // create friends fragment and add it
//        BaseFragment visibilityFragment = new BaseFragment();
//        bundle = new Bundle();
//        bundle.putString("title", getString(R.string.visibility));
//        visibilityFragment.setArguments(bundle);
        ContactFragment contactFragment = new ContactFragment();

        MineFragment mineFragment = new MineFragment();

        WeChatFragment weChatFragment = new WeChatFragment();

        FindFragment findFragment = new FindFragment();



       // add to fragments for adapter
//        fragments.add(musicFragment);
        fragments.add(weChatFragment);
        fragments.add(contactFragment);
        fragments.add(findFragment);

        Bundle bundle1 = new Bundle();
        bundle1.putString("username",username);
        mineFragment.setArguments(bundle1);
        fragments.add(mineFragment);

    }


    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
//        bind.bnve.inflateMenu(R.menu.menu_navigation_center_fab);
        bind.bnve.enableItemShiftingMode(false);
        bind.bnve.enableShiftingMode(false);
        bind.bnve.enableAnimation(false);

        // set adapter
        //页面给到适配器，把适配器设置到viewpager
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bind.vp.setAdapter(adapter);
    }

    /**
     * set listeners
     */
    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        bind.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //原先的previous  位置Position
            private int previousPosition = -1;

            //被点击Selected   导航的项NavigationItem   当on
            @Override
            public boolean onNavigationItemSelected(@NonNull/* Menu的项 */ MenuItem item) {

                // 位置Position
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_visibility:
                        position = 0;
                        break;
                    case R.id.i_music:
                        position = 1;
                        break;
                    case R.id.i_backup:
                        position = 2;
                        break;
                    case R.id.i_favor:
                        position = 3;
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if(previousPosition != position) {
                    // ViewPager 切换 页面
                    //显示第 position 个页面
                    bind.vp.setCurrentItem(position, false);
                    previousPosition = position;
//                    Log.i(TAG, "-----bnve-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                }

                return true;
            }
        });

        // set listener to change the current checked item of bottom nav when scroll view pager
        bind.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.i(TAG, "-----ViewPager-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                if (position >= 2)// 2 is center
                    position++;// if page is 2, need set bottom item to 3, and the same to 3 -> 4
                bind.bnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // center item click listener
        bind.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {

        private List<androidx.fragment.app.Fragment> data;

        //适配器固定写法
        public VpAdapter(FragmentManager fm, List<androidx.fragment.app.Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

}


