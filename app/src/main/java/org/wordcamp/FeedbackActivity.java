package org.wordcamp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.wordcamp.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by aagam on 27/5/15.
 */
public class FeedbackActivity extends AppCompatActivity {

    private EditText title, descr;
    private SwitchCompat bugSwitch;
    private String LOG_DIR_NAME;

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
            dialog.setMessage("Preparing...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setIndeterminate(true);
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            if (bugSwitch.isChecked()) {
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    StringBuilder log = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        log.append(line);
                    }
                    String logs = log.toString();
                    return logs;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            String device = Utils.getDeviceInfo();

            if (s != null) {
                LOG_DIR_NAME = getFilePath();
                if (LOG_DIR_NAME != null) {
                    try {
                        File f = new File(LOG_DIR_NAME, "applog.txt");
                        if (f.exists()) {
                            f.delete();
                        }

                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(s.getBytes());
                        fos.close();

                        Uri u = Uri.fromFile(f);
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                new String[]{BuildConfig.FEEDBACK_MAIL});
                        emailIntent.putExtra(Intent.EXTRA_STREAM, u);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
                        emailIntent.putExtra(Intent.EXTRA_TEXT, descr.getText().toString() + device);
                        startActivity(emailIntent);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{BuildConfig.FEEDBACK_MAIL});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_TEXT, descr.getText().toString() + device);
                startActivity(emailIntent);
            }
        }

    }

    public String getFilePath() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String s = Environment.getExternalStorageDirectory().toString() + "/wc";
            return s;
        } else {
            return null;
        }
    }


}
