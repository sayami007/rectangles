package apps.android.borderapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RectangleClass extends FrameLayout implements View.OnTouchListener {
    //region variables
    private static final float BUTTON_SIZE_DP = 20;
    private final float SELF_SIZE_DP = 400;
    private ImageView ic_move;
    private ImageView ic_right;
    private ImageView ic_bottom;
  //  private RectangleBorder border;
    private LayoutParams iv_move;
    private LayoutParams iv_scale_right;
    private LayoutParams iv_scale_bottom;
    private float bottomCorner = 200;
    private float rightCorner = 200;
    private Context ctx;
    private LayoutParams iv_border_params;
    private LayoutParams this_params;
    //endregion

    //region Getter and Setter
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

    //region Constructor
    RectangleClass(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        this.setBackgroundColor(Color.RED);
        initContent();
        initParams();
        addViewToMain();
    }
    //endregion

    //region Initialize
    private void initContent() {
        //
        ic_bottom = new ImageView(ctx);
        ic_bottom.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_bottom.setTag(Constant.BOTTOM);
        ic_bottom.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_bottom.setRotation(45);
        //
        ic_right = new ImageView(ctx);
        ic_right.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_right.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_right.setTag(Constant.RIGHT);
        ic_right.setRotation(315);
        //
        ic_move = new ImageView(ctx);
        ic_move.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_move.setImageResource(R.drawable.ic_action_arrow_right_bottom);
        ic_move.setRotation(135);
        ic_move.setTag(Constant.MOVE);
        //
        ic_move.setOnTouchListener(this);
        ic_right.setOnTouchListener(this);
        ic_bottom.setOnTouchListener(this);
        //
       // border = new RectangleBorder(ctx);
    }

    private void initParams() {
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
        //
        this_params = new LayoutParams(size, size);
        this_params.gravity = Gravity.CENTER;
        //
        iv_border_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       // border.setPadding(10, 10, 10, 10);
        //
        iv_move = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_move.gravity = Gravity.START | Gravity.TOP;
        //
        iv_scale_bottom = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_bottom.gravity = Gravity.BOTTOM | Gravity.CENTER;
        //
        iv_scale_right = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_right.gravity = Gravity.END | Gravity.CENTER;
        //
        this.setLayoutParams(this_params);
    }

    private void addViewToMain() {
       // this.addView(border, iv_border_params);
        this.addView(ic_move, iv_move);
        this.addView(ic_right, iv_scale_right);
        this.addView(ic_bottom, iv_scale_bottom);
    }
    //endregion

    //region listener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams();
                TypedValue tv = new TypedValue();
                int actionBarHeight = 0;
                if (this.ctx.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                }
                if (v.getTag() == Constant.MOVE) {
                    int topSize = (int) event.getRawY() - actionBarHeight;
                    params.setMarginStart((int) event.getRawX());
                    params.setMargins(0, topSize, 0, 0);
                    this.setLayoutParams(params);
                    this.postInvalidate();
                    this.requestLayout();
                } else if (v.getTag() == Constant.RIGHT) {
                    int rightSize = (int) event.getRawX() - params.getMarginStart();
                    this.getLayoutParams().width = rightSize;
                 //   setRightCorner(this.getWidth());
               //     border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                } else if (v.getTag() == Constant.BOTTOM) {
                    System.out.println("ACTION +" + actionBarHeight);
                    int bottomSize = (int) event.getRawY() - params.topMargin - actionBarHeight;
                    this.getLayoutParams().height = bottomSize;
                   // setBottomCorner(this.getHeight());
                    //border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                }
                break;
            }
        }
        return true;
    }
    //endregion

    //region methods
    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    private void setControlItemsHidden(boolean isControlItemVisible) {
        if (isControlItemVisible) {
            ic_move.setVisibility(VISIBLE);
            ic_right.setVisibility(VISIBLE);
            ic_bottom.setVisibility(VISIBLE);
        } else {
            ic_move.setVisibility(GONE);
            ic_right.setVisibility(GONE);
            ic_bottom.setVisibility(GONE);
        }
    }
//endregion

    //region Rectangle Border Class
    class RectangleBorder extends View {
        public Paint mPaint;

        public RectangleBorder(Context context) {
            super(context);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(20);
            mPaint.setColor(Color.CYAN);
            Rect rect = new Rect(10, 10, (int) getRightCorner(), (int) getBottomCorner());
            canvas.drawRect(rect, mPaint);
        }
    }
    //endregion

}
