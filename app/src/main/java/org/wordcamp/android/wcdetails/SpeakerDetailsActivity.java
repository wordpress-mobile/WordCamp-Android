package org.wordcamp.android.wcdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.wordcamp.android.R;
import org.wordcamp.android.adapters.SessionsBySpeakerListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.SessionDB;
import org.wordcamp.android.objects.SpeakerDB;
import org.wordcamp.android.objects.speaker.SpeakerNew;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aagam on 26/2/15.
 */
public class SpeakerDetailsActivity extends AppCompatActivity implements Palette.PaletteAsyncListener {

    private Toolbar toolbar;

    private SpeakerDB speakerDB;
    private SpeakerNew speaker;
    private Gson gson;
    private DBCommunicator communicator;
    private HashMap<String, Integer> titleSession;
    private TextView info;
    private RecyclerView lv;
    private ImageView backdropImageView;
    private SessionsBySpeakerListAdapter sessionsAdapter;
    private CollapsingToolbarLayout collapsingToolbar;
    private Bitmap lowerDpBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speakerDB = (SpeakerDB) getIntent().getSerializableExtra("speaker");
        setContentView(R.layout.activity_speaker_detail);

        gson = new Gson();
        communicator = new DBCommunicator(this);
        communicator.start();
        speaker = gson.fromJson(speakerDB.getGson_object(), SpeakerNew.class);

        titleSession = communicator.getSpeakerSession(speakerDB.getWc_id(), speakerDB.getSpeaker_id());

        initGUI();
    }

    private void initGUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(speakerDB.getName());
        info = (TextView) findViewById(R.id.wc_about);
        backdropImageView = (ImageView) findViewById(R.id.backdrop);

        if (titleSession.size()>0) {
            final List<String> names = new ArrayList<>(titleSession.keySet());
            sessionsAdapter = new SessionsBySpeakerListAdapter(this, names);
            sessionsAdapter.setOnSessionSelectedListener(new SessionsBySpeakerListAdapter.OnSessionSelected() {
                @Override
                public void onSessionSelected(int position) {
                    SessionDB sessionDB = communicator.getSession(speakerDB.getWc_id(), titleSession.get(names.get(position)));
                    Intent intent = new Intent(getApplicationContext(), SessionDetailsActivity.class);
                    intent.putExtra("session", sessionDB);
                    startActivity(intent);
                }
            });

            int height = titleSession.size() * getResources().getDimensionPixelSize(R.dimen.list_item_default_height);
            lv = (RecyclerView) findViewById(R.id.session_list_speakers);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            lv.setLayoutManager(mLayoutManager);
            lv.setMinimumHeight(height);
            lv.setAdapter(sessionsAdapter);
            lv.post(new Runnable() {
                @Override
                public void run() {
                    info.setText(Html.fromHtml(speakerDB.getInfo()));
                }
            });
        } else {
            info.setText(Html.fromHtml(speakerDB.getInfo()));
        }


        // Load the low-resolution image initally
        Picasso.with(this).load(speakerDB.getGravatar())
                .placeholder(R.drawable.ic_account_circle_grey600).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                lowerDpBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight() / 2);
                Palette.from(lowerDpBitmap).generate(SpeakerDetailsActivity.this);
                backdropImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        // Load the high-resolution image
        Picasso.with(this).load(WordCampUtils.generateHighResGravatarUrlFromDefault(speakerDB.getGravatar()))
                .noPlaceholder().into(backdropImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wc_speaker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_menu_website:
                startWebIntent();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startWebIntent() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getLink()));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (communicator == null) {
            communicator = new DBCommunicator(this);
        } else {
            communicator.restart();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (communicator != null)
            communicator.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (communicator != null)
            communicator.close();
    }

    @Override
    public void onGenerated(Palette palette) {
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);

        if (lowerDpBitmap != null) {
            lowerDpBitmap.recycle();
        }
    }


}
