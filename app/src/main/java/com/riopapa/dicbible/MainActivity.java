package com.riopapa.dicbible;

import static com.riopapa.dicbible.Buttons.goBackward;
import static com.riopapa.dicbible.Vars.TAB_DICT;
import static com.riopapa.dicbible.Vars.TAB_HYMN;
import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.tabBible;
import static com.riopapa.dicbible.Vars.bookMarks;
import static com.riopapa.dicbible.Vars.tabDict;
import static com.riopapa.dicbible.Vars.fileRead;
import static com.riopapa.dicbible.Vars.goBacksStacks;
import static com.riopapa.dicbible.Vars.handlePrefs;
import static com.riopapa.dicbible.Vars.goBackProcs;
import static com.riopapa.dicbible.Vars.tabHymn;
import static com.riopapa.dicbible.Vars.isReadingNow;
import static com.riopapa.dicbible.Vars.keyRefs;
import static com.riopapa.dicbible.Vars.dictTable;
import static com.riopapa.dicbible.Vars.mActivity;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.packageFolder;
import static com.riopapa.dicbible.Vars.screenMenu;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.sharedPref;
import static com.riopapa.dicbible.Vars.speaking;
import static com.riopapa.dicbible.Vars.text2Speech;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.riopapa.dicbible.model.BookMark;
import com.riopapa.dicbible.model.GoBack;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        askPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        bottomNav = findViewById(R.id.bottom_nav);

        sharedPref = getApplicationContext().getSharedPreferences("bible", MODE_PRIVATE);
        sharedEdit = sharedPref.edit();
        handlePrefs = new HandlePrefs();
        handlePrefs.get();

        utils = new Utils();
        utils.setXPixels(mContext);
        goBacksStacks = GoBack.read(sharedPref);
        bookMarks = BookMark.read(sharedPref);
        goBackProcs = new GoBackStacks();
        if (goBacksStacks.size() > 0) {
            goBackProcs.pop();
            goBackProcs.push();
        }
        packageFolder = new File(Environment.getExternalStorageDirectory(), "dicBible");
        fileRead = new FileRead(packageFolder);

        speaking = new Speaking();
        text2Speech = new Text2Speech();
        text2Speech.setReady(getApplicationContext());

        ScreenColor.apply();
        Buttons.assign();

        utils.setKeepScreen();
        tabBible = new TabBible();
        tabHymn = new TabHymn();
        tabDict = new TabDict();
        screenMenu = new ScreenMenu();

        isReadingNow = false;
        screenMenu.buildButtonColor();

        if (topTab == TAB_NEW || topTab == TAB_OLD) {
            if (nowBible > 0)
                tabBible.showBibleBody();
            else
                tabBible.showBibleList();
        } else if (topTab == TAB_HYMN) {
            if (nowHymn > 0)
                tabHymn.showHymnBody();
            else
                tabHymn.showNumberKey();
        } else
            tabBible.showBibleList();

        dictTable = new DictTable();
        keyRefs = dictTable.read(packageFolder);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        while (topTab == TAB_DICT) {
            goBackProcs.pop();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        utils.setFullScreen();
    }

    @Override
    public void onBackPressed() {

        if (isReadingNow)
            text2Speech.stopPlay();
        showExitDialog();
    }

    void showExitDialog() {

        View dialogView = this.getLayoutInflater().inflate(R.layout.exit_app, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        dialogView.findViewById(R.id.iv_icon).setOnClickListener(v -> utils.exitApp());
        dialogView.findViewById(R.id.exit_message).setOnClickListener(v -> utils.exitApp());
        dialogView.findViewById(R.id.exit_title).setOnClickListener(v -> utils.exitApp());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
