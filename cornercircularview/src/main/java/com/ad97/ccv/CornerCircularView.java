package com.ad97.ccv;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CornerCircularView extends ConstraintLayout implements View.OnTouchListener {

    /**
     * @param viewInfoList
     * This parameter is used for creating List to store information related to menu item using ViewInfo class as generics
     * @param modelInfoList
     * This parameter is used as a List to fetch and then set the information of the menu item using ModelInfo class as generics
     * @param mContext
     * This parameter is current activity context set by constructor on creation of CornerCircularView Object
     * @param constraintLayout
     * This parameter is used to get view of Constraint Layout using findViewById(..) to set view according the addButton (which is centered)
     * @param addButton
     * This parameter is used to get view of FloatingActionButton using findViewById(..) to set functions performed by this button
     * @param valueAnimator
     * This parameter used as ValueAnimator class object to handle all animations or rotations of the project
     * @param set
     * This parameter uses as ConstraintSet class object used to clone and apply different view structure according to project requirements
     */
    private List<ViewInfo> viewInfoList;
    private Context mContext;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton addButton;
    private boolean close, up, clockwise;
    private int initialX;
    private int initialY;
    private int width;
    private int height;
    private int currentValue;
    private int[] onScreenIndex = new int[3];
    private ConstraintSet set;
    private OnItemClickListener itemClickListener;

    public CornerCircularView(Context context) {
        super(context);
    }

    /**
     * constructor called when the object of our custom class is created
     *
     * @param context setting context of current activity
     * @param attrs   setting attributes from current xml or activity
     */
    public CornerCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_corner_circular_view, this);
        constraintLayout = findViewById(R.id.root_layout);
        addButton = findViewById(R.id.add);
        viewInfoList = new ArrayList<>();
        close = true;
        set = new ConstraintSet();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (close) {
                    addView();
                    addButton.setImageResource(R.drawable.minus);
                    close = false;
                } else {
                    removeView();
                    addButton.setImageResource(R.drawable.add);
                    close = true;
                }
            }
        });

    }

    public CornerCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method is used to set addButton background according to users choice
     *
     * @param addButtonColor used to set addButton color according to user requirements
     */
    public void setAddButtonColor(int addButtonColor) {
        addButton.setBackgroundTintList(ColorStateList.valueOf(addButtonColor));
    }

    /**
     * This method is used to add Menu List from the modelInfo List to set the with views required for menu
     *
     * @param modelInfo used to set information to viewInfoList with the menu item views (FloatingActionButton and TextView)
     */
    public void setMenuFromList(List<ModelInfo> modelInfo) {
        Log.i("Size", "" + viewInfoList.size());
        if (modelInfo.size() == 2) {
            viewInfoList.add(new ViewInfo(modelInfo.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(0).getButtonBackground(),
                    modelInfo.get(0).getButtonIcon(), 0));

            viewInfoList.add(new ViewInfo(modelInfo.get(1).getText(), new TextView(mContext), modelInfo.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(1).getButtonBackground(),
                    modelInfo.get(1).getButtonIcon(), 315));

        } else if (modelInfo.size() == 1) {
            viewInfoList.add(new ViewInfo(modelInfo.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(0).getButtonBackground(),
                    modelInfo.get(0).getButtonIcon(), 0));
        } else {
            viewInfoList.add(new ViewInfo(modelInfo.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(0).getButtonBackground(),
                    modelInfo.get(0).getButtonIcon(), 0));
            viewInfoList.add(new ViewInfo(modelInfo.get(1).getText(), new TextView(mContext), modelInfo.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(1).getButtonBackground(),
                    modelInfo.get(1).getButtonIcon(), 315));
            viewInfoList.add(new ViewInfo(modelInfo.get(2).getText(), new TextView(mContext), modelInfo.get(2).getTextColor(),
                    new FloatingActionButton(mContext), modelInfo.get(2).getButtonBackground(),
                    modelInfo.get(2).getButtonIcon(), 270));
            for (int i = 3; i < modelInfo.size(); i++) {
                viewInfoList.add(new ViewInfo(modelInfo.get(i).getText(), new TextView(mContext), modelInfo.get(i).getTextColor(),
                        new FloatingActionButton(mContext), modelInfo.get(i).getButtonBackground(),
                        modelInfo.get(i).getButtonIcon(), 45));
            }
        }
    }

    /**
     * addView() method is used to add views on addButton Click
     */
    @SuppressLint("ClickableViewAccessibility")
    private void addView() {
        Log.i("Size", "" + viewInfoList.size());
        currentValue = 1;
        onScreenIndex = new int[]{0, 1, 2};
        for (int i = 0; i < viewInfoList.size(); i++) {
            FloatingActionButton fabTemp = viewInfoList.get(i).getActionButton();
            TextView textTemp = viewInfoList.get(i).getTextView();
            fabTemp.setImageResource(viewInfoList.get(i).getButtonIcon());
            fabTemp.setId(View.generateViewId());
            fabTemp.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), viewInfoList.get(i).getButtonBackground())));
            fabTemp.setRippleColor(ContextCompat.getColor(getContext(), android.R.color.white));
            fabTemp.setOnTouchListener(this);

            fabTemp.setLayoutParams(new Constraints.LayoutParams((int) getResources().getDimension(R.dimen._45sdp), (int) getResources().getDimension(R.dimen._45sdp)));
            textTemp.setId(View.generateViewId());
            textTemp.setText(viewInfoList.get(i).getText());
            textTemp.setTextColor(viewInfoList.get(i).getTextColor());
            textTemp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (0.04 * width));
            constraintLayout.addView(fabTemp);
            constraintLayout.addView(textTemp);
            switch (i) {
                case 0:
                    textTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_to_up));
                    fabTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_to_up));
                    break;
                case 1:
                    textTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_right_to_top_left));
                    fabTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_right_to_top_left));
                    break;
                case 2:
                    textTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_to_left));
                    fabTemp.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_to_left));
                    break;
            }
            set.clone(constraintLayout);
            set.constrainCircle(fabTemp.getId(), addButton.getId(), (int) (width * 0.3), viewInfoList.get(i).getAngle());
            set.connect(textTemp.getId(), ConstraintSet.TOP, fabTemp.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(textTemp.getId(), ConstraintSet.RIGHT, fabTemp.getId(), ConstraintSet.RIGHT, 0);
            set.connect(textTemp.getId(), ConstraintSet.LEFT, fabTemp.getId(), ConstraintSet.LEFT, 0);
            set.applyTo(constraintLayout);
            viewInfoList.get(i).setId(fabTemp.getId());
        }
    }


    /**
     * removeView() method is used to remove views on addButton Click
     */
    private void removeView() {
        FloatingActionButton fabTemp;
        TextView textTemp;
        for (int i = 0; i < viewInfoList.size(); i++) {
            fabTemp = viewInfoList.get(i).getActionButton();
            textTemp = viewInfoList.get(i).getTextView();
            constraintLayout.removeView(fabTemp);
            constraintLayout.removeView(textTemp);
        }
    }

    /**
     * This method is called when the button is clicked not dragged or moved by the user
     *
     * @param view used to set current view clicked and then performing function according to user requirements
     */
    private void onClickButton(View view) {
        view.setPressed(true);
        for (int i = 0; i < viewInfoList.size(); i++) {
            if (view.getId() == viewInfoList.get(i).getId()) {
                /*if (modelInfoList.get(i).getIntentForActivity() != null)
                    mContext.startActivity(modelInfoList.get(i).getIntentForActivity());
                else
                    Toast.makeText(mContext, "You Clicked " + modelInfoList.get(i).getText(), Toast.LENGTH_SHORT).show();*/
                view.setPressed(false);
                itemClickListener.onClick(view,i);
                break;
            }
        }
    }

    /**
     * This method is overridden and defined to perform drag operations on all the menu items of the project
     *
     * @param view        current view touched
     * @param motionEvent which type of motion performed
     * @return true if touched
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                initialX = (int) motionEvent.getRawX() - width;
                initialY = (int) motionEvent.getRawY() - height;
                up = true;
                break;
            case MotionEvent.ACTION_UP:
                up = false;
                int finalX = (int) motionEvent.getRawX() - width;
                int finalY = (int) motionEvent.getRawY() - height;
                if (initialX == finalX && initialY == finalY)
                    onClickButton(view);
                break;
            case MotionEvent.ACTION_MOVE:
                if (viewInfoList.size() > 3) {
                    finalX = (int) motionEvent.getRawX() - width;
                    finalY = (int) motionEvent.getRawY() - height;
                    int rotation = (initialX * finalY - initialY * finalX);
                    if (rotation > 0 && up) {
                        up = false;
                        clockwise = true;
                    } else if (rotation < 0 && up) {
                        up = false;
                        clockwise = false;
                    }
                    if (clockwise) {
                        if (currentValue == 45) {
                            for (int i = 0; i < 3; i++)
                                onScreenIndex[i] = onScreenIndex[i] < viewInfoList.size() - 1 ? onScreenIndex[i] + 1 : 0;
                            currentValue = 1;
                        }
                        animate(2, 315, 270);
                    } else {
                        if (currentValue == -45) {
                            for (int i = 2; i >= 0; i--)
                                onScreenIndex[i] = onScreenIndex[i] > 0 ? onScreenIndex[i] - 1 : viewInfoList.size() - 1;
                            currentValue = 1;
                        }
                        animate(-2, -45, -90);
                    }
                }
                break;
        }

        return true;
    }


    /**
     * This method is used to perform animation using valueAnimator either it is clockwise or anticlockwise
     *
     * @param degRange degree value we want to rotate on single move/drag
     * @param angle1   angle used by different items on screen to move according to current situation on screen
     * @param angle2   angle used by different items on screen to move according to current situation on screen
     */
    private void animate(int degRange, final int angle1, final int angle2) {
        currentValue += degRange;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentValue + degRange, currentValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams element1 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[0]).getActionButton().getLayoutParams();
                ConstraintLayout.LayoutParams element2 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[1]).getActionButton().getLayoutParams();
                ConstraintLayout.LayoutParams element3 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[2]).getActionButton().getLayoutParams();

                element1.circleAngle = val;
                element2.circleAngle = val + angle1;
                element3.circleAngle = val + angle2;
                viewInfoList.get(onScreenIndex[0]).getActionButton().setLayoutParams(element1);
                viewInfoList.get(onScreenIndex[1]).getActionButton().setLayoutParams(element2);
                viewInfoList.get(onScreenIndex[2]).getActionButton().setLayoutParams(element3);
            }
        });
        valueAnimator.start();
    }

    public void onItemClick(OnItemClickListener onItemClickListener){
        itemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}