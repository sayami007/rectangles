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

public class MainBorderClass extends FrameLayout implements View.OnTouchListener {
    //region Variables
    private Activity myAct;
    private Context ctx;
    private ImageView ic_left_top;
    private ImageView ic_left_bottom;
    private ImageView ic_right_top;
    private ImageView ic_right_bottom;
    private RectangleBorder border;
    private LayoutParams iv_scale_left_top;
    private LayoutParams iv_scale_left_bottom;
    private LayoutParams iv_scale_right_top;
    private LayoutParams iv_scale_right_bottom;
    private LayoutParams iv_border_params;
    private LayoutParams this_params;
    private int size;
    private int margin;
    private int mainHeight;
    private int mainWidth;
    private float rightBottomCorner;
    private float rightTopCorner;
    private float leftTopCorner;
    private float leftBottomCorner;
    private float selfSizeDp;
    private static final float BUTTON_SIZE_DP = 20;
    private boolean isControlItemVisible = true;

    private int xDelta;
    private int yDelta;

    //endregion
    //region Getter and Setter
    public float getLeftTopCorner() {
        return leftTopCorner;
    }

    public void setLeftTopCorner(float leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
    }

    public float getLeftBottomCorner() {
        return leftBottomCorner;
    }

    public void setLeftBottomCorner(float leftBottomCorner) {
        this.leftBottomCorner = leftBottomCorner;
    }

    public boolean isControlItemVisible() {
        return isControlItemVisible;
    }

    public void setControlItemVisible(boolean controlItemVisible) {
        isControlItemVisible = controlItemVisible;
    }

