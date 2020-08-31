package com.typedef.gam.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.typedef.gam.utils.AnimatorUtil;

/**
 * FAB 行为控制器
  */

public class TableLayoutShowBehavior extends CoordinatorLayout.Behavior<TabLayout> {

    /**
     * 是否正在动画
     */
    private boolean isAnimateIng = false;

    /**
     * 是否已经显示
     */
    private boolean isShow = true;

    public TableLayoutShowBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull TabLayout child,
                                       @NonNull View directTargetChild, @NonNull View target,
                                       int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes,type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull TabLayout child, @NonNull View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                               int type, @NonNull int[] consumed) {
        // 手指上滑，隐藏FAB
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimateIng && isShow) {
            AnimatorUtil.translateHide(child, new StateListener() {
                @Override
                public void onAnimationStart(View view) {
                    super.onAnimationStart(view);
                    isShow = false;
                }
            });
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && !isAnimateIng && !isShow) {
            // 手指下滑，显示FAB
            AnimatorUtil.translateShow(child, new StateListener() {
                @Override
                public void onAnimationStart(View view) {
                    super.onAnimationStart(view);
                    isShow = true;
                }
            });
        }
    }

    class StateListener implements ViewPropertyAnimatorListener {
        @Override
        public void onAnimationStart(View view) {
            isAnimateIng = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimateIng = false;
        }

        @Override
        public void onAnimationCancel(View view) {
            isAnimateIng = false;
        }
    }
}