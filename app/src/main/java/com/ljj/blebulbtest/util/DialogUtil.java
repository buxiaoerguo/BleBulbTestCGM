//package com.ljj.blebulbtest.util;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import com.ljj.blebulbtest.R;
//
//
///**
// * @author ljj
// * @date 2020/9/21 11:37
// * @description dialog 工具
// */
//public class DialogUtil {
//    public String getDialogContent(int type) {
//        String content = "";
//        switch (type) {
//            case 0:
//                content = "";
//                break;
//            case 1:
//                content = getString(R.string.Dialog_Util_1);
//                break;
//            case 2:
//                content = getString(R.string.Dialog_Util_2);
//                break;
//            case 3:
//                content = getString(R.string.Dialog_Util_3);
//                break;
//            case 4:
//                content = getString(R.string.Dialog_Util_4);
//                break;
//            case 5:
//                content = getString(R.string.Dialog_Util_5);
//                break;
//            case 6:
//                content = getString(R.string.Dialog_Util_6);
//                break;
//            case 7:
//                content = getString(R.string.Dialog_Util_7);
//                break;
//            case 8:
//                content = getString(R.string.Dialog_Util_8);
//                break;
//            case 9:
//                content = getString(R.string.Dialog_Util_9);
//                break;
//            case 10:
//                content = getString(R.string.Dialog_Util_10);
//                break;
//            case 11:
//                content = getString(R.string.Dialog_Util_11);
//                break;
//            case 12:
//                content = getString(R.string.Dialog_Util_12);
//                break;
//            case 13:
//                content = getString(R.string.Dialog_Util_13);
//                break;
//            case 14:
//                content = getString(R.string.Dialog_Util_14);
//                break;
//            case 15:
//                content = getString(R.string.Dialog_Util_15);
//                break;
//            case 16:
//                content = getString(R.string.Dialog_Util_16);
//                break;
//            case 17:
//                content = getString(R.string.Dialog_Util_17);
//                break;
//            case 18:
//                content = getString(R.string.Dialog_Util_18);
//                break;
//            case 19:
//                content = getString(R.string.Dialog_Util_19);
//                break;
//            case 20:
//                content = getString(R.string.Dialog_Util_20);
//            default:
//                break;
//        }
//        return content;
//    }
//
//    public Dialog loadDialogs;
//
//    public void showLoading(Context context, int type) {
//        // 以特定的风格创建一个dialog
//        loadDialogs = new Dialog(context, R.style.MyDialog);
//        // 加载dialog布局view
//        View purchase = LayoutInflater.from(context).inflate(R.layout.loading, null);
////        initWiteViews(purchase, load_dialog);
//        // 设置外部点击 取消dialog
//        loadDialogs.setCancelable(true);
//        // 获得窗体对象
//        Window window = loadDialogs.getWindow();
//        // 设置窗体的对齐方式
//        window.setGravity(Gravity.CENTER);
//        TextView tv_content = purchase.findViewById(R.id.tv_content);
////        DialogUtil dialogUtil = new DialogUtil();
//        tv_content.setText(getDialogContent(type));
//        // 设置窗体动画
////        window.setWindowAnimations(R.style.AnimBottom);
//        // 设置窗体的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        loadDialogs.setContentView(purchase);
//        loadDialogs.setCanceledOnTouchOutside(false);
//        loadDialogs.setCancelable(false);
//        loadDialogs.show();
//    }
//}
