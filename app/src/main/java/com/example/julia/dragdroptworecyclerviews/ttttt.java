package com.example.myapplication.cusScrollbar;


import static com.example.myapplication.cusview.CarUiUtils.requireViewByRefId;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.R.styleable;
import com.example.myapplication.cusScrollbar.orientation.IOrientationStrategy;
import com.example.myapplication.cusview.ScrollBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;


public final class SmartScrollBarTest implements ScrollBar {
    private final String TAG = "==>scrollBar";
    //private final Paint paint;
    private int minWidth = 100;
    private int minHeight = 30;
    private RecyclerView bindView;
    private int maxLength;
    private int currentLength;
    private int orientation = 1;
    private IOrientationStrategy orientationHandler;
    private int backgroundCorner;
    private int sliderCorner;
    private int sliderColor = -1;
    private int sliderStyle;
    private float sliderLength;
    private int cantScrollState;
    private int canScrollState;
    private int dismissTime;
    private boolean enableDrag;
    private final RectF customBgRectF = new RectF();
    private float customBgCorner;
   // private final Paint customBgPaint$delegate;
    private int scrollType;
    private GestureDetector gestureDetector$delegate  ;
    private final Region sliderRegion = new Region();
    private RecyclerView.AdapterDataObserver recyclerViewDataListener;
    private Float firstRatio;
    private float lastOffsetRatio;
    private Boolean isTouched;
    private GestureDetector.SimpleOnGestureListener gestureListener = null;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 0;
    public static final int SCROLL_TYPE_SMOOTH = 1;
    public static final int SCROLL_TYPE_ITEM = 2;

    double firstX = 0;
    double firstY = 0;
    View scrollView;

    private final Interpolator mPaginationInterpolator = new AccelerateDecelerateInterpolator();
    RecyclerView mRecyclerView;
    View scrollViewTrack;
    View scrollViewthumb;
    View isDownEnabledButton;
    View isUpEnabledButton;

    private final Handler mHandler = new Handler();
    @NotNull
    public static final SmartScrollBarTest.Companion Companion = new Companion((DefaultConstructorMarker)null);

//    private final Paint getCustomBgPaint() {
//      //  Paint var1 = this.customBgPaint$delegate;
//        Object var3 = null;
//        return var1;
//    }

    private final GestureDetector getGestureDetector() {
        GestureDetector var1 = this.gestureDetector$delegate;
        Object var3 = null;
        return var1;
    }

//    private final void initAttrs(AttributeSet attrs) {
//        TypedArray var10000 = this.getContext().obtainStyledAttributes(attrs, styleable.SmartScrollBar);
//        Intrinsics.checkNotNullExpressionValue(var10000, "context.obtainStyledAttr…styleable.SmartScrollBar)");
//        TypedArray typeArray = var10000;
//        this.backgroundCorner = (int)typeArray.getDimension(styleable.SmartScrollBar_smart_background_corner, 0.0F);
//        this.sliderCorner = (int)typeArray.getDimension(styleable.SmartScrollBar_smart_slider_corner, 0.0F);
//        this.sliderColor = typeArray.getColor(styleable.SmartScrollBar_smart_slider_color, -1);
//        this.cantScrollState = typeArray.getInt(styleable.SmartScrollBar_smart_cant_scroll_style, 0);
//        this.canScrollState = typeArray.getInt(styleable.SmartScrollBar_smart_can_scroll_style, 0);
//        this.dismissTime = typeArray.getInt(styleable.SmartScrollBar_smart_dismiss_time, 1000);
//        this.orientation = typeArray.getInt(styleable.SmartScrollBar_smart_orientation, 1);
//        this.orientationHandler = IOrientationStrategy.Companion.createStrategy(this.orientation);
//        this.sliderStyle = typeArray.getInt(styleable.SmartScrollBar_smart_slider_style, 0);
//        SmartScrollBarTest var5 = this;
//
//        float var3;
//        SmartScrollBarTest var7;
//        try {
//            var7 = var5;
//            var3 = typeArray.getFraction(styleable.SmartScrollBar_smart_slider_length, 1, 1, 0.0F);
//        } catch (Exception var6) {
//            var7 = this;
//            var3 = typeArray.getDimension(styleable.SmartScrollBar_smart_slider_length, 0.0F);
//        }
//
//        var7.sliderLength = var3;
//        this.enableDrag = typeArray.getBoolean(styleable.SmartScrollBar_smart_enable_drag, false);
//        this.paint.setColor(this.sliderColor);
//        typeArray.recycle();
//    }

//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (this.orientation == 1) {
//            this.minWidth += this.minHeight;
//            this.minHeight = this.minWidth - this.minHeight;
//            this.minWidth -= this.minHeight;
//        }
//
//        if (widthSpecMode == Integer.MIN_VALUE && heightSpecMode == Integer.MIN_VALUE) {
//            this.setMeasuredDimension(this.minWidth, this.minHeight);
//        } else if (widthSpecMode == Integer.MIN_VALUE) {
//            this.setMeasuredDimension(this.minWidth, heightSpecSize);
//        } else if (heightSpecMode == Integer.MIN_VALUE) {
//            this.setMeasuredDimension(widthSpecSize, this.minHeight);
//        }
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

//    public void dispatchDraw(@Nullable Canvas canvas) {
//        super.dispatchDraw(canvas);
//        if (this.backgroundCorner != 0) {
//            if (this.backgroundCorner > Math.min(this.getWidth(), this.getHeight()) / 2) {
//                this.backgroundCorner = Math.min(this.getWidth(), this.getHeight()) / 2;
//            }
//
//            if (VERSION.SDK_INT >= 21) {
//                this.setClipToOutline(true);
//                this.setOutlineProvider((ViewOutlineProvider)(new ViewOutlineProvider() {
//                    public void getOutline(@NotNull View view, @NotNull Outline outline) {
//                        Intrinsics.checkNotNullParameter(view, "view");
//                        Intrinsics.checkNotNullParameter(outline, "outline");
//                        outline.setRoundRect(0, 0, SmartScrollBarTest.this.getWidth(), SmartScrollBarTest.this.getHeight(), (float) SmartScrollBarTest.this.backgroundCorner);
//                    }
//                }));
//            }
//
//        }
//    }

