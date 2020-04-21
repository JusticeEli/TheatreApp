package com.justice.theatreapp1;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShowProgress {


    ////////////////////PROGRESS_BAR////////////////////////////
    public static void showProgress(LinearLayout load,TextView loadTxtView ,ScrollView scrollView, boolean show) {


        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);


        }

    }


}
