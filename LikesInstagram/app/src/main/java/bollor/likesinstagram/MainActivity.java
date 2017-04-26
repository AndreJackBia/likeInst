package bollor.likesinstagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    EditText urlPhotoEdit;
    EditText numEdit;
    Button start;
    BufferedReader in = null;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "ciao", Toast.LENGTH_SHORT).show();
        urlPhotoEdit = (EditText) findViewById(R.id.TextUrl);
        numEdit = (EditText) findViewById(R.id.number);
        start = (Button) findViewById(R.id.buttonStart);

        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    String urlPhoto = URLEncoder.encode(urlPhotoEdit.getText().toString(),"UTF-8");
                    String num = URLEncoder.encode(numEdit.getText().toString(),"UTF-8");
                    URL website = new URL("https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}