package serio.tim.android.com.recipepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.appbar_title);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView webview = (WebView) findViewById(R.id.webview_browser);
        webview.setWebViewClient(new RecipeWebViewClient1());
        webview.loadUrl(url);
    }

    private class RecipeWebViewClient1 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // hide loading icon
            findViewById(R.id.spin_kit_browser).setVisibility(View.GONE);
            // show webview
            findViewById(R.id.webview_browser).setVisibility(View.VISIBLE);
        }
    }
}