    public final void bindScrollView(@NotNull RecyclerView recyclerView)  {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        this.bindDataChangedListener(recyclerView);
        if (this.bindView != null) {
            Log.e(this.TAG, "该 ScrollBar 已经绑定了一个 RecyclerView，无法重复绑定！");
        } else {


            this.bindView = recyclerView;
            recyclerView.addOnScrollListener((RecyclerView.OnScrollListener)(new RecyclerView.OnScrollListener() {
                public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
//                    Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
//                    computeLength();
//                    setInvisibleStyle();   /////////////ttttttt
//                    scrollView.postInvalidate();

                 //   updatePaginationButtons();
                }
            }));
        }
    }

    private final void bindDataChangedListener(RecyclerView recyclerView)  {
        if (recyclerView.getAdapter() == null) {
        } else {
            try {
                RecyclerView.Adapter var10000 = recyclerView.getAdapter();
                Intrinsics.checkNotNull(var10000);
                var10000.registerAdapterDataObserver((RecyclerView.AdapterDataObserver)this.recyclerViewDataListener);
            } catch (Exception var3) {
            }

        }
    }

    private final void computeLength() {
        IOrientationStrategy var10000 = this.orientationHandler;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
        }

        RecyclerView var10001 = this.bindView;
        Intrinsics.checkNotNull(var10001);
        int totalLength = var10000.computeRecyclerViewTotalLength(var10001);
        this.maxLength = totalLength;
        IOrientationStrategy var2 = this.orientationHandler;
        if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
        }

        RecyclerView var10002 = this.bindView;
        Intrinsics.checkNotNull(var10002);
        this.currentLength = var2.computeRecyclerViewCurrentLength(var10002);
    }

    private final void setInvisibleStyle() {
        IOrientationStrategy var10000 = this.orientationHandler;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
        }

        if (!var10000.canScroll(this.bindView)) {
            switch (this.cantScrollState) {
                case 0:
                    scrollView.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    scrollView.setVisibility(View.GONE);
            }
        } else {
            scrollView.setVisibility(View.VISIBLE);
        }

    }

