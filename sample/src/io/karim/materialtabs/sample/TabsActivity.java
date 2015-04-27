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
                mMaterialTabs.setIndicatorColor(getResources().getColor(extras.getInt(BasicSettingsFragment.INDICATOR_COLOR)));
                mMaterialTabs.setUnderlineColor(getResources().getColor(extras.getInt(BasicSettingsFragment.UNDERLINE_COLOR)));

                mMaterialTabs.setIndicatorHeight(Utils.dpToPx(getResources(), extras.getInt(BasicSettingsFragment.INDICATOR_HEIGHT)));
                mMaterialTabs.setUnderlineHeight(Utils.dpToPx(getResources(), extras.getInt(BasicSettingsFragment.UNDERLINE_HEIGHT)));

                mMaterialTabs.setTabPaddingLeftRight(Utils.dpToPx(getResources(), extras.getInt(BasicSettingsFragment.TAB_PADDING)));
                mMaterialTabs.setScrollOffset(Utils.dpToPx(getResources(), extras.getInt(BasicSettingsFragment.SCROLL_OFFSET)));

                mMaterialTabs.setPaddingMiddle(extras.getBoolean(BasicSettingsFragment.PADDING_MIDDLE));
                mMaterialTabs.setShouldExpand(extras.getBoolean(BasicSettingsFragment.SHOULD_EXPAND));
                mMaterialTabs.setAllCaps(extras.getBoolean(BasicSettingsFragment.TEXT_ALL_CAPS));

                mMaterialTabs.setBackgroundColor(getResources().getColor(extras.getInt(BasicSettingsFragment.TAB_BACKGROUND)));
                mMaterialTabs.setTextColorSelected(getResources().getColor(extras.getInt(BasicSettingsFragment.TEXT_COLOR_SELECTED)));
                mMaterialTabs.setTextColorUnselected(getResources().getColor(extras.getInt(BasicSettingsFragment.TEXT_COLOR_UNSELECTED)));

                mMaterialTabs.setTabTypefaceSelectedStyle(extras.getInt(BasicSettingsFragment.TEXT_STYLE_SELECTED));
                mMaterialTabs.setTabTypefaceUnselectedStyle(extras.getInt(BasicSettingsFragment.TEXT_STYLE_UNSELECTED));

                mMaterialTabs.setRippleDuration(extras.getInt(RippleSettingsFragment.RIPPLE_DURATION));
                mMaterialTabs.setRippleAlphaFloat(extras.getFloat(RippleSettingsFragment.RIPPLE_ALPHA_FLOAT));
                mMaterialTabs.setRippleColor(extras.getInt(RippleSettingsFragment.RIPPLE_COLOR));
                mMaterialTabs.setRippleDelayClick(extras.getBoolean(RippleSettingsFragment.RIPPLE_DELAY_CLICK));
                mMaterialTabs.setRippleDiameter(Utils.dpToPx(getResources(), extras.getInt(RippleSettingsFragment.RIPPLE_DIAMETER)));
                mMaterialTabs.setRippleFadeDuration(extras.getInt(RippleSettingsFragment.RIPPLE_FADE_DURATION));
                mMaterialTabs.setRippleHighlightColor(extras.getInt(RippleSettingsFragment.RIPPLE_HIGHLIGHT_COLOR));
                mMaterialTabs.setRippleHover(extras.getBoolean(RippleSettingsFragment.RIPPLE_HOVER));
                mMaterialTabs.setRippleInAdapter(false);
                mMaterialTabs.setRippleOverlay(extras.getBoolean(RippleSettingsFragment.RIPPLE_OVERLAY));
                mMaterialTabs.setRipplePersistent(extras.getBoolean(RippleSettingsFragment.RIPPLE_PERSISTENT));
                mMaterialTabs.setRippleRoundedCorners(extras.getFloat(RippleSettingsFragment.RIPPLE_ROUNDED_CORNERS_RADIUS));
            }
        }

        // TODO: complete these and add all setters.
        // TODO: pass these setters parameters that the user can set manually in MainActivity, for an awesome sample :-D
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
