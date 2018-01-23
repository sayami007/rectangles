package apps.android.borderapp;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;

import apps.android.borderapp.databinding.ActivityMainBinding;

import static apps.android.borderapp.Constant.*;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    //region:Main
    ActivityMainBinding binding;
    RectangleClass rectangleClass;
    //endregion

    //region:borderVariable
    private float this_orgX = -1;
    private float this_orgY = -1;
    private float scale_orgX = -1;
    private float scale_orgY = -1;
    private double scale_orgWidth = -1;
    private double scale_orgHeight = -1;
    private float move_orgX = -1, move_orgY = -1;
    private double centerX, centerY;
    private final static int BUTTON_SIZE_DP = 30;
    private final static int SELF_SIZE_DP = 100;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rectangleClass = new RectangleClass(getApplicationContext());
        rectangleClass.ic_left.setOnTouchListener(this);
        rectangleClass.ic_top.setOnTouchListener(this);
        rectangleClass.ic_right.setOnTouchListener(this);
        rectangleClass.ic_bottom.setOnTouchListener(this);
        binding.mainView.addView(rectangleClass);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this_orgX = rectangleClass.getX(); //0
                //System.out.println(this_orgX);
                this_orgY = rectangleClass.getY(); //0
                //System.out.println(this_orgY);
                ///////////////////////////////////
                /////////////////////////////////////
                scale_orgX = event.getRawX();//273
                //System.out.println(v.getTag() + " " + scale_orgX);
                scale_orgY = event.getRawY();//375
                //System.out.println(v.getTag() + " " + scale_orgY);
                //////////////////////////////////////
                /////////////////////////////////////
                scale_orgWidth = rectangleClass.getLayoutParams().width; //300
                //System.out.println(scale_orgWidth);
                scale_orgHeight = rectangleClass.getLayoutParams().height; //300
                //System.out.println(scale_orgHeight);
                //////////////////////////////////////
                /////////////////////////////////////
                centerX = rectangleClass.getX() + ((View) rectangleClass.getParent()).getX() + (float) rectangleClass.getWidth() / 2;//150
                //System.out.println(centerX);
                ///////////////////////////////////
                /////////////////////////////////////
                int result = 0;//0
                //System.out.println(result);
                ///////////////////////////////////
                /////////////////////////////////////
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//17104919
                //System.out.println(resourceId);
                ///////////////////////////////////
                /////////////////////////////////////
                if (resourceId > 0)//true
                    result = getResources().getDimensionPixelSize(resourceId);
                //System.out.println(resourceId > 0);
                ///////////////////////////////////
                /////////////////////////////////////
                double statusBarHeight = result;//72
                //System.out.println(statusBarHeight);
                ///////////////////////////////////
                /////////////////////////////////////
                centerY = rectangleClass.getY() + ((View) rectangleClass.getParent()).getY() + statusBarHeight + (float) rectangleClass.getHeight() / 2;//222
                //System.out.println(centerY);
                ///////////////////////////////////
                /////////////////////////////////////
                break;
            case MotionEvent.ACTION_MOVE: {
                if (v.getTag() == Constant.LEFT) {
                    rectangleClass.getLayoutParams().width = rectangleClass.getLayoutParams().width - (int) event.getRawX();
                    rectangleClass.setLeftCorner(rectangleClass.getLeftCorner() - event.getRawX());
                    rectangleClass.postInvalidate();
                    rectangleClass.requestLayout();

                } else if (v.getTag() == Constant.TOP) {
                    System.out.println("TOP");
                } else if (v.getTag() == Constant.RIGHT) {
                    rectangleClass.getLayoutParams().width = (int) event.getRawX();
                    rectangleClass.setRightCorner(event.getRawX());
                    rectangleClass.postInvalidate();
                    rectangleClass.requestLayout();
                } else if (v.getTag() == Constant.BOTTOM) {
                    rectangleClass.getLayoutParams().height = (int) event.getRawY();
                    rectangleClass.setBottomCorner(event.getRawY());
                    rectangleClass.postInvalidate();
                    rectangleClass.requestLayout();
                }
                break;

            }

/*
            case MotionEvent.ACTION_MOVE:
                double angle_diff = Math.abs(Math.atan2(event.getRawY() - scale_orgY, event.getRawX() - scale_orgX) - Math.atan2(scale_orgY - centerY, scale_orgX - centerX)) * 180 / Math.PI;
                double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());
                int size = convertDpToPixel(SELF_SIZE_DP, getApplicationContext());
                if (length2 > length1 && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)) {
                    //scale up
                    double offsetX = Math.abs(event.getRawX() - scale_orgX);
                    double offsetY = Math.abs(event.getRawY() - scale_orgY);
                    double offset = Math.max(offsetX, offsetY);
                    offset = Math.round(offset);
                    rectangleClass.getLayoutParams().width += offset;
                    rectangleClass.getLayoutParams().height += offset;
                } else if (length2 < length1 && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25) && rectangleClass.getLayoutParams().width > size / 2 && rectangleClass.getLayoutParams().height > size / 2) {
                    double offsetX = Math.abs(event.getRawX() - scale_orgX);
                    double offsetY = Math.abs(event.getRawY() - scale_orgY);
                    double offset = Math.max(offsetX, offsetY);
                    offset = Math.round(offset);
                    rectangleClass.getLayoutParams().width -= offset;
                    rectangleClass.getLayoutParams().height -= offset;
                }

                scale_orgX = event.getRawX();
                scale_orgY = event.getRawY();
                rectangleClass.postInvalidate();
                rectangleClass.requestLayout();
                break;
                */
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
