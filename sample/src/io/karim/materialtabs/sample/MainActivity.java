package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
    public static final String TEXT_COLOR_UNSELECTED = "TEXT_COLOR_UNSELECTED";
    public static final String TEXT_COLOR_SELECTED = "TEXT_COLOR_SELECTED";
    public static final String TEXT_STYLE_SELECTED = "TEXT_STYLE_SELECTED";
    public static final String TEXT_STYLE_UNSELECTED = "TEXT_STYLE_UNSELECTED";

    private static final int UNDERLINE_HEIGHT_MINIMUM_DP = 0;
    private static final int INDICATOR_HEIGHT_MINIMUM_DP = 0;
    private static final int TAB_PADDING_MINIMUM_DP = 0;
    private static final int SCROLL_OFFSET_MINIMUM_DP = 0;

    public static final String RIPPLE_DURATION = "RIPPLE_DURATION";
    public static final String RIPPLE_ALPHA_FLOAT = "RIPPLE_ALPHA_FLOAT";
    public static final String RIPPLE_COLOR = "RIPPLE_COLOR";
    public static final String RIPPLE_DELAY_CLICK = "RIPPLE_DELAY_CLICK";
    public static final String RIPPLE_DIAMETER = "RIPPLE_DIAMETER";
    public static final String RIPPLE_FADE_DURATION = "RIPPLE_FADE_DURATION";
    public static final String RIPPLE_HIGHLIGHT_COLOR = "RIPPLE_HIGHLIGHT_COLOR";
    public static final String RIPPLE_HOVER = "RIPPLE_HOVER";
    public static final String RIPPLE_OVERLAY = "RIPPLE_OVERLAY";
    public static final String RIPPLE_PERSISTENT = "RIPPLE_PERSISTENT";
    public static final String RIPPLE_ROUNDED_CORNERS_RADIUS = "RIPPLE_ROUNDED_CORNERS_RADIUS";

    private static final int RIPPLE_DURATION_MULTIPLIER = 100;

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

    // Selected Text Style
    @InjectView(R.id.selectedTextStyleRadioGroup)
    RadioGroup selectedTextStyleRadioGroup;
    @InjectView(R.id.selectedTextStyleButtonBold)
    RadioButton selectedTextStyleButtonBold;

    // Unselected Text Style
    @InjectView(R.id.unselectedTextStyleRadioGroup)
    RadioGroup unselectedTextStyleRadioGroup;
    @InjectView(R.id.unselectedTextStyleButtonBold)
    RadioButton unselectedTextStyleButtonBold;

    // Ripple Duration
    @InjectView(R.id.rippleDurationSeekBar)
    SeekBar rippleDurationSeekBar;
    @InjectView(R.id.rippleDurationTextView)
    TextView rippleDurationTextView;

    // Ripple Alpha Float
    @InjectView(R.id.rippleAlphaFloatSeekBar)
    SeekBar rippleAlphaFloatSeekBar;
    @InjectView(R.id.rippleAlphaFloatTextView)
    TextView rippleAlphaFloatTextView;

    // Ripple Color
    @InjectView(R.id.rippleColorRadioGroup)
    RadioGroup rippleColorRadioGroup;
    @InjectView(R.id.rippleColorButtonWhite)
    RadioButton rippleColorButtonWhite;

    // Ripple Delay Click
    @InjectView(R.id.rippleDelayClickCheckBox)
    CheckBox rippleDelayClickCheckBox;

    // Ripple Diameter
    @InjectView(R.id.rippleDiameterSeekBar)
    CheckBox rippleDiameterSeekBar;

    // Ripple Fade Duration
    @InjectView(R.id.rippleFadeDurationSeekBar)
    SeekBar rippleFadeDurationSeekBar;
    @InjectView(R.id.rippleFadeDurationTextView)
    TextView rippleFadeDurationTextView;

    // Ripple Highlight Color
    @InjectView(R.id.rippleHighlightColorRadioGroup)
    RadioGroup rippleHighlightColorRadioGroup;
    @InjectView(R.id.rippleHighlightColorButtonWhite)
    RadioButton rippleHighlightColorButtonWhite;

    // Ripple Diameter
    @InjectView(R.id.rippleHoverCheckBox)
    CheckBox rippleHoverCheckBox;

    // Ripple Overlay
    @InjectView(R.id.rippleOverlayCheckBox)
    CheckBox rippleOverlayCheckBox;

    // Ripple Persistent
    @InjectView(R.id.ripplePersistentCheckBox)
    CheckBox ripplePersistentCheckBox;

    // Ripple Rounded Corners Radius
    @InjectView(R.id.rippleRoundedCornersRadiusSeekBar)
    SeekBar rippleRoundedCornersRadiusSeekBar;
    @InjectView(R.id.rippleRoundedCornersRadiusTextView)
    TextView rippleRoundedCornersRadiusTextView;

    private int underlineHeightDp;
    private int indicatorHeightDp;
    private int tabPaddingDp;
    private int scrollOffsetDp;
    private int rippleDurationMs;
    private float rippleAlphaFloat;
    private int rippleFadeDurationMs;
    private int rippleRoundedCornersRadiusDp;

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

        rippleDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleDurationMs = progress * RIPPLE_DURATION_MULTIPLIER;
                rippleDurationTextView.setText(getString(R.string.ripple_duration) + ": " + rippleDurationMs + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rippleAlphaFloatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleAlphaFloat = (float) progress / rippleAlphaFloatSeekBar.getMax();
                rippleAlphaFloatTextView.setText(getString(R.string.ripple_alpha_float) + ": " + rippleAlphaFloat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rippleFadeDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleFadeDurationMs = progress * RIPPLE_DURATION_MULTIPLIER;
                rippleFadeDurationTextView.setText(getString(R.string.ripple_fade_duration) + ": " + rippleFadeDurationMs + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rippleRoundedCornersRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleRoundedCornersRadiusDp = progress;
                rippleRoundedCornersRadiusTextView.setText(
                        getString(R.string.ripple_rounded_corners_radius) + ": " + rippleRoundedCornersRadiusDp + "dp");
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

        selectedTextStyleButtonBold.setChecked(true);
        unselectedTextStyleButtonBold.setChecked(true);
        rippleColorButtonWhite.setChecked(true);
        rippleHighlightColorButtonWhite.setChecked(true);
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
        key = TEXT_COLOR_UNSELECTED;
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

        // Text Style Selected
        key = TEXT_STYLE_SELECTED;
        switch (selectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
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
        key = TEXT_STYLE_UNSELECTED;
        switch (unselectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
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

        intent.putExtra(RIPPLE_DURATION, rippleDurationMs);
        intent.putExtra(RIPPLE_ALPHA_FLOAT, rippleAlphaFloat);

        // Ripple Color
        key = RIPPLE_COLOR;
        switch (rippleColorRadioGroup.getCheckedRadioButtonId()) {
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

        intent.putExtra(RIPPLE_DELAY_CLICK, rippleDelayClickCheckBox.isChecked());
        intent.putExtra(RIPPLE_DIAMETER, rippleDiameterSeekBar.isChecked());

        intent.putExtra(RIPPLE_FADE_DURATION, rippleFadeDurationMs);

        // Ripple Highlight Color
        key = RIPPLE_HIGHLIGHT_COLOR;
        switch (rippleHighlightColorRadioGroup.getCheckedRadioButtonId()) {
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

        intent.putExtra(RIPPLE_HOVER, rippleHoverCheckBox.isChecked());
        intent.putExtra(RIPPLE_OVERLAY, rippleOverlayCheckBox.isChecked());
        intent.putExtra(RIPPLE_PERSISTENT, ripplePersistentCheckBox.isChecked());

        intent.putExtra(RIPPLE_ROUNDED_CORNERS_RADIUS, rippleRoundedCornersRadiusDp);

        startActivity(intent);
    }
}