package cn.cloudworkshop.miaoding.pagecurl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by fc on 14-11-21.
 */
public class ImagePageProvider extends BasePageProvider {
    private boolean isRotation = true;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<Bitmap> backBitmaps;
    private int width;
    private int height;


    public ImagePageProvider(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void add(Bitmap data) {
        bitmaps.add(data);
    }

    public void addBack(Bitmap backData) {
        backBitmaps.add(backData);
    }

    public void setRotation(boolean isRotation) {
        this.isRotation = isRotation;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public void setBackBitmaps(ArrayList<Bitmap> backBitmaps) {
        this.backBitmaps = backBitmaps;
    }


    public boolean isShouldRotation(boolean isBack) {
        return isRotation && isBack && (viewMode == CurlRenderer.SHOW_TWO_PAGES);
    }

    @Override
    public boolean isEnableDrawed(int index, boolean isBack) {
//        if (isBack) {
//            return backBitmaps != null && backBitmaps.size() > index && backBitmaps.get(index) != null;
//        } else {
//            return bitmaps != null && bitmaps.size() > index && bitmaps.get(index) != null;
//        }
        return super.isEnableDrawed(index, isBack);
    }

    public Bitmap getItem(int index, boolean isBack) {
        if (isBack) {
            return backBitmaps != null && backBitmaps.size() > index ? backBitmaps.get(index) : null;
        } else {
            return bitmaps != null && bitmaps.size() > index ? bitmaps.get(index) : null;
        }

    }

    public void drawBitmap(Canvas c, Rect r, int index, boolean isBack) {
        Log.i("CURLVIEW", "drawBitmap");
        Bitmap d = getItem(index, isBack);
        if (d == null) {
            return;
        }


        r.left = 0;
        r.right = width;
        r.top = 0;
        r.bottom = height;


        if (isShouldRotation(isBack)) {
            updateCanvasLRSymmetry(c);
        }

        c.drawBitmap(d, null, r, new Paint());
    }

    @Override
    public int getPageCount() {
        return bitmaps == null ? 0 : bitmaps.size();
    }
}
