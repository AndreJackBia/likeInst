package bollor.likesinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 29/04/2017.
 */

public class HashTagActivity extends AppCompatActivity {

    private List<String> myList = new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

        populateList();
        populateListView();
        registerClickCallBack();


    }


    private void populateList() {
        myList.add("HashTag1");
        myList.add("Hashtag2");
        myList.add("Hashtag3");
        myList.add("Hashtag4");
        myList.add("Hashtag5");
        myList.add("Hashtag6");

    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(HashTagActivity.this, R.layout.item_view, myList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            String currentString = myList.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_hashtag);
            imageView.setImageResource(R.drawable.logo);

            TextView makeText = (TextView) itemView.findViewById(R.id.text_hashtag);
            makeText.setText(currentString);


            return itemView;
        }
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Toast.makeText(HashTagActivity.this, "Click on" + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
