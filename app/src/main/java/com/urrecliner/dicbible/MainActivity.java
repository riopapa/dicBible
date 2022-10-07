package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.buildMenu;
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
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.sharedPref;
import static com.urrecliner.dicbible.Vars.speaking;
import static com.urrecliner.dicbible.Vars.text2Speech;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.zoomControl;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.urrecliner.dicbible.model.BookMark;
import com.urrecliner.dicbible.model.GoBack;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

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
        HandlePrefs.get();

        setFullScreen();
        utils = new Utils();
        utils.setXPixels(mContext);
        goBacks = GoBack.read(sharedPref);
        bookMarks = BookMark.read(sharedPref);
        if (bookMarks.size() == 0) {
            bookMarks.add(new BookMark(44, 3, 16,
                    System.currentTimeMillis()));
            bookMarks.add(new BookMark(23, 41, 10,
                    System.currentTimeMillis()));
            bookMarks.add(new BookMark(40, 11, 28,
                    System.currentTimeMillis()));
        }
        history = new History();
        packageFolder = new File(Environment.getExternalStorageDirectory(), "dicBible");
        fileRead = new FileRead(mActivity, packageFolder);
        BuildMenuButton.setViewVars();
        BuildMenuButton.assignListener();
        utils.setKeepScreen();
        ScreenColor.setVars();
        zoomControl = new ZoomControl(); zoomControl.set();

        speaking = new Speaking();
        text2Speech = new Text2Speech();
        text2Speech.setReady(getApplicationContext());

        makeBible = new MakeBible();
        makeHymn = new MakeHymn();
        buildMenu = new BuildMenu();
        handlePrefs = new HandlePrefs();

        if (topTab < TAB_MODE_HYMN) {
            buildMenu.setBible();
            if (nowBible > 0)
                makeBible.showBibleBody();
            else
                makeBible.showBibleList();
        } else {
            buildMenu.setHYMN();
            if (nowHymn > 0)
                makeHymn.showHymnBody();
            else
                makeHymn.showNumberKey();
        }

    }

    private void setFullScreen() {
        getSupportActionBar().hide();       // let Full Screen
        final View decoView = getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decoView.setSystemUiVisibility(flags);
    }

    final long BACK_DELAY = 1000;
    long backKeyPressedTime;
    @Override
    public void onBackPressed() {

        if (isReadingNow)
            text2Speech.stopRead();
        confirmExit();
    }


    void confirmExit() {
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_quit, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.quitApp).setOnClickListener(v -> {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        });
    }


    int saveTab, saveBible, saveChapter, saveVerse, saveHymn;

    private void saveNow() {
        saveTab = topTab;
        saveBible = nowBible;
        saveChapter = nowChapter;
        saveVerse = nowVerse;
        saveHymn = nowHymn;
    }
    private void reloadNow() {
        topTab = saveTab;
        nowBible = saveBible;
        nowChapter = saveChapter;
        nowVerse = saveVerse;
        nowHymn = saveHymn;
    }
    public static class MainDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater.inflate(R.layout.dialog_quit, null));
            mBuilder.setTitle(getString(R.string.wanna_quit));
            return mBuilder.create();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

    }

    public void finish_bye(View v) {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
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

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList <String> result = new ArrayList<>();
        for (String perm : wanted) if (hasPermission(perm)) result.add(perm);
        return result;
    }
    private boolean hasPermission(String permission) {
        return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }

    //    @TargetApi(Build.VERSION_CODES.M)
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