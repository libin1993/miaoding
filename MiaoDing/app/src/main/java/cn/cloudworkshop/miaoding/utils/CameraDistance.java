package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Author：Libin on 2018/5/3 16:02
 * Email：1993911441@qq.com
 * Describe：
 */
public class CameraDistance {
    public static boolean distance(Context context, Bitmap bitmap, double height) {
        Mat img = new Mat();
        Utils.bitmapToMat(bitmap, img);
        HOGDescriptor hogDescriptor = new HOGDescriptor();
        hogDescriptor.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
        MatOfRect matOfRect = new MatOfRect();
        MatOfDouble matOfDouble = new MatOfDouble();
        double maxHeight = 0;
        hogDescriptor.detectMultiScale(img, matOfRect, matOfDouble, 0, new Size(
                4, 4), new Size(8, 8), 1.05, 2, false);
        LogUtils.log(matOfRect.toArray().length+"");
        if (matOfRect.toArray().length > 0) { // 判断是否检测到目标对象，如果有就画矩形，没有就执行下一步
            for (Rect r : matOfRect.toArray()) { // 检测到的目标转成数组形式，方便遍历
                r.x += Math.round(r.width * 0.1);
                r.width = (int) Math.round(r.width * 0.8);
                r.y += Math.round(r.height * 0.045);
                r.height = (int) Math.round(r.height * 0.85);
                Imgproc.rectangle(img, r.tl(), r.br(), new Scalar(0, 0, 255), 2); // 画出矩形
                if (maxHeight <= r.height) {
                    maxHeight = r.height;
                }
            }
            ToastUtils.showToast(context, String.valueOf(maxHeight - height));
            Imgcodecs.imwrite(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/CloudWorkshop/people1.png", img); // 将已经完成检测的Mat对象写出，参数：输出路径，检测完毕的Mat对象。
            if (Math.abs(maxHeight - height) < 50) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
