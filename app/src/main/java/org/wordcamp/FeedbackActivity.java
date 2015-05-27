package org.wordcamp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aagam on 27/5/15.
 */
public class FeedbackActivity extends AppCompatActivity {

    private EditText title, descr;
    private SwitchCompat bugSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initUI();
    }


    private void initUI() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getString(R.string.help_and_feedback));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = (EditText) findViewById(R.id.titleFeedback);
        descr = (EditText) findViewById(R.id.descrFeedback);
        bugSwitch = (SwitchCompat) findViewById(R.id.bugSwitch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sendFeedback) {
            new SendFeedback().execute();
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public class SendFeedback extends AsyncTask<String, String, String> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FeedbackActivity.this);
            dialog.setMessage("Posting...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setIndeterminate(true);
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(BuildConfig.FEEDBACK_URL);
            String logs = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("title", title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("descr", descr.getText().toString()));
                if (bugSwitch.isChecked()) {
                    nameValuePairs.add(new BasicNameValuePair("cat", "issue"));
                    try {
                        Process process = Runtime.getRuntime().exec("logcat -d");
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));

                        StringBuilder log = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            log.append(line);
                        }
                        logs = log.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    nameValuePairs.add(new BasicNameValuePair("cat", "enhancement"));
                }

                String device = Build.VERSION.SDK_INT + " "         // API Level
                        + Build.DEVICE + " "                     // Device
                        + Build.MANUFACTURER + " "                       // Manufacturer
                        + Build.MODEL + " "                       // Model
                        + Build.PRODUCT;
                nameValuePairs.add(new BasicNameValuePair("device", device));

                Gson gson = new Gson();
                String json = gson.toJson(logs);
                nameValuePairs.add(new BasicNameValuePair("logs", json));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                if (response != null) {
                    return response.toString();
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s == null) {
                Toast.makeText(FeedbackActivity.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FeedbackActivity.this, "Successfully posted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
