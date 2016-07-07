package com.android.stockbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/7/7.
 */
public class NativeNewTabPage extends LinearLayout{
    private Button mOpenWeb;
    private Context mContext;
    private Controller mController;

    public NativeNewTabPage(Context context, Controller controller) {
        super(context);
        mContext = context;
        mController = controller;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.native_new_tab_page, this);
        mOpenWeb = (Button)findViewById(R.id.open_web_page);
        mOpenWeb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.smartUrlFilter("www.baidu.com", false);
                // Only shortcut javascript URIs for now, as there is special
                // logic in UrlHandler for other schemas
                if (url != null && url.startsWith("javascript:")) {
                    mController.openTab(mOpenWeb.getText().toString(), false, true, true);
                    return;
                }
                Intent i = new Intent();
                String action = Intent.ACTION_SEARCH;
                i.setAction(action);
                i.putExtra(SearchManager.QUERY, mOpenWeb.getText().toString());
                if (mOpenWeb.getText().toString() != null) {
                    Bundle appData = new Bundle();
                    appData.putString("source", mOpenWeb.getText().toString());
                    i.putExtra("source", appData);
                }
                mController.handleNewIntent(i);
            }
        });
    }

    public void destroy() {
        mController = null;
    }
}
