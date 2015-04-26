package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    public static final String DIVIDER_COLOR = "DIVIDER_COLOR";
    public static final String DIVIDER_WIDTH = "DIVIDER_WIDTH";
    public static final String INDICATOR_HEIGHT = "INDICATOR_HEIGHT";
    public static final String UNDERLINE_HEIGHT = "UNDERLINE_HEIGHT";
    public static final String TAB_PADDING = "TAB_PADDING";
    public static final String DIVIDER_PADDING = "DIVIDER_PADDING";
    public static final String SCROLL_OFFSET = "SCROLL_OFFSET";

    private static final int DIVIDER_WIDTH_MINIMUM_DP = 20;
    private static final int UNDERLINE_HEIGHT_MINIMUM_DP = 20;
    private static final int INDICATOR_HEIGHT_MINIMUM_DP = 20;
    private static final int DIVIDER_PADDING_DP = 20;
    private static final int TAB_PADDING_MINIMUM_DP = 20;
    private static final int SCROLL_OFFSET_MINIMUM_DP = 20;

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

    // Divider Color
    @InjectView(R.id.dividerColorRadioGroup)
    RadioGroup dividerColorRadioGroup;
    @InjectView(R.id.dividerColorButtonMantis)
    RadioButtonCenter dividerColorButtonMantis;

    // Divider Width
    @InjectView(R.id.dividerWidthSeekBar)
    SeekBar dividerWidthSeekBar;
    @InjectView(R.id.dividerWidthTextView)
    TextView dividerWidthTextView;

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

    // Divider Padding
    @InjectView(R.id.dividerPaddingSeekBar)
    SeekBar dividerPaddingSeekBar;
    @InjectView(R.id.dividerPaddingTextView)
    TextView dividerPaddingTextView;

    // Scroll Offset
    @InjectView(R.id.scrollOffsetSeekBar)
    SeekBar scrollOffsetSeekBar;
    @InjectView(R.id.scrollOffsetTextView)
    TextView scrollOffsetTextView;

    private int underlineHeightDp;
    private int dividerWidthDp;
    private int indicatorHeightDp;
    private int dividerPaddingDp;
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
        dividerColorButtonMantis.setChecked(true);

        dividerWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dividerWidthDp = progress + DIVIDER_WIDTH_MINIMUM_DP;
                dividerWidthTextView.setText(getString(R.string.divider_width) + ": " + dividerWidthDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

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

        dividerPaddingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dividerPaddingDp = progress + DIVIDER_PADDING_DP;
                dividerPaddingTextView.setText(getString(R.string.divider_padding) + ": " + dividerPaddingDp + "dp");

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

        // Divider Color
        key = DIVIDER_COLOR;
        switch (dividerColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.dividerColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.dividerColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.dividerColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.dividerColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.dividerColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.dividerColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.dividerColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(DIVIDER_WIDTH, dividerWidthDp);
        intent.putExtra(INDICATOR_HEIGHT, indicatorHeightDp);
        intent.putExtra(UNDERLINE_HEIGHT, underlineHeightDp);

        intent.putExtra(TAB_PADDING, tabPaddingDp);
        intent.putExtra(DIVIDER_PADDING, dividerPaddingDp);
        intent.putExtra(SCROLL_OFFSET, scrollOffsetDp);

        startActivity(intent);
    }
}
