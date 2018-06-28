package com.cheanda.a8805.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cheanda.a8805.fragment.AttributeFragment;
import com.cheanda.a8805.fragment.ChengeOilFragment;
import com.cheanda.a8805.fragment.PriceFragment;


/**
 * Created by dell on 2017/3/8.
 */
public class ResultPagerAdapter extends FragmentPagerAdapter {

    private String[] mList;
    public ResultPagerAdapter(FragmentManager fm, String[] mList){
        super(fm);
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.length;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AttributeFragment videofragment = new AttributeFragment();//(AttributeFragment) FragmentFactory.createFragment(0);
                return videofragment;
            case 1:
                ChengeOilFragment collifragment = new ChengeOilFragment();//(ChengeOilFragment) FragmentFactory.createFragment(1);
                return collifragment;
            case 2:
                PriceFragment hotfragment = new PriceFragment();//(PriceFragment) FragmentFactory.createFragment(2);
                return hotfragment;

        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mList[position];
    }

}
