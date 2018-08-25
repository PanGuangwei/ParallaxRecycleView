package com.pgw.parallaxrecycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * <pre>
 *     author : Administrator
 *     e-mail : 89925977@qq.com
 *     time   : 2018/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ParallaxView extends View {
	private Paint mPaint;
	private Bitmap srcBmp;
	private float scale=1;
	private float bmpHeight;
	private Matrix matrix;
	private boolean isInit=true;
	private float sy;
	private float transDistance=0;
	private int mSlop;

	public ParallaxView(Context context) {
		this(context,null);
	}

	public ParallaxView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ParallaxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		matrix=new Matrix();
		mSlop= ViewConfiguration.get(context).getScaledTouchSlop();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (srcBmp!=null){
			if (isInit){
				matrix.postScale(scale,scale);
				isInit=false;
			}
			canvas.drawBitmap(srcBmp,matrix,mPaint);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		transDistance=t;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				sy=event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaY= (int) (event.getY()-sy);
				if (Math.abs(deltaY)>mSlop&&transDistance-deltaY>=0&&(transDistance-deltaY)<bmpHeight*scale-getHeight()){
					scrollBy(0,-deltaY);
					sy=event.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}


	public void changeScroll(int x,int y){
		if (Math.abs(y)>mSlop&&transDistance-y>=0&&(transDistance-y)<bmpHeight*scale-getHeight()){
			scrollBy(0,-y);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int w=MeasureSpec.getSize(widthMeasureSpec);
		int h=MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(w,h);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		Drawable bg=getBackground();
		if (bg!=null){
			srcBmp=drawableToBitmap(bg);
			scale=getWidth()*1.0f/srcBmp.getWidth()*1.0f;
			bmpHeight=srcBmp.getHeight();
		}
	}

	public Bitmap drawableToBitmap(Drawable drawable){
		if (drawable instanceof BitmapDrawable){
			BitmapDrawable bd= (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w=drawable.getIntrinsicWidth();
		int h=drawable.getIntrinsicHeight();
		Bitmap bitmap=Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bitmap);
		drawable.setBounds(0,0,w,h);
		drawable.draw(canvas);
		return bitmap;
	}
}