//    protected void onDraw(@NotNull Canvas canvas) {
//        Intrinsics.checkNotNullParameter(canvas, "canvas");
//        super.onDraw(canvas);
//        if (!this.customBgRectF.isEmpty()) {
//            canvas.drawRoundRect(this.customBgRectF, this.customBgCorner, this.customBgCorner, this.getCustomBgPaint());
//        }
//
//        IOrientationStrategy var10000;
//        RectF var6;
//        if (this.sliderStyle == 1 && this.sliderLength != 0.0F) {
//            var10000 = this.orientationHandler;
//            if (var10000 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
//            }
//
//            var6 = var10000.createFixedSlider(this.sliderLength, this.getWidth(), this.getHeight(), this.bindView);
//        } else {
//            var10000 = this.orientationHandler;
//            if (var10000 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
//            }
//
//            var6 = var10000.createSlider(this.maxLength, this.currentLength, this.getWidth(), this.getHeight(), this.bindView);
//        }
//
//        RectF scrollRect = var6;
//        canvas.drawRoundRect(scrollRect, (float)this.sliderCorner, (float)this.sliderCorner, this.paint);
//        Region var7 = this.sliderRegion;
//        boolean $i$f$toRect = false;
//        Rect r$iv = new Rect();
//        scrollRect.roundOut(r$iv);
//        var7.set(r$iv);
//        this.setVisibleStyle();
//    }





//    private final void setVisibleStyle() {
//        this.setAlpha(1.0F);
//        if (this.canScrollState == 1) {
//            this.animate().alpha(0.0F).setDuration((long)this.dismissTime).start();
//        }
//
//    }


//    public boolean onTouchEvent(@Nullable MotionEvent event) {
//        return this.getGestureDetector().onTouchEvent(event);
//    }

    private final void scrollRecyclerViewByDistance(float offsetRatio) {
        RecyclerView var10000 = this.bindView;
        if (var10000 != null && var10000.getAdapter() != null) {
            var10000 = this.bindView;
            if (var10000 != null && var10000.getLayoutManager() != null) {
                if (this.orientation == 1) {
                    var10000 = this.bindView;
                    Intrinsics.checkNotNull(var10000);
                    IOrientationStrategy var10002 = this.orientationHandler;
                    if (var10002 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
                    }

                    RecyclerView var10003 = this.bindView;
                    Intrinsics.checkNotNull(var10003);
                    int var3 = var10002.computeRecyclerViewTotalLength(var10003);
                    var10003 = this.bindView;
                    Intrinsics.checkNotNull(var10003);
                    Log.d("---custom----","----youcus---"+offsetRatio);
                    int scrollDis = MathKt.roundToInt((float)(var3 - var10003.getHeight()) * (offsetRatio - this.lastOffsetRatio));
                    Log.d("----scrollDis",scrollDis+"-------");
                    var10000.scrollBy(0, scrollDis);
                } else {
                    var10000 = this.bindView;
                    Intrinsics.checkNotNull(var10000);
                    IOrientationStrategy var10001 = this.orientationHandler;
                    if (var10001 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("orientationHandler");
                    }

                    RecyclerView var4 = this.bindView;
                    Intrinsics.checkNotNull(var4);
                    int var2 = var10001.computeRecyclerViewTotalLength(var4);
                    var4 = this.bindView;
                    Intrinsics.checkNotNull(var4);
                    var10000.scrollBy(MathKt.roundToInt((float)(var2 - var4.getWidth()) * (offsetRatio - this.lastOffsetRatio)), 0);
                }

                this.lastOffsetRatio = offsetRatio;
            }
        }
    }

    public final void scrollRecyclerViewByPosition(float ratio) {
        RecyclerView var10000 = this.bindView;
        if (var10000 != null && var10000.getAdapter() != null) {
            var10000 = this.bindView;
            if (var10000 != null && var10000.getLayoutManager() != null) {
                var10000 = this.bindView;
                RecyclerView.Adapter var4 = var10000 != null ? var10000.getAdapter() : null;
                Intrinsics.checkNotNull(var4);
                Intrinsics.checkNotNullExpressionValue(var4, "bindView?.adapter!!");
                int position = var4.getItemCount() - 1;
                float transRatio = ratio < (float)0 ? 0.0F : (ratio >= (float)1 ? 1.0F : ratio);
                var10000 = this.bindView;
                RecyclerView.LayoutManager var5 = var10000 != null ? var10000.getLayoutManager() : null;
                Intrinsics.checkNotNull(var5);
                var5.scrollToPosition((int)((float)position * transRatio));
            }
        }
    }


    GestureDetector  initGesture(Context context)  {
        GestureDetector gestureDetector = new GestureDetector(context,this.gestureListener);

        gestureDetector.setIsLongpressEnabled(false);
//        try {
//            gestureDetector.getClass().getDeclaredField("mTouchSlopSquare").setAccessible(true);
//            gestureDetector.getClass().getDeclaredField("mTouchSlopSquare").set(gestureDetector,0);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

        return gestureDetector;
    }
