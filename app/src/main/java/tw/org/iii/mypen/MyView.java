package tw.org.iii.mypen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private LinkedList<HashMap<String,Float>> line;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        line =  new LinkedList<>();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(4);
        for(int i = 1 ; i <=line.size() ;i++) {
            canvas.drawLine(line.get(i-1).get("x"),line.get(i-1).get("y")
                    , line.get(i).get("x"),line.get(i).get("y"), p);
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
        HashMap<String,Float>point =
            new HashMap<>();
        point.put("x", x); point.put("y", y);
        line.add(point);
        invalidate();
    }
    public void doTouchMove(float x ,float y){
        HashMap<String,Float>point =
                new HashMap<>();
        point.put("x", x); point.put("y", y);
        line.add(point);
        invalidate();
    }
}