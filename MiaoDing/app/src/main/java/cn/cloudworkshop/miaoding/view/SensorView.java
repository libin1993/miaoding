package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cn.cloudworkshop.miaoding.R;

/**
 * Author：Libin on 2016/9/10 14:15
 * Email：1993911441@qq.com
 * Describe：陀螺仪
 */
public class SensorView extends View {
    // 定义水平仪中的气泡图标
    public Bitmap verBubble;
    public Bitmap horBubble;
    // 定义水平仪中气泡的X、Y坐标
    public float verX, verY;
    public float horX, horY;


    public SensorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载水平仪图片和气泡图片
        verBubble = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sensor_bubble);
        horBubble = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sensor_bubble);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 根据气泡坐标绘制气泡
        canvas.drawBitmap(verBubble, verX, verY, null);
        canvas.drawBitmap(horBubble, horX, horY, null);


    }
}