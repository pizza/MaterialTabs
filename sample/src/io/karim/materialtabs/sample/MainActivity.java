package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.karim.materialtabs.sample.sampleui.RadioButtonCenter;


public class MainActivity extends ActionBarActivity {

    public static final String INDICATOR_COLOR = "INDICATOR_COLOR";
    public static final String UNDERLINE_COLOR = "UNDERLINE_COLOR";
    public static final String INDICATOR_HEIGHT = "INDICATOR_HEIGHT";
    public static final String UNDERLINE_HEIGHT = "UNDERLINE_HEIGHT";
    public static final String TAB_PADDING = "TAB_PADDING";
    public static final String SCROLL_OFFSET = "SCROLL_OFFSET";
    public static final String PADDING_MIDDLE = "PADDING_MIDDLE";
    public static final String SHOULD_EXPAND = "SHOULD_EXPAND";
    public static final String TEXT_ALL_CAPS = "TEXT_ALL_CAPS";
    public static final String TAB_BACKGROUND = "TAB_BACKGROUND";
    public static final String TEXT_COLOR = "TEXT_COLOR";
    public static final String TEXT_COLOR_SELECTED = "TEXT_COLOR_SELECTED";

    private static final int UNDERLINE_HEIGHT_MINIMUM_DP = 0;
    private static final int INDICATOR_HEIGHT_MINIMUM_DP = 0;
    private static final int TAB_PADDING_MINIMUM_DP = 0;
    private static final int SCROLL_OFFSET_MINIMUM_DP = 0;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    // Indicator Color
    @InjectView(R.id.indicatorColorRadioGroup)
    RadioGroup indicatorColorRadioGroup;
    @InjectView(R.id.indicatorColorButtonMantis)
    RadioButtonCenter indicatorColorButtonMantis;

    // Indicator Color
    @InjectView(R.id.underlineColorRadioGroup)
    RadioGroup underlineColorRadioGroup;
    @InjectView(R.id.underlineColorButtonMantis)
    RadioButtonCenter underlineColorButtonMantis;

    // Indicator Height
    @InjectView(R.id.indicatorHeightSeekBar)
    SeekBar indicatorHeightSeekBar;
    @InjectView(R.id.indicatorHeightTextView)
    TextView indicatorHeightTextView;

    // Underline Height
    @InjectView(R.id.underlineHeightSeekBar)
    SeekBar underlineHeightSeekBar;
    @InjectView(R.id.underlineHeightTextView)
    TextView underlineHeightTextView;

    // Tab Padding Left Right
    @InjectView(R.id.tabPaddingSeekBar)
    SeekBar tabPaddingSeekBar;
    @InjectView(R.id.tabPaddingTextView)
    TextView tabPaddingTextView;

    // Scroll Offset
    @InjectView(R.id.scrollOffsetSeekBar)
    SeekBar scrollOffsetSeekBar;
    @InjectView(R.id.scrollOffsetTextView)
    TextView scrollOffsetTextView;

    // Padding Middle
    @InjectView(R.id.paddingMiddleCheckBox)
    CheckBox paddingMiddleCheckBox;

    // Should Expand
    @InjectView(R.id.shouldExpandCheckBox)
    CheckBox shouldExpandCheckBox;

    // Text All Caps
    @InjectView(R.id.textAllCapsCheckBox)
    CheckBox textAllCapsCheckBox;

    // Tab Text Color
    @InjectView(R.id.tabTextColorRadioGroup)
    RadioGroup tabTextColorRadioGroup;
    @InjectView(R.id.tabTextColorButtonWhite)
    RadioButtonCenter tabTextColorButtonWhite;

    // Tab Text Selected Color
    @InjectView(R.id.tabTextSelectedColorRadioGroup)
    RadioGroup tabTextSelectedColorRadioGroup;
    @InjectView(R.id.tabTextSelectedColorButtonWhite)
    RadioButtonCenter tabTextSelectedColorButtonWhite;

    // Tab Background Color
    @InjectView(R.id.tabBackgroundColorRadioGroup)
    RadioGroup tabBackgroundColorRadioGroup;
    @InjectView(R.id.tabBackgroundColorButtonMantis)
    RadioButtonCenter tabBackgroundColorButtonMantis;

