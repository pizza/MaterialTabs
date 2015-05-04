package io.karim.materialtabs.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import butterknife.OnClick;
import io.karim.materialtabs.sample.sampleui.RadioButtonCenter;

public class TabsSettingsFragment extends Fragment {

    public static final String INDICATOR_COLOR = "INDICATOR_COLOR";
    public static final String UNDERLINE_COLOR = "UNDERLINE_COLOR";
    public static final String INDICATOR_HEIGHT = "INDICATOR_HEIGHT";
    public static final String UNDERLINE_HEIGHT = "UNDERLINE_HEIGHT";
    public static final String TAB_PADDING = "TAB_PADDING";
    public static final String PADDING_MIDDLE = "PADDING_MIDDLE";
    public static final String SAME_WEIGHT_TABS = "SAME_WEIGHT_TABS";
    public static final String TEXT_ALL_CAPS = "TEXT_ALL_CAPS";
    public static final String TAB_BACKGROUND = "TAB_BACKGROUND";
    public static final String TOOLBAR_BACKGROUND = "TOOLBAR_BACKGROUND";
    public static final String TEXT_COLOR_UNSELECTED = "TEXT_COLOR_UNSELECTED";
    public static final String TEXT_COLOR_SELECTED = "TEXT_COLOR_SELECTED";
    public static final String TEXT_STYLE_SELECTED = "TEXT_STYLE_SELECTED";
    public static final String TEXT_STYLE_UNSELECTED = "TEXT_STYLE_UNSELECTED";
    public static final String SHOW_TOOLBAR = "SHOW_TOOLBAR";

    private static final int UNDERLINE_HEIGHT_DEFAULT_DP = 0;
    private static final int INDICATOR_HEIGHT_DEFAULT_DP = 2;
    private static final int TAB_PADDING_DEFAULT_DP = 12;

    // Indicator Color
    @InjectView(R.id.indicatorColorRadioGroup)
    RadioGroup indicatorColorRadioGroup;
    @InjectView(R.id.indicatorColorButtonWhite)
    RadioButtonCenter indicatorColorButtonWhite;

    // Underline Color
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

    // Padding Middle
    @InjectView(R.id.paddingMiddleCheckBox)
    CheckBox paddingMiddleCheckBox;

    // Should Expand
    @InjectView(R.id.sameWeightTabsCheckBox)
    CheckBox sameWeightTabsCheckBox;

    // Text All Caps
    @InjectView(R.id.textAllCapsCheckBox)
    CheckBox textAllCapsCheckBox;

    // Show Toolbar
    @InjectView(R.id.showToolbarCheckBox)
    CheckBox showToolbarCheckBox;

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
    @InjectView(R.id.tabBackgroundColorButtonFireEngineRed)
    RadioButtonCenter tabBackgroundColorButtonFireEngineRed;

