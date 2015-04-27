package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private final BasicSettingsFragment basicSettingsFragment = new BasicSettingsFragment();
    private final RippleSettingsFragment rippleSettingsFragment = new RippleSettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mMaterialTabs.setViewPager(mViewPager);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_me:
                return true;
            case R.id.action_go:
                goToTabsActivityButtonClicked();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void goToTabsActivityButtonClicked() {
        Intent intent = new Intent(this, TabsActivity.class);

        // Indicator Color`
        String key = BasicSettingsFragment.INDICATOR_COLOR;
        switch (basicSettingsFragment.indicatorColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.indicatorColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.indicatorColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.indicatorColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.indicatorColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.indicatorColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.indicatorColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.indicatorColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Underline Color
        key = BasicSettingsFragment.UNDERLINE_COLOR;
        switch (basicSettingsFragment.underlineColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.underlineColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.underlineColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.underlineColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.underlineColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.underlineColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.underlineColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.underlineColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(BasicSettingsFragment.INDICATOR_HEIGHT, basicSettingsFragment.indicatorHeightDp);
        intent.putExtra(BasicSettingsFragment.UNDERLINE_HEIGHT, basicSettingsFragment.underlineHeightDp);

        intent.putExtra(BasicSettingsFragment.TAB_PADDING, basicSettingsFragment.tabPaddingDp);
        intent.putExtra(BasicSettingsFragment.SCROLL_OFFSET, basicSettingsFragment.scrollOffsetDp);

        intent.putExtra(BasicSettingsFragment.SHOULD_EXPAND, basicSettingsFragment.shouldExpandCheckBox.isChecked());
        intent.putExtra(BasicSettingsFragment.TEXT_ALL_CAPS, basicSettingsFragment.textAllCapsCheckBox.isChecked());
        intent.putExtra(BasicSettingsFragment.PADDING_MIDDLE, basicSettingsFragment.paddingMiddleCheckBox.isChecked());

        // Tab Background Color
        key = BasicSettingsFragment.TAB_BACKGROUND;
        switch (basicSettingsFragment.tabBackgroundColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabBackgroundColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabBackgroundColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabBackgroundColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabBackgroundColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabBackgroundColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabBackgroundColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabBackgroundColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Color
        key = BasicSettingsFragment.TEXT_COLOR_UNSELECTED;
        switch (basicSettingsFragment.tabTextColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabTextColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabTextColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabTextColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabTextColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabTextColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabTextColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabTextColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Color Selected
        key = BasicSettingsFragment.TEXT_COLOR_SELECTED;
        switch (basicSettingsFragment.tabTextSelectedColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabTextSelectedColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabTextSelectedColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabTextSelectedColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabTextSelectedColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabTextSelectedColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabTextSelectedColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabTextSelectedColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Style Selected
        key = BasicSettingsFragment.TEXT_STYLE_SELECTED;
        switch (basicSettingsFragment.selectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
            case R.id.selectedTextStyleButtonNormal:
                intent.putExtra(key, Typeface.NORMAL);
                break;
            case R.id.selectedTextStyleButtonItallic:
                intent.putExtra(key, Typeface.ITALIC);
                break;
            case R.id.selectedTextStyleButtonBold:
            default:
                intent.putExtra(key, Typeface.BOLD);
                break;
        }

        // Text Style Unselected
        key = BasicSettingsFragment.TEXT_STYLE_UNSELECTED;
        switch (basicSettingsFragment.unselectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
            case R.id.unselectedTextStyleButtonNormal:
                intent.putExtra(key, Typeface.NORMAL);
                break;
            case R.id.unselectedTextStyleButtonItallic:
                intent.putExtra(key, Typeface.ITALIC);
                break;
            case R.id.unselectedTextStyleButtonBold:
            default:
                intent.putExtra(key, Typeface.BOLD);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_DURATION, rippleSettingsFragment.rippleDurationMs);
        intent.putExtra(RippleSettingsFragment.RIPPLE_ALPHA_FLOAT, rippleSettingsFragment.rippleAlphaFloat);

        // Ripple Color
        key = RippleSettingsFragment.RIPPLE_COLOR;
        switch (rippleSettingsFragment.rippleColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rippleColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.rippleColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.rippleColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.rippleColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.rippleColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.rippleColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.rippleColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_DELAY_CLICK, rippleSettingsFragment.rippleDelayClickCheckBox.isChecked());
        intent.putExtra(RippleSettingsFragment.RIPPLE_DIAMETER, rippleSettingsFragment.rippleDiameterDp);

        intent.putExtra(RippleSettingsFragment.RIPPLE_FADE_DURATION, rippleSettingsFragment.rippleFadeDurationMs);

        // Ripple Highlight Color
        key = RippleSettingsFragment.RIPPLE_HIGHLIGHT_COLOR;
        switch (rippleSettingsFragment.rippleHighlightColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rippleHighlightColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.rippleHighlightColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.rippleHighlightColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.rippleHighlightColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.rippleHighlightColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.rippleHighlightColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.rippleHighlightColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_HOVER, rippleSettingsFragment.rippleHoverCheckBox.isChecked());
        intent.putExtra(RippleSettingsFragment.RIPPLE_OVERLAY, rippleSettingsFragment.rippleOverlayCheckBox.isChecked());
        intent.putExtra(RippleSettingsFragment.RIPPLE_PERSISTENT, rippleSettingsFragment.ripplePersistentCheckBox.isChecked());

        intent.putExtra(RippleSettingsFragment.RIPPLE_ROUNDED_CORNERS_RADIUS, rippleSettingsFragment.rippleRoundedCornersRadiusDp);

        startActivity(intent);
    }


    public class MainActivityPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Tabs", "Ripple"};

        public MainActivityPagerAdapter(FragmentManager fm) {
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
            switch (position) {
                case 0:
                default:
                    return basicSettingsFragment;
                case 1:
                    return rippleSettingsFragment;
            }
        }
    }
}
