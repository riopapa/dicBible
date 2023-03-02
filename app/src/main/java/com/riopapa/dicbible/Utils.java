package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.TAB_MODE_KEY;
import static com.riopapa.dicbible.Vars.alwaysOn;
import static com.riopapa.dicbible.Vars.goBacks;
import static com.riopapa.dicbible.Vars.history;
import static com.riopapa.dicbible.Vars.mActivity;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.xPixels;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.riopapa.dicbible.cookiebar.CookieBar;

import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    void log(String tag, String text) {
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        String where = " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber();
        Log.w(tag , where + " " + text);
    }

    void setXPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        try {
            assert windowManager != null;
            windowManager.getDefaultDisplay().getMetrics(dm);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        xPixels = dm.widthPixels;
    }

    void setKeepScreen() {
        if (alwaysOn)
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    void showSnackBar(String title, String text) {

        CookieBar.build(mActivity)
                .setTitle(title)
//                .setTitleColor(menuColorFore)
                .setMessage(text)
//                .setMessageColor(menuSelectedBack)
                .setCookiePosition(CookieBar.BOTTOM)
                .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                .show();

    }

    void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {   // >= android 11
            WindowInsetsController controller = mActivity.getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() |
                        WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    void confirmExit() {

        if (topTab == TAB_MODE_KEY)
            history.pop();
        HandlePrefs.saveArray("goBack", goBacks);
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_quit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.quitApp).setOnClickListener(v -> {
            alertDialog.dismiss();
            exitApp();
        });
    }

    void exitApp() {
        if (topTab == TAB_MODE_KEY)
            history.pop();
        HandlePrefs.saveArray("goBack", goBacks);
        mActivity.finish();
        new Timer().schedule(new TimerTask() {
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 500);
    }

}