package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.karim.materialtabs.sample.sampleui.RadioButtonCenter;


public class MainActivity extends ActionBarActivity {

    public static final String INDICATOR_COLOR = "INDICATOR_COLOR";
    public static final String UNDERLINE_COLOR = "UNDERLINE_COLOR";
    public static final String DIVIDER_COLOR = "DIVIDER_COLOR";

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
        switch (indicatorColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.indicatorColorButtonFireEngineRed:
                intent.putExtra(INDICATOR_COLOR, R.color.fire_engine_red);
                break;
            case R.id.indicatorColorButtonGorse:
                intent.putExtra(INDICATOR_COLOR, R.color.gorse);
                break;
            case R.id.indicatorColorButtonIrisBlue:
                intent.putExtra(INDICATOR_COLOR, R.color.iris_blue);
                break;
            case R.id.indicatorColorButtonSafetyOrange:
                intent.putExtra(INDICATOR_COLOR, R.color.safety_orange);
                break;
            case R.id.indicatorColorButtonMantis:
            default:
                intent.putExtra(INDICATOR_COLOR, R.color.mantis);
                break;
        }

        // Underline Color
        switch (underlineColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.underlineColorButtonFireEngineRed:
                intent.putExtra(UNDERLINE_COLOR, R.color.fire_engine_red);
                break;
            case R.id.underlineColorButtonGorse:
                intent.putExtra(UNDERLINE_COLOR, R.color.gorse);
                break;
            case R.id.underlineColorButtonIrisBlue:
                intent.putExtra(UNDERLINE_COLOR, R.color.iris_blue);
                break;
            case R.id.underlineColorButtonSafetyOrange:
                intent.putExtra(UNDERLINE_COLOR, R.color.safety_orange);
                break;
            case R.id.underlineColorButtonMantis:
            default:
                intent.putExtra(UNDERLINE_COLOR, R.color.mantis);
                break;
        }

        // Divider Color
        switch (dividerColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.dividerColorButtonFireEngineRed:
                intent.putExtra(DIVIDER_COLOR, R.color.fire_engine_red);
                break;
            case R.id.dividerColorButtonGorse:
                intent.putExtra(DIVIDER_COLOR, R.color.gorse);
                break;
            case R.id.dividerColorButtonIrisBlue:
                intent.putExtra(DIVIDER_COLOR, R.color.iris_blue);
                break;
            case R.id.dividerColorButtonSafetyOrange:
                intent.putExtra(DIVIDER_COLOR, R.color.safety_orange);
                break;
            case R.id.dividerColorButtonMantis:
            default:
                intent.putExtra(DIVIDER_COLOR, R.color.mantis);
                break;
        }

        startActivity(intent);
    }
}
