package edu.uw.animdemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private GestureDetectorCompat mDetector;
    private DrawingView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, new GestureListener());
        view = (DrawingView)findViewById(R.id.drawingView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            Log.v(TAG, event.toString());
            mDetector.onTouchEvent(event);
            int pointerIndex  = MotionEventCompat.getActionIndex(event);
            int pointerID = MotionEventCompat.getPointerId(event, pointerIndex);
            int action = MotionEventCompat.getActionMasked(event);
            float x = event.getX(); //get location of event
            float y = event.getY() - getSupportActionBar().getHeight(); //closer to center...
            switch(action) {
                    case (MotionEvent.ACTION_DOWN) : //put finger down
                            //e.g., move ball
                    //                view.ball.cx = x;
                            //                view.ball.cy = y;
                                                    ObjectAnimator animx = ObjectAnimator.ofFloat(view.ball, "x", x);
                            ObjectAnimator animy = ObjectAnimator.ofFloat(view.ball, "y", y);
                            animx.setDuration(1000);
                            animy.setDuration(1500);
                            AnimatorSet animatorSet = new AnimatorSet();
                           animatorSet.playTogether(animx, animy);
                            animatorSet.start();
                            view.addTouch(pointerID, event.getX(pointerIndex), event.getY(pointerIndex)- getSupportActionBar().getHeight());

                            return true;
                    case (MotionEvent.ACTION_POINTER_DOWN) :
                            view.addTouch(pointerID, event.getX(pointerIndex), event.getY(pointerIndex)- getSupportActionBar().getHeight());
                            return true;
                   case (MotionEvent.ACTION_POINTER_UP) :
                            view.removeTouch(pointerID);
                            return true;
                    case (MotionEvent.ACTION_MOVE) : //move finger
                            //e.g., move ball
                                    view.ball.cx = x;
                            view.ball.cy = y;
                            return true;
                    case (MotionEvent.ACTION_UP) : //lift finger up
                            view.removeTouch(pointerID);
                            return true;
                    case (MotionEvent.ACTION_CANCEL) : //aborted gesture
                        case (MotionEvent.ACTION_OUTSIDE) : //outside bounds
                        default :
                            return super.onTouchEvent(event);
                }
        }

    /** Menus **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_pulse:
                //make the ball change size!
                AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animations);
                anim.setTarget(view.ball);
                anim.start();
                return true;
            case R.id.menu_button:
                startActivity(new Intent(MainActivity.this, ButtonActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GestureListener implements GestureDetector.OnGestureListener {

        @Override
         public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
         public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }
    }
}
