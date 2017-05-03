package com.example.david.instalike;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoFragment extends Fragment {

    EditText urlPhotoEdit;
    Spinner numEdit;
    TextView textNumber;
    TextView textResult;
    ProgressDialog dialog;
    String URL;
    WebView webView;
    int count;
    private Button start;
    ArrayList<Integer> numSpin = new ArrayList<Integer>();



    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        urlPhotoEdit = (EditText) view.findViewById(R.id.TextUrl);
        numEdit = (Spinner) view.findViewById(R.id.spinnerNum);
        textNumber = (TextView) view.findViewById(R.id.TextNumber);
        textResult = (TextView) view.findViewById(R.id.textViewResult);
        webView = (WebView) view.findViewById(R.id.webView);
        start = (Button) view.findViewById(R.id.buttonStart);

        for (int i = 10; i <= 200; i = i + 10) {
            numSpin.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, numSpin);
        numEdit.setAdapter(adapter);

        return view;
    }
    public void onStart() {
        super.onStart();
        urlPhotoEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getActivity(), "got the focus", Toast.LENGTH_LONG).show();
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(urlPhotoEdit.getText().toString());

                }
            }
        });
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    dialog = ProgressDialog.show(getActivity(), "Adding likes",
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
        TwoFragment.OkHttpHandler okHttpHandler = new TwoFragment.OkHttpHandler();
        okHttpHandler.execute(URL);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = (count + 1) + getCount();
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
                    Toast.makeText(getActivity(), "ritorna null", Toast.LENGTH_SHORT).show();
                    startLike(URL);
                } else if (s.equals("done")) {

                    setCount(0);
                    if (getCount() == 8) {
                        textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();


                    } else {
                        Toast.makeText(getActivity(), "ritorna done", Toast.LENGTH_SHORT).show();
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
}