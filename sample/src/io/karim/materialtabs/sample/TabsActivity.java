/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;
import io.karim.Utils;

public class TabsActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        SamplePagerAdapter adapter = new SamplePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mMaterialTabs.setViewPager(mViewPager);

        applyParametersFromIntentExtras();

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setCurrentItem(0);

        mMaterialTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(TabsActivity.this, "Selected tab #" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void applyParametersFromIntentExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mMaterialTabs.setIndicatorColor(getResources().getColor(extras.getInt(MainActivity.INDICATOR_COLOR)));
                mMaterialTabs.setUnderlineColor(getResources().getColor(extras.getInt(MainActivity.UNDERLINE_COLOR)));
                mMaterialTabs.setDividerColor(getResources().getColor(extras.getInt(MainActivity.DIVIDER_COLOR)));

                mMaterialTabs.setDividerWidth(Utils.dpToPx(getResources(), extras.getInt(MainActivity.DIVIDER_WIDTH)));
                mMaterialTabs.setIndicatorHeight(Utils.dpToPx(getResources(), extras.getInt(MainActivity.INDICATOR_HEIGHT)));
                mMaterialTabs.setUnderlineHeight(Utils.dpToPx(getResources(), extras.getInt(MainActivity.UNDERLINE_HEIGHT)));

                mMaterialTabs.setDividerPadding(Utils.dpToPx(getResources(), extras.getInt(MainActivity.DIVIDER_PADDING)));
                mMaterialTabs.setTabPaddingLeftRight(Utils.dpToPx(getResources(), extras.getInt(MainActivity.TAB_PADDING)));
                mMaterialTabs.setScrollOffset(Utils.dpToPx(getResources(), extras.getInt(MainActivity.SCROLL_OFFSET)));
            }
        }

        // TODO: complete these and add all setters.
        // TODO: pass these setters parameters that the user can set manually in MainActivity, for an awesome sample :-D
        mMaterialTabs.setRippleDuration(1000);
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Categories", "Home", "Top Paid"};
        //, "Top Free", "Top Grossing", "Top New Paid", "Top New Free", "Trending"};

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SampleFragment.newInstance(position);
        }
    }
}
