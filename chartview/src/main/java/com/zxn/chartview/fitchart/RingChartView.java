/*
 * Copyright Txus Ballesteros 2015 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.zxn.chartview.fitchart;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.zxn.chartview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 环形比例图表,Ring
 * Updated by zxn on 2020/9/12.
 */
public class RingChartView extends View {
    static final int ANIMATION_MODE_LINEAR = 0;
    static final int ANIMATION_MODE_OVERDRAW = 1;
    static final int DEFAULT_VIEW_RADIUS = 0;
    static final int DEFAULT_MIN_VALUE = 0;
    static final int DEFAULT_MAX_VALUE = 100;
    static final int START_OSTIAL_ANGLE_A = -90;//-90
    static final float START_OSTIAL_ANGLE_B = 0.0f;
    static final int START_ANGLE = -92;//-90
    static final int ANIMATION_DURATION = 1000;
    static final float INITIAL_ANIMATION_PROGRESS = 0.0f;
    static final float MAXIMUM_SWEEP_ANGLE = 360f;
    static final int DESIGN_MODE_SWEEP_ANGLE = 216;
    private static final String TAG = "FitChart";
    private RectF drawingArea;
    private Paint backStrokePaint;
    private Paint valueDesignPaint;
    private int backStrokeColor;
    private int valueStrokeColor;
    private float strokeSize;
    private float minValue = DEFAULT_MIN_VALUE;
    private float maxValue = DEFAULT_MAX_VALUE;
    private List<FitChartValue> chartValues;
    private float animationProgress = INITIAL_ANIMATION_PROGRESS;
    private float maxSweepAngle = MAXIMUM_SWEEP_ANGLE;
    private AnimationMode animationMode = AnimationMode.LINEAR;
    private Paint ostialPaint;
    private Paint mouthPaint;
    private String title = "";
    private Paint titlePaint;
    private float titleSize;
    private float numberSize;
    private String number = "";
    private Paint numberPaint;
    private int ostialColor;

    public RingChartView(Context context) {
        super(context);
        initializeView(null);
    }

