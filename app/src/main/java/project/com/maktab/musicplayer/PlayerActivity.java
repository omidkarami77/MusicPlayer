package project.com.maktab.musicplayer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Random;

import project.com.maktab.musicplayer.model.Song;
import project.com.maktab.musicplayer.model.SongLab;

public class PlayerActivity extends AppCompatActivity implements PlayerFragment.CallBacks {
    private static final String ID_EXTRA = "id_extra_song";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ViewPagerAdapter mAdapter;
    private List<Song> mSongList;
    public static boolean mShuffle;
    public static boolean mRepeateAll;


    public static Intent newIntent(Context context, Long songId) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(ID_EXTRA, songId);
        return intent;
    }

    public int getSongIndex(Long id) {
        int index = -1;

        for (int i = 0; i < mSongList.size(); i++) {

            if (mSongList.get(i).getId().equals(id)) {
                index = i;
                break;

            }
        }
        return index;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);



        mViewPager = findViewById(R.id.player_view_pager);
        mTabLayout = findViewById(R.id.player_tab_layout);
        mSongList = SongLab.getInstance().getSongList();

        Long id = getIntent().getLongExtra(ID_EXTRA, 0);

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(getSongIndex(id));


        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment.newInstance(id))
                .commit();*/


    }


    @Override
    public void nextSong() {
        if (!mShuffle) {
            int current = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(current + 1);
        } else
            mViewPager.setCurrentItem(randomGenerator());
    }

    @Override
    public void previousSong() {
        if (!mShuffle) {
            int current = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(current - 1);
        } else
            mViewPager.setCurrentItem(randomGenerator());
    }

    @Override
    public void repeateList() {
        mViewPager.setCurrentItem(0);
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {
            return PlayerFragment.newInstance(mSongList.get(i).getId());
        }

        @Override
        public int getCount() {
            return mSongList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mSongList.get(position).getTitle();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    private int randomGenerator() {
        Random random = new Random();
        int low = 0;
        int high = mSongList.size();
        int result = random.nextInt(high - low) + low;
        return result;

    }


}
