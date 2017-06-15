package com.alokomkar.rxmoviedb.trailers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Alok on 15/06/17.
 */

public class TrailerViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<TrailerFragment> trailerFragments;

    public TrailerViewPagerAdapter(FragmentManager fm, List<TrailerFragment> trailerFragments) {
        super(fm);
        this.trailerFragments = trailerFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return trailerFragments.get(position);
    }

    @Override
    public int getCount() {
        return trailerFragments.size();
    }
}
