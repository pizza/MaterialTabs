package io.karim.materialtabs.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.materialtabs.sample.sampleui.RadioButtonCenter;

/**
 * Created by karim on 4/27/15.
 */
public class BasicSettingsFragment extends Fragment {

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

    int underlineHeightDp;
    int indicatorHeightDp;
    int tabPaddingDp;
    int scrollOffsetDp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_settings, container, false);
        ButterKnife.inject(this, rootView);

        setup();
        return rootView;
    }

    private void setup() {

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

        selectedTextStyleButtonBold.setChecked(true);
        unselectedTextStyleButtonBold.setChecked(true);
    }
}
