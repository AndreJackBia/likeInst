package com.teamsmm.david.instalike;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


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

    int count;
    private Button start;
    NumberPicker np;
    String[] numSpin = new String[20];
    private static final String TAG = "TwoFragment";
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    ImageButton goToInsta;

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

        start = (Button) view.findViewById(R.id.buttonStart);
        np = (NumberPicker) view.findViewById(R.id.numberPicker);
        mAdView = (AdView) view.findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        view.setTag("fragmentTwo");
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-8190889007212102/9621001670");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

                String urlPhoto = urlPhotoEdit.getText().toString();
                if (!urlPhoto.contains("instagram") || (urlPhoto.equals(""))) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Wrong Input")
                            .setMessage("You inserted an invalid URL")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    urlPhotoEdit.setText("");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {

                    if (numEdit == 0) {
                        numEdit = 10;
                    }
                    String num = String.valueOf(numEdit);
                    textResult.setText("");
                    dialog = ProgressDialog.show(getActivity(), "Adding likes",
                            "loading...", true);
                    URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                    startLike(URL);
                }
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
                    String urlPhoto = urlPhotoEdit.getText().toString();
                    if (!urlPhoto.contains("instagram") & !urlPhoto.equals("")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Wrong Input")
                                .setMessage("You inserted an invalid URL")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        urlPhotoEdit.setText("");
                                        InputMethodManager inputManager =
                                                (InputMethodManager) getContext().
                                                        getSystemService(Context.INPUT_METHOD_SERVICE);
                                        inputManager.hideSoftInputFromWindow(
                                                getActivity().getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {


                    }


                }
            }
        });
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        String urlPhoto = urlPhotoEdit.getText().toString();
                        if (!urlPhoto.contains("instagram")) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Wrong Input")
                                    .setMessage("You inserted an invalid URL")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            urlPhotoEdit.setText("");
                                            InputMethodManager inputManager =
                                                    (InputMethodManager) getContext().
                                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                                            inputManager.hideSoftInputFromWindow(
                                                    getActivity().getCurrentFocus().getWindowToken(),
                                                    InputMethodManager.HIDE_NOT_ALWAYS);

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        } else {
                            textResult.setText("");
                            dialog = ProgressDialog.show(getActivity(), "Adding likes",
                                    "loading...", true);
                            if (numEdit == 0) {
                                numEdit = 10;
                            }
                            String num = String.valueOf(numEdit);
                            URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                            startLike(URL);
                        }
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

    int countNull;

    public int getCountNull() {
        return countNull;
    }

    public void setCountNull(int countNull) {
        this.countNull = countNull;
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
                    setCountNull(getCountNull() + 1);
                    if (getCountNull() == 10) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Sorry!")
                                .setMessage("The server failed during fulfilling the request, please retry later.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //urlPhotoEdit.setText("");
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        // textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();
                        setCountNull(0);
                    }
                    startLike(URL);
                } else if (s.equals("done")) {

                    setCount(getCount() + 1);
                    if (getCount() == 10) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Sorry!")
                                .setMessage("The server failed during fulfilling the request, please retry later.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //urlPhotoEdit.setText("");

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        // textResult.setText("The server failed during fulfilling the request, please retry later.");
                        dialog.cancel();
                        setCount(0);
                    } else {
                        startLike(URL);
                    }
                } else if (s.equals("finish")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Congratulations!")
                            .setMessage("You got new likes!!!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    urlPhotoEdit.setText("");

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                    // textResult.setText(s);
                    dialog.cancel();
                } else if (s.equals("empty")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Wrong input")
                            .setMessage("Please insert a valid URL")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //urlPhotoEdit.setText("");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    //textResult.setText("Please insert link");
                    dialog.cancel();
                } else if (s.equals("error link")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Wrong input")
                            .setMessage("Please insert a valid URL")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //urlPhotoEdit.setText("");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    //textResult.setText(s);
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