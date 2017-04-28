package bollor.likesinstagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    EditText urlPhotoEdit;
    Spinner numEdit;
    Button start;
    TextView textNumber;
    TextView textResult;
    ProgressDialog dialog;
    String URL;
    WebView webView;
    int check = 0;

    ArrayList<Integer> numSpin = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        urlPhotoEdit = (EditText) findViewById(R.id.TextUrl);
        numEdit = (Spinner) findViewById(R.id.spinnerNum);
        start = (Button) findViewById(R.id.buttonStart);
        textNumber = (TextView) findViewById(R.id.TextNumber);
        textNumber.setText("How many likes?");
        textResult = (TextView) findViewById(R.id.textViewResult);
        webView = (WebView) findViewById(R.id.webView);


        for (int i = 1; i <= 200; i++) {
            numSpin.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numSpin);
        numEdit.setAdapter(adapter);

    }

    public void onStart() {
        super.onStart();
        urlPhotoEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(urlPhotoEdit.getText().toString());
                    webView.getSettings().setPluginState(WebSettings.PluginState.OFF);
                }
            }
        });
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    dialog = ProgressDialog.show(MainActivity.this, "Adding likes",
                            "loading...", true);
                    String urlPhoto = urlPhotoEdit.getText().toString();
                    String num = numEdit.getSelectedItem().toString();


                    URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                    startLike(URL);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void startLike(String URL) {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(URL);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }


    public class OkHttpHandler extends AsyncTask<String, Void, String> {
        int i = 0;
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            try {
                Request.Builder builder = new Request.Builder();
                builder.url(params[0]);
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s == null) {
                    Toast.makeText(MainActivity.this, "ritorna null", Toast.LENGTH_SHORT).show();
                    startLike(URL);
                } else if (s.equals("done")) {

                    i++;
                    Toast.makeText(MainActivity.this, "ritorna done", Toast.LENGTH_SHORT).show();
                    if (i == 7) {
                        textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();

                    }
                    startLike(URL);
                } else if (s.equals("finish")) {
                    textResult.setText(s);
                    dialog.cancel();
                } else {
                    textResult.setText(s);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}