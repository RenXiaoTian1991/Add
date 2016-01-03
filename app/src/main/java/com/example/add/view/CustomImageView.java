package com.example.add.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.add.R;

/**
 * Created by myself on 15/8/24.
 */

public class CustomImageView extends View
{

    /**
     * TYPE_CIRCLE / TYPE_ROUND
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    /**
     * ͼƬ
     */
    private Bitmap mSrc;

    /**
     * Բ�ǵĴ�С
     */
    private int mRadius;

    /**
     * �ؼ��Ŀ��
     */
    private int mWidth;
    /**
     * �ؼ��ĸ߶�
     */
    private int mHeight;

    public CustomImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context)
    {
        this(context, null);
    }

    /**
     * ��ʼ��һЩ�Զ���Ĳ���
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomImageView_src:
                    mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_type:
                    type = a.getInt(attr, 0);// Ĭ��ΪCircle
                    break;
                case R.styleable.CustomImageView_borderRadius:
                    type = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                            getResources().getDisplayMetrics()));// Ĭ��Ϊ10DP
                    break;
            }
        }
        a.recycle();
    }

    /**
     * ����ؼ��ĸ߶ȺͿ��
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * ���ÿ��
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        } else
        {
            // ��ͼƬ�����Ŀ�
            int desireByImg = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            }
        }

        /***
         * ���ø߶�
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else
        {
            int desire = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);

    }

    /**
     * ����
     */
    @Override
    protected void onDraw(Canvas canvas)
    {

        switch (type)
        {
            // �����TYPE_CIRCLE����Բ��
            case TYPE_CIRCLE:

                int min = Math.min(mWidth, mHeight);
                /**
                 * �������һ�£���С��ֵ����ѹ��
                 */
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);

                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                break;
            case TYPE_ROUND:
                canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
                break;

        }

    }

    /**
     * ���ԭͼ�ͱ䳤����Բ��ͼƬ
     *
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * ����һ��ͬ���С�Ļ���
         */
        Canvas canvas = new Canvas(target);
        /**
         * ���Ȼ���Բ��
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * ʹ��SRC_IN���ο������˵��
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * ����ͼƬ
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * ���ԭͼ���Բ��
     *
     * @param source
     * @return
     */
    private Bitmap createRoundConerImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, 50f, 50f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}