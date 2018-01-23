package apps.android.borderapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Bibesh on 1/19/18.
 */

public class RectangleClass extends FrameLayout {

    //region variables
    private static final float BUTTON_SIZE_DP = 10;
    private final float SELF_SIZE_DP = 400;
    public ImageView ic_left;
    public ImageView ic_top;
    public ImageView ic_right;
    public ImageView ic_bottom;
    public RectangleBorder border;
    private float bottomCorner = 200;
    private float rightCorner = 200;
    private float leftCorner = 10;
    private float topCorner = 10;
    private Context ctx;
    //endregion

    //region:Getter and Setter
    public float getLeftCorner() {
        return leftCorner;
    }

    public void setLeftCorner(float leftCorner) {
        this.leftCorner = leftCorner;
    }

    public float getTopCorner() {
        return topCorner;
    }

    public void setTopCorner(float topCorner) {
        this.topCorner = topCorner;
    }

    public float getBottomCorner() {
        return bottomCorner;
    }

    public void setBottomCorner(float bottomCorner) {
        this.bottomCorner = bottomCorner;
    }

    public float getRightCorner() {
        return rightCorner;
    }

    public void setRightCorner(float rightCorner) {
        this.rightCorner = rightCorner;
    }
    //endregion

    //region:Rectangle Border Class
    class RectangleBorder extends View {
        public Paint mPaint;

        public RectangleBorder(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(20);
            mPaint.setColor(Color.CYAN);
            Rect rect = new Rect((int) getLeftCorner(), (int) getTopCorner(), (int) getRightCorner(), (int) getBottomCorner());
            canvas.drawRect(rect, mPaint);
        }
    }
    //endregion

    RectangleClass(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        init();
    }

    private void init() {
        ic_bottom = new ImageView(ctx);
        ic_bottom.setBackgroundColor(Color.RED);
        ic_bottom.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_right = new ImageView(ctx);
        ic_right.setBackgroundColor(Color.RED);
        ic_right.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_top = new ImageView(ctx);
        ic_top.setBackgroundColor(Color.RED);
        ic_top.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_left = new ImageView(ctx);
        ic_left.setBackgroundColor(Color.RED);
        ic_left.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_left.setTag(Constant.LEFT);
        ic_top.setTag(Constant.TOP);
        ic_bottom.setTag(Constant.BOTTOM);
        ic_right.setTag(Constant.RIGHT);
        //endregion
        border = new RectangleBorder(ctx);
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
        LayoutParams this_params = new LayoutParams(size, size);
        this_params.gravity = Gravity.CENTER;
        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        LayoutParams iv_border_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv_border_params.setMargins(margin, margin, margin, margin);
        LayoutParams iv_scale_left = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_left.gravity = Gravity.START | Gravity.CENTER;
        LayoutParams iv_scale_top = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_top.gravity = Gravity.TOP | Gravity.CENTER;
        LayoutParams iv_scale_bottom = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_bottom.gravity = Gravity.BOTTOM | Gravity.CENTER;
        LayoutParams iv_scale_right = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_right.gravity = Gravity.END | Gravity.CENTER;
        this.setLayoutParams(this_params);
        this.addView(border, iv_border_params);
        this.addView(ic_left, iv_scale_left);
        this.addView(ic_top, iv_scale_top);
        this.addView(ic_right, iv_scale_right);
        this.addView(ic_bottom, iv_scale_bottom);
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

}
