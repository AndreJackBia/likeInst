package bollor.likesinstagram;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;

import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    EditText urlPhotoEdit;
    Spinner numEdit;
    Button start;
    Button buttonHashtag;
    TextView textNumber;
    TextView textResult;
    ProgressDialog dialog;
    String URL;
    WebView webView;
    Button buttonInstagram;
    int count;


    ArrayList<Integer> numSpin = new ArrayList<Integer>();


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = (count + 1) + getCount();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        urlPhotoEdit = (EditText) findViewById(R.id.TextUrl);
        numEdit = (Spinner) findViewById(R.id.spinnerNum);
        start = (Button) findViewById(R.id.buttonStart);
        buttonHashtag = (Button) findViewById(R.id.buttonHashtag);
        buttonInstagram = (Button) findViewById(R.id.buttonInstagram);

        textNumber = (TextView) findViewById(R.id.TextNumber);
        textNumber.setText("How many likes?");
        textResult = (TextView) findViewById(R.id.textViewResult);
        webView = (WebView) findViewById(R.id.webView);


        for (int i = 10; i <= 200; i = i + 10) {
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

                    setCount(0);
                    if (getCount() == 8) {
                        textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();


                    } else {
                        Toast.makeText(MainActivity.this, "ritorna done", Toast.LENGTH_SHORT).show();
                        startLike(URL);

                    }

                } else if (s.equals("finish")) {
                    textResult.setText(s);
                    dialog.cancel();
                } else if (s.equals("empty")) {
                    textResult.setText("Please insert link");
                    dialog.cancel();
                } else {
                    textResult.setText(s);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void GoToHashtag(View view) {
        Intent intent = new Intent(this, HashTagActivity.class);
        startActivity(intent);
    }

    public void GoToInstagram(View view) {
        Uri uri = Uri.parse("http://instagram.com/_u/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/")));
        }
    }
}