    public RectangleBorder getBorder() {
        return border;
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

    public Activity getMyAct() {
        return myAct;
    }

    public void setMyAct(Activity myAct) {
        this.myAct = myAct;
    }

    public float getRightBottomCorner() {
        return rightBottomCorner;
    }

    public void setRightBottomCorner(float rightBottomCorner) {
        this.rightBottomCorner = rightBottomCorner - 90;
    }

    public float getRightTopCorner() {
        return rightTopCorner;
    }

    public void setRightTopCorner(float rightTopCorner) {
        this.rightTopCorner = rightTopCorner - 90;
    }

    public float getSelfSizeDp() {
        return selfSizeDp;
    }

    public void setSelfSizeDp(float selfSizeDp) {
        this.selfSizeDp = selfSizeDp;
    }

    //endregion
    //region Constructor
    public MainBorderClass(Context ctx) {
        super(ctx);
        setCtx(ctx);
        setMyAct((Activity) ctx);
        initContent();
        initParams();
        addViewToMain();
    }
    //endregion
    //region Initialize

    /**
     * Initialize the border and handles
     */
    private void initContent() {
        //Getting the screen maximum width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getMyAct().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        setMainWidth(width);
        setMainHeight(height);
        checkMaxArea(getMainWidth(), getMainHeight());
        //Initialize borders and handles for the borders
        border = new RectangleBorder(ctx);
        border.setTag(Constant.BORDER);
        ic_left_top = new ImageView(ctx);
        ic_left_top.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_left_top.setTag(Constant.LEFT_TOP);
        ic_left_top.setImageResource(R.drawable.ic_move);
        ic_left_bottom = new ImageView(ctx);
        ic_left_bottom.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_left_bottom.setTag(Constant.LEFT_BOTTOM);
        ic_left_bottom.setImageResource(R.drawable.ic_move);
        ic_right_top = new ImageView(ctx);
        ic_right_top.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_right_top.setImageResource(R.drawable.ic_move);
        ic_right_top.setTag(Constant.RIGHT_TOP);
        ic_right_bottom = new ImageView(ctx);
        ic_right_bottom.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.circle_image_view_background));
        ic_right_bottom.setTag(Constant.RIGHT_BOTTOM);
        ic_right_bottom.setImageResource(R.drawable.ic_move);
        //Setting the listener for the handles
        ic_left_top.setOnTouchListener(this);
        ic_left_bottom.setOnTouchListener(this);
        ic_right_top.setOnTouchListener(this);
        ic_right_bottom.setOnTouchListener(this);
    }

    /**
     * Initialize the Layout parameters for border and handles
     */
    private void initParams() {
        setBackgroundColor(Color.MAGENTA);
        //Getting the size and margin for buttons
        size = convertDpToPixel(selfSizeDp, getContext());
        margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        //Setting the border
        setLeftTopCorner(0);
        setLeftBottomCorner(0);
        setRightBottomCorner(getMainHeight());
        setRightTopCorner(getMainWidth());
        //
        this_params = new LayoutParams(size, size);
        this_params.gravity = Gravity.CENTER;
        iv_border_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv_border_params.setMargins(margin, margin, margin, margin);
        iv_scale_left_top = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_left_top.gravity = Gravity.START | Gravity.TOP;
        iv_scale_left_bottom = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_left_bottom.gravity = Gravity.START | Gravity.BOTTOM;
        iv_scale_right_top = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_right_top.gravity = Gravity.END | Gravity.TOP;
        iv_scale_right_bottom = new LayoutParams(convertDpToPixel(BUTTON_SIZE_DP, getContext()), convertDpToPixel(BUTTON_SIZE_DP, getContext()));
        iv_scale_right_bottom.gravity = Gravity.BOTTOM | Gravity.END;
    }

    /**
     * Adding params to the views
     */
    private void addViewToMain() {
        this.setLayoutParams(this_params);
        this.addView(border, iv_border_params);
        this.addView(ic_left_top, iv_scale_left_top);
        this.addView(ic_left_bottom, iv_scale_left_bottom);
        this.addView(ic_right_top, iv_scale_right_top);
        this.addView(ic_right_bottom, iv_scale_right_bottom);
    }

    //endregion
    //region listener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("BORDER"+border.getX());
        System.out.println("BORDER Y"+border.getY());
        System.out.println(border.getWidth());
        System.out.println(border.getHeight());
        int X = (int) event.getRawX();
        int Y = (int) event.getRawY();
        float rightTop = getRightTopCorner();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getTag().toString()) {
                    case Constant.LEFT_TOP:
                        FrameLayout.LayoutParams leftTopParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                        xDelta = X - leftTopParam.leftMargin;
                        yDelta = Y - leftTopParam.topMargin;
                        break;
                    case Constant.LEFT_BOTTOM:
                        FrameLayout.LayoutParams leftBottomParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                        xDelta = X - leftBottomParam.leftMargin;
                        yDelta = Y + leftBottomParam.bottomMargin;
                        break;
                    case Constant.RIGHT_TOP:
                        FrameLayout.LayoutParams rightTopParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                        xDelta = X + rightTopParam.rightMargin;
                        yDelta = Y - rightTopParam.topMargin;
                        break;
                    case Constant.RIGHT_BOTTOM:
                        FrameLayout.LayoutParams rightBottomParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                        xDelta = X + rightBottomParam.rightMargin;
                        yDelta = Y + rightBottomParam.bottomMargin;
                        break;

                }
                return true;
            case MotionEvent.ACTION_MOVE: {
                if (v.getTag() == Constant.RIGHT_TOP) {
                    setRightTopCorner(X + v.getWidth());
                    setLeftBottomCorner(v.getY());
                    border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                    FrameLayout.LayoutParams rightTopParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                    rightTopParam.rightMargin = -(X - xDelta);
                    rightTopParam.topMargin = Y - yDelta;
                    v.setLayoutParams(rightTopParam);
                    //
                    FrameLayout.LayoutParams icLeftTopParams = (FrameLayout.LayoutParams) ic_left_top.getLayoutParams();
                    icLeftTopParams.topMargin = Y - yDelta;
                    FrameLayout.LayoutParams icRightBottomParams = (FrameLayout.LayoutParams) ic_right_bottom.getLayoutParams();
                    icRightBottomParams.rightMargin = -(X - xDelta);
                    //
                    if (rightTopParam.topMargin <= 0) {
                        rightTopParam.topMargin = 0;
                        icLeftTopParams.topMargin = 0;
                    }
                    if (rightTopParam.topMargin >= mainHeight - getStatusBarHeight() - v.getWidth()) {
                        rightTopParam.topMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                        icLeftTopParams.topMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                    }
                    if (getRightTopCorner() > border.getWidth() - v.getWidth()) {
                        System.out.println(getRightTopCorner());
                        setRightTopCorner(mainWidth);
                    }
                    if (rightTopParam.rightMargin <= 0) {
                        setRightTopCorner(mainWidth);
                        rightTopParam.rightMargin = 0;
                        icRightBottomParams.rightMargin = 0;
                    }
                    if (rightTopParam.rightMargin >= mainWidth - v.getWidth()) {
                        setRightTopCorner(v.getWidth()+border.getX());
                        rightTopParam.rightMargin = mainWidth - v.getWidth();
                        icRightBottomParams.rightMargin = mainWidth - v.getWidth();
                    }
                    //
                }
                if (v.getTag() == Constant.RIGHT_BOTTOM) {
                    FrameLayout.LayoutParams rightBottomParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                    rightBottomParam.rightMargin = -(X - xDelta);
                    rightBottomParam.bottomMargin = -(Y - yDelta);
                    v.setLayoutParams(rightBottomParam);
                    //
                    FrameLayout.LayoutParams icRightTopParams = (FrameLayout.LayoutParams) ic_right_top.getLayoutParams();
                    icRightTopParams.rightMargin = -(X - xDelta);
                    FrameLayout.LayoutParams icLeftBottomParams = (FrameLayout.LayoutParams) ic_left_bottom.getLayoutParams();
                    icLeftBottomParams.bottomMargin = -(Y - yDelta);
                    //
                    setRightTopCorner(X + v.getWidth());
                    setRightBottomCorner(Y - v.getWidth());
                    //
                    if (rightBottomParam.bottomMargin <= 0) {
                        rightBottomParam.bottomMargin = 0;
                        icLeftBottomParams.bottomMargin = 0;
                    }
                    if (rightBottomParam.bottomMargin >= mainHeight - getStatusBarHeight() - v.getWidth()) {
                        rightBottomParam.bottomMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                        icLeftBottomParams.bottomMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                    }
                    if (rightBottomParam.rightMargin <= 0) {
                        setRightTopCorner(mainWidth);
                        rightBottomParam.rightMargin = 0;
                        icRightTopParams.rightMargin = 0;
                    }
                    if (rightBottomParam.rightMargin >= mainWidth - v.getWidth()) {
                        setRightTopCorner(v.getWidth()+border.getX());
                        rightBottomParam.rightMargin = mainWidth - v.getWidth();
                        icRightTopParams.rightMargin = mainWidth - v.getWidth();
                    }
                    //
                    border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();
                } else if (v.getTag() == Constant.LEFT_BOTTOM) {
                    FrameLayout.LayoutParams leftBottomParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                    leftBottomParam.leftMargin = X - xDelta;
                    leftBottomParam.bottomMargin = -(Y - yDelta);
                    v.setLayoutParams(leftBottomParam);
                    //
                    FrameLayout.LayoutParams icLeftTopParams = (FrameLayout.LayoutParams) ic_left_top.getLayoutParams();
                    icLeftTopParams.leftMargin = X - xDelta;
                    FrameLayout.LayoutParams icRightBottomParams = (FrameLayout.LayoutParams) ic_right_bottom.getLayoutParams();
                    icRightBottomParams.bottomMargin = -(Y - yDelta);
                    //
                    if (leftBottomParam.leftMargin <= 0) {
                        leftBottomParam.leftMargin = 0;
                        icLeftTopParams.leftMargin = 0;
                    }
                    if (leftBottomParam.bottomMargin <= 0) {
                        leftBottomParam.bottomMargin = 0;
                        icRightBottomParams.bottomMargin = 0;
                    }
                    if (leftBottomParam.leftMargin >= mainWidth - v.getWidth()) {
                        leftBottomParam.leftMargin = mainWidth - v.getWidth();
                        icLeftTopParams.leftMargin = mainWidth - v.getWidth();
                    }
                    if (leftBottomParam.bottomMargin >= mainHeight - getStatusBarHeight() - v.getWidth()) {
                        leftBottomParam.bottomMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                        icRightBottomParams.bottomMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                    }
                    //
                    setLeftTopCorner(v.getX());
                    setRightBottomCorner(Y);
                    border.invalidate();
                    this.postInvalidate();
                    this.requestLayout();

                } else if (v.getTag() == Constant.LEFT_TOP) {
                    FrameLayout.LayoutParams leftTopParam = (FrameLayout.LayoutParams) v.getLayoutParams();
                    leftTopParam.leftMargin = X - xDelta;
                    leftTopParam.topMargin = Y - yDelta;
                    //
                    FrameLayout.LayoutParams icLeftBottomParams = (FrameLayout.LayoutParams) ic_left_bottom.getLayoutParams();
                    icLeftBottomParams.leftMargin = X - xDelta;
                    FrameLayout.LayoutParams icRightTopParams = (FrameLayout.LayoutParams) ic_right_top.getLayoutParams();
                    icRightTopParams.topMargin = Y - yDelta;
                    //
                    if (leftTopParam.leftMargin <= 0) {
                        leftTopParam.leftMargin = 0;
                        icLeftBottomParams.leftMargin = 0;
                    }
                    if (leftTopParam.leftMargin >= mainWidth - v.getWidth()) {
                        leftTopParam.leftMargin = mainWidth - v.getWidth();
                        icLeftBottomParams.leftMargin = mainWidth - v.getWidth();
                    }
                    if (leftTopParam.topMargin <= 0) {
                        leftTopParam.topMargin = 0;
                        icRightTopParams.topMargin = 0;
                    }
                    if (leftTopParam.topMargin >= mainHeight - getStatusBarHeight() - v.getWidth()) {
                        leftTopParam.topMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                        icRightTopParams.topMargin = mainHeight - getStatusBarHeight() - v.getWidth();
                    }
                    //
                    v.setLayoutParams(leftTopParam);
                    setLeftBottomCorner(v.getY());
                    setLeftTopCorner(v.getX());
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

    /**
     * Checking whether width is greater or height
     *
     * @param mainWidth  Width of the view
     * @param mainHeight Height of the view
     */
    private void checkMaxArea(int mainWidth, int mainHeight) {
        if (mainWidth >= mainHeight)
            setSelfSizeDp(mainWidth);
        else
            setSelfSizeDp(mainHeight);
    }

    /**
     * Check the status bar height
     *
     * @return height of status bar
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Checks and return action bar height
     *
     * @param context
     * @return height of action bar
     */
    private int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * Convert Dp to Pixel
     *
     * @param dp
     * @param context
     * @return
     */
    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * Show or hide the control items
     */
    public void setControlItemsHidden() {
        if (isControlItemVisible) {
            ic_left_bottom.setVisibility(VISIBLE);
            ic_left_top.setVisibility(VISIBLE);
            ic_right_bottom.setVisibility(VISIBLE);
            ic_right_top.setVisibility(VISIBLE);
        } else {
            ic_left_bottom.setVisibility(GONE);
            ic_left_top.setVisibility(GONE);
            ic_right_bottom.setVisibility(GONE);
            ic_right_top.setVisibility(GONE);

        }
        this.postInvalidate();
        this.requestLayout();

    }

    //endregion
//region Rectangle Border Class
    class RectangleBorder extends View implements OnTouchListener {
        //region Variables
        private Paint mPaint;
        private boolean isErasable = false;
        private boolean isFinalBitmap = false;
        private Bitmap bitmap;
        private Canvas canvas;
        private int borderRadius;
        private RectF rect;
        //endregion
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


        //region Initialize
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
                    setRect(new RectF(getLeftTopCorner(), getLeftBottomCorner(), getRightTopCorner(), getRightBottomCorner()));
                    canvas.drawRoundRect(getRect(), getBorderRadius(), getBorderRadius(), mPaint);
                    mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
                } else {
                    mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
                }
            } else {
                mainCanvas.drawBitmap(bitmap, 0, 0, mPaint);
            }
        }
        //endregion

        //region Listeners
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
        //endregion
    }
//endregion
}
