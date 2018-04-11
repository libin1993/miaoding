package cn.cloudworkshop.miaoding.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.List;


/**
 * Author：Libin on 2016/10/24 17:48
 * Email：1993911441@qq.com
 * Describe:图片base64编码
 */
public class ImageEncodeUtils {
    /**
     * @param fileList
     * @return 文件编码
     */
    public static String encodeFile(List<String> fileList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileList.size(); i++) {
            String s = fileToBase64(fileList.get(i));
            if (i < fileList.size() - 1) {
                sb.append(s).append(",");
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }


    /**
     * @param filePath
     * @return 文件base64编码
     */
    public static String fileToBase64(String filePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        bitmap.recycle();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * @param bm
     * @return 将Bitmap转换成InputStream
     */
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
