package io.karim.materialtabs.sample;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.karim.materialtabs.sample.ui.RadioButtonCenter;

public class TabsSettingsFragment extends Fragment implements ResettableFragment {

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
    public static final String NUMBER_OF_TABS = "NUMBER_OF_TABS";

    private static final int UNDERLINE_HEIGHT_DEFAULT_DP = 0;
    private static final int INDICATOR_HEIGHT_DEFAULT_DP = 2;
    private static final int TAB_PADDING_DEFAULT_DP = 12;
    private static final int NUMBER_OF_TABS_DEFAULT = 3;

    private MainActivity mainActivity;

    // Indicator Height
    @InjectView(R.id.numberOfTabsSeekBar)
    SeekBar numberOfTabsSeekBar;
    @InjectView(R.id.numberOfTabsTextView)
    TextView numberOfTabsTextView;

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
    int numberOfTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs_settings, container, false);
        ButterKnife.inject(this, rootView);

        setupAndReset();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) getActivity();
        mainActivity.addFragment(this);
    }

    @Override
    public void onDetach() {
        mainActivity.removeFragment(this);
        super.onDetach();
    }

    @Override
    public void setupAndReset() {
        /** SeekBars **/

        underlineHeightDp = UNDERLINE_HEIGHT_DEFAULT_DP;
        underlineHeightTextView.setText(getString(R.string.underline_height) + ": " + underlineHeightDp + "dp");
        underlineHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                underlineHeightDp = progress;
                underlineHeightTextView.setText(getString(R.string.underline_height) + ": " + underlineHeightDp + "dp");
                mainActivity.startTabsActivityIntent.putExtra(UNDERLINE_HEIGHT, underlineHeightDp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        numberOfTabs = NUMBER_OF_TABS_DEFAULT;
        numberOfTabsTextView.setText(getString(R.string.number_of_tabs) + ": " + numberOfTabs);
        numberOfTabsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfTabs = progress + 1;
                numberOfTabsTextView.setText(getString(R.string.number_of_tabs) + ": " + numberOfTabs);
                mainActivity.startTabsActivityIntent.putExtra(NUMBER_OF_TABS, numberOfTabs);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        indicatorHeightDp = INDICATOR_HEIGHT_DEFAULT_DP;
        indicatorHeightTextView.setText(getString(R.string.indicator_height) + ": " + indicatorHeightDp + "dp");
        indicatorHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                indicatorHeightDp = progress;
                indicatorHeightTextView.setText(getString(R.string.indicator_height) + ": " + indicatorHeightDp + "dp");
                mainActivity.startTabsActivityIntent.putExtra(INDICATOR_HEIGHT, indicatorHeightDp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        tabPaddingDp = TAB_PADDING_DEFAULT_DP;
        tabPaddingTextView.setText(getString(R.string.tab_padding) + ": " + tabPaddingDp + "dp");
        tabPaddingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tabPaddingDp = progress;
                tabPaddingTextView.setText(getString(R.string.tab_padding) + ": " + tabPaddingDp + "dp");
                mainActivity.startTabsActivityIntent.putExtra(TAB_PADDING, tabPaddingDp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        underlineHeightSeekBar.setProgress(underlineHeightDp);
        numberOfTabsSeekBar.setProgress(numberOfTabs);
        indicatorHeightSeekBar.setProgress(indicatorHeightDp);
        tabPaddingSeekBar.setProgress(tabPaddingDp);

        /** RadioGroups **/

        // Indicator Color
        indicatorColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = INDICATOR_COLOR;
                switch (checkedId) {
                    case R.id.indicatorColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.indicatorColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.indicatorColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.indicatorColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.indicatorColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.indicatorColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.indicatorColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Underline Color
        underlineColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = UNDERLINE_COLOR;
                switch (checkedId) {
                    case R.id.underlineColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.underlineColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.underlineColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.underlineColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.underlineColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.underlineColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.underlineColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Tab Background Color
        tabBackgroundColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TAB_BACKGROUND;
                switch (checkedId) {
                    case R.id.tabBackgroundColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.tabBackgroundColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.tabBackgroundColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.tabBackgroundColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.tabBackgroundColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.tabBackgroundColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.tabBackgroundColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Toolbar Background Color
        toolbarColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TOOLBAR_BACKGROUND;
                switch (checkedId) {
                    case R.id.toolbarColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.toolbarColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.toolbarColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.toolbarColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.toolbarColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.toolbarColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.toolbarColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Text Color Unselected
        tabTextColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TEXT_COLOR_UNSELECTED;
                switch (checkedId) {
                    case R.id.tabTextColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.tabTextColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.tabTextColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.tabTextColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.tabTextColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.tabTextColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.tabTextColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Text Color Selected
        tabTextSelectedColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TEXT_COLOR_SELECTED;
                switch (checkedId) {
                    case R.id.tabTextSelectedColorButtonFireEngineRed:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.fire_engine_red);
                        break;
                    case R.id.tabTextSelectedColorButtonGorse:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.gorse);
                        break;
                    case R.id.tabTextSelectedColorButtonIrisBlue:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.iris_blue);
                        break;
                    case R.id.tabTextSelectedColorButtonSafetyOrange:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.safety_orange);
                        break;
                    case R.id.tabTextSelectedColorButtonWhite:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.white);
                        break;
                    case R.id.tabTextSelectedColorButtonBlack:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.black);
                        break;
                    case R.id.tabTextSelectedColorButtonMantis:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, R.color.mantis);
                        break;
                }
            }
        });

        // Text Style Selected
        selectedTextStyleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TEXT_STYLE_SELECTED;
                switch (checkedId) {
                    case R.id.selectedTextStyleButtonNormal:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.NORMAL);
                        break;
                    case R.id.selectedTextStyleButtonItallic:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.ITALIC);
                        break;
                    case R.id.selectedTextStyleButtonBold:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.BOLD);
                        break;
                }
            }
        });

        // Text Style Unselected
        unselectedTextStyleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String key = TEXT_STYLE_UNSELECTED;
                switch (checkedId) {
                    case R.id.unselectedTextStyleButtonNormal:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.NORMAL);
                        break;
                    case R.id.unselectedTextStyleButtonItallic:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.ITALIC);
                        break;
                    case R.id.unselectedTextStyleButtonBold:
                    default:
                        mainActivity.startTabsActivityIntent.putExtra(key, Typeface.BOLD);
                        break;
                }
            }
        });

        indicatorColorButtonWhite.setChecked(true);
        underlineColorButtonMantis.setChecked(true);

        tabTextColorButtonWhite.setChecked(true);
        tabTextSelectedColorButtonWhite.setChecked(true);

        tabBackgroundColorButtonFireEngineRed.setChecked(true);
        toolbarColorButtonFireEngineRed.setChecked(true);

        selectedTextStyleButtonBold.setChecked(true);
        unselectedTextStyleButtonBold.setChecked(true);

        /** CheckBoxes **/

        // Text Style Unselected
        sameWeightTabsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivity.startTabsActivityIntent.putExtra(SAME_WEIGHT_TABS, isChecked);
            }
        });

        // Text Style Unselected
        paddingMiddleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivity.startTabsActivityIntent.putExtra(PADDING_MIDDLE, isChecked);
            }
        });

        // Text Style Unselected
        textAllCapsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivity.startTabsActivityIntent.putExtra(TEXT_ALL_CAPS, isChecked);
            }
        });

        // Text Style Unselected
        showToolbarCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivity.startTabsActivityIntent.putExtra(SHOW_TOOLBAR, isChecked);
            }
        });

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
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_background_color)
                                              .setMessage(R.string.tab_background_color_details)
                                              .create()
                                              .show();
    }

    @OnClick(R.id.tabTextColorInfoButton)
    public void tabTextColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_text_color).setMessage(R.string.tab_text_color_details).create().show();
    }

    @OnClick(R.id.tabTextSelectedColorInfoButton)
    public void tabTextSelectedColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.tab_text_selected_color)
                                              .setMessage(R.string.tab_text_selected_color_details)
                                              .create()
                                              .show();
    }

    @OnClick(R.id.toolbarColorInfoButton)
    public void toolbarColorInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.toolbar_color).setMessage(R.string.toolbar_color_details).create().show();
    }

    @OnClick(R.id.textSelectedStyleInfoButton)
    public void textSelectedStyleInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.text_selected_style)
                                              .setMessage(R.string.text_selected_style_details)
                                              .create()
                                              .show();
    }

    @OnClick(R.id.textUnselectedStyleInfoButton)
    public void textUnselectedStyleInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.text_unselected_style)
                                              .setMessage(R.string.text_unselected_style_details)
                                              .create()
                                              .show();
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

    @OnClick(R.id.numberOfTabsInfoButton)
    public void numberOfTabsInfoButtonClicked() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.number_of_tabs).setMessage(R.string.number_of_tabs_details).create().show();
    }
}
