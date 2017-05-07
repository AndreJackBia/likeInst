package com.teamsmm.david.instalike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ThreeFragment extends Fragment {
    private static final String TAG = "ThreeFragment";
    private AdView mAdView;
    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        TextView t1 = (TextView) view.findViewById(R.id.textInfo2);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        view.setTag("fragmentTwo");
        mAdView.loadAd(adRequest);
        TextView t2 = (TextView) view.findViewById(R.id.textInfo4);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t3 = (TextView) view.findViewById(R.id.textInfo7);
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}