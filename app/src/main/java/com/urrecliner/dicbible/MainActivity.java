package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.ButtonAssign.goBackward;
import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.fileRead;
import static com.urrecliner.dicbible.Vars.goBacks;
import static com.urrecliner.dicbible.Vars.handlePrefs;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.makeHymn;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.screenMenu;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.sharedPref;
import static com.urrecliner.dicbible.Vars.speaking;
import static com.urrecliner.dicbible.Vars.text2Speech;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.zoomControl;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.urrecliner.dicbible.model.BookMark;
import com.urrecliner.dicbible.model.GoBack;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ScaleGestureDetector scaleGestureDetector;
    float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;

        askPermission();

        if (!Environment.isExternalStorageManager()){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }

        sharedPref = getApplicationContext().getSharedPreferences("bible", MODE_PRIVATE);
        sharedEdit = sharedPref.edit();
        handlePrefs = new HandlePrefs();
        handlePrefs.get();

        utils = new Utils();
        utils.setXPixels(mContext);
        goBacks = GoBack.read(sharedPref);
        bookMarks = BookMark.read(sharedPref);
        history = new History();
        if (goBacks.size() > 0) {
            history.pop();
            history.push();
        }
        packageFolder = new File(Environment.getExternalStorageDirectory(), "dicBible");
        fileRead = new FileRead(mActivity, packageFolder);
//        zoomControl = new ZoomControl(); zoomControl.set();

        speaking = new Speaking();
        text2Speech = new Text2Speech();
        text2Speech.setReady(getApplicationContext());

        setFullScreen();
        ScreenColor.apply();
        ButtonAssign.init();

        utils.setKeepScreen();
        makeBible = new MakeBible();
        makeHymn = new MakeHymn();
        screenMenu = new ScreenMenu();
        zoomControl = new ZoomControl();

        isReadingNow = false;
        screenMenu.buildButtonColor();

        if (topTab == TAB_MODE_NEW || topTab == TAB_MODE_OLD) {
            if (nowBible > 0)
                makeBible.showBibleBody();
            else
                makeBible.showBibleList();
        } else if (topTab == TAB_MODE_HYMN) {
            if (nowHymn > 0)
                makeHymn.showHymnBody();
            else
                makeHymn.showNumberKey();
        } else
            makeBible.showBibleList();

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.w("onTouchEvent "+mScaleFactor, "touch="+motionEvent);
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Log.w("ScaleListener.onScale","f="+scaleGestureDetector.getScaleFactor());
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.3f, Math.min(mScaleFactor, 10.0f));
            scrollView.setScaleX(mScaleFactor);
            scrollView.setScaleY(mScaleFactor);
            return true;
        }
    }

    private void setFullScreen() {
        getSupportActionBar().hide();       // let Full Screen
        WindowInsetsController controller = getWindow().getInsetsController();
        if(controller != null){
            // 상태바와 네비게이션을 사라지게한다
            controller.hide(WindowInsets.Type.statusBars() |
                    WindowInsets.Type.navigationBars());
            // 특정 행동(화면 끝을 스와이프하는 등)을 했을 때에만
            // 시스템 바가 나타나도록 설정systemBarsBehavior
            controller.setSystemBarsBehavior(
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        }
    }

    @Override
    public void onBackPressed() {

        if (isReadingNow)
            text2Speech.stopPlay();
        if (goBacks.size() > 0)
            goBackward();

    }

    // ↓ ↓ ↓ P E R M I S S I O N    RELATED /////// ↓ ↓ ↓ ↓
    ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();

    private void askPermission() {
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (permissionsToRequest.size() != 0) {
            requestPermissions(permissionsToRequest.toArray(new String[0]),
//            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                    ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList <String> result = new ArrayList<>();
        for (String perm : wanted) if (hasPermission(perm)) result.add(perm);
        return result;
    }
    private boolean hasPermission(String permission) {
        return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }

//    //    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : permissionsToRequest) {
                if (hasPermission(perms)) {
                    permissionsRejected.add(perms);
                }
            }
            if (permissionsRejected.size() > 0) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    String msg = "These permissions are mandatory for the application. Please allow access.";
                    showDialog(msg);
                }
            } else
                Toast.makeText(getApplicationContext(), "Permissions not granted.", Toast.LENGTH_LONG).show();
        }
    }
    private void showDialog(String msg) {
        showMessageOKCancel(msg,
                (dialog, which) -> requestPermissions(permissionsRejected.toArray(
                        new String[0]), ALL_PERMISSIONS_RESULT));
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

// ↑ ↑ ↑ ↑ P E R M I S S I O N    RELATED /////// ↑ ↑ ↑

}