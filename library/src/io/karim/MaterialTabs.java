/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.karim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import io.karim.materialtabs.R;

public class MaterialTabs extends HorizontalScrollView {

    private static final String TAG = MaterialTabs.class.getSimpleName();

    public interface CustomTabProvider {
        View getCustomTabView(ViewGroup parent, int position);
    }

    public interface OnTabReselectedListener {
        void onTabReselected(int position);
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    private static final int[] ATTRS = new int[]{android.R.attr.textColorPrimary, android.R.attr.textSize, android.R.attr.textColor,
            android.R.attr.padding, android.R.attr.paddingLeft, android.R.attr.paddingRight};

    private final PagerAdapterObserver adapterObserver = new PagerAdapterObserver();

    // These indexes must be related to the ATTR array above.
    private static final int TEXT_COLOR_PRIMARY = 0;
    private static final int TEXT_SIZE_INDEX = 1;
    private static final int TEXT_COLOR_INDEX = 2;
    private static final int PADDING_INDEX = 3;
    private static final int PADDING_LEFT_INDEX = 4;
    private static final int PADDING_RIGHT_INDEX = 5;

    private final LinearLayout.LayoutParams defaultTabLayoutParams;
    private final LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    private OnTabSelectedListener tabSelectedListener = null;
    private OnTabReselectedListener tabReselectedListener = null;
    public OnPageChangeListener delegatePageListener;

    private final LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private final Paint rectPaint;

    private int indicatorColor;
    private int indicatorHeight = 2;

    private int underlineHeight = 0;
    private int underlineColor;

    private int tabPadding = 12;
    private int tabTextSize = 14;

    private int tabTextColorUnselected;
    private int tabTextColorSelected;

    private int paddingLeft = 0;
    private int paddingRight = 0;

    private boolean sameWeightTabs = false;
    private boolean textAllCaps = true;
    private boolean isPaddingMiddle = false;

    private Typeface tabTypeface = null;
    private int tabTypefaceUnselectedStyle = Typeface.BOLD;
    private int tabTypefaceSelectedStyle = Typeface.BOLD;

    private int scrollOffset;
    private int lastScrollX = 0;

    // Fields from MaterialRippleLayout
    private int rippleColor;
    private int rippleHighlightColor;
    private boolean rippleOverlay;
    private float rippleDiameterDp;
    private int rippleDuration;
    private float rippleAlphaFloat;
    private boolean rippleDelayClick;
    private int rippleFadeDuration;
    private boolean ripplePersistent;
    private boolean rippleInAdapter;
    private float rippleRoundedCornersDp;
    //~ Fields from MaterialRippleLayout

    private Locale locale;

    public MaterialTabs(Context context) {
        this(context, null);
    }

    public MaterialTabs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialTabs(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);
        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // Get system attrs (android:textSize and android:textColor).
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        tabTextSize = a.getDimensionPixelSize(TEXT_SIZE_INDEX, tabTextSize);
        int textPrimaryColor = a.getColor(TEXT_COLOR_PRIMARY, android.R.color.white);
        tabTextColorUnselected = a.getColor(TEXT_COLOR_INDEX, textPrimaryColor);