    public RingChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(attrs);
    }

    public RingChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(attrs);
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float value) {
        minValue = value;
    }

    /*public void setValues(List<FitChartValue> values) {//kxd
        chartValues.clear();
        maxSweepAngle = 0;
        float offsetSweepAngle = START_ANGLE;
        for (FitChartValue chartValue : values) {
            float sweepAngle = calculateSweepAngle(chartValue.getValue());
            chartValue.setPaint(buildPaintForValue());
            chartValue.setStartAngle(offsetSweepAngle);
            chartValue.setSweepAngle(sweepAngle);
            chartValues.add(chartValue);
            offsetSweepAngle += sweepAngle;
            maxSweepAngle += sweepAngle;
        }
        playAnimation();
    }*/

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float value) {
        maxValue = value;
    }

    public void setValue(float value) {
        chartValues.clear();
        FitChartValue chartValue = new FitChartValue(value, valueStrokeColor);
        chartValue.setPaint(buildPaintForValue(strokeSize));
        chartValue.setStartAngle(START_ANGLE);
        chartValue.setSweepAngle(calculateSweepAngle(value));
        chartValues.add(chartValue);
        maxSweepAngle = chartValue.getSweepAngle();
        playAnimation();
    }

    public void setAnimationMode(AnimationMode mode) {
        this.animationMode = mode;
    }

    void setAnimationSeek(float value) {
        animationProgress = value;
        invalidate();
    }

    private Paint buildPaintForValue() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeSize);
        //paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeCap(Paint.Cap.BUTT);
        return paint;
    }

    private void initializeView(AttributeSet attrs) {//k
        chartValues = new ArrayList<>();
        initializeBackground();//更改背景.
        readAttributes(attrs);//读取属性.
        preparePaints();//准备绘画.
    }

    private void initializeBackground() {
        if (!isInEditMode()) {
            if (getBackground() == null) {
                setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            }
        }
    }

    private void calculateDrawableArea() {
        float drawPadding = (strokeSize / 2);
        float width = getWidth();
        float height = getHeight();
        float left = drawPadding;
        float top = drawPadding;
        float right = width - drawPadding;
        float bottom = height - drawPadding;
        drawingArea = new RectF(left, top, right, bottom);
    }

    /*private void readAttributes(AttributeSet attrs) {//修改!
        Resources resources = getContext().getResources();
        valueStrokeColor = resources.getColor(R.color.default_chart_value_color);
        backStrokeColor = resources.getColor(R.color.default_back_stroke_color);
        strokeSize = resources.getDimension(R.dimen.dp_px20);//尺寸.
        if (attrs != null) {
            TypedArray attributes = getContext()
                    .getTheme().obtainStyledAttributes(attrs, R.styleable.FitChart, 0, 0);
            strokeSize = attributes
                    .getDimensionPixelSize(R.styleable.FitChart_strokeSize, (int) strokeSize);
            valueStrokeColor = attributes
                    .getColor(R.styleable.FitChart_valueStrokeColor, valueStrokeColor);
            backStrokeColor = attributes
                    .getColor(R.styleable.FitChart_backStrokeColor, backStrokeColor);
            int attrAnimationMode = attributes.getInteger(R.styleable.FitChart_animationMode, ANIMATION_MODE_LINEAR);
            if (attrAnimationMode == ANIMATION_MODE_LINEAR) {
                animationMode = AnimationMode.LINEAR;
            } else {
                animationMode = AnimationMode.OVERDRAW;
            }
            attributes.recycle();
        }
    }*/


    /*private void preparePaints() {
        backStrokePaint = getPaint();
        backStrokePaint.setColor(backStrokeColor);
        backStrokePaint.setStyle(Paint.Style.STROKE);
        backStrokePaint.setStrokeWidth(strokeSize);
        valueDesignPaint = getPaint();
        valueDesignPaint.setColor(valueStrokeColor);
        valueDesignPaint.setStyle(Paint.Style.STROKE);
        valueDesignPaint.setStrokeWidth(strokeSize);
    }*/

    private Paint getPaint() {
        if (!isInEditMode()) {
            return new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            return new Paint();
        }
    }

    private float getViewRadius() {
        if (drawingArea != null) {
            return (drawingArea.width() / 2);
        } else {
            return DEFAULT_VIEW_RADIUS;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDrawableArea();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.max(width, height);
        setMeasuredDimension(size, size);
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderBack(canvas);
        renderValues(canvas);
    }*/


    private void renderBack(Canvas canvas) {
        Path path = new Path();
        float viewRadius = getViewRadius();
        path.addCircle(drawingArea.centerX(), drawingArea.centerY(), viewRadius, Path.Direction.CCW);
        canvas.drawPath(path, backStrokePaint);
    }

    private void renderValues(Canvas canvas) {
        if (!isInEditMode()) {
            int valuesCounter = (chartValues.size() - 1);
            for (int index = valuesCounter; index >= 0; index--) {
                renderValue(canvas, chartValues.get(index));
            }
        } else {
            renderValue(canvas, null);
        }
    }

    private void renderValue(Canvas canvas, FitChartValue value) {
        if (!isInEditMode()) {
            float animationSeek = calculateAnimationSeek();
            Renderer renderer = RendererFactory.getRenderer(animationMode, value, drawingArea);
            Path path = renderer.buildPath(animationProgress, animationSeek);
            if (path != null) {
                canvas.drawPath(path, value.getPaint());
            }
        } else {
            Path path = new Path();
            path.addArc(drawingArea, START_ANGLE, DESIGN_MODE_SWEEP_ANGLE);
            canvas.drawPath(path, valueDesignPaint);
        }
    }

    private float calculateAnimationSeek() {
        return ((maxSweepAngle * animationProgress) + START_ANGLE);
    }

    private float calculateSweepAngle(float value) {
        float chartValuesWindow = Math.max(minValue, maxValue) - Math.min(minValue, maxValue);
        float chartValuesScale = (360f / chartValuesWindow);
        return (value * chartValuesScale);
    }

    private void playAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animationSeek", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setTarget(this);
        animatorSet.play(animator);
        animatorSet.start();
    }

    public void setCurrentProgress(float progress) {
        this.setMinValue(0f);
        this.setMaxValue(100f);
        Resources resources = getResources();
        List<FitChartValue> values = new ArrayList<>();

        float mouthProgress = 1f;
        if (progress >= 100f) {
            progress = 100f;
            //mouthProgress = 0f;
            values.add(new FitChartValue(progress, resources.getColor(R.color.chart_value_1)));
        } else if (progress < 0f) {
            progress = 0f;
            //mouthProgress = 0f;
            values.add(new FitChartValue(progress, resources.getColor(R.color.chart_value_1)));
        } else if (progress == 0f) {
            values.add(new FitChartValue(100f, resources.getColor(R.color.use_back_stroke_color)));
        } else {
            values.add(new FitChartValue(progress, resources.getColor(R.color.chart_value_1)));
            values.add(new FitChartValue(mouthProgress, resources.getColor(R.color.kxd_white)));
            values.add(new FitChartValue(100f, resources.getColor(R.color.use_back_stroke_color)));
        }
        this.setValues(values);
    }

    public void setAmount(float sumAmount, float usableAmount) {
        float usedAmount = sumAmount - usableAmount;
        this.setMinValue(0f);
        this.setMaxValue(100f);
        Resources resources = getResources();
        List<FitChartValue> values = new ArrayList<>();
        float mouthProgress = 1f;//mouthProgress
        float usedAmountProgress = (usedAmount / sumAmount) * 98f;
        if ((sumAmount - usableAmount) > 0 && usableAmount != 0 && usableAmount != -1) {
            values.clear();
            values.add(new FitChartValue(mouthProgress, resources.getColor(R.color.kxd_white)));
            values.add(new FitChartValue(usedAmountProgress, resources.getColor(R.color.chart_value_1)));
            values.add(new FitChartValue(mouthProgress, resources.getColor(R.color.kxd_white)));
            //values.add(new FitChartValue(100f, resources.getColor(R.color.use_back_stroke_color)));
        } else if ((sumAmount - usableAmount) == 0 && usableAmount != 0) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.use_back_stroke_color)));
        } else if (usableAmount == 0) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.chart_value_1)));
        } else if (usableAmount == -1) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.default_back_stroke_color)));
        }
        this.setValues(values);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(String number, boolean isRed) {
        this.number = number;
        if (isRed) {
            numberPaint.setColor(Color.parseColor("#ff4444"));
            numberSize = titleSize * 1.2f;
            numberPaint.setTextSize(numberSize);
        }
        /*if ("额度申请".equals(number)) {
            numberPaint.setColor(Color.parseColor("#ff4444"));
            numberSize = titleSize * 1.2f;
            numberPaint.setTextSize(numberSize);
        }else if (number.contains("¥")){
            numberPaint.setColor(Color.parseColor("#ff4444"));
            numberSize = titleSize * 1.2f;
            numberPaint.setTextSize(numberSize);
        }*/
    }

    //backStrokeColor   //#3b8ed4,
    public void setbackStrokeColor(int backStrokeColor) {
        this.backStrokeColor = backStrokeColor;//----
        //postInvalidate();
    }

    //ostialColor
    public void setOstialColor(int ostialColor) {
        this.ostialColor = ostialColor;//----
        postInvalidate();
    }

    private Paint buildPaintForValue(float width) {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        //paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeCap(Paint.Cap.BUTT);
        return paint;
    }

    private void readAttributes(AttributeSet attrs) {
        Resources resources = getContext().getResources();
        valueStrokeColor = resources.getColor(R.color.default_chart_value_color);//进度色
        backStrokeColor = resources.getColor(R.color.use_back_stroke_color);//整个环形颜色.
        strokeSize = 40;
        if (attrs != null) {
            TypedArray attributes = getContext()
                    .getTheme().obtainStyledAttributes(attrs, R.styleable.RingChartView, 0, 0);
            strokeSize = attributes
                    .getDimensionPixelSize(R.styleable.RingChartView_strokeSize, (int) strokeSize);
            titleSize = attributes.getDimension(R.styleable.RingChartView_titleSize, 20);
            numberSize = titleSize * 1.5f;
            valueStrokeColor = attributes
                    .getColor(R.styleable.RingChartView_valueStrokeColor, valueStrokeColor);
            backStrokeColor = attributes
                    .getColor(R.styleable.RingChartView_backStrokeColor, backStrokeColor);
            int attrAnimationMode = attributes.getInteger(R.styleable.RingChartView_animationMode, ANIMATION_MODE_LINEAR);
            if (attrAnimationMode == ANIMATION_MODE_LINEAR) {
                animationMode = AnimationMode.LINEAR;
            } else {
                animationMode = AnimationMode.OVERDRAW;
            }
            attributes.recycle();
        }
    }


    private void preparePaints() {
        //画整个环的笔.
        backStrokePaint = getPaint();
        backStrokePaint.setColor(backStrokeColor);
        backStrokePaint.setStyle(Paint.Style.STROKE);
        backStrokePaint.setStrokeWidth(strokeSize);

        //画标题的笔
        titlePaint = getPaint();
        titlePaint.setStrokeWidth(0);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(titleSize);
        titlePaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体

        //数字.
        numberPaint = getPaint();
        numberPaint.setStrokeWidth(0);
        numberPaint.setColor(Color.BLACK);
        numberPaint.setTextSize(numberSize);
        numberPaint.setTypeface(Typeface.DEFAULT); //设置字体//DEFAULT_BOLD


        //画进度的笔.
        valueDesignPaint = getPaint();
        valueDesignPaint.setColor(valueStrokeColor);
        valueDesignPaint.setStyle(Paint.Style.STROKE);
        //valueDesignPaint.setStrokeCap(Paint.Cap.ROUND);
        valueDesignPaint.setStrokeCap(Paint.Cap.SQUARE);
        valueDesignPaint.setStrokeWidth(strokeSize);

        //画开口2的笔.
        mouthPaint = getPaint();
        mouthPaint.setColor(getResources().getColor(R.color.kxd_white));
        mouthPaint.setStyle(Paint.Style.STROKE);
        mouthPaint.setStrokeCap(Paint.Cap.SQUARE);//square
        mouthPaint.setStrokeWidth(strokeSize + 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderBack(canvas);//画出整个圆环.
        renderTitle(canvas);//画出标题.
        renderValues(canvas);//画出进度.
        //renderStartOstial(canvas);//画开始开口.
    }

    private void renderTitle(Canvas canvas) {
        float titleWidth = titlePaint.measureText(title);
        canvas.drawText(title,
                drawingArea.centerX() - titleWidth / 2,
                drawingArea.centerY() / 2 + titleSize / 2,
                titlePaint);
        //画额度
        float numberWidth = numberPaint.measureText(number);
        canvas.drawText(number,
                drawingArea.centerX() - (numberWidth / 2f),
                drawingArea.centerY() + numberSize / 2f,
                numberPaint);

    }

    //修改.setValues
    public void setValues(List<FitChartValue> values) {
        chartValues.clear();
        maxSweepAngle = 0;
        float offsetSweepAngle = START_ANGLE;
        for (int i = 0; i < values.size(); i++) {
            //FitChartValue chartValue : values
            FitChartValue chartValue = values.get(i);
            float sweepAngle = calculateSweepAngle(chartValue.getValue());
            if (values.size() == 1) {
                chartValue.setPaint(buildPaintForValue());
            } else if (values.size() == 3) {
                if (i == 0 || i == 2) {
                    chartValue.setPaint(buildPaintForValue(strokeSize + strokeSize / 4));
                } else {
                    chartValue.setPaint(buildPaintForValue());
                }
            }
            chartValue.setStartAngle(offsetSweepAngle);
            chartValue.setSweepAngle(sweepAngle);
            chartValues.add(chartValue);
            offsetSweepAngle += sweepAngle;
            maxSweepAngle += sweepAngle;
        }
        playAnimation();
    }

}


//不要了!-----------------------------------------------------------
    /*private void renderStartOstial(Canvas canvas) {
        ostialPaint.setColor(ostialColor);
        canvas.drawLine(drawingArea.centerX() + strokeSize / 4,
                0,
                drawingArea.centerX() + strokeSize / 4,
                strokeSize * 2,
                ostialPaint);// 画线
    }*/

    /*private void renderEndOstial(Canvas canvas) {
        //canvas.drawLine(60, 40, 100, 40, ostialPaint);// 画线
        //float startX, float startY, float stopX, float stopY,
        //canvas.drawLine(110, 40, drawingArea.centerX(), drawingArea.centerY(), ostialPaint);// 斜线
        Path path = new Path();
        path.addArc(drawingArea, DESIGN_MODE_SWEEP_ANGLE - 6, 1);
        canvas.drawPath(path, mouthPaint);
    }*/
