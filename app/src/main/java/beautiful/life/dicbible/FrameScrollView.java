package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.linearLayout;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.menuColorBack;
import static beautiful.life.dicbible.Vars.scrollView;
import static beautiful.life.dicbible.Vars.textView;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

public class FrameScrollView {
    public FrameScrollView() {
        scrollView = new NestedScrollView(mContext);
        scrollView.setBackgroundColor(menuColorBack);
        textView = new TextView(mContext);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.setMargins(20, 60, 30, 0);
        scrollView.addView(linearLayout, lParams);

    }
}