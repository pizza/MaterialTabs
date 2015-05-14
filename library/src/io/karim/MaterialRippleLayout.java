/*
 * Copyright (C) 2014 Balys Valentukevicius
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

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.util.Property;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import io.karim.materialtabs.R;

import static android.view.GestureDetector.SimpleOnGestureListener;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MaterialRippleLayout extends FrameLayout {

    private static final String TAG = MaterialRippleLayout.class.getSimpleName();

    public static final int DEFAULT_DURATION = 250;
    public static final int DEFAULT_FADE_DURATION = 125;
    public static final float DEFAULT_DIAMETER_DP = 20;
    public static final float DEFAULT_ALPHA = 0.2f;
    public static final int DEFAULT_COLOR = Color.WHITE;
    public static final boolean DEFAULT_DELAY_CLICK = false;
    public static final boolean DEFAULT_PERSISTENT = false;
    public static final boolean DEFAULT_SEARCH_ADAPTER = false;
    public static final boolean DEFAULT_RIPPLE_OVERLAY = false;
    public static final int DEFAULT_ROUNDED_CORNERS_DP = 0;
    public static final int FADE_EXTRA_DELAY = 50;
    public static final long HOVER_DURATION = 2500;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect bounds = new Rect();

    private int rippleColor;
    private int rippleHighlightColor;
    private boolean rippleOverlay;
    private int rippleDiameterPx;
    private int rippleDuration;
    private int rippleAlphaInt;
    private boolean rippleDelayClick;
    private int rippleFadeDuration;
    private boolean ripplePersistent;
    private boolean rippleInAdapter;
    private float rippleRoundedCornersPx;

    private float radius;

    private AdapterView parentAdapter;
    private View childView;

    private AnimatorSet rippleAnimator;
    private ObjectAnimator hoverAnimator;

    private Point currentCoordinates = new Point();
    private Point previousCoordinates = new Point();

    private int layerType;

    private boolean eventCancelled;
    private boolean prepressed;
    private int positionInAdapter;

    private final GestureDetector gestureDetector;
    private PressedEvent pendingPressEvent;

    public static RippleBuilder on(View view) {
        return new RippleBuilder(view);
    }

    public MaterialRippleLayout(Context context) {
        this(context, null, 0);
    }

    public MaterialRippleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Animations
     */
    private final Property<MaterialRippleLayout, Float> radiusProperty = new Property<MaterialRippleLayout, Float>(Float.class, "radius") {
        @Override
        public Float get(MaterialRippleLayout object) {
            return object.getRadius();
        }

        @Override
        public void set(MaterialRippleLayout object, Float value) {
            object.setRadius(value);
        }
    };

    @Override
    public final void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("MaterialRippleLayout can host only one child");
        }
        childView = child;
        super.addView(child, index, params);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (childView == null) {
            throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
        }
        childView.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !findClickableViewInChild(childView, (int) event.getX(), (int) event.getY());
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean superOnTouchEvent = super.onTouchEvent(event);

        if (!isEnabled() || !childView.isEnabled()) {
            return superOnTouchEvent;
        }

        boolean isEventInBounds = bounds.contains((int) event.getX(), (int) event.getY());

        if (isEventInBounds) {
            previousCoordinates.set(currentCoordinates.x, currentCoordinates.y);
            currentCoordinates.set((int) event.getX(), (int) event.getY());
        }

        boolean gestureResult = gestureDetector.onTouchEvent(event);
        if (gestureResult || mHasPerformedLongPress) {
            return true;
        } else {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    if (prepressed) {
                        childView.setPressed(true);
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                childView.setPressed(false);
                            }
                        }, ViewConfiguration.getPressedStateDuration());
                    }

                    if (isEventInBounds) {
                        PerformClickEvent pendingClickEvent = new PerformClickEvent();
                        startRipple(pendingClickEvent);
                        if (!rippleDelayClick) {
                            pendingClickEvent.run();
                        }
                    }

                    cancelPressedEvent();
                    break;
                case MotionEvent.ACTION_DOWN:
                    setBackgroundColor(rippleHighlightColor);

                    setPositionInAdapter();
                    eventCancelled = false;
                    pendingPressEvent = new PressedEvent(event);
                    if (isInScrollingContainer()) {
                        cancelPressedEvent();
                        prepressed = true;
                        postDelayed(pendingPressEvent, ViewConfiguration.getTapTimeout());
                    } else {
                        pendingPressEvent.run();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (rippleInAdapter) {
                        // Do not use current coordinates in adapter since they tend to jump drastically on scroll.
                        currentCoordinates.set(previousCoordinates.x, previousCoordinates.y);
                        previousCoordinates = new Point();
                    }
                    childView.onTouchEvent(event);
                    startRipple(null);
                    cancelPressedEvent();
                    break;
                case MotionEvent.ACTION_MOVE:
                    setBackgroundColor(rippleHighlightColor);

                    if (isEventInBounds && !eventCancelled) {
                        invalidate();
                    } else if (!isEventInBounds) {
                        startRipple(null);
                    }

                    if (!isEventInBounds) {
                        cancelPressedEvent();
                        if (hoverAnimator != null) {
                            hoverAnimator.cancel();
                        }
                        childView.onTouchEvent(event);
                        eventCancelled = true;
                    }
                    break;
            }
            return true;
        }
    }

    private void cancelPressedEvent() {
        if (pendingPressEvent != null) {
            removeCallbacks(pendingPressEvent);
            prepressed = false;
        }
    }

    private boolean mHasPerformedLongPress;

    private void startHover() {
        if (eventCancelled) {
            return;
        }

        if (hoverAnimator != null) {
            hoverAnimator.cancel();
        }
        final float radius = (float) (Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) * 1.2f);
        hoverAnimator = ObjectAnimator.ofFloat(this, radiusProperty, rippleDiameterPx, radius).setDuration(HOVER_DURATION);
        hoverAnimator.setInterpolator(new LinearInterpolator());
        hoverAnimator.start();
    }

    private void startRipple(final Runnable animationEndRunnable) {
        if (eventCancelled) {
            return;
        }

        float endRadius = getEndRadius();

        cancelAnimations();

        rippleAnimator = new AnimatorSet();
        rippleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!ripplePersistent) {
                    setRadius(0);
                    setRippleAlphaInt(rippleAlphaInt);
                }
                if (animationEndRunnable != null && rippleDelayClick) {
                    animationEndRunnable.run();
                }
                childView.setPressed(false);

                setBackgroundColor(Color.TRANSPARENT);
            }
        });

        ObjectAnimator ripple = ObjectAnimator.ofFloat(this, radiusProperty, radius, endRadius);
        ripple.setDuration(rippleDuration);
        ripple.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator fade = ObjectAnimator.ofInt(this, circleAlphaProperty, rippleAlphaInt, 0);
        fade.setDuration(rippleFadeDuration);
        fade.setInterpolator(new AccelerateInterpolator());
        fade.setStartDelay(rippleDuration - rippleFadeDuration - FADE_EXTRA_DELAY);

        if (ripplePersistent) {
            rippleAnimator.play(ripple);
        } else if (getRadius() > endRadius) {
            fade.setStartDelay(0);
            rippleAnimator.play(fade);
        } else {
            rippleAnimator.playTogether(ripple, fade);
        }
        rippleAnimator.start();
    }

    private void cancelAnimations() {
        if (rippleAnimator != null) {
            rippleAnimator.cancel();
            rippleAnimator.removeAllListeners();
        }

        if (hoverAnimator != null) {
            hoverAnimator.cancel();
        }
    }

    private final Property<MaterialRippleLayout, Integer> circleAlphaProperty = new Property<MaterialRippleLayout, Integer>(Integer.class,
            "rippleAlphaFloat") {
        @Override
        public Integer get(MaterialRippleLayout object) {
            return object.getRippleAlphaInt();
        }

        @Override
        public void set(MaterialRippleLayout object, Integer value) {
            object.setRippleAlphaInt(value);
        }
    };

    private boolean isInScrollingContainer() {
        ViewParent p = getParent();
        while (p != null && p instanceof ViewGroup) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

                if (((ViewGroup) p).shouldDelayChildPressedState()) {
                    return true;
                }
            }
            p = p.getParent();
        }
        return false;
    }

    private AdapterView findParentAdapterView() {
        if (parentAdapter != null) {
            return parentAdapter;
        }
        ViewParent current = getParent();
        while (true) {
            if (current instanceof AdapterView) {
                parentAdapter = (AdapterView) current;
                return parentAdapter;
            } else {
                try {
                    current = current.getParent();
                } catch (NullPointerException npe) {
                    throw new RuntimeException("Could not find a parent AdapterView");
                }
            }
        }
    }

    private void setPositionInAdapter() {
        if (rippleInAdapter) {
            positionInAdapter = findParentAdapterView().getPositionForView(MaterialRippleLayout.this);
        }
    }

    private boolean adapterPositionChanged() {
        if (rippleInAdapter) {
            int newPosition = findParentAdapterView().getPositionForView(MaterialRippleLayout.this);
            final boolean changed = newPosition != positionInAdapter;
            positionInAdapter = newPosition;
            if (changed) {
                cancelPressedEvent();
                cancelAnimations();
                childView.setPressed(false);
                setRadius(0);
            }
            return changed;
        }
        return false;
    }

    private boolean findClickableViewInChild(View view, int x, int y) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                final Rect rect = new Rect();
                child.getHitRect(rect);

                final boolean contains = rect.contains(x, y);
                if (contains) {
                    return findClickableViewInChild(child, x - rect.left, y - rect.top);
                }
            }
        } else if (view != childView) {
            return (view.isEnabled() && (view.isClickable() || view.isLongClickable() || view.isFocusableInTouchMode()));
        }

        return view.isFocusableInTouchMode();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(0, 0, w, h);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    /**
     * Drawing
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        final boolean positionChanged = adapterPositionChanged();
        if (rippleOverlay) {
            super.draw(canvas);
            if (!positionChanged) {
                if (rippleRoundedCornersPx != 0) {
                    Path clipPath = new Path();
                    RectF rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
                    clipPath.addRoundRect(rect, rippleRoundedCornersPx, rippleRoundedCornersPx, Path.Direction.CW);
                    canvas.clipPath(clipPath);
                }
                canvas.drawCircle(currentCoordinates.x, currentCoordinates.y, radius, paint);
            }
        } else {
            if (!positionChanged) {
                canvas.drawCircle(currentCoordinates.x, currentCoordinates.y, radius, paint);
            }
            super.draw(canvas);
        }
    }

    public MaterialRippleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);
        gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                mHasPerformedLongPress = childView.performLongClick();
                if (mHasPerformedLongPress) {
                    startRipple(null);
                    cancelPressedEvent();
                }
            }

            @Override
            public boolean onDown(MotionEvent e) {
                mHasPerformedLongPress = false;
                return super.onDown(e);
            }
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialRippleLayout);
        rippleColor = a.getColor(R.styleable.MaterialRippleLayout_mrlRippleColor, DEFAULT_COLOR);

        // Making default ripple highlight color the same as rippleColor but with 1/4 the alpha.
        rippleHighlightColor = Color.argb((int) (Color.alpha(rippleColor) * 0.25), Color.red(rippleColor), Color.green(rippleColor),
                Color.blue(rippleColor));
        rippleHighlightColor = a.getColor(R.styleable.MaterialRippleLayout_mrlRippleHighlightColor, rippleHighlightColor);
        rippleDiameterPx = a.getDimensionPixelSize(R.styleable.MaterialRippleLayout_mrlRippleDiameter,
                Utils.dpToPx(getResources(), DEFAULT_DIAMETER_DP));
        rippleOverlay = a.getBoolean(R.styleable.MaterialRippleLayout_mrlRippleOverlay, DEFAULT_RIPPLE_OVERLAY);
        rippleDuration = a.getInt(R.styleable.MaterialRippleLayout_mrlRippleDuration, DEFAULT_DURATION);
        rippleAlphaInt = (int) (255 * a.getFloat(R.styleable.MaterialRippleLayout_mrlRippleAlpha, DEFAULT_ALPHA));
        rippleDelayClick = a.getBoolean(R.styleable.MaterialRippleLayout_mrlRippleDelayClick, DEFAULT_DELAY_CLICK);
        rippleFadeDuration = a.getInteger(R.styleable.MaterialRippleLayout_mrlRippleFadeDuration, DEFAULT_FADE_DURATION);
        ripplePersistent = a.getBoolean(R.styleable.MaterialRippleLayout_mrlRipplePersistent, DEFAULT_PERSISTENT);
        rippleInAdapter = a.getBoolean(R.styleable.MaterialRippleLayout_mrlRippleInAdapter, DEFAULT_SEARCH_ADAPTER);
        rippleRoundedCornersPx = a.getDimensionPixelSize(R.styleable.MaterialRippleLayout_mrlRippleRoundedCorners, DEFAULT_ROUNDED_CORNERS_DP);

        a.recycle();

        paint.setColor(rippleColor);
        paint.setAlpha(rippleAlphaInt);

        enableClipPathSupportIfNecessary();
    }

    private float getRadius() {
        return radius;
    }


    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    private float getEndRadius() {
        final int width = getWidth();
        final int height = getHeight();

        final int halfWidth = width / 2;
        final int halfHeight = height / 2;

        final float radiusX = halfWidth > currentCoordinates.x ? width - currentCoordinates.x : currentCoordinates.x;
        final float radiusY = halfHeight > currentCoordinates.y ? height - currentCoordinates.y : currentCoordinates.y;

        //noinspection SuspiciousNameCombination
        return (float) Math.sqrt(Math.pow(radiusX, 2) + Math.pow(radiusY, 2)) * 1.2f;
    }

    public int getRippleAlphaInt() {
        return paint.getAlpha();
    }

    public void setRippleAlphaInt(Integer alphaInt) {
        paint.setAlpha(alphaInt);
        invalidate();
    }

    /**
     * Accessor
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
        this.rippleHighlightColor = Color.argb((int) (Color.alpha(rippleColor) * 0.25), Color.red(rippleColor), Color.green(rippleColor),
                Color.blue(rippleColor));
        paint.setColor(rippleColor);
        paint.setAlpha(rippleAlphaInt);
        invalidate();
    }

    public void setRippleHighlightColor(int rippleHighlightColor) {
        this.rippleHighlightColor = rippleHighlightColor;
        invalidate();
    }

    public void setRippleOverlay(boolean rippleOverlay) {
        this.rippleOverlay = rippleOverlay;
    }

    public void setRippleDiameterPx(int rippleDiameterPx) {
        this.rippleDiameterPx = rippleDiameterPx;
    }

    public void setRippleDuration(int rippleDuration) {
        this.rippleDuration = rippleDuration;
    }

    public void setRippleDelayClick(boolean rippleDelayClick) {
        this.rippleDelayClick = rippleDelayClick;
    }

    public void setRippleFadeDuration(int rippleFadeDuration) {
        this.rippleFadeDuration = rippleFadeDuration;
    }

    public void setRipplePersistent(boolean ripplePersistent) {
        this.ripplePersistent = ripplePersistent;
    }

    public void setRippleInAdapter(boolean rippleInAdapter) {
        this.rippleInAdapter = rippleInAdapter;
    }

    public void setRippleRoundedCornersPx(int rippleRoundedCornerPx) {
        this.rippleRoundedCornersPx = rippleRoundedCornerPx;
        enableClipPathSupportIfNecessary();
    }

    public void setDefaultRippleAlphaInt(int alphaInt) {
        this.rippleAlphaInt = alphaInt;
        paint.setAlpha(alphaInt);
        invalidate();
    }

    public void performRipple() {
        currentCoordinates = new Point(getWidth() / 2, getHeight() / 2);
        startRipple(null);
    }

    public void performRipple(Point anchor) {
        currentCoordinates = new Point(anchor.x, anchor.y);
        startRipple(null);
    }

    /**
     * {@link Canvas#clipPath(Path)} is not supported in hardware accelerated layers before API 18. Use software layer instead
     * <p/>
     * https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
     */
    private void enableClipPathSupportIfNecessary() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (rippleRoundedCornersPx != 0) {
                layerType = getLayerType();
                setLayerType(LAYER_TYPE_SOFTWARE, null);
            } else {
                setLayerType(layerType, null);
            }
        }
    }

    /**
     * Helper
     */
    private class PerformClickEvent implements Runnable {

        @Override
        public void run() {
            if (mHasPerformedLongPress) {
                return;
            }

            // If parent is an AdapterView, try to call its ItemClickListener.
            if (getParent() instanceof AdapterView) {
                clickAdapterView((AdapterView) getParent());
            } else if (rippleInAdapter) {
                // Find adapter view.
                clickAdapterView(findParentAdapterView());
            } else {
                // Otherwise, just perform click on child.
                childView.performClick();
            }
        }

        private void clickAdapterView(AdapterView parent) {
            final int position = parent.getPositionForView(MaterialRippleLayout.this);
            final long itemId = parent.getAdapter() != null ? parent.getAdapter().getItemId(position) : 0;
            if (position != AdapterView.INVALID_POSITION) {
                parent.performItemClick(MaterialRippleLayout.this, position, itemId);
            }
        }
    }

    private final class PressedEvent implements Runnable {

        private final MotionEvent event;

        public PressedEvent(MotionEvent event) {
            this.event = event;
        }

        @Override
        public void run() {
            prepressed = false;

            // Prevent the child's long click, let the ripple layout call its performLongClick
            childView.setLongClickable(false);
            childView.onTouchEvent(event);
            childView.setPressed(true);
            startHover();
        }
    }

    /**
     * Builder
     */
    public static class RippleBuilder {

        private final Context context;
        private final View child;

        private int rippleColor = DEFAULT_COLOR;
        private int rippleHighlightColor;
        private boolean rippleOverlay = DEFAULT_RIPPLE_OVERLAY;
        private float rippleDiameterDp = DEFAULT_DIAMETER_DP;
        private int rippleDuration = DEFAULT_DURATION;
        private float rippleAlphaFloat = DEFAULT_ALPHA;
        private boolean rippleDelayClick = DEFAULT_DELAY_CLICK;
        private int rippleFadeDuration = DEFAULT_FADE_DURATION;
        private boolean ripplePersistent = DEFAULT_PERSISTENT;
        private boolean rippleSearchAdapter = DEFAULT_SEARCH_ADAPTER;
        private float rippleRoundedCornerDp = DEFAULT_ROUNDED_CORNERS_DP;

        public RippleBuilder(View child) {
            this.child = child;
            this.context = child.getContext();
        }

        public RippleBuilder rippleColor(int color) {
            this.rippleColor = color;
            this.rippleHighlightColor = Color.argb((int) (Color.alpha(rippleColor) * 0.25), Color.red(rippleColor), Color.green(rippleColor),
                    Color.blue(rippleColor));
            return this;
        }

        /**
         * Make sure to call this after rippleColor() in the Builder.
         */
        public RippleBuilder rippleHighlightColor(int highlightColor) {
            this.rippleHighlightColor = highlightColor;
            return this;
        }

        public RippleBuilder rippleOverlay(boolean overlay) {
            this.rippleOverlay = overlay;
            return this;
        }

        public RippleBuilder rippleDiameterDp(float diameterDp) {
            this.rippleDiameterDp = diameterDp;
            return this;
        }

        public RippleBuilder rippleDuration(int duration) {
            this.rippleDuration = duration;
            return this;
        }

        /**
         * @param alpha value between 0 and 1
         */
        public RippleBuilder rippleAlpha(float alpha) {
            this.rippleAlphaFloat = alpha;
            return this;
        }

        public RippleBuilder rippleDelayClick(boolean delayClick) {
            this.rippleDelayClick = delayClick;
            return this;
        }

        public RippleBuilder rippleFadeDuration(int fadeDuration) {
            this.rippleFadeDuration = fadeDuration;
            return this;
        }

        public RippleBuilder ripplePersistent(boolean persistent) {
            this.ripplePersistent = persistent;
            return this;
        }

        public RippleBuilder rippleInAdapter(boolean inAdapter) {
            this.rippleSearchAdapter = inAdapter;
            return this;
        }

        public RippleBuilder rippleRoundedCornersDp(float radiusDp) {
            this.rippleRoundedCornerDp = radiusDp;
            return this;
        }

        public MaterialRippleLayout create() {
            MaterialRippleLayout layout = new MaterialRippleLayout(context);
            layout.setRippleColor(rippleColor);
            layout.setDefaultRippleAlphaInt((int) (255 * rippleAlphaFloat));
            layout.setRippleDelayClick(rippleDelayClick);
            layout.setRippleDiameterPx(Utils.dpToPx(context.getResources(), rippleDiameterDp));
            layout.setRippleDuration(rippleDuration);
            layout.setRippleFadeDuration(rippleFadeDuration);
            layout.setRippleHighlightColor(rippleHighlightColor);
            layout.setRipplePersistent(ripplePersistent);
            layout.setRippleOverlay(rippleOverlay);
            layout.setRippleInAdapter(rippleSearchAdapter);
            layout.setRippleRoundedCornersPx(Utils.dpToPx(context.getResources(), rippleRoundedCornerDp));

            ViewGroup.LayoutParams params = child.getLayoutParams();
            ViewGroup parent = (ViewGroup) child.getParent();
            int index = 0;

            if (parent != null && parent instanceof MaterialRippleLayout) {
                throw new IllegalStateException("MaterialRippleLayout could not be created: parent of the view already is a MaterialRippleLayout");
            }

            if (parent != null) {
                index = parent.indexOfChild(child);
                parent.removeView(child);
            }

            layout.addView(child, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            if (parent != null) {
                parent.addView(layout, index, params);
            }

            return layout;
        }
    }
}
