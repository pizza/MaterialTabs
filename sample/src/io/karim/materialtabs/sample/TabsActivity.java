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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;
import io.karim.Utils;

public class TabsActivity extends AppCompatActivity {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                // Replace text with generated code ;-)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void applyParametersFromIntentExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mMaterialTabs.setIndicatorColor(getResources().getColor(extras.getInt(TabsSettingsFragment.INDICATOR_COLOR)));
                mMaterialTabs.setUnderlineColor(getResources().getColor(extras.getInt(TabsSettingsFragment.UNDERLINE_COLOR)));

                mMaterialTabs.setIndicatorHeight(Utils.dpToPx(getResources(), extras.getInt(TabsSettingsFragment.INDICATOR_HEIGHT)));
                mMaterialTabs.setUnderlineHeight(Utils.dpToPx(getResources(), extras.getInt(TabsSettingsFragment.UNDERLINE_HEIGHT)));

                mMaterialTabs.setTabPaddingLeftRight(Utils.dpToPx(getResources(), extras.getInt(TabsSettingsFragment.TAB_PADDING)));
                mMaterialTabs.setScrollOffset(Utils.dpToPx(getResources(), extras.getInt(TabsSettingsFragment.SCROLL_OFFSET)));

                mMaterialTabs.setPaddingMiddle(extras.getBoolean(TabsSettingsFragment.PADDING_MIDDLE));
                mMaterialTabs.setShouldExpand(extras.getBoolean(TabsSettingsFragment.SHOULD_EXPAND));
                mMaterialTabs.setAllCaps(extras.getBoolean(TabsSettingsFragment.TEXT_ALL_CAPS));

                int toolbarColor = getResources().getColor(extras.getInt(TabsSettingsFragment.TOOLBAR_BACKGROUND));
                mToolbar.setBackgroundColor(toolbarColor);
                mMaterialTabs.setBackgroundColor(getResources().getColor(extras.getInt(TabsSettingsFragment.TAB_BACKGROUND)));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.argb(Color.alpha(toolbarColor), Color.red(toolbarColor) / 2, Color.green(toolbarColor) / 2,
                            Color.blue(toolbarColor) / 2));
                }

                mMaterialTabs.setTextColorSelected(getResources().getColor(extras.getInt(TabsSettingsFragment.TEXT_COLOR_SELECTED)));
                mMaterialTabs.setTextColorUnselected(getResources().getColor(extras.getInt(TabsSettingsFragment.TEXT_COLOR_UNSELECTED)));

                mMaterialTabs.setTabTypefaceSelectedStyle(extras.getInt(TabsSettingsFragment.TEXT_STYLE_SELECTED));
                mMaterialTabs.setTabTypefaceUnselectedStyle(extras.getInt(TabsSettingsFragment.TEXT_STYLE_UNSELECTED));

                mMaterialTabs.setRippleDuration(extras.getInt(RippleSettingsFragment.RIPPLE_DURATION));
                mMaterialTabs.setRippleAlphaFloat(extras.getFloat(RippleSettingsFragment.RIPPLE_ALPHA_FLOAT));
                mMaterialTabs.setRippleColor(getResources().getColor(extras.getInt(RippleSettingsFragment.RIPPLE_COLOR)));
                mMaterialTabs.setRippleDelayClick(extras.getBoolean(RippleSettingsFragment.RIPPLE_DELAY_CLICK));
                mMaterialTabs.setRippleDiameterDp(extras.getFloat(RippleSettingsFragment.RIPPLE_DIAMETER));
                mMaterialTabs.setRippleFadeDuration(extras.getInt(RippleSettingsFragment.RIPPLE_FADE_DURATION));
                mMaterialTabs.setRippleHighlightColor(getResources().getColor(extras.getInt(RippleSettingsFragment.RIPPLE_HIGHLIGHT_COLOR)));
                mMaterialTabs.setRippleInAdapter(false);
                mMaterialTabs.setRippleOverlay(extras.getBoolean(RippleSettingsFragment.RIPPLE_OVERLAY));
                mMaterialTabs.setRipplePersistent(extras.getBoolean(RippleSettingsFragment.RIPPLE_PERSISTENT));
                mMaterialTabs.setRippleRoundedCornersDp(extras.getInt(RippleSettingsFragment.RIPPLE_ROUNDED_CORNERS_RADIUS));
            }
        }
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
