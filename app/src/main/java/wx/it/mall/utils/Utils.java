package wx.it.mall.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Utils {
    // 获取屏幕宽度
    public static int getScreenWidth(Context context){
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        display.getMetrics(dm);
        return dm.widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight(Context context){
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        display.getMetrics(dm);
        return dm.heightPixels;
    }

    //dp转像素
    public static int dp2px(Context context,float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
