package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.alwaysOn;
import static com.urrecliner.dicbible.Vars.goBacks;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.xPixels;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.urrecliner.dicbible.cookiebar.CookieBar;

//import org.aviran.cookiebar2.CookieBar;

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
    void sxx (String title, String text) {
        View mLayoutView = mActivity.findViewById(R.id.fBody);
        Snackbar snackbar = Snackbar.make(mLayoutView, "", Snackbar.LENGTH_LONG);
        View sView = mActivity.getLayoutInflater().inflate(R.layout.snack_message, null);
        TextView tv1 = sView.findViewById(R.id.textView1);
        TextView tv2 = sView.findViewById(R.id.textView2);

        tv1.setText(title);
        tv2.setText(text);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//        snackbarLayout.setPadding(8, 8, 8, 8);
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarLayout.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;
        sView.setLayoutParams(params);
        snackbarLayout.addView(sView, 0);

        snackbar.show();
    }


    void confirmExit() {

        HandlePrefs.saveArray("goBack", goBacks);
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_quit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.quitApp).setOnClickListener(v -> {
            alertDialog.dismiss();
            mActivity.finish();
            new Timer().schedule(new TimerTask() {
                public void run() {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }, 500);
        });
    }

}