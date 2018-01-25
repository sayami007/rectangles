package apps.android.borderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
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
    private RectangleBorder border;
    private LayoutParams iv_move;
    private LayoutParams iv_scale_right;
    private LayoutParams iv_scale_bottom;
    private float bottomCorner;
    private float rightCorner;
    private Context ctx;
    private int mainHeight;
    private int mainWidth;
    private LayoutParams iv_border_params;
    private LayoutParams this_params;
    private Activity myAct;
    //endregion

    //region Getter and Setter
    public RectangleBorder getBorder() {
        return border;
    }

    public void setBorder(RectangleBorder border) {
        this.border = border;
    }

    public LayoutParams getIv_move() {
        return iv_move;
    }

    public void setIv_move(LayoutParams iv_move) {
        this.iv_move = iv_move;
    }

    public LayoutParams getIv_scale_right() {
        return iv_scale_right;
    }

    public void setIv_scale_right(LayoutParams iv_scale_right) {
        this.iv_scale_right = iv_scale_right;
    }

    public LayoutParams getIv_scale_bottom() {
        return iv_scale_bottom;
    }

    public void setIv_scale_bottom(LayoutParams iv_scale_bottom) {
        this.iv_scale_bottom = iv_scale_bottom;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getMainHeight() {
        return mainHeight;
    }

    public void setMainHeight(int mainHeight) {
        this.mainHeight = mainHeight;
    }

    public int getMainWidth() {
        return mainWidth;
    }

    public void setMainWidth(int mainWidth) {
        this.mainWidth = mainWidth;
    }

    public LayoutParams getIv_border_params() {
        return iv_border_params;
    }

    public void setIv_border_params(LayoutParams iv_border_params) {
        this.iv_border_params = iv_border_params;
    }

    public LayoutParams getThis_params() {
        return this_params;
    }

    public void setThis_params(LayoutParams this_params) {
        this.this_params = this_params;
    }

    public Activity getMyAct() {
        return myAct;
    }

    public void setMyAct(Activity myAct) {
        this.myAct = myAct;
    }

    public float getBottomCorner() {
        return bottomCorner;
    }

    public void setBottomCorner(float bottomCorner) {
        this.bottomCorner = bottomCorner - 30;
    }

    public float getRightCorner() {
        return rightCorner;
    }

    public void setRightCorner(float rightCorner) {
        this.rightCorner = rightCorner - 50;
    }
    //endregion

    //region Constructor
    RectangleClass(Context ctx) {
        super(ctx);
        setCtx(ctx);
        setMyAct((Activity) ctx);
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
        ic_move.setImageResource(R.drawable.ic_move);
        ic_move.setRotation(135);
        ic_move.setTag(Constant.MOVE);
        //
        border = new RectangleBorder(ctx);
        border.setTag(Constant.BORDER);
        //
        ic_move.setOnTouchListener(this);
        ic_right.setOnTouchListener(this);
        ic_bottom.setOnTouchListener(this);
        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getMyAct().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        setMainWidth(width);
        setMainHeight(height);
    }

    private void initParams() {
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
        //
        this_params = new LayoutParams(size, size);
        this_params.gravity = Gravity.CENTER;
        this_params.setMargins(50, 50, 50, 50);
        setBottomCorner(this_params.height);
        setRightCorner(this_params.width);
        //
        iv_border_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv_border_params.setMarginStart(40);
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
        this.addView(border, iv_border_params);
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
                if (v.getTag() == Constant.MOVE || v.getTag() == Constant.BORDER) {
                    int topSize = (int) event.getRawY() - getStatusBarHeight() - getActionBarHeight(this.ctx);
                    params.setMargins((int) event.getRawX(), topSize, 0, 0);
                    this.setLayoutParams(params);
                    border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                } else if (v.getTag() == Constant.RIGHT) {
                    int rightSize = (int) event.getRawX() - params.getMarginStart();
                    this.getLayoutParams().width = rightSize;
                    setRightCorner(this.getWidth());
                    border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                } else if (v.getTag() == Constant.BOTTOM) {
                    int bottomSize = (int) event.getRawY() - params.topMargin - getActionBarHeight(this.ctx);
                    this.getLayoutParams().height = bottomSize;
                    setBottomCorner(this.getHeight());
                    border.invalidate();
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
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public void setControlItemsHidden(boolean isControlItemVisible) {
        if (isControlItemVisible) {
            if (border.isFinalBitmap) {
                ic_move.setVisibility(VISIBLE);
                ic_right.setVisibility(GONE);
                ic_bottom.setVisibility(GONE);
            } else {
                ic_move.setVisibility(VISIBLE);
                ic_right.setVisibility(VISIBLE);
                ic_bottom.setVisibility(VISIBLE);
            }
        } else {
            ic_move.setVisibility(GONE);
            ic_right.setVisibility(GONE);
            ic_bottom.setVisibility(GONE);
        }
        this.postInvalidate();
        this.requestLayout();
    }
    //endregion

    //region Rectangle Border Class
    class RectangleBorder extends View implements OnTouchListener {
        private Paint mPaint;
        private boolean isErasable = false;
        private boolean isFinalBitmap = false;
        private Bitmap bitmap;
        private Canvas canvas;
        private int borderRadius;
        private RectF rect;
        //region GetterSetter

        public Paint getmPaint() {
            return mPaint;
        }

        public void setmPaint(Paint mPaint) {
            this.mPaint = mPaint;
        }

        public boolean isErasable() {
            return isErasable;
        }

        public void setErasable(boolean erasable) {
            isErasable = erasable;
        }

        public boolean isFinalBitmap() {
            return isFinalBitmap;
        }

        public void setFinalBitmap(boolean finalBitmap) {
            isFinalBitmap = finalBitmap;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Canvas getCanvas() {
            return canvas;
        }

        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }

        public int getBorderRadius() {
            return borderRadius;
        }

        public void setBorderRadius(int borderRadius) {
            this.borderRadius = borderRadius;
        }

        public RectF getRect() {
            return rect;
        }

        public void setRect(RectF rect) {
            this.rect = rect;
        }
        //endregion


        public RectangleBorder(Context context) {
            super(context);
            setmPaint(new Paint());
            getmPaint().setStyle(Paint.Style.STROKE);
            getmPaint().setStrokeWidth(10);
            getmPaint().setColor(Color.CYAN);
            this.setOnTouchListener(this);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas mainCanvas) {
            super.onDraw(mainCanvas);
            if (!isErasable) {
                if (!isFinalBitmap) {
                    bitmap = Bitmap.createBitmap(getMainWidth(), getMainHeight(), Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(bitmap);
                    setRect(new RectF(10, 10, (int) getRightCorner(), (int) getBottomCorner()));
                    canvas.drawRoundRect(getRect(), getBorderRadius(), getBorderRadius(), mPaint);
                    mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
                } else {
                    mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
                }
            } else {
                mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (canvas != null) {
                Paint deletingPaint = new Paint();
                deletingPaint.setStrokeWidth(30);
                deletingPaint.setStyle(Paint.Style.STROKE);
                deletingPaint.setAntiAlias(true);
                deletingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawPoint(event.getX(), event.getY(), deletingPaint);
                invalidate();
                return true;
            } else
                return false;
        }
    }
    //endregion
}
