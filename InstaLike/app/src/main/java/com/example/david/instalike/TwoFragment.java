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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoFragment extends Fragment {

    EditText urlPhotoEdit;
    int numEdit;
    TextView textNumber;
    TextView textResult;
    ProgressDialog dialog;
    String URL;
    WebView webView;
    int count;
    private Button start;
    NumberPicker np;
    String[] numSpin = new String[20];
    private static final String TAG = "TwoFragment";
    private AdView mAdView;
    InterstitialAd mInterstitialAd;

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
        textNumber = (TextView) view.findViewById(R.id.TextNumber);
        textResult = (TextView) view.findViewById(R.id.textViewResult);
        webView = (WebView) view.findViewById(R.id.webView);
        start = (Button) view.findViewById(R.id.buttonStart);
        np = (NumberPicker) view.findViewById(R.id.numberPicker);
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-8190889007212102/9446204873");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                textResult.setText("");
                dialog = ProgressDialog.show(getActivity(), "Adding likes",
                        "loading...", true);
                String urlPhoto = urlPhotoEdit.getText().toString();
                String num = String.valueOf(numEdit);


                URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                startLike(URL);
            }
        });

        requestNewInterstitial();
        return view;
    }

    public void onStart() {
        super.onStart();

        int j = 0;
        for (int i = 10; i <= 200; i = i + 10) {

            numSpin[j] = String.valueOf(i);
            j++;
        }
        final String[] ns = numSpin;
        // Objects[] picker = (Objects[]) numSpin.toArray();

        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(20);
        np.setDisplayedValues(ns);
        // np.setDisplayedValues(new String []{"3","4","12"});

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                //textResult.setText("Selected Number : " + newVal);
                numEdit = newVal * 10;

            }
        });

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
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        textResult.setText("");
                        dialog = ProgressDialog.show(getActivity(), "Adding likes",
                                "loading...", true);
                        String urlPhoto = urlPhotoEdit.getText().toString();
                        String num = String.valueOf(numEdit);


                        URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                        startLike(URL);
                    }

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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

                    setCount(getCount() + 1);
                    if (getCount() == 8) {
                        textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();
                        setCount(0);
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
                } else if (s.equals("error link")) {
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