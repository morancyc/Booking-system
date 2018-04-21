package com.example.asus.bookingsystem;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
自定义的View
 */
public class SeatView extends View {


    private SeatChecker seatChecker;     //用于实现接口函数

    Paint paint = new Paint();
    Matrix matrix = new Matrix();        //全局矩阵
    Matrix tempMatrix = new Matrix();    //座位图矩阵


    private ArrayList<String> txt=new ArrayList<>();   //存储显示的文字

    public ArrayList<Integer> Soldrow=new ArrayList<Integer>();    //存储Order表中已经卖出的座位
    public ArrayList<Integer> Soldcol=new ArrayList<Integer>();

    private int a1,a2,a3,b1,b2,b3;       //用于存储选座排号列号（最多3个）
    private int countselect;             //已经选择的座位数

    String Movie_Time,Movie_Date,Movie_Name;
    Integer Movie_ID;

    private float zoom;

    int spacing;               //座位水平间距
    int verSpacing;            //座位垂直间距

    int row;                 //行数
    int column;             //列数

    Bitmap seatBitmap;         //可选时座位的图片
    Bitmap checkedSeatBitmap;  //选中时座位的图片
    Bitmap seatSoldBitmap;     //座位已经售出时的图片

    private float defaultImgW = 40;    //默认的座位图宽度

    private float defaultImgH = 34;    //默认的座位图高度

    /**
     * 一个座位图片的宽度
     */
    private int seatWidth;

    /**
     * 一个座位图片的高度
     */
    private int seatHeight;

    /**
     * 整个座位图的宽度
     */
    int seatBitmapWidth;

    /**
     * 整个座位图的高度
     */
    int seatBitmapHeight;

    float screenHeight;               //荧幕高度
    float screenWidthScale = 0.5f;    //荧幕默认宽度与座位图的比例
    int defaultScreenWidth;           //荧幕最小宽度

    Paint pathPaint;

    boolean isScaling = false;            //标识是否正在缩放
    boolean firstScale = true;     //是否是第一次缩放
    boolean hasinited = false;      //是否初始化完成
    boolean isFirstDraw;            //是否第一次执行onDraw
    boolean isOnClick =false ;

    private int downX, downY;      //鼠标按下事件用到

    int lastX;
    int lastY;


    private boolean pointer;    //用于指示按下时手指个数

    float xScale1 = 1;         //初始时候的缩放比例
    float yScale1 = 1;

    private float scaleX;      //缩放比例
    private float scaleY;

    public SeatView(Context context) {
        super(context);
    }

