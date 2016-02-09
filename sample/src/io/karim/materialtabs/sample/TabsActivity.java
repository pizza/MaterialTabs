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
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;
import io.karim.Utils;

public class TabsActivity extends AppCompatActivity {

    private static final String TAG = TabsActivity.class.getSimpleName();

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private String mExportableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        int numberOfTabs = 3;
        if (getIntent() != null && getIntent().getExtras() != null) {
            numberOfTabs = getIntent().getExtras().getInt(TabsSettingsFragment.NUMBER_OF_TABS);
        }

        SamplePagerAdapter adapter = new SamplePagerAdapter(getSupportFragmentManager(), numberOfTabs);
        mViewPager.setAdapter(adapter);

        mMaterialTabs.setViewPager(mViewPager);

        mMaterialTabs.setOnTabSelectedListener(new MaterialTabs.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.i(TAG, "onTabSelected called with position " + position);
            }
        });

        mMaterialTabs.setOnTabReselectedListener(new MaterialTabs.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Log.i(TAG, "onTabReselected called with position " + position);
            }
        });

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
                sendIntent.putExtra(Intent.EXTRA_TEXT, mExportableString);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void applyParametersFromIntentExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Resources resources = getResources();

                int showToolbar = extras.getBoolean(TabsSettingsFragment.SHOW_TOOLBAR) ? View.VISIBLE : View.GONE;
                int indicatorColor = resources.getColor(extras.getInt(TabsSettingsFragment.INDICATOR_COLOR));
                int underlineColor = resources.getColor(extras.getInt(TabsSettingsFragment.UNDERLINE_COLOR));
                int indicatorHeightDp = extras.getInt(TabsSettingsFragment.INDICATOR_HEIGHT);
                int underlineHeightDp = extras.getInt(TabsSettingsFragment.UNDERLINE_HEIGHT);
                int tabPaddingDp = extras.getInt(TabsSettingsFragment.TAB_PADDING);

                mToolbar.setVisibility(showToolbar);

                mMaterialTabs.setIndicatorColor(indicatorColor);
                mMaterialTabs.setUnderlineColor(underlineColor);
                mMaterialTabs.setIndicatorHeight(Utils.dpToPx(resources, indicatorHeightDp));
                mMaterialTabs.setUnderlineHeight(Utils.dpToPx(resources, underlineHeightDp));
                mMaterialTabs.setTabPaddingLeftRight(Utils.dpToPx(resources, tabPaddingDp));

                boolean paddingMiddle = extras.getBoolean(TabsSettingsFragment.PADDING_MIDDLE);
                boolean sameWeightTabs = extras.getBoolean(TabsSettingsFragment.SAME_WEIGHT_TABS);
                boolean textAllCaps = extras.getBoolean(TabsSettingsFragment.TEXT_ALL_CAPS);

                mMaterialTabs.setPaddingMiddle(paddingMiddle);
                mMaterialTabs.setSameWeightTabs(sameWeightTabs);
                mMaterialTabs.setAllCaps(textAllCaps);

                int toolbarColor = resources.getColor(extras.getInt(TabsSettingsFragment.TOOLBAR_BACKGROUND));
                int tabBackgroundColor = resources.getColor(extras.getInt(TabsSettingsFragment.TAB_BACKGROUND));
                mToolbar.setBackgroundColor(toolbarColor);
                mMaterialTabs.setBackgroundColor(tabBackgroundColor);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.argb(Color.alpha(toolbarColor), Color.red(toolbarColor) / 2, Color.green(toolbarColor) / 2,
                            Color.blue(toolbarColor) / 2));
                }

                int textColorSelected = resources.getColor(extras.getInt(TabsSettingsFragment.TEXT_COLOR_SELECTED));
                int textColorUnselected = resources.getColor(extras.getInt(TabsSettingsFragment.TEXT_COLOR_UNSELECTED));
                int tabStyleSelected = extras.getInt(TabsSettingsFragment.TEXT_STYLE_SELECTED);
                int tabStyleUnselected = extras.getInt(TabsSettingsFragment.TEXT_STYLE_UNSELECTED);

                mMaterialTabs.setTextColorSelected(textColorSelected);
                mMaterialTabs.setTextColorUnselected(textColorUnselected);

                mMaterialTabs.setTabTypefaceSelectedStyle(tabStyleSelected);
                mMaterialTabs.setTabTypefaceUnselectedStyle(tabStyleUnselected);

                int rippleDuration = extras.getInt(RippleSettingsFragment.RIPPLE_DURATION);
                float rippleAlphaFloat = extras.getFloat(RippleSettingsFragment.RIPPLE_ALPHA_FLOAT);
                int rippleColor = resources.getColor(extras.getInt(RippleSettingsFragment.RIPPLE_COLOR));
                boolean rippleDelayClick = extras.getBoolean(RippleSettingsFragment.RIPPLE_DELAY_CLICK);
                float rippleDiameterDp = extras.getFloat(RippleSettingsFragment.RIPPLE_DIAMETER);
                int rippleFadeDuration = extras.getInt(RippleSettingsFragment.RIPPLE_FADE_DURATION);
                int rippleHighlightColor = resources.getColor(extras.getInt(RippleSettingsFragment.RIPPLE_HIGHLIGHT_COLOR));
                boolean rippleOverlay = extras.getBoolean(RippleSettingsFragment.RIPPLE_OVERLAY);
                boolean ripplePersistent = extras.getBoolean(RippleSettingsFragment.RIPPLE_PERSISTENT);
                int rippleRoundedCornusRadiusDp = extras.getInt(RippleSettingsFragment.RIPPLE_ROUNDED_CORNERS_RADIUS);

                mMaterialTabs.setRippleDuration(rippleDuration);
                mMaterialTabs.setRippleAlphaFloat(rippleAlphaFloat);
                mMaterialTabs.setRippleColor(rippleColor);
                mMaterialTabs.setRippleDelayClick(rippleDelayClick);
                mMaterialTabs.setRippleDiameterDp(rippleDiameterDp);
                mMaterialTabs.setRippleFadeDuration(rippleFadeDuration);
                mMaterialTabs.setRippleHighlightColor(rippleHighlightColor);
                mMaterialTabs.setRippleInAdapter(false);
                mMaterialTabs.setRippleOverlay(rippleOverlay);
                mMaterialTabs.setRipplePersistent(ripplePersistent);
                mMaterialTabs.setRippleRoundedCornersDp(rippleRoundedCornusRadiusDp);

                mExportableString = createExportableText(showToolbar, indicatorColor, underlineColor, indicatorHeightDp, underlineHeightDp,
                        tabPaddingDp, paddingMiddle, sameWeightTabs, textAllCaps, toolbarColor, tabBackgroundColor, textColorSelected,
                        textColorUnselected, tabStyleSelected, tabStyleUnselected, rippleDuration, rippleAlphaFloat, rippleColor, rippleDelayClick,
                        rippleDiameterDp, rippleFadeDuration, rippleHighlightColor, rippleOverlay, ripplePersistent, rippleRoundedCornusRadiusDp);
            }
        }
    }

    private String createExportableText(int showToolbar, int indicatorColor, int underlineColor, int indicatorHeightDp, int underlineHeightDp,
            int tabPaddingDp, boolean paddingMiddle, boolean sameWeightTabs, boolean textAllCaps, int toolbarColor, int tabBackgroundColor,
            int textColorSelected, int textColorUnselected, int tabStyleSelected, int tabStyleUnselected, int rippleDuration, float rippleAlphaFloat,
            int rippleColor, boolean rippleDelayClick, float rippleDiameterDp, int rippleFadeDuration, int rippleHighlightColor,
            boolean rippleOverlay, boolean ripplePersistent, int rippleRoundedCornusRadiusDp) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<io.karim.MaterialTabs\n")
                     .append("        android:id=\"@+id/material_tabs\"\n")
                     .append("        android:layout_width=\"match_parent\"\n")
                     .append("        android:layout_height=\"48dp\"\n")
                     .append("        android:background=\"?attr/colorPrimary\"\n")
                     .append("        app:mtIndicatorColor=\"")
                     .append("#")
                     .append(Integer.toHexString(indicatorColor))
                     .append("\"\n")
                     .append("        app:mtUnderlineColor=\"")
                     .append("#")
                     .append(Integer.toHexString(underlineColor))
                     .append("\"\n")
                     .append("        app:mtUnderlineHeight=\"")
                     .append(underlineHeightDp)
                     .append("dp\"\n")
                     .append("        app:mtIndicatorHeight=\"")
                     .append(indicatorHeightDp)
                     .append("dp\"\n")
                     .append("        app:mtTabPaddingLeftRight=\"")
                     .append(tabPaddingDp)
                     .append("dp\"\n")
                     .append("        app:mtSameWeightTabs=\"")
                     .append(sameWeightTabs)
                     .append("\"\n")
                     .append("        app:mtTextAllCaps=\"")
                     .append(textAllCaps)
                     .append("\"\n")
                     .append("        app:mtPaddingMiddle=\"")
                     .append(paddingMiddle)
                     .append("\"\n")
                     .append("        app:mtTextColorSelected=\"")
                     .append("#")
                     .append(Integer.toHexString(textColorSelected))
                     .append("\"\n")
                     .append("        android:textColor=\"")
                     .append("#")
                     .append(Integer.toHexString(textColorUnselected))
                     .append("\"\n")
                     .append("        app:mtTextUnselectedStyle=\"")
                     .append(getStyleFromStyleInt(tabStyleUnselected))
                     .append("\"\n")
                     .append("        app:mtTextSelectedStyle=\"")
                     .append(getStyleFromStyleInt(tabStyleSelected))
                     .append("\"\n")
                     .append("        app:mtMrlRippleColor=\"")
                     .append("#")
                     .append(Integer.toHexString(rippleColor))
                     .append("\"\n")
                     .append("        app:mtMrlRippleHighlightColor=\"")
                     .append("#")
                     .append(Integer.toHexString(rippleHighlightColor))
                     .append("\"\n")
                     .append("        app:mtMrlRippleDiameter=\"")
                     .append(rippleDiameterDp)
                     .append("dp\"\n")
                     .append("        app:mtMrlRippleOverlay=\"")
                     .append(rippleOverlay)
                     .append("\"\n")
                     .append("        app:mtMrlRippleAlpha=\"")
                     .append(rippleAlphaFloat)
                     .append("\"\n")
                     .append("        app:mtMrlRippleDuration=\"")
                     .append(rippleDuration)
                     .append("\"\n")
                     .append("        app:mtMrlRippleFadeDuration=\"")
                     .append(rippleFadeDuration)
                     .append("\"\n")
                     .append("        app:mtMrlRippleDelayClick=\"")
                     .append(rippleDelayClick)
                     .append("\"\n")
                     .append("        app:mtMrlRipplePersistent=\"")
                     .append(ripplePersistent)
                     .append("\"\n")
                     .append("        app:mtMrlRippleInAdapter=\"")
                     .append("false")
                     .append("\"\n")
                     .append("        app:mtMrlRippleRoundedCorners=\"")
                     .append(rippleRoundedCornusRadiusDp)
                     .append("dp\"\n/>");
        return stringBuilder.toString();
    }

    private String getStyleFromStyleInt(int styleInt) {
        switch (styleInt) {
            case Typeface.BOLD:
            default:
                return "bold";
            case Typeface.ITALIC:
                return "italic";
            case Typeface.NORMAL:
                return "normal";
        }
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Item One", "Item Two", "Item Three", "Item Four", "Item Five", "Item Six", "Item Seven", "Item Eight",
                "Item Nine", "Item Ten", "Item Eleven"};

        private final ArrayList<String> mTitles;

        public SamplePagerAdapter(FragmentManager fm, int numberOfTabs) {
            super(fm);
            mTitles = new ArrayList<>();
            for (int i = 0; i < numberOfTabs; i++) {
                mTitles.add(TITLES[i]);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            return SampleFragment.newInstance(position);
        }
    }
}
