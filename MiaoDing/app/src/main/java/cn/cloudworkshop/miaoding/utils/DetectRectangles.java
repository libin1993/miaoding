/*
package cn.cloudworkshop.miaoding.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Author：binge on 2017/1/11 17:37
 * Email：1993911441@qq.com
 * Describe：检测矩形工具类
 *//*

public class DetectRectangles {

    */
/**
     * @param bitmap
     * @return 检测矩形
     *//*

    public static Map<String, String> findRectangles(Activity activity, Bitmap bitmap, int blockSize, double delta) {
        //轮廓面积
        double maxArea = 0.0;

        List<Double> areaList = new ArrayList<>();
        MatOfPoint maxApprox = null;
        Mat mRgba = new Mat();
        //读取照片，转传成mat
        Utils.bitmapToMat(bitmap, mRgba);
        //截取上半部分
//        Mat roiMat = mRgba.submat(new Rect(0, 0, mRgba.cols(), mRgba.rows() / 2));
        Mat roiMat = mRgba.submat(new Rect(0, 0, mRgba.cols(), mRgba.rows()));
        bitmap.recycle();

        //建立灰度图像存储空间
        Mat gray = new Mat(roiMat.size(), CvType.CV_8U);
        //彩色图像灰度化
        Imgproc.cvtColor(roiMat, gray, Imgproc.COLOR_BGR2GRAY);
        //滤波
        Imgproc.blur(gray, gray, new Size(3, 3));
        //图像二值化
//        Imgproc.threshold(gray, gray, 140, 255, Imgproc.THRESH_BINARY);
        Imgproc.adaptiveThreshold(gray, gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, delta);

        List<MatOfPoint> contours = new ArrayList<>();
        //寻找所有轮廓
        Imgproc.findContours(gray, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/CloudWorkshop/camera" + System.currentTimeMillis() + ".jpg";
        Imgcodecs.imwrite(path, gray);
        //对寻找到的轮廓遍历
        for (int i = 0; i < contours.size(); i++) {
            //由于approxPolyDP参数类型的原因 对MatOfPoint 进行转化
            MatOfPoint2f inputApprox = new MatOfPoint2f(contours.get(i).toArray());
            MatOfPoint2f outputApprox = new MatOfPoint2f();
            //从轮廓逼近多边形曲线
            Imgproc.approxPolyDP(inputApprox, outputApprox, Imgproc.arcLength(inputApprox, true) * 0.02, true);
            //将类型转换回来
            MatOfPoint approx = new MatOfPoint(outputApprox.toArray());
            //对于生成的逼近多边形 应该有四个顶点  对最小面积限制 防止检测无用四边形
            if (approx.toList().size() == 4 && Math.abs(Imgproc.contourArea(approx)) > 50000
                    && Math.abs(Imgproc.contourArea(approx)) < 80000) {
                double maxCosine = 0;
                for (int j = 2; j < 5; j++) {
                    //角度计算
                    double cosine = Math.abs(angle(approx.toArray()[j % 4],
                            approx.toArray()[j - 2], approx.toArray()[j - 1]));

                    maxCosine = Math.max(maxCosine, cosine);
                }
                //对最大的cos
                if (maxCosine < 0.3 && isA4Paper(approx)) {
                    double area = Math.abs(Imgproc.contourArea(approx));
                    areaList.add(area);
                    if (area > maxArea) {
                        maxArea = area;
                        maxApprox = approx;
                    }

                }
            }
        }

        //保留4位小数,四舍五入
        DecimalFormat df = new DecimalFormat("#0.0000");
        df.setRoundingMode(RoundingMode.UP);
        Map<String, String> map = new HashMap<>();

        if (maxArea > 50000) {
            Collections.sort(areaList);
            String scale = df.format(21 / Math.sqrt(maxArea / Math.sqrt(2)) * 0.925605955);
            double scale1 = Math.sqrt(areaList.get(0) / areaList.get(areaList.size() - 1));
            Toast.makeText(activity, areaList.get(0) + "," + areaList.get(areaList.size() - 1) + ","
                    + scale1, Toast.LENGTH_SHORT).show();
            LogUtils.log(areaList.get(0) + "," + areaList.get(areaList.size() - 1) + "," + scale1);

//            String scale = String.valueOf(Math.sqrt(areaList.get(0) / areaList.get(1)));
//            double scale1 = Math.sqrt(areaList.get(0) / areaList.get(1));
//            String scale = df.format(21 / Math.sqrt(areaList.get(0) / Math.sqrt(2)) * scale1);
            Point point = findPoint(maxApprox);
            map.put("scale", scale);
            map.put("y", String.valueOf(point.y));
            return map;
        } else {
            return null;
        }

    }

    */
/**
     * @return 是否检测到A4纸
     *//*

    private static boolean isA4Paper(MatOfPoint approx) {
        Point point1 = approx.toArray()[0];
        Point point2 = approx.toArray()[1];
        Point point3 = approx.toArray()[2];
        double dx1 = point2.x - point1.x;
        double dy1 = point2.y - point1.y;
        double dx2 = point3.x - point2.x;
        double dy2 = point3.y - point2.y;

        double length1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double length2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);

        double max = Math.max(length1, length2);
        double min = Math.min(length1, length2);

        return max / min > 1.38 && max / min < 1.43;
    }

    */
/**
     * @param approx
     * @return 矩形左上角坐标
     *//*

    private static Point findPoint(MatOfPoint approx) {
        Point minPoint = approx.toArray()[0];
        for (int i = 0; i < approx.toArray().length; i++) {
            Point point = approx.toArray()[i];

            if (point.x * point.y <= minPoint.x * minPoint.y) {
                minPoint = point;
            }
        }
        return minPoint;
    }


    */
/**
     * @param pt1
     * @param pt2
     * @param pt0
     * @return 利用余弦定理 计算夹角余弦
     *//*

    private static double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
                + 1e-10);

    }


    private static void drawRectangles(Mat image, List<MatOfPoint> squares) {
        for (int i = 0; i < squares.size(); i++) {
            //对方形每条边分别画线
            for (int j = 0; j < 4; j++) {
                Point pt1 = squares.get(i).toArray()[j];
                Point pt2 = squares.get(i).toArray()[(j + 1) % 4];
                Imgproc.line(image, pt1, pt2, new Scalar(255, 0, 0), 3);
            }
        }
    }
}
*/
