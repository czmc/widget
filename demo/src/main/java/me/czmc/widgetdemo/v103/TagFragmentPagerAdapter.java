package me.czmc.widgetdemo.v103;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TagFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mContent;
        private ArrayList<String> mTitles;
        public TagFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> mContents, ArrayList<String> titles) {
            super(fm);
            this.mContent = mContents;
            this.mTitles=titles;
        }
        @Override
        public int getCount() {
            return mContent.size();
        }
        @Override
        public Fragment getItem(int position) {
            return mContent.get(position);
        }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position % mTitles.size());
    }
}