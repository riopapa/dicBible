package com.urrecliner.dicbible;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ZoomListener implements View.OnTouchListener {

    private final ScaleGestureDetector scaleGestureDetector;

    ZoomListener(Context c) {

        scaleGestureDetector = new ScaleGestureDetector(c, new MyOnScaleGestureListener());
    }

    ScaleGestureDetector getScaleGestureDetector() {
        return scaleGestureDetector;
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {

        return scaleGestureDetector.onTouchEvent(motionEvent);
    }

    public class MyOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scaleFactor = detector.getScaleFactor();
//            utils.log("Scale"," factor "+scaleFactor);
            if (scaleFactor > 1.25f)
                onZoomOut();
            else if (scaleFactor < 0.85f)
                onZoomIn();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            utils.log("Scale"," Begin factor "+detector.getScaleFactor());
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
//            utils.log("Scale"," End factor "+detector.getScaleFactor());
        }

    }
    public void onZoomOut() { }
    public void onZoomIn() { }

}

//    private GestureDetector gestureDetector;

//    GestureDetector getGestureDetector() {
//        return gestureDetector;
//    }