//    public SmartScrollBarTest(@Nullable Context context)  {
//        super(context);
//        this.customBgPaint$delegate = new Paint(); /// LazyKt.lazy((Function0)null);  //INSTANCE
//        this.scrollType = 1;
//
//
//        //this.gestureDetector$delegate
//
//        this.sliderRegion = new Region();
//        this.paint = new Paint();
//        this.paint.setStyle(Style.FILL);
//        this.recyclerViewDataListener = new RecyclerView.AdapterDataObserver() {
//            public void onChanged() {
//                RecyclerView var10000 = SmartScrollBarTest.this.bindView;
//                if (var10000 != null) {
//                    var10000.postDelayed((Runnable)(new Runnable() {
//                        public final void run() {
//                            SmartScrollBarTest.this.maxLength = 0;
//                            SmartScrollBarTest.this.computeLength();
//                            SmartScrollBarTest.this.setInvisibleStyle();
//                            SmartScrollBarTest.this.postInvalidate();
//                        }
//                    }), 200L);
//                }
//
//            }
//        };
//        this.gestureListener = new GestureDetector.SimpleOnGestureListener() {
//            public boolean onDown(@NotNull MotionEvent e) {
//                Intrinsics.checkNotNullParameter(e, "e");
//                if (e.getAction() == 0) {
//                    SmartScrollBarTest.this.firstRatio = null;
//                    SmartScrollBarTest.this.lastOffsetRatio = 0.0F;
//                    SmartScrollBarTest.this.isTouched = null;
//                }
//
//                return SmartScrollBarTest.this.enableDrag;
//            }
//
//            public boolean onScroll(@NotNull MotionEvent e1, @NotNull MotionEvent e2, float distanceX, float distanceY) {
//                Intrinsics.checkNotNullParameter(e1, "e1");
//                Intrinsics.checkNotNullParameter(e2, "e2");
//                if (SmartScrollBarTest.this.isTouched == null) {
//                    SmartScrollBarTest.this.isTouched = SmartScrollBarTest.this.sliderRegion.contains((int)e1.getX(), (int)e1.getY());
//                }
//
//                if (Intrinsics.areEqual(SmartScrollBarTest.this.isTouched, true)) {
//                    if (SmartScrollBarTest.this.bindView != null) {
//                        boolean var7 = false;
//                        boolean barLengthx = false;
//                        float offsetRatio = 0.0F;
//                        int barLength;
//                        if (SmartScrollBarTest.this.orientation == 1) {
//                            barLength = SmartScrollBarTest.this.getHeight() - SmartScrollBarTest.this.sliderRegion.getBounds().height();
//                            if (barLength == 0) {
//                                return true;
//                            }
//
//                            if (SmartScrollBarTest.this.firstRatio == null) {
//                                SmartScrollBarTest.this.firstRatio = (float) SmartScrollBarTest.this.sliderRegion.getBounds().top / (float)barLength;
//                            }
//
//                            offsetRatio = (e2.getY() - e1.getY()) / (float)barLength;
//                        } else {
//                            barLength = SmartScrollBarTest.this.getWidth() - SmartScrollBarTest.this.sliderRegion.getBounds().width();
//                            if (barLength == 0) {
//                                return true;
//                            }
//
//                            if (SmartScrollBarTest.this.firstRatio == null) {
//                                SmartScrollBarTest.this.firstRatio = (float) SmartScrollBarTest.this.sliderRegion.getBounds().left / (float)barLength;
//                            }
//
//                            offsetRatio = (e2.getX() - e1.getX()) / (float)barLength;
//                        }
//
//                        if (SmartScrollBarTest.this.scrollType == 1) {
//                            SmartScrollBarTest.this.scrollRecyclerViewByDistance(offsetRatio);
//                        } else {
//                            Float var10000 = SmartScrollBarTest.this.firstRatio;
//                            float ratio = (var10000 != null ? var10000 : 0.0F) + offsetRatio;
//                            if (ratio <= (float)0) {
//                                ratio = 0.0F;
//                            }
//
//                            if (ratio >= 1.0F) {
//                                ratio = 1.0F;
//                            }
//
//                            SmartScrollBarTest.this.scrollRecyclerViewByPosition(ratio);
//                        }
//                    }
//
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        };
//
//        this.gestureDetector$delegate = initGesture();
//    }

