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

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines;
    private Resources res;
    private boolean isInit;
    private int viewW, viewH;
    private Bitmap bmpFly;
    private Matrix matrix;



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<>();
        res = context.getResources();
        matrix = new Matrix();

    }


        private void init(){

            viewW = getWidth(); viewH = getHeight();
            float flyW = viewW / 8f, flyH = flyW;

            bmpFly = BitmapFactory.decodeResource(res, R.drawable.fly);
            bmpFly = resizeBitmap(bmpFly,flyW,flyH);
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

        Bitmap bmpFly = BitmapFactory.decodeResource(res, R.drawable.fly);
        float flyW = viewW / 8f, flyH = flyW;
        Matrix matrix = new Matrix();
        matrix.postScale(flyW/bmpFly.getWidth(), flyW/bmpFly.getHeight());
        bmpFly = Bitmap.createBitmap(bmpFly,0,0,bmpFly.getWidth(),bmpFly.getHeight(),matrix, false);

        canvas.drawBitmap(bmpFly, 0,0,null);



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