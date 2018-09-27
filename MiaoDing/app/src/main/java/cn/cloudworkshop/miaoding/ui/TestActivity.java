package cn.cloudworkshop.miaoding.ui;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.camera.CustomCameraView;
import cn.cloudworkshop.miaoding.utils.LogUtils;

/**
 * Author：Libin on 2018/9/14 10:38
 * Email：1993911441@qq.com
 * Describe：
 */
public class TestActivity extends BaseActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    @BindView(R.id.camera_opencv)
    JavaCameraView camera;

    private Mat mat;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明导航栏
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        camera.setCvCameraViewListener(this);
    }


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    camera.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        //初始化OpenCV
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onCameraViewStopped() {

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //要执行的事件
            testPeople(mat);
            handler.postDelayed(this, 3000);
        }
    };

    @Override
    public Mat onCameraFrame(final CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mat = inputFrame.rgba();
        return mat;
    }


    private void testPeople(Mat src) {
        //压缩图片
//                Mat detMat = new Mat();
//                Imgproc.resize(mat, detMat, new Size(mat.width() / 2, mat.height() / 2));
        //建立灰度图像存储空间
        Mat gray = new Mat(src.size(), CvType.CV_8U);
        //彩色图像灰度化
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        //人体轮廓检测
        HOGDescriptor hogDescriptor = new HOGDescriptor();
        hogDescriptor.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
        MatOfRect matOfRect = new MatOfRect();
        MatOfDouble matOfDouble = new MatOfDouble();

        hogDescriptor.detectMultiScale(gray, matOfRect, matOfDouble, 0, new Size(
                        4, 4), new Size(8, 8), 1.2,
                2, false);
        int maxHeight = 0;
        if (matOfRect.toArray().length > 0) { // 判断是否检测到目标对象，如果有就画矩形，没有就执行下一步
            Rect rMax = null;
            for (Rect r : matOfRect.toArray()) { // 检测到的目标转成数组形式，方便遍历
                if (maxHeight <= r.height) {
                    maxHeight = r.height;
                    rMax = r;
                }
            }
            LogUtils.log(maxHeight+"");

            if (rMax != null) {
                rMax.x += Math.round(rMax.width * 0.1);
                rMax.width = (int) Math.round(rMax.width * 0.8);
                rMax.y += Math.round(rMax.height * 0.045);
                rMax.height = (int) Math.round(rMax.height * 0.85);
//                Imgproc.rectangle(mat, rMax.tl(), rMax.br(), new Scalar(255, 0, 0), 2); // 画出矩形

//                Imgcodecs.imwrite(Environment.getExternalStorageDirectory().getAbsolutePath()
//                        + "/CloudWorkshop/people" + System.currentTimeMillis() + ".jpg", mat);
            }
        }
    }
}