    public SeatView(Context context,AttributeSet attrs) {
        super(context, attrs);

    }
    public SeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {

        spacing = (int) dip2Px(5);         //相邻座位之间的距离
        verSpacing = (int) dip2Px(10);     //上下座位之间的距离
        defaultScreenWidth = (int) dip2Px(80);

        //txt.add("3排4座");
        //txt.add("6排10座");

        a1=-1;  a2=-1;  a3=-1; b1=-1; b2=-1; b3=-1;

        seatBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seat_gray);
        checkedSeatBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.seat_green);
        seatSoldBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.seat_sold);

        xScale1 = defaultImgW / seatBitmap.getWidth();        //初始化初始缩放比例
        yScale1 = defaultImgH / seatBitmap.getHeight();
        seatWidth=(int) (seatBitmap.getWidth()*xScale1);
        seatHeight=(int) (seatBitmap.getHeight()*yScale1);

        seatBitmapWidth = (int) (column * seatBitmap.getWidth() * xScale1 + (column - 1) * spacing);
        seatBitmapHeight = (int) (row * seatBitmap.getHeight() * yScale1 + (row - 1) * verSpacing);

        paint.setColor(Color.RED);

        screenHeight = dip2Px(20);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));

        matrix.postTranslate(spacing, screenHeight +  verSpacing);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(hasinited) {
            drawSeat(canvas);

            drawScreen(canvas);
            drawText(canvas);
        }
        super.onDraw(canvas);
    }

    /*******************************实现画图函数********************************************/
    /*************************************************************************************/
    void drawScreen(Canvas canvas) {
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));
        float startY = 0;

        float centerX = seatBitmapWidth * getMatrixScaleX() / 2 + getTranslateX();
        float screenWidth = seatBitmapWidth * screenWidthScale * getMatrixScaleX();
        if (screenWidth < defaultScreenWidth) {
            screenWidth = defaultScreenWidth;
        }

        Path path = new Path();
        path.moveTo(centerX, startY);
        path.lineTo(centerX - screenWidth / 2, startY);
        path.lineTo(centerX - screenWidth / 2 + 20, screenHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + screenWidth / 2 - 20, screenHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + screenWidth / 2, startY);

        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.BLACK);
        pathPaint.setTextSize(20 * getMatrixScaleX());

        String screenName=" 主屏幕 ";
        canvas.drawText(screenName, centerX - pathPaint.measureText(screenName) / 2, getBaseLine(pathPaint, startY, startY + screenHeight * getMatrixScaleY()), pathPaint);
    }


    void drawSeat(Canvas canvas) {

        zoom = getMatrixScaleX();
        float translateX = getTranslateX();
        float translateY = getTranslateY();
        float scaleX = zoom;
        float scaleY = zoom;

        for(int i=0;i<row;i++)
        {
            float top = i * seatBitmap.getHeight() * yScale1 * scaleY + i * verSpacing * scaleY + translateY;
            float bottom = top + seatBitmap.getHeight() * yScale1 * scaleY;
            if (bottom < 0 || top > getHeight()) {
                continue;
            }

            for(int j=0;j<column;j++)
            {
                float left=translateX + j*+ seatBitmap.getWidth()*xScale1*scaleX + j*spacing *scaleX;
                float right = left + seatBitmap.getWidth() * xScale1 *scaleX;
                if (right < 0 || left > getWidth()) {
                    continue;
                }
                tempMatrix.setTranslate(left,top);
                tempMatrix.postScale(xScale1,yScale1,left,top);
                tempMatrix.postScale(scaleX,scaleY,left,top);
                //根据座位类型(已选，已售，可选)分别绘制图片
                if(IsSold(i,j))
                {
                    canvas.drawBitmap(seatSoldBitmap, tempMatrix, paint);
                }else if(isHave(i,j))
                {
                    canvas.drawBitmap(checkedSeatBitmap,tempMatrix,paint);
                }else {
                    canvas.drawBitmap(seatBitmap, tempMatrix, paint);
                }

            }

        }

    }

    /**********************************用于判断座位的函数******************************/
    private boolean isHave(int row,int column)
    {
        if(a1==row&&b1==column)
            return true;
        if(a2==row&&b2==column)
            return true;
        if(a3==row&&b3==column)
            return true;
        return false;

    }

    private boolean IsSold(int row, int column) {

        for (int j=0;j<Soldrow.size();j++)
        {
            int m=Soldrow.get(j)-1;
            int n=Soldcol.get(j)-1;
            if(m==row&&n==column)
            {
                return true;
            }

        }
        return false;

    }