    // Toolbar Background Color
    @InjectView(R.id.toolbarColorRadioGroup)
    RadioGroup toolbarColorRadioGroup;
    @InjectView(R.id.toolbarColorButtonFireEngineRed)
    RadioButtonCenter toolbarColorButtonFireEngineRed;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs_settings, container, false);
        ButterKnife.inject(this, rootView);

        setupAndReset();
        return rootView;
    }

    void setupAndReset() {
        indicatorColorButtonWhite.setChecked(true);
        underlineColorButtonMantis.setChecked(true);

        underlineHeightDp = UNDERLINE_HEIGHT_DEFAULT_DP;
        underlineHeightSeekBar.setProgress(underlineHeightDp);
        underlineHeightTextView.setText(getString(R.string.underline_height) + ": " + underlineHeightDp + "dp");
        underlineHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                underlineHeightDp = progress;
                underlineHeightTextView.setText(getString(R.string.underline_height) + ": " + underlineHeightDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        indicatorHeightDp = INDICATOR_HEIGHT_DEFAULT_DP;
        indicatorHeightSeekBar.setProgress(indicatorHeightDp);
        indicatorHeightTextView.setText(getString(R.string.indicator_height) + ": " + indicatorHeightDp + "dp");
        indicatorHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                indicatorHeightDp = progress;
                indicatorHeightTextView.setText(getString(R.string.indicator_height) + ": " + indicatorHeightDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        tabPaddingDp = TAB_PADDING_DEFAULT_DP;
        tabPaddingSeekBar.setProgress(tabPaddingDp);
        tabPaddingTextView.setText(getString(R.string.tab_padding) + ": " + tabPaddingDp + "dp");
        tabPaddingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tabPaddingDp = progress;
                tabPaddingTextView.setText(getString(R.string.tab_padding) + ": " + tabPaddingDp + "dp");
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

        tabBackgroundColorButtonFireEngineRed.setChecked(true);
        toolbarColorButtonFireEngineRed.setChecked(true);

        selectedTextStyleButtonBold.setChecked(true);
        unselectedTextStyleButtonBold.setChecked(true);

        sameWeightTabsCheckBox.setChecked(true);
        paddingMiddleCheckBox.setChecked(false);
        textAllCapsCheckBox.setChecked(true);

        showToolbarCheckBox.setChecked(true);
    }

    @OnClick(R.id.tabPaddingInfoButton)
    public void tabPaddingInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_padding).setMessage(R.string.tab_padding_details).create().show();
    }

    @OnClick(R.id.tabBackgroundColorInfoButton)
    public void tabBackgroundColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_background_color).setMessage(R.string.tab_background_color_details).create().show();
    }

    @OnClick(R.id.tabTextColorInfoButton)
    public void tabTextColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_text_color).setMessage(R.string.tab_text_color_details).create().show();
    }

    @OnClick(R.id.tabTextSelectedColorInfoButton)
    public void tabTextSelectedColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_text_selected_color).setMessage(R.string.tab_text_selected_color_details).create().show();
    }

    @OnClick(R.id.toolbarColorInfoButton)
    public void toolbarColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.toolbar_color).setMessage(R.string.toolbar_color_details).create().show();
    }

    @OnClick(R.id.textSelectedStyleInfoButton)
    public void textSelectedStyleInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.text_selected_style).setMessage(R.string.text_selected_style_details).create().show();
    }

    @OnClick(R.id.textUnselectedStyleInfoButton)
    public void textUnselectedStyleInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.text_unselected_style).setMessage(R.string.text_unselected_style_details).create().show();
    }

    @OnClick(R.id.textAllCapsInfoButton)
    public void textAllCapsInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.text_all_caps).setMessage(R.string.text_all_caps_details).create().show();
    }

    @OnClick(R.id.underlineColorInfoButton)
    public void underlineColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.underline_color).setMessage(R.string.underline_color_details).create().show();
    }

    @OnClick(R.id.underlineHeightInfoButton)
    public void underlineHeightInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.underline_height).setMessage(R.string.underline_height_details).create().show();
    }

    @OnClick(R.id.indicatorColorInfoButton)
    public void indicatorColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.indicator_color).setMessage(R.string.indicator_color_details).create().show();
    }

    @OnClick(R.id.indicatorHeightInfoButton)
    public void indicatorHeightInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.indicator_height).setMessage(R.string.indicator_height_details).create().show();
    }


    @OnClick(R.id.paddingMiddleInfoButton)
    public void paddingMiddleInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.padding_middle).setMessage(R.string.padding_middle_details).create().show();
    }

    @OnClick(R.id.sameWeighTabsInfoButton)
    public void sameWeighTabsInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.same_weight_tabs).setMessage(R.string.same_weight_tabs_details).create().show();
    }

    @OnClick(R.id.showToolbarInfoButton)
    public void showToolbarInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.show_toolbar).setMessage(R.string.show_toolbar_details).create().show();
    }
}
