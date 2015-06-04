package org.wordcamp.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.wordcamp.android.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                        log.append("\n");
                    }
                    return log.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String appLogs) {
            super.onPostExecute(appLogs);
            dialog.dismiss();
            StringBuilder emailMessage = new StringBuilder(descr.getText().toString());
            emailMessage.append("\n\n\n=================================\n");
            emailMessage.append(Utils.getDeviceInfo());
            emailMessage.append("\n\n\nApp Infos:\n");
            emailMessage.append("App version: ");
            emailMessage.append(BuildConfig.VERSION_NAME);
            if (appLogs != null) {
                emailMessage.append("\n\n\nApp Logs:\n");
                emailMessage.append(appLogs);
            }
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                    BuildConfig.FEEDBACK_MAIL, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage.toString());
            startActivity(emailIntent);
        }
    }
}
