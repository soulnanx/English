package com.example.renan.english.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.renan.english.ui.fragment.MajorityNoteFragment;
import com.example.renan.english.ui.fragment.MinorNoteFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vagnnermartins on 12/11/14.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int TOTAL_FRAGMENT = 2;
    private final Context context;
    public Map<Integer, Fragment> mapFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        mapFragment = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Integer indexFragment = null;
        switch (position) {
            case MajorityNoteFragment.POSITION:
                fragment = Fragment.instantiate(context, MajorityNoteFragment.class.getName());
                indexFragment = MajorityNoteFragment.POSITION;
                break;

            case MinorNoteFragment.POSITION:
                fragment = Fragment.instantiate(context, MinorNoteFragment.class.getName());
                indexFragment = MinorNoteFragment.POSITION;
                break;
        }
        mapFragment.put(indexFragment, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case MajorityNoteFragment.POSITION:
                title = MajorityNoteFragment.NAME_TAB.toUpperCase();
                break;

            case MinorNoteFragment.POSITION:
                title = MinorNoteFragment.NAME_TAB.toUpperCase();
                break;
        }
        return title;
    }
}