//    public SmartScrollBarTest(@Nullable Context context, @Nullable AttributeSet attrs)  {
//
//        super(context, attrs);
//        this.customBgPaint$delegate = new Paint(); ///LazyKt.lazy((Function0)null);///INSTANCE
//        this.scrollType = 1;
//      //  gestureDetector$delegate1 = initGesture();
//        this.sliderRegion = new Region();
//        this.paint = new Paint();
//        this.paint.setStyle(Style.FILL);
//        this.recyclerViewDataListener = new RecyclerView.AdapterDataObserver() {
//            public void onChanged() {
//                RecyclerView var10000 = SmartScrollBarTest.this.bindView;
//                if (var10000 != null) {
//                    var10000.postDelayed((Runnable)(new Runnable() {
//                        public final void run() {
//                            SmartScrollBarTest.this.maxLength = 0;
//                            SmartScrollBarTest.this.computeLength();
//                            SmartScrollBarTest.this.setInvisibleStyle();
//                            SmartScrollBarTest.this.postInvalidate();
//                        }
//                    }), 200L);
//                }
//
//            }
//        };
//        this.gestureListener = new GestureDetector.SimpleOnGestureListener() {
//            public boolean onDown(@NotNull MotionEvent e) {
//                Intrinsics.checkNotNullParameter(e, "e");
//                if (e.getAction() == 0) {
//                    SmartScrollBarTest.this.firstRatio = null;
//                    SmartScrollBarTest.this.lastOffsetRatio = 0.0F;
//                    SmartScrollBarTest.this.isTouched = null;
//                }
//
//                return SmartScrollBarTest.this.enableDrag;
//            }
//
//            public boolean onScroll(@NotNull MotionEvent e1, @NotNull MotionEvent e2, float distanceX, float distanceY) {
//                Intrinsics.checkNotNullParameter(e1, "e1");
//                Intrinsics.checkNotNullParameter(e2, "e2");
//                if (SmartScrollBarTest.this.isTouched == null) {
//                    SmartScrollBarTest.this.isTouched = SmartScrollBarTest.this.sliderRegion.contains((int)e1.getX(), (int)e1.getY());
//                }
//
//                if (Intrinsics.areEqual(SmartScrollBarTest.this.isTouched, true)) {
//                    if (SmartScrollBarTest.this.bindView != null) {
//                        boolean var7 = false;
//                        boolean barLengthx = false;
//                        float offsetRatio = 0.0F;
//                        int barLength;
//                        if (SmartScrollBarTest.this.orientation == 1) {
//                            barLength = SmartScrollBarTest.this.getHeight() - SmartScrollBarTest.this.sliderRegion.getBounds().height();
//                            if (barLength == 0) {
//                                return true;
//                            }
//
//                            if (SmartScrollBarTest.this.firstRatio == null) {
//                                SmartScrollBarTest.this.firstRatio = (float) SmartScrollBarTest.this.sliderRegion.getBounds().top / (float)barLength;
//                            }
//
//                            offsetRatio = (e2.getY() - e1.getY()) / (float)barLength;
//                        } else {
//                            barLength = SmartScrollBarTest.this.getWidth() - SmartScrollBarTest.this.sliderRegion.getBounds().width();
//                            if (barLength == 0) {
//                                return true;
//                            }
//
//                            if (SmartScrollBarTest.this.firstRatio == null) {
//                                SmartScrollBarTest.this.firstRatio = (float) SmartScrollBarTest.this.sliderRegion.getBounds().left / (float)barLength;
//                            }
//
//                            offsetRatio = (e2.getX() - e1.getX()) / (float)barLength;
//                        }
//
//                        if (SmartScrollBarTest.this.scrollType == 1) {
//                            SmartScrollBarTest.this.scrollRecyclerViewByDistance(offsetRatio);
//                        } else {
//                            Float var10000 = SmartScrollBarTest.this.firstRatio;
//                            float ratio = (var10000 != null ? var10000 : 0.0F) + offsetRatio;
//                            if (ratio <= (float)0) {
//                                ratio = 0.0F;
//                            }
//
//                            if (ratio >= 1.0F) {
//                                ratio = 1.0F;
//                            }
//
//                            SmartScrollBarTest.this.scrollRecyclerViewByPosition(ratio);
//                        }
//                    }
//
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        };
//        //gestureDetector$delegate1 = initGesture();
//       // this.gestureDetector$delegate = initGesture();
//        this.initAttrs(attrs);
//    }

    private int[] getVerticalRange() {
        int[] verticalRange = new int[2];
        verticalRange[0] = (int) scrollViewTrack.getY() + scrollViewthumb.getHeight() / 2;
        verticalRange[1] = (int) scrollViewTrack.getY() + scrollViewTrack.getHeight()
                - scrollViewthumb.getHeight() / 2;
        return verticalRange;
    }
    @Override
    public void initialize(RecyclerView recyclerView, View scrollViewss) {

        scrollView = scrollViewss;
        mRecyclerView = recyclerView;


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        firstX =    ev.getX();
                        firstY = ev.getY();
                        Log.d("i---downion","------"+firstY+"----"+firstX);

                    case MotionEvent.ACTION_MOVE:

                        int finalX = (int) ev.getX();
                        int finalY = (int) ev.getY();
                        Log.d("on---fion","------"+finalY+"----"+finalX);
                        int mLastY = (int) (firstY - finalY);
                        int mLastX = (int) (firstX - finalX);

                        Log.d("position---scrollview","------"+mLastY+"----");
                        //((View)getParent()).scrollBy(0,mLastY);
                        scrollView.scrollBy(0,mLastY);
                        // invalidate();
                        break;
                    default:
                        break;
                }

                return  true;
            }
        });
        scrollViewthumb = requireViewByRefId(scrollView, R.id.car_ui_scrollbar_thumb);
        scrollViewTrack = requireViewByRefId(scrollView, R.id.car_ui_scrollbar_track);

        isDownEnabledButton = requireViewByRefId(scrollView, R.id.car_ui_scrollbar_page_down);

        isUpEnabledButton = requireViewByRefId(scrollView, R.id.car_ui_scrollbar_page_up);
        scrollViewTrack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector$delegate.onTouchEvent(event);
                return true;
            }
        });

       // scrollView.setVisibility(View.INVISIBLE);