    private int underlineHeightDp;
    private int indicatorHeightDp;
    private int tabPaddingDp;
    private int scrollOffsetDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        indicatorColorButtonMantis.setChecked(true);
        underlineColorButtonMantis.setChecked(true);

        underlineHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                underlineHeightDp = progress + UNDERLINE_HEIGHT_MINIMUM_DP;
                underlineHeightTextView.setText(getString(R.string.underline_height) + ": " + underlineHeightDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        indicatorHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                indicatorHeightDp = progress + INDICATOR_HEIGHT_MINIMUM_DP;
                indicatorHeightTextView.setText(getString(R.string.indicator_height) + ": " + indicatorHeightDp + "dp");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        tabPaddingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tabPaddingDp = progress + TAB_PADDING_MINIMUM_DP;
                tabPaddingTextView.setText(getString(R.string.tab_padding) + ": " + tabPaddingDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        scrollOffsetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scrollOffsetDp = progress + SCROLL_OFFSET_MINIMUM_DP;
                scrollOffsetTextView.setText(getString(R.string.scroll_offset) + ": " + scrollOffsetDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        tabTextColorButtonWhite.setChecked(true);
        tabTextSelectedColorButtonWhite.setChecked(true);
        tabBackgroundColorButtonMantis.setChecked(true);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.goToTabsActivityButton)
    public void goToTabsActivityButtonClicked() {
        Intent intent = new Intent(this, TabsActivity.class);

        // Indicator Color
        String key = INDICATOR_COLOR;
        switch (indicatorColorRadioGroup.getCheckedRadioButtonId()) {
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
        key = UNDERLINE_COLOR;
        switch (underlineColorRadioGroup.getCheckedRadioButtonId()) {
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

        intent.putExtra(INDICATOR_HEIGHT, indicatorHeightDp);
        intent.putExtra(UNDERLINE_HEIGHT, underlineHeightDp);

        intent.putExtra(TAB_PADDING, tabPaddingDp);
        intent.putExtra(SCROLL_OFFSET, scrollOffsetDp);

        intent.putExtra(SHOULD_EXPAND, shouldExpandCheckBox.isChecked());
        intent.putExtra(TEXT_ALL_CAPS, textAllCapsCheckBox.isChecked());
        intent.putExtra(PADDING_MIDDLE, paddingMiddleCheckBox.isChecked());

        // Tab Background Color
        key = TAB_BACKGROUND;
        switch (tabBackgroundColorRadioGroup.getCheckedRadioButtonId()) {
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
        key = TEXT_COLOR;
        switch (tabTextColorRadioGroup.getCheckedRadioButtonId()) {
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
        key = TEXT_COLOR_SELECTED;
        switch (tabTextSelectedColorRadioGroup.getCheckedRadioButtonId()) {
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

//        <attr name="pstsTextAlpha" format="integer" />
//        <attr name="pstsTextStyle">
//        <flag name="normal" value="0x0" />
//        <flag name="bold" value="0x1" />
//        <flag name="italic" value="0x2" />
//        </attr>
//        <attr name="pstsTextSelectedStyle">
//        <flag name="normal" value="0x0" />
//        <flag name="bold" value="0x1" />
//        <flag name="italic" value="0x2" />
//        </attr>
//        <attr name="pstsMrlRippleColor" format="color" localization="suggested" />
//        <attr name="pstsMrlRippleHighlightColor" format="color" localization="suggested" />
//        <attr name="pstsMrlRippleDimension" format="dimension" localization="suggested" />
//        <attr name="pstsMrlRippleOverlay" format="boolean" localization="suggested" />
//        <attr name="pstsMrlRippleAlpha" format="float" localization="suggested" />
//        <attr name="pstsMrlRippleDuration" format="integer" localization="suggested" />
//        <attr name="pstsMrlRippleFadeDuration" format="integer" localization="suggested" />
//        <attr name="pstsMrlRippleHover" format="boolean" localization="suggested" />
//        <attr name="pstsMrlRippleBackground" format="color" localization="suggested" />
//        <attr name="pstsMrlRippleDelayClick" format="boolean" localization="suggested" />
//        <attr name="pstsMrlRipplePersistent" format="boolean" localization="suggested" />
//        <attr name="pstsMrlRippleInAdapter" format="boolean" localization="suggested" />
//        <attr name="pstsMrlRippleRoundedCorners" format="dimension" localization="suggested" />

        startActivity(intent);
    }
}
