package com.example.david.instalike;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class OneFragment extends Fragment {
    private List<String> myList = new ArrayList<String>();
    private List<String> myListTite = new ArrayList<String>();
    private static final String TAG = "OneFragment";
    private AdView mAdView;

    public OneFragment() {
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewHashtag);
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        populateList();

        populateListView(view);
        registerClickCallBack(view);
        return view;
    }

    private void populateList() {
        myListTite.add("Most Popular");
        myList.add("#love #tweegram #photooftheday #20likes #amazing #smile #follow4follow #like4like #look #instalike #igers #picoftheday #food #instadaily #instafollow #followme #girl #iphoneonly #instagood #bestoftheday #instacool #instago #all_shots #follow #webstagram #colorful #style #swag");

        myListTite.add("Very Popular");
        myList.add("#fun #instagramers #food #smile #pretty #followme #nature #lol #dog #hair #onedirection #sunset #swag #throwbackthursday #instagood #beach #statigram #friends #hot #funny #blue #life #art #instahub #photo #cool #pink #bestoftheday #clouds");

        myListTite.add("Popular");
        myList.add("#amazing #followme #all_shots #textgram #family #instago #igaddict #awesome #girls #instagood #my #bored #baby #music #red #green #water #harrystyles #bestoftheday #black #party #white #yum #flower #2012 #night #instalove #niallhoran #jj_forum");

        myListTite.add("Weather");
        myList.add("#nature #sky #sun #summer #beach #beautiful #pretty #sunset #sunrise #blue #flowers #night #tree #twilight #clouds #beauty #light #cloudporn #photooftheday #love #green #skylovers #dusk #weather #day #red #iphonesia #mothernature");

        myListTite.add("Beach");
        myList.add("#beach #sun #nature #water #TFLers #ocean #lake #instagood #photooftheday #beautiful #sky #clouds #cloudporn #fun #pretty #sand #reflection #amazing #beauty #beautiful #shore #waterfoam #seashore #waves #wave");

        myListTite.add("Sunset");
        myList.add("#sunset #sunrise #sun #TFLers #pretty #beautiful #red #orange #pink #sky #skyporn #cloudporn #nature #clouds #horizon #photooftheday #instagood #gorgeous #warm #view #night #morning #silhouette #instasky #all_sunsets");

        myListTite.add("Sun");
        myList.add("#sun #sunny #sunnyday #sunnydays #sunlight #light #sunshine #shine #nature #sky #skywatcher #thesun #sunrays #photooftheday #beautiful #beautifulday #weather #summer #goodday #goodweather #instasunny #instasun #instagood #clearskies #clearsky #blueskies #lookup #bright #brightsun");

        myListTite.add("Summer");
        myList.add("#summer #summertime #sun #hot #sunny #warm #fun #beautiful #sky #clearskys #season #seasons #instagood #instasummer #photooftheday #nature #TFLers #clearsky #bluesky #vacationtime #weather #summerweather #sunshine #summertimeshine");

        myListTite.add("Winter");
        myList.add("#winter #cold #holidays #snow #rain #christmas #snowing #blizzard #snowflakes #wintertime #staywarm #cloudy #instawinter #instagood #holidayseason #photooftheday #season #seasons #nature");

        myListTite.add("Dog");
        myList.add("#dog #dog #puppy #pup #cute #eyes #instagood #dogs_of_instagram #pet #pets #animal #animals #petstagram #petsagram #dogsitting #photooftheday #dogsofinstagram #ilovemydog #instagramdogs #nature #dogstagram #dogoftheday #lovedogs #lovepuppies #hound #adorable #doglover #instapuppy #instadog");

        myListTite.add("Cats");
        myList.add("#cat #cats #catsagram #catstagram #instagood #kitten #kitty #kittens #pet #pets #animal #animals #petstagram #petsagram #photooftheday #catsofinstagram #ilovemycat #instagramcats #nature #catoftheday #lovecats #furry #sleeping #lovekittens #adorable #catlover #instacat");

        myListTite.add("Selfie");
        myList.add("#selfie #selfienation #selfies #me #love #pretty #handsome #instagood #instaselfie #selfietime #face #shamelessselefie #life #hair #portrait #igers #fun #followme #instalove #smile #igdaily #eyes #follow");

        myListTite.add("Iphone");
        myList.add("#iphone #iphoneonly #apple #appleiphone #ios #iphone3g #iphone3gs #iphone4 #iphone5 #technology #electronics #mobile #instagood #instaiphone #phone #photooftheday #smartphone #iphoneography #iphonegraphy #iphoneographer #iphoneology #iphoneographers #iphonegraphic #iphoneogram #teamiphone");

        myListTite.add("Follow");
        myList.add("#follow #f4f #followme #followforfollow #follow4follow #teamfollowback #followher #followbackteam #followhim #followall #followalways #followback #me #love #pleasefollow #follows #follower #following");
    }

    private void populateListView(View view) {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) view.findViewById(R.id.listViewHashtag);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(getActivity(), R.layout.item_view, myList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            String currentStringHashtag = myList.get(position);
            String currentStringTitle = myListTite.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_hashtag);
            imageView.setImageResource(R.drawable.imageedit_4_9866281557);

            TextView makeText = (TextView) itemView.findViewById(R.id.text_hashtag);
            makeText.setText(currentStringHashtag);

            TextView makeTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            makeTextTitle.setText(currentStringTitle);


            return itemView;
        }
    }

    private void registerClickCallBack(View view) {
        ListView list = (ListView) view.findViewById(R.id.listViewHashtag);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Toast.makeText(getActivity(), "Copied", Toast.LENGTH_LONG).show();
                String copiedStrig = myList.get(position);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(copiedStrig);


            }
        });
    }
}