//        scrollView.addOnLayoutChangeListener(
//                (View v,
//                 int left,
//                 int top,
//                 int right,
//                 int bottom,
//                 int oldLeft,
//                 int oldTop,
//                 int oldRight,
//                 int oldBottom) -> mHandler.post(this::updatePaginationButtons));

       // View finalScrollView = scrollView;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() {
            public boolean onDown(@NotNull MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                if (e.getAction() == 0) {
                    SmartScrollBarTest.this.firstRatio = null;
                    SmartScrollBarTest.this.lastOffsetRatio = 0.0F;
                    SmartScrollBarTest.this.isTouched = null;
                }

                return SmartScrollBarTest.this.enableDrag;
            }

            public boolean onScroll(@NotNull MotionEvent e1, @NotNull MotionEvent e2, float distanceX, float distanceY) {
                Intrinsics.checkNotNullParameter(e1, "e1");
                Intrinsics.checkNotNullParameter(e2, "e2");
                if (SmartScrollBarTest.this.isTouched == null) {
                   SmartScrollBarTest.this.isTouched = sliderRegion.contains((int)e1.getX(), (int)e1.getY());
                }
                SmartScrollBarTest.this.isTouched = true;
                if (Intrinsics.areEqual(SmartScrollBarTest.this.isTouched, true)) {
                    if (SmartScrollBarTest.this.bindView != null) {
                        boolean var7 = false;
                        boolean barLengthx = false;
                        float offsetRatio = 0.0F;
                        int barLength;
                        if (SmartScrollBarTest.this.orientation == 1) {


                            final int[] scrollbarRange = getVerticalRange();
                            //    Log.d("----scrolllenght--",scrollbarRange[1]+"ddddd");
                          //  Log.d("----newDragPos--",newDragPos+"ddddd");

                            barLength = scrollbarRange[1] - scrollbarRange[0];
                          ///  barLength = scrollView.getHeight() - scrollViewthumb.getheight();
                            if (barLength == 0) {
                                return true;
                            }

                            if (SmartScrollBarTest.this.firstRatio == null) {
                                SmartScrollBarTest.this.firstRatio = (float) scrollViewthumb.getY() / (float)barLength; ////
                            }

                            offsetRatio = (e2.getY() - e1.getY()) / (float)barLength;
                        }





//                        else {
//                            barLength = scrollView.getWidth() - SmartScrollBarTest.this.sliderRegion.getBounds().width();
//                            if (barLength == 0) {
//                                return true;
//                            }
//
//                            if (SmartScrollBarTest.this.firstRatio == null) {
//                                SmartScrollBarTest.this.firstRatio = (float) SmartScrollBarTest.this.sliderRegion.getBounds().left / (float)barLength;
//                            }
//
//                            offsetRatio = (e2.getX() - e1.getX()) / (float)barLength;
//                        }

                        SmartScrollBarTest.this.scrollType = 1;
                        if (SmartScrollBarTest.this.scrollType == 1) {
                            SmartScrollBarTest.this.scrollRecyclerViewByDistance(offsetRatio);
                        } else {
                            Float var10000 = SmartScrollBarTest.this.firstRatio;
                            float ratio = (var10000 != null ? var10000 : 0.0F) + offsetRatio;
                            if (ratio <= (float)0) {
                                ratio = 0.0F;
                            }

                            if (ratio >= 1.0F) {
                                ratio = 1.0F;
                            }

                            SmartScrollBarTest.this.scrollRecyclerViewByPosition(ratio);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        };
        this.gestureDetector$delegate = initGesture( recyclerView.getContext());

                this.recyclerViewDataListener = new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                RecyclerView var10000 = SmartScrollBarTest.this.bindView;
                if (var10000 != null) {
                    var10000.postDelayed((Runnable)(new Runnable() {
                        public final void run() {
                            SmartScrollBarTest.this.maxLength = 0;
                            SmartScrollBarTest.this.computeLength();
                            SmartScrollBarTest.this.setInvisibleStyle();
                            scrollView.postInvalidate();
                        }
                    }), 200L);
                }

            }
        };

        this.orientationHandler = IOrientationStrategy.Companion.createStrategy(this.orientation);
        bindScrollView(mRecyclerView);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
    private void updatePaginationButtons() {
        boolean isAtStart = isAtStart();
        boolean isAtEnd = isAtEnd();
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        // enable/disable the button before the view is shown. So there is no flicker.
        Log.d("----isStart---","------start---"+isAtStart+"----isEnd"+isAtEnd);
        setUpEnabled(!isAtStart);
        setDownEnabled(!isAtEnd);
        if ((isAtStart && isAtEnd) || layoutManager == null || layoutManager.getItemCount() == 0) {
            scrollView.setVisibility(View.INVISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
        }
        if (layoutManager == null) {
            return;
        }
        if (layoutManager.canScrollVertically()) {
            setParameters(
                    getRecyclerView().computeVerticalScrollRange(),
                    getRecyclerView().computeVerticalScrollOffset(),
                    getRecyclerView().computeVerticalScrollExtent());
        } else {
            setParameters(
                    getRecyclerView().computeHorizontalScrollRange(),
                    getRecyclerView().computeHorizontalScrollOffset(),
                    getRecyclerView().computeHorizontalScrollExtent());
        }
        scrollView.invalidate();
    }


    private void setParameters(
            @IntRange(from = 0) int range,
            @IntRange(from = 0) int offset,
            @IntRange(from = 0) int extent) {

        Log.d("---range---","range"+range+"---d--"+offset+"exste"+extent);
        // Not laid out yet, so values cannot be calculated.
        if (!scrollView.isLaidOut()) {
            return;
        }
        // If the scroll bars aren't visible, then no need to update.
        if (scrollView.getVisibility() == View.GONE || range == 0) {
            return;
        }
        int thumbLength = calculateScrollThumbLength(range, extent);
        int thumbOffset = calculateScrollThumbOffset(range, offset, thumbLength);

        Log.d("---thumbOffset---","---d--"+thumbOffset);
        // Sets the size of the thumb and request a redraw if needed.
        ViewGroup.LayoutParams lp = scrollViewthumb.getLayoutParams();
        if (lp.height != thumbLength) {
            lp.height = thumbLength;
            scrollViewthumb.requestLayout();
        }
        moveY(scrollViewthumb, thumbOffset);
    }

    private int calculateScrollThumbLength(int range, int extent) {
        // Scale the length by the available space that the thumb can fill.
        return Math.round(((float) extent / range) * scrollViewTrack.getHeight());
    }
    /**
     * Calculates and returns how much the scroll thumb should be offset from the top of where it
     * has
     * been laid out.
     *
     * @param range       The total amount of space the scroll bar is allowed to roam over.
     * @param offset      The amount the scroll bar should be offset, expressed in the same units as
     *                    the
     *                    given range.
     * @param thumbLength The current length of the thumb in pixels.
     * @return The amount the thumb should be offset in pixels.
     */
    private int calculateScrollThumbOffset(int range, int offset, int thumbLength) {
        // Ensure that if the user has reached the bottom of the list, then the scroll bar is
        // aligned to the bottom as well. Otherwise, scale the offset appropriately.
        // This offset will be a value relative to the parent of this scrollbar, so start by where
        // the top of scrollbar track is.

        Log.d("---range---","range"+range+"---d--"+offset+"thumleng"+thumbLength);
        Log.d("---ScrollTrack---","trach"+scrollViewTrack.getHeight());
        return scrollViewTrack.getTop()
                + (isDownEnabled()
                ? Math.round(((float) offset / range) * scrollViewTrack.getHeight())
                : scrollViewTrack.getHeight() - thumbLength);
    }

    private boolean isDownEnabled() {
        return isDownEnabledButton.isEnabled();
    }

    private void setUpEnabled(boolean enabled) {
        isUpEnabledButton.setEnabled(enabled);
        isUpEnabledButton.setAlpha(enabled ? 1f : 0.2f);
    }
    private void setDownEnabled(boolean enabled) {
        isDownEnabledButton.setEnabled(enabled);
        isDownEnabledButton.setAlpha(enabled ? 1f :  0.2f);
    }

    private void moveY(final View view, float newPosition) {
        view.animate()
                .y(newPosition)
                .setDuration(/* duration= */ 0)
                .setInterpolator(mPaginationInterpolator)
                .start();
    }

    /** Returns {@code true} if the RecyclerView is completely displaying the first item. */
    boolean isAtStart() {
       RecyclerView.LayoutManager  layoutManager=  mRecyclerView.getLayoutManager();
        if (layoutManager == null || layoutManager.getChildCount() == 0) {
            return true;
        }
        @NonNull View firstChild = Objects.requireNonNull(layoutManager.getChildAt(0));
        OrientationHelper orientationHelper =
                layoutManager.canScrollVertically() ? OrientationHelper.createVerticalHelper(layoutManager)
                        : OrientationHelper.createHorizontalHelper(layoutManager);
        // Check that the first child is completely visible and is the first item in the list.
        return orientationHelper.getDecoratedStart(firstChild)
                >= orientationHelper.getStartAfterPadding() && layoutManager.getPosition(firstChild)
                == 0;
    }
    /** Returns {@code true} if the RecyclerView is completely displaying the last item. */
    boolean isAtEnd() {
        RecyclerView.LayoutManager  layoutManager=  mRecyclerView.getLayoutManager();
        if (layoutManager == null || layoutManager.getChildCount() == 0) {
            return true;
        }
        int childCount = layoutManager.getChildCount();
        OrientationHelper orientationHelper =
                layoutManager.canScrollVertically() ? OrientationHelper.createVerticalHelper(layoutManager)
                        : OrientationHelper.createHorizontalHelper(layoutManager);
        @NonNull View lastVisibleChild = Objects.requireNonNull(
                layoutManager.getChildAt(childCount - 1));
        // The list has reached the bottom if the last child that is visible is the last item
        // in the list and it's fully shown.
        return layoutManager.getPosition(lastVisibleChild) == (layoutManager.getItemCount() - 1)
                && layoutManager.getDecoratedBottom(lastVisibleChild)
                <= orientationHelper.getEndAfterPadding();
    }

    @Override
    public void requestLayout() {
        scrollView.requestLayout();
    }

    @Override
    public void setPadding(int paddingStart, int paddingEnd) {
        scrollView.setPadding(scrollView.getPaddingLeft(), paddingStart,
                scrollView.getPaddingRight(), paddingEnd);
    }


    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
} 