        underlineColor = textPrimaryColor;
        indicatorColor = textPrimaryColor;
        int padding = a.getDimensionPixelSize(PADDING_INDEX, 0);
        paddingLeft = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_LEFT_INDEX, 0);
        paddingRight = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_RIGHT_INDEX, 0);
        a.recycle();

        a = context.obtainStyledAttributes(attrs, R.styleable.MaterialTabs);

        // Get custom attrs of MaterialTabs.
        indicatorColor = a.getColor(R.styleable.MaterialTabs_mtIndicatorColor, indicatorColor);
        underlineColor = a.getColor(R.styleable.MaterialTabs_mtUnderlineColor, underlineColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.MaterialTabs_mtIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.MaterialTabs_mtUnderlineHeight, underlineHeight);
        tabPadding = a.getDimensionPixelSize(R.styleable.MaterialTabs_mtTabPaddingLeftRight, tabPadding);
        sameWeightTabs = a.getBoolean(R.styleable.MaterialTabs_mtSameWeightTabs, sameWeightTabs);
        textAllCaps = a.getBoolean(R.styleable.MaterialTabs_mtTextAllCaps, textAllCaps);
        isPaddingMiddle = a.getBoolean(R.styleable.MaterialTabs_mtPaddingMiddle, isPaddingMiddle);
        tabTypefaceUnselectedStyle = a.getInt(R.styleable.MaterialTabs_mtTextUnselectedStyle, Typeface.BOLD);
        tabTypefaceSelectedStyle = a.getInt(R.styleable.MaterialTabs_mtTextSelectedStyle, Typeface.BOLD);
        tabTextColorSelected = a.getColor(R.styleable.MaterialTabs_mtTextColorSelected, textPrimaryColor);

        // Get custom attrs of MaterialRippleLayout.
        rippleColor = a.getColor(R.styleable.MaterialTabs_mtMrlRippleColor, MaterialRippleLayout.DEFAULT_COLOR);
        // Making default ripple highlight color the same as rippleColor but with 1/4 the alpha.
        rippleHighlightColor = Color.argb((int) (Color.alpha(rippleColor) * 0.25), Color.red(rippleColor), Color.green(rippleColor),
                Color.blue(rippleColor));
        rippleHighlightColor = a.getColor(R.styleable.MaterialTabs_mtMrlRippleHighlightColor, rippleHighlightColor);
        rippleDiameterDp = a.getDimension(R.styleable.MaterialTabs_mtMrlRippleDiameter, MaterialRippleLayout.DEFAULT_DIAMETER_DP);
        rippleOverlay = a.getBoolean(R.styleable.MaterialTabs_mtMrlRippleOverlay, MaterialRippleLayout.DEFAULT_RIPPLE_OVERLAY);
        rippleDuration = a.getInt(R.styleable.MaterialTabs_mtMrlRippleDuration, MaterialRippleLayout.DEFAULT_DURATION);
        rippleAlphaFloat = a.getFloat(R.styleable.MaterialTabs_mtMrlRippleAlpha, MaterialRippleLayout.DEFAULT_ALPHA);
        rippleDelayClick = a.getBoolean(R.styleable.MaterialTabs_mtMrlRippleDelayClick, MaterialRippleLayout.DEFAULT_DELAY_CLICK);
        rippleFadeDuration = a.getInteger(R.styleable.MaterialTabs_mtMrlRippleFadeDuration, MaterialRippleLayout.DEFAULT_FADE_DURATION);
        ripplePersistent = a.getBoolean(R.styleable.MaterialTabs_mtMrlRipplePersistent, MaterialRippleLayout.DEFAULT_PERSISTENT);
        rippleInAdapter = a.getBoolean(R.styleable.MaterialTabs_mtMrlRippleInAdapter, MaterialRippleLayout.DEFAULT_SEARCH_ADAPTER);
        rippleRoundedCornersDp = a.getDimension(R.styleable.MaterialTabs_mtMrlRippleRoundedCorners, MaterialRippleLayout.DEFAULT_ROUNDED_CORNERS_DP);

        a.recycle();

        setMarginBottomTabContainer();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    private void setMarginBottomTabContainer() {
        ViewGroup.MarginLayoutParams mlp = (MarginLayoutParams) tabsContainer.getLayoutParams();
        int bottomMargin = indicatorHeight >= underlineHeight ? indicatorHeight : underlineHeight;
        mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, bottomMargin);
        tabsContainer.setLayoutParams(mlp);
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);
        pager.getAdapter().registerDataSetObserver(adapterObserver);
        adapterObserver.setAttached(true);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        tabCount = pager.getAdapter().getCount();
        View tabView;
        for (int i = 0; i < tabCount; i++) {
            if (pager.getAdapter() instanceof CustomTabProvider) {
                tabView = ((CustomTabProvider) pager.getAdapter()).getCustomTabView(this, i);
            } else {
                tabView = LayoutInflater.from(getContext()).inflate(R.layout.mt_tab, this, false);
            }

            MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.on(tabView)
                                                                            .rippleAlpha(rippleAlphaFloat)
                                                                            .rippleColor(rippleColor)
                                                                            .rippleDelayClick(rippleDelayClick)
                                                                            .rippleDiameterDp(rippleDiameterDp)
                                                                            .rippleDuration(rippleDuration)
                                                                            .rippleFadeDuration(rippleFadeDuration)
                                                                            .rippleHighlightColor(rippleHighlightColor)
                                                                            .rippleInAdapter(rippleInAdapter)
                                                                            .rippleOverlay(rippleOverlay)
                                                                            .ripplePersistent(ripplePersistent)
                                                                            .rippleRoundedCornersDp(rippleRoundedCornersDp)
                                                                            .create();

            CharSequence title = pager.getAdapter().getPageTitle(i);

            addTab(i, title, materialRippleLayout);
        }

        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    private void addTab(final int position, CharSequence title, View tabView) {
        TextView textView = (TextView) tabView.findViewById(R.id.mt_tab_title);
        if (textView != null) {
            if (title != null) {
                textView.setText(title);
            }
        }

        tabView.setFocusable(true);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectedListener != null) {
                    tabSelectedListener.onTabSelected(position);
                }

                if (pager.getCurrentItem() != position) {
                    View tab = tabsContainer.getChildAt(pager.getCurrentItem());
                    notSelected(tab);
                    pager.setCurrentItem(position);
                } else if (tabReselectedListener != null) {
                    tabReselectedListener.onTabReselected(position);
                }
            }
        });

        tabsContainer.addView(tabView, position, sameWeightTabs ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View v = tabsContainer.getChildAt(i);
            v.setPadding(tabPadding, v.getPaddingTop(), tabPadding, v.getPaddingBottom());
            TextView tab_title = (TextView) v.findViewById(R.id.mt_tab_title);

            if (tab_title != null) {
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tab_title.setTextColor(tabTextColorUnselected);
                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a pre-ICS-build.
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab_title.setAllCaps(true);
                    } else {
                        tab_title.setText(tab_title.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {
        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            // Half screen offset. Either tabs start at the middle of the view scrolling straight away or tabs start at the beginning (no padding)
            // scrolling when indicator gets to the middle of the view width.
            newScrollX -= scrollOffset;
            Pair<Float, Float> lines = getIndicatorCoordinates();
            newScrollX += ((lines.second - lines.first) / 2);
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    private Pair<Float, Float> getIndicatorCoordinates() {
        // Default: line is below current tab.
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // If there is an offset, start interpolating left and right coordinates between current and next tab.
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }
        return new Pair<>(lineLeft, lineRight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isPaddingMiddle || paddingLeft > 0 || paddingRight > 0) {
            // Make sure tabContainer is bigger than the HorizontalScrollView to be able to scroll.
            tabsContainer.setMinimumWidth(getWidth());
            // Clipping padding to false to see the tabs while we pass them swiping.
            setClipToPadding(false);
        }

        if (tabsContainer.getChildCount() > 0) {
            tabsContainer.getChildAt(0).getViewTreeObserver().addOnGlobalLayoutListener(firstTabGlobalLayoutListener);
        }
        super.onLayout(changed, l, t, r, b);
    }

    private final OnGlobalLayoutListener firstTabGlobalLayoutListener = new OnGlobalLayoutListener() {

        @SuppressWarnings("deprecation")
        @Override
        public void onGlobalLayout() {
            View view = tabsContainer.getChildAt(0);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            if (isPaddingMiddle) {
                int mHalfWidthFirstTab = view.getWidth() / 2;
                paddingLeft = paddingRight = getWidth() / 2 - mHalfWidthFirstTab;
            }
            setPadding(paddingLeft, getPaddingTop(), paddingRight, getPaddingBottom());
            if (scrollOffset == 0) {
                scrollOffset = getWidth() / 2 - paddingLeft;
            }

            currentPosition = pager.getCurrentItem();
            currentPositionOffset = 0f;
            scrollToChild(currentPosition, 0);
            updateSelection(currentPosition);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();
        // Draw indicator line.
        rectPaint.setColor(indicatorColor);
        Pair<Float, Float> lines = getIndicatorCoordinates();
        canvas.drawRect(lines.first + paddingLeft, height - indicatorHeight, lines.second + paddingLeft, height, rectPaint);
        // Draw underline.
        rectPaint.setColor(underlineColor);
        canvas.drawRect(paddingLeft, height - underlineHeight, tabsContainer.getWidth() + paddingRight, height, rectPaint);
    }

    public void setOnTabReselectedListener(OnTabReselectedListener tabReselectedListener) {
        this.tabReselectedListener = tabReselectedListener;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            int offset = tabCount > 0 ? (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()) : 0;
            scrollToChild(position, offset);
            invalidate();
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            // No transparency for current item.
            View currentTab = tabsContainer.getChildAt(pager.getCurrentItem());
            selected(currentTab);

            // Half transparent for prev item.
            if (pager.getCurrentItem() - 1 >= 0) {
                View prevTab = tabsContainer.getChildAt(pager.getCurrentItem() - 1);
                notSelected(prevTab);
            }

            // Half transparent for next item.
            if (pager.getCurrentItem() + 1 <= pager.getAdapter().getCount() - 1) {
                View nextTab = tabsContainer.getChildAt(pager.getCurrentItem() + 1);
                notSelected(nextTab);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            updateSelection(position);
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }

    }

    private void updateSelection(int position) {
        for (int i = 0; i < tabCount; ++i) {
            View tv = tabsContainer.getChildAt(i);
            final boolean selected = i == position;
            tv.setSelected(selected);
            if (selected) {
                selected(tv);
            } else {
                notSelected(tv);
            }
        }
    }

    private void notSelected(View tab) {
        if (tab != null) {
            TextView title = (TextView) tab.findViewById(R.id.mt_tab_title);
            if (title != null) {
                title.setTypeface(tabTypeface, tabTypefaceUnselectedStyle);
                title.setTextColor(tabTextColorUnselected);
            }
        }
    }

    private void selected(View tab) {
        if (tab != null) {
            TextView title = (TextView) tab.findViewById(R.id.mt_tab_title);
            if (title != null) {
                title.setTypeface(tabTypeface, tabTypefaceSelectedStyle);
                title.setTextColor(tabTextColorSelected);
            }
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {

        private boolean attached = false;

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        public void setAttached(boolean attached) {
            this.attached = attached;
        }

        public boolean isAttached() {
            return attached;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (pager != null) {
            if (!adapterObserver.isAttached()) {
                pager.getAdapter().registerDataSetObserver(adapterObserver);
                adapterObserver.setAttached(true);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (pager != null) {
            if (adapterObserver.isAttached()) {
                pager.getAdapter().unregisterDataSetObserver(adapterObserver);
                adapterObserver.setAttached(false);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        if (currentPosition != 0 && tabsContainer.getChildCount() > 0) {
            notSelected(tabsContainer.getChildAt(0));
            selected(tabsContainer.getChildAt(currentPosition));
        }
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /** Getters * */

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public boolean getSameWeightTabs() {
        return sameWeightTabs;
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public int getTextColor() {
        return tabTextColorUnselected;
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    /** Setters * */

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public void setSameWeightTabs(boolean sameWeightTabs) {
        this.sameWeightTabs = sameWeightTabs;
        if (pager != null) {
            requestLayout();
        }
    }

    public void setPaddingMiddle(boolean isPaddingMiddle) {
        this.isPaddingMiddle = isPaddingMiddle;
        if (pager != null) {
            requestLayout();
        }
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public void setTextColorUnselected(int textColor) {
        this.tabTextColorUnselected = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        setTextColorUnselected(getResources().getColor(resId));
    }

    public void setTextColorSelected(int textColorSelected) {
        this.tabTextColorSelected = textColorSelected;
        invalidate();
    }

    public void setTextColorSelectedResource(int resId) {
        setTextColorSelected(getResources().getColor(resId));
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceSelectedStyle = style;
        updateTabStyles();
    }

    public void setTabTypefaceUnselectedStyle(int tabTypefaceUnselectedStyle) {
        this.tabTypefaceUnselectedStyle = tabTypefaceUnselectedStyle;
        invalidate();
    }

    public void setTabTypefaceSelectedStyle(int tabTypefaceSelectedStyle) {
        this.tabTypefaceSelectedStyle = tabTypefaceSelectedStyle;
        invalidate();
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }


    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
        notifyDataSetChanged();
    }

    public void setRippleHighlightColor(int rippleHighlightColor) {
        this.rippleHighlightColor = rippleHighlightColor;
        notifyDataSetChanged();
    }

    public void setRippleOverlay(boolean rippleOverlay) {
        this.rippleOverlay = rippleOverlay;
        notifyDataSetChanged();
    }

    public void setRippleDiameterDp(float rippleDiameterDp) {
        this.rippleDiameterDp = rippleDiameterDp;
        notifyDataSetChanged();
    }

    public void setRippleDuration(int rippleDuration) {
        this.rippleDuration = rippleDuration;
        notifyDataSetChanged();
    }

    public void setRippleAlphaFloat(float rippleAlphaFloat) {
        this.rippleAlphaFloat = rippleAlphaFloat;
        notifyDataSetChanged();
    }

    public void setRippleDelayClick(boolean rippleDelayClick) {
        this.rippleDelayClick = rippleDelayClick;
        notifyDataSetChanged();
    }

    public void setRippleFadeDuration(int rippleFadeDuration) {
        this.rippleFadeDuration = rippleFadeDuration;
        notifyDataSetChanged();
    }

    public void setRipplePersistent(boolean ripplePersistent) {
        this.ripplePersistent = ripplePersistent;
        notifyDataSetChanged();
    }

    public void setRippleInAdapter(boolean rippleInAdapter) {
        this.rippleInAdapter = rippleInAdapter;
        notifyDataSetChanged();
    }

    public void setRippleRoundedCornersDp(float rippleRoundedCornersDp) {
        this.rippleRoundedCornersDp = rippleRoundedCornersDp;
        notifyDataSetChanged();
    }

}
