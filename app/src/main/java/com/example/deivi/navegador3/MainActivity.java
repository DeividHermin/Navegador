package com.example.deivi.navegador3;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    AutoCompleteTextView tv;
    EjemploBD db;
    ArrayAdapter<String> adapter;
    String[] historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wv = (WebView) findViewById(R.id.webView);
        tv = (AutoCompleteTextView) findViewById(R.id.etUrl);

        wv.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView ue, String url, Bitmap e){
                //guardaPrevious(url);
                tv.setText(url);

            }
        });

        tv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        searchWeb();
                        return true;
                    }
                }
                return false;
            }
        });

        db = new EjemploBD(getApplicationContext());
        actualizaAdapter();
        tv.setAdapter(adapter);

        historial = new String[2];
        //Toast.makeText(getApplicationContext(), ""+str[v.size()-3], Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.btHome:     wv.loadUrl("https://www.google.es");break;
            case R.id.btPrev:     break;
            case R.id.btNext:     break;
            case R.id.btReload:   wv.reload();break;
            case R.id.btSearch:   searchWeb();break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchWeb(){
        wv.loadUrl(tv.getText().toString());
        guardaUrl(tv.getText().toString());
        actualizaAdapter();
    }

    public void guardaUrl(String url){
        db.agregar(url);
    }

    public void actualizaAdapter(){
        Vector v = db.obtenerSugerencias();
        String[] str = new String[v.size()];
        for(int i=0; i<v.size(); i++){
            str[i]=v.get(i).toString();
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, str);
    }
}
