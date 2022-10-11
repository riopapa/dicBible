package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.scrollView;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ZoomControl {

    int zoomCnt = 1000, yRatio;
    boolean shouldSave = true;

    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView imageView;
    public void set() {
    }

    public void setax() {

            ZoomListener zoomListener = new ZoomListener(mActivity) {

            @Override
            public void onZoomOut() {
                zoomCnt++;
                Log.w("onZoomOut", "zoomCnt=" + zoomCnt);
//                if (zoomCnt%3 == 0 && textSizeScript < 100) {
//                    if (shouldSave)
//                        yRatio = saveYPositionRatio();
//                    shouldSave = false;
//                    textSizeScript++;
//                    textSizeRefer++;
//                    textSizeDic++;
//                    textSizeHymn++;
//                    if (topTab < TAB_MODE_HYMN)
//                        makeBible.showBibleBody();
//                    else if (topTab == TAB_MODE_HYMN)
//                        makeHymn.showHymnBody();
////                    scrollView.post(() -> new Timer().schedule(new TimerTask() {
////                        public void run() {
////                            scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight() * yRatio / 1000);
////                            shouldSave = true;
////                        }
////                    }, 30));
//                }
            }

            @Override
            public void onZoomIn() {
                zoomCnt--;
                Log.w("onZoomIn", "zoomCnt=" + zoomCnt);
//                if (zoomCnt%4 == 0 && textSizeScript > 40) {
//                    if (shouldSave)
//                        yRatio = saveYPositionRatio();
//                    shouldSave = false;
//                    textSizeScript--;
//                    textSizeRefer--;
//                    textSizeDic--;
//                    textSizeHymn--;
//                    if (topTab < TAB_MODE_HYMN)
//                        makeBible.showBibleBody();
//                    else if (topTab == TAB_MODE_HYMN)
//                        makeHymn.showHymnBody();
////                    scrollView.post(() -> new Timer().schedule(new TimerTask() {
////                        public void run() {
////                            scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight() * yRatio / 1000);
////                            shouldSave = true;
////                        }
////                    }, 30));
//                }
            }

            int saveYPositionRatio() {
                int totalHeight = scrollView.getChildAt(0).getHeight();
                return (totalHeight != 0) ? (scrollView.getScrollY() * 1000 / totalHeight) : 0;
            }
        };

        scrollView.setOnTouchListener(zoomListener);

    }

    public void setx() {

    }
}