package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.makeHymn;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.textSizeRefer;
import static com.urrecliner.dicbible.Vars.textSizeHymn;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.topTab;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ZoomControl {


    public void set() {

        ZoomListener zoomListener = new ZoomListener(mActivity) {
            int zoomCnt = 1000, yRatio;
            boolean shouldSave = true;
            @Override
            public void onZoomOut() {
                zoomCnt++;
                Log.w("onZoomOut", "zoomCnt="+zoomCnt);
                if (zoomCnt%3 == 0 && textSizeScript < 100) {
                    if (shouldSave)
                        yRatio = saveYPositionRatio();
                    shouldSave = false;
                    textSizeScript++;
                    textSizeRefer++;
                    textSizeDic++;
                    textSizeHymn++;
                    if (topTab < TAB_MODE_HYMN)
                        makeBible.showBibleBody();
                    else if (topTab == TAB_MODE_HYMN)
                        makeHymn.showHymnBody();
                    scrollView.post(() -> new Timer().schedule(new TimerTask() {
                        public void run() {
                            scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight() * yRatio / 1000);
                            shouldSave = true;
                        }
                    }, 300));
                }
            }

            @Override
            public void onZoomIn() {
                zoomCnt--;
                if (zoomCnt%3 == 0 && textSizeScript > 40) {
                    if (shouldSave)
                        yRatio = saveYPositionRatio();
                    shouldSave = false;
                    textSizeScript--;
                    textSizeRefer--;
                    textSizeDic--;
                    textSizeHymn--;
                    if (topTab < TAB_MODE_HYMN)
                        makeBible.showBibleBody();
                    else if (topTab == TAB_MODE_HYMN)
                        makeHymn.showHymnBody();
                    scrollView.post(() -> new Timer().schedule(new TimerTask() {
                        public void run() {
                            scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight() * yRatio / 1000);
                            shouldSave = true;
                        }
                    }, 300));
                }
            }
            int saveYPositionRatio() {
                int totalHeight = scrollView.getChildAt(0).getHeight();
                return (totalHeight != 0) ? (scrollView.getScrollY() * 1000 / totalHeight):0;
            }
        };

        fBody.setOnTouchListener(zoomListener);

    }

}