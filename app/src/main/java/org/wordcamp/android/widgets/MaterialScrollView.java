package org.wordcamp.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import org.wordcamp.android.R;

import java.util.ArrayList;


public class MaterialScrollView extends ScrollView {

	private static final int DEFAULT_PARALLAX_VIEWS = 1;
	private static final float DEFAULT_INNER_PARALLAX_FACTOR = 1.9F;
	private static final float DEFAULT_PARALLAX_FACTOR = 1.9F;
	private int numOfParallaxViews = DEFAULT_PARALLAX_VIEWS;
	private float innerParallaxFactor = DEFAULT_PARALLAX_FACTOR;
	private float parallaxFactor = DEFAULT_PARALLAX_FACTOR;
	private ArrayList<MaterialView> materialViews = new ArrayList<MaterialView>();

	public MaterialScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public MaterialScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public MaterialScrollView(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialScroll);
		this.parallaxFactor = typeArray.getFloat(R.styleable.MaterialScroll_parallax_factor, DEFAULT_PARALLAX_FACTOR);
		this.innerParallaxFactor = typeArray.getFloat(R.styleable.MaterialScroll_inner_parallax_factor, DEFAULT_INNER_PARALLAX_FACTOR);
		this.numOfParallaxViews = typeArray.getInt(R.styleable.MaterialScroll_parallax_views_num, DEFAULT_PARALLAX_VIEWS);
		typeArray.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		makeViewsParallax();
	}

	private void makeViewsParallax() {
		if (getChildCount() > 0 && getChildAt(0) instanceof ViewGroup) {
			ViewGroup viewsHolder = (ViewGroup) getChildAt(0);
			int numOfParallaxViews = Math.min(this.numOfParallaxViews, viewsHolder.getChildCount());
			for (int i = 0; i < numOfParallaxViews; i++) {
				MaterialView materialView = new ScrollViewParallaxedItem(viewsHolder.getChildAt(i));
				materialViews.add(materialView);
			}
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float parallax = parallaxFactor;
		for (MaterialView materialView : materialViews) {
			materialView.setOffset((float)t / parallax);
			parallax *= innerParallaxFactor;
			materialView.animateNow();
		}
	}

	protected class ScrollViewParallaxedItem extends MaterialView {

		public ScrollViewParallaxedItem(View view) {
			super(view);
		}

		@Override
		protected void translatePreICS(View view, float offset) {
			view.offsetTopAndBottom((int)offset - lastOffset);
			lastOffset = (int)offset;
		}
	}
}
