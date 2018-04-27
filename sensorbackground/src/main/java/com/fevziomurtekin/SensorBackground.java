package com.fevziomurtekin;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class SensorBackground extends ImageView {

    // Image's scroll orientation
    public final static byte ORIENTATION_NONE = -1;
    public final static byte ORIENTATION_HORIZONTAL = 0;
    public final static byte ORIENTATION_VERTICAL = 1;
    private byte mOrientation = ORIENTATION_NONE;

    // Enable splash screen
    private boolean isSplash=false;

    //splash time.
    private long splashDelayTime;

    // sensor delay time.
    private float sensorDelayTime;

    // Image's width and height
    private int mDrawableWidth;
    private int mDrawableHeight;

    // View's width and height
    private int mWidth;
    private int mHeight;

    // Image's offset from initial state(center in the view).
    private float mMaxOffset;

    // The scroll progress.
    private float mProgress;
    private OnSensorScrollListener sensorScrollListener;


    // Observe scroll state

    public SensorBackground(Context context) {
        this(context, null);
    }

    public SensorBackground(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SensorBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(ScaleType.CENTER_CROP);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.sBackground);
        isSplash = typedArray.getBoolean(R.styleable.sBackground_sb_splash, false);
        splashDelayTime = (long) typedArray.getInt(R.styleable.sBackground_sb_splash_time, 3000);
        sensorDelayTime = typedArray.getFloat(R.styleable.sBackground_sb_delay_time,500);
        typedArray.recycle();



    }

    private void splash(final SensorBackground sensorBackground) {
        Log.e("Main-splash","splashe girdi");
        ValueAnimator vA = ValueAnimator.ofFloat(0.1f, 1f);
        vA.setDuration(splashDelayTime);
        vA.setRepeatCount(0);
        vA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("Main","splashe girdi :"+animation.getAnimatedValue());
                float aV= (float) animation.getAnimatedValue();
                    sensorBackground.setScaleX(aV);
                    sensorBackground.setScaleY(aV);
            }
        });
        vA.start();

    }

    public void setSensorHelper(SensorHelper helper) {

        if (helper != null) {
            helper.addsensorBackground(this);
        }
    }

    public void updateProgress(float progress) {
            mProgress = progress;
            invalidate();
        if (sensorScrollListener != null) {
            sensorScrollListener.onScrolled(this, -mProgress);
        }
    }

    public void setSensorDelayTime(float sensorDelayTime) {
        this.sensorDelayTime = sensorDelayTime;
    }

    public void setSplashDelayTime(long splashDelayTime) {
        this.splashDelayTime = splashDelayTime;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        if (getDrawable() != null) {
            mDrawableWidth = getDrawable().getIntrinsicWidth();
            mDrawableHeight = getDrawable().getIntrinsicHeight();

            if (mDrawableWidth * mHeight > mDrawableHeight * mWidth) {
                mOrientation = ORIENTATION_HORIZONTAL;
                float imgScale = (float) mHeight / (float) mDrawableHeight;
                mMaxOffset = Math.abs((mDrawableWidth * imgScale - mWidth) * 0.5f);
            } else if(mDrawableWidth * mHeight < mDrawableHeight * mWidth) {
                mOrientation = ORIENTATION_VERTICAL;
                float imgScale = (float) mWidth / (float) mDrawableWidth;
                mMaxOffset = Math.abs((mDrawableHeight * imgScale - mHeight) * 0.5f);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null || isInEditMode()) {
            super.onDraw(canvas);
            return;
        }

        // Draw image
        if (mOrientation == ORIENTATION_HORIZONTAL) {
            float currentOffsetX = mMaxOffset * mProgress;
            canvas.save();
            canvas.translate(currentOffsetX, 0);
            super.onDraw(canvas);
            canvas.restore();
        } else if (mOrientation == ORIENTATION_VERTICAL) {
            float currentOffsetY = mMaxOffset * mProgress;
            canvas.save();
            canvas.translate(0, currentOffsetY);
            super.onDraw(canvas);
            canvas.restore();
        }

        // Draw scrollbar
    }


    @Override
    public void setScaleType(ScaleType scaleType) {
        /**
         * Do nothing because PanoramaImageView only
         * supports {@link scaleType.CENTER_CROP}
         */
    }

    /**
     * Interface definition for a callback to be invoked when the image is scrolling
     */

    public interface OnSensorScrollListener {
        /**
         * Call when the image is scrolling
         *
         * @param view the panoramaImageView shows the image
         *
         * @param offsetProgress value between (-1, 1) indicating the offset progress.
         *                 -1 means the image scrolls to show its left(top) bound,
         *                 1 means the image scrolls to show its right(bottom) bound.
         */
        void onScrolled(SensorBackground view, float offsetProgress);
    }

    public void setSplash(boolean splash) {
        isSplash = splash;
        if(splash)
            splash(this);

    }

    public void setSensorScrollListener(OnSensorScrollListener sensorScrollListener) {
        this.sensorScrollListener = sensorScrollListener;
    }

    public byte getOrientation() {
        return mOrientation;
    }
}
