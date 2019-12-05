package project.com.maktab.musicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import project.com.maktab.musicplayer.model.SongLab;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ViewPagerAdapter mViewPagerAdapter;
    private Toolbar mToolbar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_coordinator);
        checkPermission();
//
//        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//
//        }



        mViewPager = findViewById(R.id.main_view_pager);
        mTabLayout = findViewById(R.id.main_tab_layout);

        setSupportActionBar(mToolbar);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        SongLab.getInstance().init(this);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return SongsRecyclerFragment.newInstance("all", 0l);
                case 1:
                    return AlbumRecyclerFragment.newInstance();
                case 2:
                    return ArtistRecyclerFragment.newInstance();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.songs);
                case 1:
                    return getString(R.string.album);
                case 2:
                    return getString(R.string.artists);
                default:
                    return super.getPageTitle(position);
            }
        }
    }
    private boolean checkPermission (){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 1);
            return false;
        }else return true;
    }




}