/*****************************************************************************/



    //  画底部文字，根据txt
    private void drawText(Canvas canvas) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;

        //以分辨率为480*800准，计算宽高比值
        float ratioWidth = (float) mScreenWidth / 480;
        float ratioHeight = (float) mScreenHeight / 800;
        float ratioMetrics = Math.min(ratioWidth, ratioHeight);
        int textSize = Math.round(25 * ratioMetrics);

        TextPaint txtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setColor(Color.BLACK);
        txtPaint.setTypeface(Typeface.DEFAULT_BOLD);

        float top = (int)dip2Px(380);
        txtPaint.setTextSize(textSize);

        for(int i=0;i<txt.size();i++)
        {
            float left=i*dip2Px(65);

            canvas.drawText(txt.get(i), left, getBaseLine(txtPaint, top, top + dip2Px(50)), txtPaint);

        }

    }

    /**************************************************************************************/
    /**********************************点击以及手势监听**************************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int)event.getY();
        super.onTouchEvent(event);

        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        int pointerCount = event.getPointerCount();
        if(pointerCount>1)
        {
            pointer = true;   //多指操作
        }
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                pointer = false;
                downX = x;
                downY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isScaling&&!isOnClick)
                {
                    int downDX = Math.abs(downX - x);
                    int downDY = Math.abs(downY - y);
                    if((downDX >10||downDY >10)&&!pointer)
                    {
                        int dx = x - lastX;
                        int dy = y - lastY;
                        matrix.postTranslate(dx,dy);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                autoScale();
                int downDX = Math.abs(x - downX);
                int downDY = Math.abs(y - downY);
                if ((downDX > 10 || downDY > 10) && !pointer) {
                    autoScroll();
                }
                break;
        }
        isOnClick = false;
        lastY = y;
        lastX = x;

        return true;
    }


    ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            isScaling = true;
            float scaleFactor = detector.getScaleFactor();
            if (getMatrixScaleY() * scaleFactor > 3) {
                scaleFactor = 3 / getMatrixScaleY();
            }
            if (firstScale) {
                scaleX = detector.getCurrentSpanX();
                scaleY = detector.getCurrentSpanY();
                firstScale = false;
            }

            if (getMatrixScaleY() * scaleFactor < 0.5) {
                scaleFactor = 0.5f / getMatrixScaleY();
            }
            matrix.postScale(scaleFactor, scaleFactor, scaleX, scaleY);
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            isScaling = false;
            firstScale = true;
        }
    });

    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isOnClick = true;
            int x = (int) e.getX();
            int y = (int) e.getY();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    //计算每一张图片的位置
                    int tempX = (int) ((j * seatWidth + j * spacing) * getMatrixScaleX() + getTranslateX());
                    int maxTemX = (int) (tempX + seatWidth * getMatrixScaleX());

                    int tempY = (int) ((i * seatHeight + i * verSpacing) * getMatrixScaleY() + getTranslateY());
                    int maxTempY = (int) (tempY + seatHeight * getMatrixScaleY());

                    if (!IsSold(i, j)) {
                        if (x >= tempX && x <= maxTemX && y >= tempY && y <= maxTempY)   //确定点击的是这张图片
                        {
                            if(isHave(i,j))
                             {
                                remove(i,j);
                                countselect-=1;
                                if (seatChecker != null) {
                                    seatChecker.unCheck(i, j);
                                }
                            } else {
                                if (countselect >= 3) {
                                    Toast.makeText(getContext(), "最多只能选择3个", Toast.LENGTH_SHORT).show();
                                    return super.onSingleTapConfirmed(e);
                                } else {
                                    addChooseSeat(i, j);
                                    countselect+=1;
                                    if (seatChecker != null) {
                                        seatChecker.checked(i, j);
                                    }
                                }
                            }
                            float currentScaleY = getMatrixScaleY();

                            if (currentScaleY < 1.7) {
                                scaleX = x;
                                scaleY = y;
                                zoomAnimate(currentScaleY, 1.9f);
                            }

                            invalidate();
                            break;
                        }
                    }
                }
            }

            return super.onSingleTapConfirmed(e);
        }
    });
    private void remove(int row, int column)
    {
        if(a1==row&&b1==column)
        {
            a1=-1;
            b1=-1;
        }
        else if(a2==row&&b2==column)
        {
            a2=-1;
            b2=-1;
        }
        else if(a3==row&&b3==column)
        {
            a3=-1;
            b3=-1;
        }
        String remov = (row+1)+"排"+(column+1)+"座";
        for(int j=0;j<txt.size();j++)
        {
            if(txt.get(j).equals(remov))
                txt.remove(j);
        }
    }

    private void addChooseSeat(int row, int column) {
        if(a1==-1&&b1==-1)
        {
            a1=row;
            b1=column;
        }
        else if(a2==-1&&b2==-1)
        {
            a2=row;
            b2=column;
        }
        else
        {
            a3=row;
            b3=column;
        }
        String add = (row+1)+"排"+(column+1)+"座";
        txt.add(add);

    }

    /**************************************************************************************/
    /**********************************处理滑动的函数****************************************/

    private void autoScroll() {
        float currentSeatBitmapWidth = seatBitmapWidth * getMatrixScaleX();
        float currentSeatBitmapHeight = seatBitmapHeight * getMatrixScaleY();
        float moveYLength = 0;
        float moveXLength = 0;

        //处理左右滑动的情况
        if (currentSeatBitmapWidth < getWidth()) {
            if (getTranslateX() < 0 || getMatrixScaleX() <  spacing) {
                //计算要移动的距离

                if (getTranslateX() < 0) {
                    moveXLength = (-getTranslateX()) + spacing;
                } else {
                    moveXLength =spacing - getTranslateX();
                }

            }
        } else {

            if (getTranslateX() < 0 && getTranslateX() + currentSeatBitmapWidth > getWidth()) {

            } else {
                //往左侧滑动
                if (getTranslateX() + currentSeatBitmapWidth < getWidth()) {
                    moveXLength = getWidth() - (getTranslateX() + currentSeatBitmapWidth);
                } else {
                    //右侧滑动
                    moveXLength = -getTranslateX() + spacing;
                }
            }

        }

        float startYPosition = screenHeight * getMatrixScaleY() + verSpacing * getMatrixScaleY() ;

        //处理上下滑动
        if (currentSeatBitmapHeight < getHeight()) {

            if (getTranslateY() < startYPosition) {
                moveYLength = startYPosition - getTranslateY();
            } else {
                moveYLength = -(getTranslateY() - (startYPosition));
            }

        } else {

            if (getTranslateY() < 0 && getTranslateY() + currentSeatBitmapHeight > getHeight()) {

            } else {
                //往上滑动
                if (getTranslateY() + currentSeatBitmapHeight < getHeight()) {
                    moveYLength = getHeight() - (getTranslateY() + currentSeatBitmapHeight);
                } else {
                    moveYLength = -(getTranslateY() - (startYPosition));
                }
            }
        }

        Point start = new Point();
        start.x = (int) getTranslateX();
        start.y = (int) getTranslateY();

        Point end = new Point();
        end.x = (int) (start.x + moveXLength);
        end.y = (int) (start.y + moveYLength);

        moveAnimate(start, end);

    }

    private void autoScale() {

        if (getMatrixScaleX() > 2.2) {
            zoomAnimate(getMatrixScaleX(), 2.0f);
        } else if (getMatrixScaleX() < 0.98) {
            zoomAnimate(getMatrixScaleX(), 1.0f);
        }
    }


    public interface SeatChecker {

        /**
         接口函数
         */

        void checked(int row, int column);

        void unCheck(int row, int column);

        String[] checkedSeatTxt(int row,int column);

    }


    public void setSeatChecker(SeatChecker seatChecker) {
        this.seatChecker = seatChecker;
        invalidate();
    }


    float[] m = new float[9];
    //一些对全局矩阵操作的函数
    private float getTranslateX() {
        matrix.getValues(m);
        return m[2];
    }

    private float getTranslateY() {
        matrix.getValues(m);
        return m[5];
    }

    private float getMatrixScaleY() {
        matrix.getValues(m);
        return m[4];
    }

    private float getMatrixScaleX() {
        matrix.getValues(m);
        return m[Matrix.MSCALE_X];
    }

    /*************************************用于计算的函数*****************************************/
    private float dip2Px(float value) {
        return getResources().getDisplayMetrics().density * value;
    }

    private float getBaseLine(Paint p, float top, float bottom) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        int baseline = (int) ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);
        return baseline;
    }

    private void moveAnimate(Point start, Point end) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MoveEvaluator(), start, end);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        MoveAnimation moveAnimation = new MoveAnimation();
        valueAnimator.addUpdateListener(moveAnimation);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private void zoomAnimate(float cur, float tar) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(cur, tar);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        ZoomAnimation zoomAnim = new ZoomAnimation();
        valueAnimator.addUpdateListener(zoomAnim);
        valueAnimator.addListener(zoomAnim);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private void zoom(float zoom) {
        float z = zoom / getMatrixScaleX();
        matrix.postScale(z, z, scaleX, scaleY);
        invalidate();
    }

    private void move(Point p) {
        float x = p.x - getTranslateX();
        float y = p.y - getTranslateY();
        matrix.postTranslate(x, y);
        invalidate();
    }

    //move类
    class MoveAnimation implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Point p = (Point) animation.getAnimatedValue();

            move(p);
        }
    }

    class MoveEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
            int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
            return new Point(x, y);
        }
    }

    //zoom类
    class ZoomAnimation implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            zoom = (Float) animation.getAnimatedValue();
            zoom(zoom);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

    }
/****************************************************************************************/
    //进行初始化的接口函数
    public void setData(int row, int column,String Movie_Time,String Movie_Date,String Movie_Name,Integer Movie_ID) {
        this.row = row;
        this.column = column;
        this.Movie_Date=Movie_Date;
        this.Movie_ID=Movie_ID;
        this.Movie_Name=Movie_Name;
        this.Movie_Time=Movie_Time;


        BmobQuery<Order> query = new BmobQuery<Order>();      //查询已经选座情况，初始化ArrayList<Integer> row,col
        query.addWhereEqualTo("Movie_Time",Movie_Time);
        query.addWhereEqualTo("Movie_Date",Movie_Date);
        query.addWhereEqualTo("Movie_ID",Movie_ID);
        query.addWhereEqualTo("Movie_Name",Movie_Name);

        query.setLimit(100);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {   //如果查询成功
                    for (int i = 0; i < list.size(); i++) {
                        Soldrow.add(list.get(i).getSeat_V());
                        Soldcol.add(list.get(i).getSeat_H());
                    }
                    hasinited=true;
                    invalidate();
                    int a=Soldrow.size()+1;
                    String p = String.valueOf(a);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        init();
        invalidate();
        invalidate();

    }

}
