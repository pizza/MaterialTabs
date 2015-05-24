package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    /**
     * Intent used to start {@link TabsActivity}.
     */
    public Intent startTabsActivityIntent;

    /**
     * Holds references to fragments from the time they are attached to Activity until they are dettached.
     */
    private ArrayList<ResettableFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_main));

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        startTabsActivityIntent = new Intent(this, TabsActivity.class);

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
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_me:
                DialogFragment newFragment = new MeDialogFragment();
                newFragment.show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.action_reset:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Reset everything to default?")
                                  .setCancelable(false)
                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          resetDefaults();
                                      }
                                  })
                                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          dialog.cancel();
                                      }
                                  });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
            case R.id.action_go:
                startActivity(startTabsActivityIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Resets default values of all the settings in both {@link TabsSettingsFragment} and {@link RippleSettingsFragment}.
     */
    private void resetDefaults() {
        for (ResettableFragment f : mFragments) {
            f.setupAndReset();
        }
    }

    /**
     * Add fragment to {@link #mFragments} when attached to Activity.
     */
    public void addFragment(ResettableFragment f) {
        mFragments.add(f);
    }


    /**
     * Remove fragment from {@link #mFragments} when detached to Activity.
     */
    public void removeFragment(ResettableFragment f) {
        mFragments.remove(f);
    }

    public class MainActivityPagerAdapter extends FragmentPagerAdapter implements MaterialTabs.CustomTabProvider {

        private final String[] TITLES = {"Tabs", "Ripple"};

        private final int[] ICONS = {R.drawable.ic_tabs, R.drawable.ic_ripple};

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
                    return new TabsSettingsFragment();
                case 1:
                    return new RippleSettingsFragment();
            }
        }

        @Override
        public View getCustomTabView(ViewGroup parent, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageDrawable(MainActivity.this.getResources().getDrawable(ICONS[position]));
            return imageView;
        }
    }


    public static class MeDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_me, container, false);
            TextView githubTextView = (TextView) view.findViewById(R.id.github_text_view);
            TextView twitterTextView = (TextView) view.findViewById(R.id.twitter_text_view);

            String link = getString(R.string.github);
            Spannable spannable = new SpannableString(link);
            spannable.setSpan(new URLSpan(getString(R.string.github_link)), 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            githubTextView.setText(spannable, TextView.BufferType.SPANNABLE);
            githubTextView.setMovementMethod(LinkMovementMethod.getInstance());

            link = getString(R.string.twitter);
            spannable = new SpannableString(link);
            spannable.setSpan(new URLSpan(getString(R.string.twitter_link)), 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            twitterTextView.setText(spannable, TextView.BufferType.SPANNABLE);
            twitterTextView.setMovementMethod(LinkMovementMethod.getInstance());

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return view;
        }
    }
}
