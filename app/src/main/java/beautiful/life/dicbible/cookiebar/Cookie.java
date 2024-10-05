package beautiful.life.dicbible.cookiebar;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import beautiful.life.dicbible.R;

final class Cookie extends LinearLayout implements View.OnTouchListener {
    //Used for debug purposes
    //private static int cookieCounter = 0;
    //private int cookieId;

    private Animation slideOutAnimation;
    private ViewGroup layoutCookie;
    private TextView titleTextView;
    private TextView messageTextView;
    private ImageView iconImageView;
    private int layoutGravity = Gravity.BOTTOM;
    private float initialDragX;
    private float dismissOffsetThreshold;
    private float viewWidth;
    private boolean swipedOut;
    private int animationInTop;
    private int animationInBottom;
    private int animationOutTop;
    private int animationOutBottom;
    private boolean isSwipeable;
    private CookieBarDismissListener dismissListener;
    private boolean timeOutDismiss;
    private boolean isCookieRemovalInProgress;
    private final Handler handler = new Handler();

    public Cookie(@NonNull final Context context) {
        this(context, null);
//        cookieId = ++cookieCounter;
    }

    public Cookie(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Cookie(@NonNull final Context context, @Nullable final AttributeSet attrs,
                  final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getLayoutGravity() {
        return layoutGravity;
    }

    private void initViews(@LayoutRes int rootView, CookieBar.CustomViewInitializer viewInitializer) {
//        if (rootView != 0) {
//            inflate(getContext(), rootView, this);
//            if (viewInitializer != null) {
//                viewInitializer.initView(getChildAt(0));
//            }
//        } else {
            inflate(getContext(), R.layout.exit_app, this);
//        }

        if (getChildAt(0).getLayoutParams() instanceof LayoutParams) {
            LayoutParams lp = (LayoutParams) (getChildAt(0).getLayoutParams());
            lp.gravity = Gravity.BOTTOM;
        }

        layoutCookie = findViewById(R.id.cookie);
        titleTextView = findViewById(R.id.exit_title);
        messageTextView = findViewById(R.id.exit_message);
        iconImageView = findViewById(R.id.iv_icon);

        if(rootView == 0) {
            validateLayoutIntegrity();
            initDefaultStyle(getContext());
        }
        layoutCookie.setOnTouchListener(this);
    }

    private void validateLayoutIntegrity() {
        if (layoutCookie == null || titleTextView == null || messageTextView == null ||
                iconImageView == null) {

            throw new RuntimeException("Your custom cookie view is missing one of the default required views");
        }
    }


    /**
     * Init the default text color or background color. You can change the default style by set the
     * Theme's attributes.
     * <p>
     * <pre>
     *  <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
     *          <item name="cookieTitleColor">@color/default_title_color</item>
     *          <item name="cookieMessageColor">@color/default_message_color</item>
     *          <item name="cookieActionColor">@color/default_action_color</item>
     *          <item name="cookieBackgroundColor">@color/default_bg_color</item>
     *  </style>
     * </pre>
     */
    private void initDefaultStyle(Context context) {
//        int titleColor = ThemeResolver.getColor(context, R.attr.cookieTitleColor, Color.WHITE);
//        int messageColor = ThemeResolver.getColor(context, R.attr.cookieMessageColor, Color.WHITE);
//        int actionColor = ThemeResolver.getColor(context, R.attr.cookieActionColor, Color.WHITE);
//        int backgroundColor = ThemeResolver.getColor(context, R.attr.cookieBackgroundColor,
//                ContextCompat.getColor(context, R.color.bibleColorFore));

        int titleColor = ContextCompat.getColor(context,R.color.cookie_title_color);
        int messageColor = ContextCompat.getColor(context,R.color.cookie_message_color);
        int backgroundColor = ContextCompat.getColor(context,R.color.cookie_back_color);

        titleTextView.setTextColor(titleColor);
        messageTextView.setTextColor(messageColor);
        layoutCookie.setBackgroundColor(backgroundColor);
    }

    public void setParams(final CookieBar.Params params) {
        initViews(params.customViewResource, params.viewInitializer);

        long duration = params.duration;
        layoutGravity = params.cookiePosition;
        animationInTop = params.animationInTop;
        animationInBottom = params.animationInBottom;
        animationOutTop = params.animationOutTop;
        animationOutBottom = params.animationOutBottom;
        isSwipeable = params.enableSwipeToDismiss;
        boolean isAutoDismissEnabled = params.enableAutoDismiss;
        dismissListener = params.dismissListener;

        if (params.iconResId != 0 && iconImageView != null) {
            iconImageView.setVisibility(VISIBLE);
            iconImageView.setBackgroundResource(params.iconResId);
            if (params.iconAnimator != null) {
                params.iconAnimator.setTarget(iconImageView);
                params.iconAnimator.start();
            }
        }

        if (titleTextView != null && !TextUtils.isEmpty(params.title)) {
            titleTextView.setVisibility(VISIBLE);
            titleTextView.setText(params.title);
            if (params.titleColor != 0) {
                titleTextView.setTextColor(ContextCompat.getColor(getContext(), params.titleColor));
            }
            setDefaultTextSize(titleTextView, R.attr.cookieTitleSize);
        }

        if (messageTextView != null && !TextUtils.isEmpty(params.message)) {
            messageTextView.setVisibility(VISIBLE);
            messageTextView.setText(params.message);
            if (params.messageColor != 0) {
                messageTextView.setTextColor(ContextCompat.getColor(getContext(), params.messageColor));
            }
            setDefaultTextSize(messageTextView, R.attr.cookieMessageSize);
        }

        if (params.backgroundColor != 0) {
            layoutCookie
                    .setBackgroundColor(ContextCompat.getColor(getContext(), params.backgroundColor));
        }

        int defaultPadding = getContext().getResources().getDimensionPixelSize(R.dimen.cookie_padding);
        int padding = ThemeResolver.getDimen(getContext(), R.attr.cookiePadding, defaultPadding);

        if (layoutGravity == Gravity.BOTTOM) {
            layoutCookie.setPadding(padding, padding, padding, padding);
        }

        createInAnim();
        createOutAnim();

//        Log.i("Cookiebar", "Dismiss delay activated for " + cookieId);
        if (isAutoDismissEnabled) {
            handler.postDelayed(() -> {
                timeOutDismiss = true;
                dismiss();
            }, duration);
        }
    }

    public boolean isRemovalInProgress() {
        return isCookieRemovalInProgress;
    }

    private void setDefaultTextSize(TextView textView, @AttrRes int attr) {
        float size = ThemeResolver.getDimen(getContext(), attr, 0);
        if (size > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        viewWidth = getWidth();
        dismissOffsetThreshold = viewWidth / 3;

        if (layoutGravity == Gravity.TOP) {
            super.onLayout(changed, l, 0, r, layoutCookie.getMeasuredHeight());
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private void createInAnim() {
        int animationResId = layoutGravity == Gravity.BOTTOM ? animationInBottom : animationInTop;
        Animation slideInAnimation = AnimationUtils.loadAnimation(getContext(), animationResId);
        setAnimation(slideInAnimation);
    }

    private void createOutAnim() {
        int animationResId = layoutGravity == Gravity.BOTTOM ? animationOutBottom : animationOutTop;
        slideOutAnimation = AnimationUtils.loadAnimation(getContext(), animationResId);
    }

    public void dismiss() {
        dismiss(null);
    }

    public CookieBarDismissListener getDismissListener() {
        return dismissListener;
    }

    public void dismiss(final CookieBarDismissListener listener) {
        isCookieRemovalInProgress = true;
//        Log.i("CookieBar", "Removing cookie #" +  cookieId);
        handler.removeCallbacksAndMessages(null);
        if(listener != null) {
            dismissListener = listener;
        }

        if (swipedOut) {
            removeFromParent();
            cookieListenerDismiss(CookieBarDismissListener.DismissType.USER_DISMISS);
            return;
        }

        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                setVisibility(View.GONE);
                removeFromParent();
                cookieListenerDismiss(getDismissType());
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // no implementation
            }
        });

        startAnimation(slideOutAnimation);
    }

    void silentDismiss() {
//        Log.i("Cookiebar", "Removing stale cooke " + cookieId);
        isCookieRemovalInProgress = true;
        handler.removeCallbacksAndMessages(null);
        cookieListenerDismiss(CookieBarDismissListener.DismissType.REPLACE_DISMISS);
        removeFromParent();
    }

    private int getDismissType() {
        int dismissType = CookieBarDismissListener.DismissType.PROGRAMMATIC_DISMISS;
        if(timeOutDismiss) {
            dismissType = CookieBarDismissListener.DismissType.DURATION_COMPLETE;
        }
        return dismissType;
    }

    private void cookieListenerDismiss(@CookieBarDismissListener.DismissType int dismissType) {
        if (dismissListener != null) {
            dismissListener.onDismiss(dismissType);
        }
    }

    private void removeFromParent() {
        handler.postDelayed(() -> {
            ViewParent parent = getParent();
            if (parent != null) {
                Cookie.this.clearAnimation();
                ((ViewGroup) parent).removeView(Cookie.this);
            }
        }, 200);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!isSwipeable) {
            return true;
        }

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialDragX = motionEvent.getRawX();
                return true;

            case MotionEvent.ACTION_UP:
                if (!swipedOut) {
                    view.animate()
                            .x(0)
                            .alpha(1)
                            .setDuration(200)
                            .start();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (swipedOut) {
                    return true;
                }
                float offset = motionEvent.getRawX() - initialDragX;
                float alpha = 1 - Math.abs(offset / viewWidth);
                long duration = 0;

                if (Math.abs(offset) > dismissOffsetThreshold) {
                    offset = viewWidth * Math.signum(offset);
                    alpha = 0;
                    duration = 200;
                    swipedOut = true;
                }

                view.animate()
                        .setListener(swipedOut ? getDestroyListener() : null)
                        .x(offset)
                        .alpha(alpha)
                        .setDuration(duration)
                        .start();

                return true;

            default:
                return false;
        }
    }

    private Animator.AnimatorListener getDestroyListener() {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                // no implementation
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                // no implementation
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // no implementation
            }
        };
    }
}