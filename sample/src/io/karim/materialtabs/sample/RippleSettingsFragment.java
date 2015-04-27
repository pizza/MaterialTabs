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

public class RippleSettingsFragment extends Fragment {

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

    int rippleDurationMs;
    float rippleAlphaFloat;
    int rippleFadeDurationMs;
    int rippleRoundedCornersRadiusDp;
    int rippleDiameterDp;

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
    SeekBar rippleDiameterSeekBar;
    @InjectView(R.id.rippleDiameterTextView)
    TextView rippleDiameterTextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ripple, container, false);
        ButterKnife.inject(this, rootView);

        setup();
        return rootView;
    }

    private void setup() {
        rippleDurationTextView.setText(getString(R.string.ripple_duration) + ": " + rippleDurationMs + "ms");
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

        rippleAlphaFloatTextView.setText(getString(R.string.ripple_alpha_float) + ": " + rippleAlphaFloat);
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

        rippleFadeDurationTextView.setText(getString(R.string.ripple_fade_duration) + ": " + rippleFadeDurationMs + "ms");
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

        rippleRoundedCornersRadiusTextView.setText(getString(R.string.ripple_rounded_corners_radius) + ": " + rippleRoundedCornersRadiusDp + "dp");
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

        rippleDiameterTextView.setText(getString(R.string.ripple_diameter) + ": " + rippleDiameterDp + "dp");
        rippleDiameterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleDiameterDp = progress;
                rippleDiameterTextView.setText(getString(R.string.ripple_diameter) + ": " + rippleDiameterDp + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rippleColorButtonWhite.setChecked(true);
        rippleHighlightColorButtonWhite.setChecked(true);
    }
}
