package com.example.hdxy.baseproject.Widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hdxy.baseproject.R;

/**
 * description : 自定义标题栏
 * author : wzx
 * email : 445826958@qq.com
 * date : 2019/7/10 09:40
 */
public class TitleBar extends FrameLayout {

    private Context mContext;
    private ImageView mImgLeft;
    private TextView mTvLeft;
    private FrameLayout mFlLeftContent;
    private TextView mTvTitle;
    private ImageView mImgRight;
    private TextView mTvRight;
    private FrameLayout mFlRightContent;
    private Activity mActivity;
    private OnLeftRightClickListener mOnLeftRightClickListener;

    public TitleBar(@NonNull Context context) {
        this(context,null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.common_title, this, true);
        mImgLeft = ((ImageView) rootView.findViewById(R.id.img_left));
        mTvLeft = ((TextView) rootView.findViewById(R.id.tv_left));
        mFlLeftContent = ((FrameLayout) rootView.findViewById(R.id.fl_left_content));
        mTvTitle = ((TextView) rootView.findViewById(R.id.tv_title));
        mImgRight = ((ImageView) rootView.findViewById(R.id.img_right));
        mTvRight = ((TextView) rootView.findViewById(R.id.tv_right));
        mFlRightContent = ((FrameLayout) rootView.findViewById(R.id.fl_right_content));
    }


    public void bind(Activity activity){
        mActivity = activity;
    }

    public void setBackTitle(String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (mImgLeft!=null){
            mImgLeft.setVisibility(View.VISIBLE);
            mImgLeft.setImageResource(R.drawable.back);
        }
        if (rightRes!=null){
            if (rightRes instanceof String){
                if (mTvRight!=null){
                    mTvRight.setVisibility(View.VISIBLE);
                    mTvRight.setText(((String) rightRes));
                }
            }else if (rightRes instanceof Integer){
                if (mImgRight!=null){
                    mImgRight.setVisibility(View.VISIBLE);
                    mImgRight.setImageResource((Integer)rightRes);
                }
            }
        }

        if (mFlLeftContent!=null){
            mFlLeftContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });
        }

        if (mFlRightContent!=null){
            mFlRightContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mOnLeftRightClickListener.onRightClick();
                }
            });
        }
    }
    public void setCustomTitle(Object leftRes,String title,Object rightRes){
        if (mTvTitle!=null){
            mTvTitle.setText(title);
        }

        if (leftRes!=null){
            if (leftRes instanceof String){
                if (mTvLeft!=null){
                    mTvLeft.setVisibility(View.VISIBLE);
                    mTvLeft.setText(((String) leftRes));
                }
            }else if (leftRes instanceof Integer){
                if (mImgLeft!=null){
                    mImgLeft.setVisibility(View.VISIBLE);
                    mImgLeft.setImageResource((Integer)leftRes);
                }
            }
        }

        if (rightRes!=null){
            if (rightRes instanceof String){
                if (mTvRight!=null){
                    mTvRight.setVisibility(View.VISIBLE);
                    mTvRight.setText(((String) rightRes));
                }
            }else if (rightRes instanceof Integer){
                if (mImgRight!=null){
                    mImgRight.setVisibility(View.VISIBLE);
                    mImgRight.setImageResource((Integer)rightRes);
                }
            }
        }

        if (mFlLeftContent!=null){
            mFlLeftContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLeftRightClickListener.onLeftClick();
                }
            });
        }

        if (mFlRightContent!=null){
            mFlRightContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLeftRightClickListener.onRightClick();
                }
            });
        }
    }
    public void setLeftTitleColor(int color){
        if (mTvLeft!=null) {
            mTvLeft.setTextColor(color);
        }
    }
    public void setLeftRightColor(int color){
        if (mTvRight!=null){
            mTvRight.setTextColor(color);
        }
    }
    public void setLeftImgSize(int width,int height){
        if (mImgLeft!=null){
            ViewGroup.LayoutParams layoutParams = mImgLeft.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            mImgLeft.setLayoutParams(layoutParams);
        }
    }
    public void setRightImgSize(int width,int height){
        if (mImgRight!=null){
            ViewGroup.LayoutParams layoutParams = mImgRight.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            mImgRight.setLayoutParams(layoutParams);
        }
    }


    interface OnLeftRightClickListener{
        void onLeftClick();
        void onRightClick();
    }

    public void setOnLeftRightClickListener(OnLeftRightClickListener onLeftRightClickListener){
        mOnLeftRightClickListener = onLeftRightClickListener;
    }


}
