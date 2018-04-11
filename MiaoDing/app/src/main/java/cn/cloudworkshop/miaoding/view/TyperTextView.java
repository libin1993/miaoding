package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.cloudworkshop.miaoding.R;

/**
 * Author：binge on 2017/3/30 18:01
 * Email：1993911441@qq.com
 * Describe：逐字显示
 */
public class TyperTextView extends TextView {
    public static final int INVALIDATE = 0x767;
    private CharSequence mText;
    private Handler handler;
    private int charIncrease;
    private int typerSpeed;

    public TyperTextView(Context context) {
        this(context, null);
    }

    public TyperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TyperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TyperTextView);
        typerSpeed = typedArray.getInt(R.styleable.TyperTextView_typerSpeed, 40);
        charIncrease = typedArray.getInt(R.styleable.TyperTextView_charIncrease, 1);
        typedArray.recycle();
        mText = getText();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int currentLength = getText().length();
                if (currentLength < mText.length()) {
                    if (currentLength + charIncrease > mText.length()) {
                        charIncrease = mText.length() - currentLength;
                    }
                    append(mText.subSequence(currentLength, currentLength + charIncrease));

                    Message message = Message.obtain();
                    message.what = INVALIDATE;
                    handler.sendMessageDelayed(message, typerSpeed);
                    return false;
                }
                return false;
            }
        });
    }

    public int getTyperSpeed() {
        return typerSpeed;
    }

    public void setTyperSpeed(int typerSpeed) {
        this.typerSpeed = typerSpeed;
    }

    public int getCharIncrease() {
        return charIncrease;
    }

    public void setCharIncrease(int charIncrease) {
        this.charIncrease = charIncrease;
    }


    public void setProgress(float progress) {
        setText(mText.subSequence(0, (int) (mText.length() * progress)));
    }

    public void animateText(CharSequence text) {
        if (text == null) {
            throw new RuntimeException("text must not  be null");
        }
        mText = text;
        setText("");
        Message message = Message.obtain();
        message.what = INVALIDATE;
        handler.sendMessage(message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(INVALIDATE);
    }
}
