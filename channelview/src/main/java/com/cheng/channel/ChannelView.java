package com.cheng.channel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cheng.channel.adapter.StyleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChannelView extends ScrollView {
    private Context mContext;
    private Map<String, List<Channel>> channelContents = new LinkedHashMap<>();
    private ChannelLayout channelLayout;

    /**
     * 列数
     */
    private int channelColumn = 4;

    private int channelWidth;

    private int channelHeight;

    /**
     * 周围padding
     */
    private int channelPadding;

    /**
     * 频道板块title高度
     */
    private int platesTitleHeight;

    /**
     * 频道板块title左右padding
     */
    private int platesTitleLeftRightPadding;

    /**
     * 水平方向上的间隔线
     */
    private int channelHorizontalSpacing;

    /**
     * 竖直方向上的间隔线
     */
    private int channelVerticalSpacing;

    @DrawableRes
    @Deprecated
    private int channelNormalBackground;

    @DrawableRes
    @Deprecated
    private int channelEditBackground;

    @DrawableRes
    @Deprecated
    private int channelFocusedBackground;

    @DrawableRes
    @Deprecated
    private int channelFixedBackground;

    @ColorInt
    @Deprecated
    private int channelNormalTextColor;

    @ColorInt
    @Deprecated
    private int channelFixedTextColor;

    @ColorInt
    @Deprecated
    private int channelFocusedTextColor;

    @DrawableRes
    private int tipEditBackground;

    @DrawableRes
    private int tipFinishBackground;

    @DrawableRes
    private int platesTitleBackground;

    @ColorInt
    private int tipEditTextColor;

    @ColorInt
    private int tipFinishTextColor;

    @ColorInt
    private int platesTitleColor;

    private boolean platesTitleBold;

    private int platesTitleSize;

    private int tipEditTextSize;

    private int tipFinishTextSize;

    @Deprecated
    private int channelTextSize;

    private List<View> fixedTextView = new ArrayList<>();

    private List<View> allTextView = new ArrayList<>();

    private float density;

    public ChannelView(Context context) {
        this(context, null);
    }

    public ChannelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChannelView);
        channelHeight = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelHeight, getResources().getDimensionPixelSize(R.dimen.channelHeight));
        channelColumn = typedArray.getInteger(R.styleable.ChannelView_channelColumn, channelColumn);
        channelPadding = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelPadding, getResources().getDimensionPixelSize(R.dimen.channelPadding));
        channelHorizontalSpacing = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelHorizontalSpacing, getResources().getDimensionPixelSize(R.dimen.channelHorizontalSpacing));
        channelVerticalSpacing = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelVerticalSpacing, getResources().getDimensionPixelSize(R.dimen.channelVerticalSpacing));
        channelNormalBackground = typedArray.getResourceId(R.styleable.ChannelView_channelNormalBackground, R.drawable.bg_channel_normal);
        channelEditBackground = typedArray.getResourceId(R.styleable.ChannelView_channelEditBackground, R.drawable.bg_channel_edit);
        channelFocusedBackground = typedArray.getResourceId(R.styleable.ChannelView_channelFocusedBackground, R.drawable.bg_channel_focused);
        channelFixedBackground = typedArray.getResourceId(R.styleable.ChannelView_channelFixedBackground, R.drawable.bg_channel_normal);
        channelNormalTextColor = typedArray.getColor(R.styleable.ChannelView_channelNormalTextColor, getResources().getColor(R.color.channelNormalTextColor));
        channelFixedTextColor = typedArray.getColor(R.styleable.ChannelView_channelFixedTextColor, getResources().getColor(R.color.channelFixedTextColor));
        channelFocusedTextColor = typedArray.getColor(R.styleable.ChannelView_channelFocusedTextColor, getResources().getColor(R.color.channelNormalTextColor));
        channelTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        tipEditBackground = typedArray.getResourceId(R.styleable.ChannelView_tipEditBackground, R.drawable.bg_channel_transparent);
        platesTitleBackground = typedArray.getResourceId(R.styleable.ChannelView_platesTitleBackground, R.drawable.bg_channel_transparent);
        tipEditTextColor = typedArray.getColor(R.styleable.ChannelView_tipEditTextColor, getResources().getColor(R.color.channelNormalTextColor));
        platesTitleColor = typedArray.getColor(R.styleable.ChannelView_platesTitleColor, getResources().getColor(R.color.channelNormalTextColor));
        platesTitleBold = typedArray.getBoolean(R.styleable.ChannelView_platesTitleBold, false);
        platesTitleSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        tipEditTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_tipEditTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        platesTitleHeight = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleHeight, getResources().getDimensionPixelSize(R.dimen.platesTitleHeight));
        platesTitleLeftRightPadding = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleLeftRightPadding, getResources().getDimensionPixelSize(R.dimen.platesTitleLeftRightPadding));
        tipFinishBackground = typedArray.getResourceId(R.styleable.ChannelView_tipFinishBackground, R.drawable.bg_channel_transparent);
        tipFinishTextColor = typedArray.getColor(R.styleable.ChannelView_tipFinishTextColor, getResources().getColor(R.color.channelNormalTextColor));
        tipFinishTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_tipFinishTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        typedArray.recycle();
        if (channelColumn < 1) {
            channelColumn = 1;
        }
        density = context.getResources().getDisplayMetrics().density;
        maxAccessDrag = density * DRAG_THRESHOLD + 0.5f;
    }

    /**
     * 可允许拖拽的阈值(单位为dp)
     */
    private final int DRAG_THRESHOLD = 5;

    /**
     * 触摸频道进行move时，当达到该值时可允许频道拖拽
     */
    private float maxAccessDrag;

    /**
     * 添加频道板块
     *
     * @see StyleAdapter#getChannelData()
     */
    @Deprecated
    public void addPlate(String plateName, List<Channel> channelList) {
        if (channelList != null && channelList.size() > 0) {
            if (channelContents.size() != 0) {
                for (Channel channel : channelList) {
                    channel.channelBelong = channelContents.size();
                }
            } else {
                myChannelCode = new int[channelList.size()];
                for (int i = 0; i < channelList.size(); i++) {
                    channelList.get(i).code = i;
                    myChannelCode[i] = i;
                }
            }
            channelContents.put(plateName, channelList);
        }
    }

    /**
     * 设置频道正常状态下背景
     *
     * @see StyleAdapter#setNormalStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelNormalBackground(@DrawableRes int channelNormalBackground) {
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setBackgroundResource(view, channelNormalBackground);
            }
        }
    }

    /**
     * 设置频道编辑状态下背景
     *
     * @see StyleAdapter#setEditStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelEditBackground(@DrawableRes int channelEditBackground) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelEditBackground(channelEditBackground);
        }
    }

    /**
     * 设置频道编辑且点击状态下背景
     *
     * @see StyleAdapter#setFocusedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFocusedBackground(@DrawableRes int channelFocusedBackground) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelFocusedBackground(channelFocusedBackground);
        }
    }

    /**
     * 设置固定频道的背景
     *
     * @param channelFixedBackground
     * @see StyleAdapter#setFixedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFixedBackground(@DrawableRes int channelFixedBackground) {
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setBackgroundResource(view, channelFixedBackground);
            }
        }
    }

    /**
     * 设置固定频道的颜色
     *
     * @param channelFixedTextColor
     * @see StyleAdapter#setFixedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFixedTextColor(@ColorInt int channelFixedTextColor) {
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextColor(view, channelFixedTextColor);
            }
        }
    }

    /**
     * 设置频道字体颜色
     *
     * @param channelNormalTextColor
     * @see StyleAdapter#setNormalStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelNormalTextColor(@ColorInt int channelNormalTextColor) {
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextColor(view, channelNormalTextColor);
            }
        }
    }

    /**
     * 设置编辑且点击状态下频道字体颜色
     *
     * @param channelFocusedTextColor
     * @see StyleAdapter#setFocusedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFocusedTextColor(@ColorInt int channelFocusedTextColor) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelFocusedTextColor(channelFocusedTextColor);
        }
    }

    /**
     * 设置频道字体大小
     *
     * @param channelTextSize
     * @see StyleAdapter#createStyleView(ViewGroup, String)
     */
    @Deprecated
    public void setChannelTextSizeRes(@DimenRes int channelTextSize) {
        this.channelTextSize = getResources().getDimensionPixelSize(channelTextSize);
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
    }

    /**
     * 设置频道字体大小
     *
     * @param channelTextSize
     * @see StyleAdapter#createStyleView(ViewGroup, String)
     */
    @Deprecated
    public void setChannelTextSize(int unit, int channelTextSize) {
        this.channelTextSize = (int) TypedValue.applyDimension(unit, channelTextSize, getResources().getDisplayMetrics());
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
    }

    private OnChannelListener onChannelListener;

    private DefaultStyleAdapter defaultStyleAdapter;

    //位置交换的回调
    private PositionChangeCallBack onPositionChangeCallBack;

    /**
     * 检查adapter是否是defaultStyleAdapter
     *
     * @return
     */
    private boolean checkDefaultAdapter() {
        return defaultStyleAdapter != null || styleAdapter instanceof DefaultStyleAdapter;
    }

    /**
     * 是否已填充数据
     */
    private boolean isInflateData;

    /**
     * 添加完频道模块之后，进行填充数据
     *
     * @see StyleAdapter#getChannelData()
     */
    @Deprecated
    public void inflateData() {
        //只填充一次数据
        if (isInflateData) {
            return;
        }
        isInflateData = true;
        if (styleAdapter == null) {
            styleAdapter = defaultStyleAdapter = new DefaultStyleAdapter() {

                @Override
                public LinkedHashMap<String, List<Channel>> getChannelData() {
                    return null;
                }
            };
        }
        LinkedHashMap<String, List<Channel>> channelData = styleAdapter.getChannelData();
        if (channelData != null) {
            channelContents.clear();
            Set<String> keySet = channelData.keySet();
            for (String key : keySet) {
                addPlate(key, channelData.get(key));
            }
        }
        //如果只有一组频道，默认再加上一组
        if (channelContents.size() == 0) {
            channelContents.put("没有频道哦~~", null);
        }
        if (checkDefaultAdapter()) {
            defaultStyleAdapter = (DefaultStyleAdapter) styleAdapter;
            defaultStyleAdapter.setChannelTextSize(channelTextSize);
            defaultStyleAdapter.setChannelNormalBackground(channelNormalBackground);
            defaultStyleAdapter.setChannelFocusedBackground(channelFocusedBackground);
            defaultStyleAdapter.setChannelEditBackground(channelEditBackground);
            defaultStyleAdapter.setChannelFixedBackground(channelFixedBackground);
            defaultStyleAdapter.setChannelNormalTextColor(channelNormalTextColor);
            defaultStyleAdapter.setChannelFixedTextColor(channelFixedTextColor);
            defaultStyleAdapter.setChannelFocusedTextColor(channelFocusedTextColor);
        }
        if (channelLayout == null) {
            channelLayout = new ChannelLayout(mContext);
            addView(channelLayout);
        }
    }

    /**
     * 获取我的频道
     *
     * @return
     */
    public List<Channel> getMyChannel() {
        List<Channel> channels = new ArrayList<>();
        if (channelLayout != null && channelLayout.channelGroups.size() > 0 && channelLayout.channelGroups.get(0) != null) {
            for (View view : channelLayout.channelGroups.get(0)) {
                channels.add(getChannelAttr(view).channel);
            }
        }
        return channels;
    }

    private int[] myChannelCode;

    public interface OnChannelListener {

        /**
         * 编辑频道完成
         *
         * @param channelList
         */
        void channelEditFinish(List<Channel> channelList);

        /**
         * 开始编辑频道
         */
        void channelEditStart();
    }

    /**
     * @param onChannelListener
     */
    @Deprecated
    public void setOnChannelItemClickListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    /**
     * 位置交换回调
     *
     * @param callBack
     */
    public void setPositionChangeCallBack(PositionChangeCallBack callBack) {
        this.onPositionChangeCallBack = callBack;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionMask = ev.getAction() & MotionEvent.ACTION_MASK;
        if (actionMask == MotionEvent.ACTION_POINTER_DOWN) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    private StyleAdapter styleAdapter;

    private class ChannelLayout extends GridLayout implements OnClickListener, OnTouchListener {

        /**
         * 频道最小可拖动距离
         */
        private int RANGE = 30;

        private final int DURATION_TIME = 200;

        /**
         * 是否重新布局
         */
        private boolean isAgainLayout = true;

        /**
         * 所有频道组
         */
        private List<ArrayList<View>> channelGroups = new ArrayList<>();

        /**
         * 所有频道
         */
        ArrayList<View> myChannels = new ArrayList<>();

        /**
         * 每组channel的行数
         */
        private int[] groupChannelColumns;

        private TextView tipEdit, tipFinish;

        /**
         * 是否是编辑状态
         */
        private boolean isEditState;

        /**
         * 是否允许拖拽
         */
        private boolean isAccessDrag;

        /**
         * 可允许拖拽的最小间隔时间
         */
        private final int MIN_TIME_INTERVAL = 65;

        public ChannelLayout(Context context) {
            this(context, null);
        }

        public ChannelLayout(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ChannelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);//ChannelLayout的宽
            //不是通过动画改变ChannelLayout的高度
            int height = 0;
            int allChannelTitleHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (getChannelAttr(childAt).type == ChannelAttr.TITLE) {
                    //计算标题View的宽高
                    childAt.measure(MeasureSpec.makeMeasureSpec(width - channelPadding * 2, MeasureSpec.EXACTLY), heightMeasureSpec);
                    allChannelTitleHeight += childAt.getMeasuredHeight();
                } else if (getChannelAttr(childAt).type == ChannelAttr.CHANNEL) {
                    //计算每个频道的宽高
                    channelWidth = (width - channelVerticalSpacing * (channelColumn * 2 - 2) - channelPadding * 2) / channelColumn;
                    childAt.measure(MeasureSpec.makeMeasureSpec(channelWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(channelHeight, MeasureSpec.EXACTLY));
                }
            }
            for (int groupChannelColumn : groupChannelColumns) {
                if (groupChannelColumn > 0) {
                    height += channelHeight * groupChannelColumn + (groupChannelColumn * 2 - 2) * channelHorizontalSpacing;
                }
            }
            height += channelPadding * 2 + allChannelTitleHeight;//ChannelLayout的高
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (isAgainLayout) {
                super.onLayout(changed, left, top, right, bottom);
                for (int i = 0; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    ChannelAttr tag = getChannelAttr(childAt);
                    tag.coordinate.x = childAt.getX();
                    tag.coordinate.y = childAt.getY();
                }
                isAgainLayout = false;
            }
        }

        private void init() {
            RANGE = (int) (density * RANGE + 0.5f);
            setColumnCount(channelColumn);
            addChannelView();
        }

        /**
         * 设置频道View
         */
        private void addChannelView() {
            if (channelContents != null) {
                groupChannelColumns = new int[channelContents.size()];
                int j = 0;
                int startRow = 0;
                for (String aKeySet : channelContents.keySet()) {//遍历key值，设置标题名称
                    List<Channel> channelContent = channelContents.get(aKeySet);
                    if (channelContent == null) {
                        channelContent = new ArrayList<>();
                    }
                    groupChannelColumns[j] = channelContent.size() % channelColumn == 0 ? channelContent.size() / channelColumn : channelContent.size() / channelColumn + 1;
                    if (j == 0) {
                        startRow = 0;
                    } else {
                        startRow += groupChannelColumns[j - 1] + 1;
                    }
                    Spec rowSpec = GridLayout.spec(startRow);
                    //标题要占channelColumn列
                    Spec columnSpec = GridLayout.spec(0, channelColumn);

                    ChannelLayoutParams layoutParams = new ChannelLayoutParams(rowSpec, columnSpec);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.cgl_my_channel, null);
                    if (j == 0) {
                        tipEdit = view.findViewById(R.id.tv_tip_edit);
                        tipEdit.setVisibility(VISIBLE);
                        tipEdit.setOnClickListener(this);
                        tipEdit.setBackgroundResource(tipEditBackground);
                        tipEdit.setTextColor(tipEditTextColor);
                        tipEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipEditTextSize);
                        tipFinish = view.findViewById(R.id.tv_tip_finish);
                        tipFinish.setVisibility(INVISIBLE);
                        tipFinish.setOnClickListener(this);
                        tipFinish.setBackgroundResource(tipFinishBackground);
                        tipFinish.setTextColor(tipFinishTextColor);
                        tipFinish.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipFinishTextSize);
                    }

                    ChannelAttr channelTitleAttr = new ChannelAttr();
                    channelTitleAttr.type = ChannelAttr.TITLE;
                    channelTitleAttr.coordinate = new PointF();
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    tvTitle.setText(aKeySet);
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, platesTitleSize);
                    tvTitle.setBackgroundResource(platesTitleBackground);
                    tvTitle.setTextColor(platesTitleColor);
                    if (platesTitleBold) {
                        tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    layoutParams.height = platesTitleHeight;
                    layoutParams.leftMargin = channelPadding;
                    //为标题View添加一个ChannelAttr属性
                    layoutParams.mChannelAttr = channelTitleAttr;
                    view.setPadding(platesTitleLeftRightPadding, 0, platesTitleLeftRightPadding, 0);
                    addView(view, layoutParams);

                    //添加每个方块item
                    ArrayList<View> channelGroup = new ArrayList<>();
                    int remainder = channelContent.size() % channelColumn;
                    for (int i = 0; i < channelContent.size(); i++) {//遍历value中的频道
                        ViewHolder holder = styleAdapter.createStyleView(this, channelContent.get(i).channelName);
                        View channelView = holder.itemView;
                        if (channelView == null) {
                            throw new RuntimeException("You must set an adapter for the channel.");
                        }
                        ChannelAttr channelAttr = new ChannelAttr();
                        channelAttr.type = ChannelAttr.CHANNEL;
                        channelAttr.groupIndex = j;
                        channelAttr.coordinate = new PointF();
                        channelAttr.channel = channelContent.get(i);
                        channelView.setOnTouchListener(this);
                        styleAdapter.setNormalStyle(holder);
                        allTextView.add(channelView);
                        channelView.setOnClickListener(this);
                        //设置每个频道的间距
                        ChannelLayoutParams params = new ChannelLayoutParams();
                        int leftMargin = channelVerticalSpacing, topMargin = channelHorizontalSpacing, rightMargin = channelVerticalSpacing, bottomMargin = channelHorizontalSpacing;
                        if (i % channelColumn == 0) {
                            leftMargin = channelPadding;
                        }
                        if ((i + 1) % channelColumn == 0) {
                            rightMargin = channelPadding;
                        }
                        if (i < channelColumn) {
                            topMargin = 0;
                        }
                        if (remainder == 0) {
                            if (i >= channelContent.size() - channelColumn) {
                                bottomMargin = 0;
                            }
                        } else {
                            if (i >= channelContent.size() - remainder) {
                                bottomMargin = 0;
                            }
                        }
                        params.mChannelAttr = channelAttr;
                        params.mViewHolder = holder;
                        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                        addView(channelView, params);
                        channelGroup.add(channelView);
                    }

                    channelGroups.add(channelGroup);
                    j++;
                }
            }

            //added by caoshuang
            for (int i = 0; i < channelGroups.size(); i++) {
                myChannels.addAll(channelGroups.get(i));
            }
        }

        /**
         * 拖拽时距离点击时的最远距离
         */
        private double maxDistanceToDownPosition;

        private int indexIndex = -1, targetIndex = -1;

        //modified by caoshuang
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            //如果点击的是我的频道组中的频道
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                maxDistanceToDownPosition = 0;
                downX = dragX = event.getRawX();
                downY = dragY = event.getRawY();
                if (isEditState) {
                    setTime(v);
                }
            }
            if (isEditState) {
                if (event.getAction() == MotionEvent.ACTION_MOVE && isAccessDrag) {
                    //手移动时拖动频道
                    //请求ScrollView不要拦截MOVE事件，交给TextView处理
                    requestDisallowInterceptTouchEvent(true);
                    if (maxDistanceToDownPosition < maxAccessDrag) {
                        double sqrt = Math.sqrt(Math.pow(event.getRawX() - downX, 2) + Math.pow(event.getRawY() - downY, 2));
                        if (sqrt > maxDistanceToDownPosition) {
                            maxDistanceToDownPosition = sqrt;
                        }
                    }
                    channelDrag(v, event);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (thread != null && thread.isAlive() && !thread.isInterrupted()) {
                        thread.interrupt();
                    }
                    if (isAccessDrag) {
                        isAccessDrag = false;
                        styleAdapter.setEditStyle(getViewHolder(v));

                        if (indexIndex != -1) {
                            ChannelAttr vIndexTag = getChannelAttr(v);
                            if (targetIndex != -1) {    //有目标则与其交换（交互目标view和坐标）
                                PointF targetCoordinate, indexCoordinate, cK;
                                View vK;

                                indexCoordinate = vIndexTag.coordinate;

                                View targetView = myChannels.get(targetIndex);    //目标
                                ChannelAttr targetTag = getChannelAttr(targetView);
                                targetCoordinate = targetTag.coordinate;

                                //交换两个view
                                vK = v;
                                myChannels.set(indexIndex, myChannels.get(targetIndex));
                                myChannels.set(targetIndex, vK);

                                //交换两个坐标
                                cK = targetCoordinate;
                                targetCoordinate = indexCoordinate;
                                indexCoordinate = cK;

                                vIndexTag.coordinate = indexCoordinate;
                                targetTag.coordinate = targetCoordinate;

                                myChannels.get(indexIndex).animate().x(targetCoordinate.x).y(targetCoordinate.y).setDuration(DURATION_TIME);
                                myChannels.get(targetIndex).animate().x(indexCoordinate.x).y(indexCoordinate.y).setDuration(DURATION_TIME);

                                onPositionChangeCallBack.changePosition(indexIndex, targetIndex);

                                indexIndex = -1;
                                targetIndex = -1;
                            } else {    //没有目标则回到自身坐标
                                v.animate().x(vIndexTag.coordinate.x).y(vIndexTag.coordinate.y).setDuration(DURATION_TIME);
                            }
                        }

                        return !(maxDistanceToDownPosition < maxAccessDrag);
                    }
                }
            }
            return false;
        }

        private void setTime(final View v) {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(MIN_TIME_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    Message message = new Message();
                    message.obj = v;
                    handler.sendMessage(message);
                }
            };
            thread.start();
        }

        private Thread thread;

        private Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                View v = (View) msg.obj;
                v.bringToFront();
                styleAdapter.setFocusedStyle(getViewHolder(v));
                isAccessDrag = true;
            }
        };

        @Override
        public void onClick(View v) {
            if (v == tipEdit) {
                edit();
                if (onChannelListener != null) {
                    onChannelListener.channelEditStart();
                }
            } else if (v == tipFinish) {//点击完成按钮时
                changeTip(false);
                if (onChannelListener != null) {
                    onChannelListener.channelEditFinish(getMyChannel());
                }
            }
        }

        private void edit() {
            for (ArrayList<View> channelViews : channelGroups) {
                for (View view : channelViews) {
                    styleAdapter.setEditStyle(getViewHolder(view));
                }
            }
            changeTip(true);
        }

        private float downX, downY;
        private float dragX, dragY;

        /**
         * 频道拖动
         */
        private void channelDrag(View v, MotionEvent event) {
            float moveX = event.getRawX();
            float moveY = event.getRawY();
            v.setX(v.getX() + (moveX - dragX));
            v.setY(v.getY() + (moveY - dragY));
            dragX = moveX;
            dragY = moveY;

            int vIndex = myChannels.indexOf(v);
            for (int i = 0; i < myChannels.size(); i++) {
                if (i != vIndex) {
                    View iChannel = myChannels.get(i);
                    ChannelAttr iChannelTag = getChannelAttr(iChannel);
                    int x1 = (int) iChannelTag.coordinate.x;
                    int y1 = (int) iChannelTag.coordinate.y;
                    int sqrt = (int) Math.sqrt((v.getX() - x1) * (v.getX() - x1) + (v.getY() - y1) * (v.getY() - y1));
                    indexIndex = vIndex;
                    if (sqrt <= RANGE) {
                        targetIndex = i;
                        break;
                    } else {
                        targetIndex = -1;
                    }
                }
            }
        }

        /**
         * 更改提示语
         *
         * @param state
         */
        private void changeTip(boolean state) {
            if (state) {
                tipFinish.setVisibility(VISIBLE);
                tipEdit.setVisibility(INVISIBLE);
                isEditState = true;
            } else {
                tipFinish.setVisibility(INVISIBLE);
                tipEdit.setVisibility(VISIBLE);
                isEditState = false;
                for (ArrayList<View> channelViews : channelGroups) {
                    for (View view : channelViews) {
                        styleAdapter.setNormalStyle(getViewHolder(view));
                    }
                }
            }
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            handler.removeCallbacksAndMessages(null);
        }
    }

    private ChannelAttr getChannelAttr(View view) {
        return ((ChannelLayoutParams) view.getLayoutParams()).mChannelAttr;
    }

    private ViewHolder getViewHolder(View view) {
        return ((ChannelLayoutParams) view.getLayoutParams()).mViewHolder;
    }

    public static class ChannelLayoutParams extends GridLayout.LayoutParams {
        ViewHolder mViewHolder;
        ChannelAttr mChannelAttr;

        public ChannelLayoutParams(GridLayout.Spec rowSpec, GridLayout.Spec columnSpec) {
            super(rowSpec, columnSpec);
        }

        public ChannelLayoutParams() {
        }
    }
}
