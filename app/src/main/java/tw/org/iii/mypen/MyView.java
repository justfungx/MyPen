package tw.org.iii.mypen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines;
    private Resources res;
    private boolean isInit;
    private int viewW, viewH;
    private Bitmap bmpFly ,bmpBg;
    private Matrix matrix;
    private Timer timer;
    private float flyX ,flyY,flyW,flyH,dx,dy;



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<>();
        res = context.getResources();
        matrix = new Matrix();
        timer = new Timer();

    }

    Timer getTimer(){return timer;}

        private void init(){

            viewW = getWidth(); viewH = getHeight();
            float flyW = viewW / 8f, flyH = flyW;

            bmpBg = BitmapFactory.decodeResource(res, R.drawable.bg);
            bmpBg = resizeBitmap(bmpBg, viewW,viewH);

            bmpFly = BitmapFactory.decodeResource(res, R.drawable.fly);
            bmpFly = resizeBitmap(bmpFly,flyW,flyH);
            dx = dy = 10;

            timer.schedule(new RefreshView(), 0, 40);
            timer.schedule(new FlyTask(), 1000, 100);
                isInit = true;
        }

    public  Bitmap resizeBitmap(Bitmap src ,float newW ,float newH){
        matrix.reset();

        matrix.postScale(newW/src.getWidth(), newW/src.getHeight());
        bmpFly = Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix, false);
        return bmpFly;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();


        canvas.drawBitmap(bmpFly, 0,0,null);
        canvas.drawBitmap(bmpFly, flyX, flyY,null);



        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(4);
        for (LinkedList<HashMap<String,Float>> line:lines) {
            for (int i = 1; i <= line.size(); i++) {
                canvas.drawLine(line.get(i - 1).get("x"), line.get(i - 1).get("y")
                        , line.get(i).get("x"), line.get(i).get("y"), p);
            }
        }
    }

    private class RefreshView extends TimerTask {
        @Override
        public void run() {
            //invalidate();
            postInvalidate();
        }
    }


    private class FlyTask extends TimerTask {
        @Override
        public void run() {
            if (flyX<0 || flyX +flyW > viewW) dx *= -1;
            if (flyY<0 || flyY + flyH > viewH) dy *= -1;
            flyX += dx; flyY += dy;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX(), ey = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            doTouchDown(ex,ey);
            Log.d("DK", "Down:" + ex + " x " + ey);

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            doTouchMove(ex,ey);
            Log.d("DK", "Move:" + ex + " x " + ey);
        }
        return true;
    }
    public void doTouchDown (float x,float y){
        LinkedList<HashMap<String,Float>> line =
                new LinkedList<>();
        lines.add(line);
        addPoint(x,y);
    }
    public void doTouchMove(float x ,float y){
        addPoint(x,y);
    }
    private void addPoint(float x, float y){
        HashMap<String,Float> point =
                new HashMap<>();
        point.put("x", x); point.put("y", y);
        lines.getLast().add(point);
        invalidate();
    }
}