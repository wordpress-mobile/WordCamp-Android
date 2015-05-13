package org.wordcamp.db;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.wordcamp.R;
import org.wordcamp.objects.WordCampDB;

/**
 * Created by aagam on 27/1/15.
 */
public class DBTester extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        DBCommunicator cm = new DBCommunicator(this);
        cm.start();
        WordCampDB wc = cm.getWC(69153);


        Log.e("wc",wc.getWc_title());
/*
        Gson gc = new Gson();
        WordCamps wc = gc.fromJson(WCobject,WordCamps.class);
        long id  = cm.addWC(wc);
*/

    }

    public String WCobject = "{\"ID\":69153,\"title\":\"WordCamp Ahmedabad\",\"status\":\"publish\",\"type\":\"wordcamp\",\"author\":{\"ID\":1,\"username\":\"admin\",\"name\":\"admin\",\"first_name\":\"\",\"last_name\":\"\",\"nickname\":\"admin\",\"slug\":\"iandunn\",\"URL\":\"\",\"avatar\":\"http:\\/\\/0.gravatar.com\\/avatar\\/64f677e30cd713a9467794a26711e42d?s=96\",\"description\":\"\",\"registered\":\"2014-03-21T19:39:16+00:00\",\"meta\":{\"links\":{\"self\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/users\\/1\",\"archives\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/users\\/1\\/posts\"}}},\"content\":\"<p>WordCamp Ahmedabad is a two-day event, where both professionals and enthusiasts can come and share their love for WordPress and its ecosystem.<\\/p>\\n<p>On the first day we\\u2019ll hold two simultaneous tracks of conferences, where speakers will present on a wide range of subjects sharing their stories, experiences and expertise. The second day is more of a community day, where we\\u2019ll hold workshops designed to encourage hands-on work and interactivity among participants.<\\/p>\\n\",\"parent\":null,\"link\":\"http:\\/\\/central.wordcamp.dev\\/wordcamps\\/wordcamp-ahmedabad\\/\",\"date\":\"2015-01-20T12:19:01\",\"modified\":\"2015-01-22T19:38:43\",\"format\":\"standard\",\"slug\":\"wordcamp-ahmedabad\",\"guid\":\"http:\\/\\/central.wordcamp.dev\\/?post_type=wordcamp&#038;p=69153\",\"excerpt\":\"<p>WordCamp Ahmedabad is a two-day event, where both professionals and enthusiasts can come and share their love for WordPress and its ecosystem. On the first day we\\u2019ll hold two simultaneous tracks of conferences, where speakers will present on a wide range of subjects sharing their stories, experiences and expertise. The second day is more of [&hellip;]<\\/p>\\n\",\"menu_order\":0,\"comment_status\":\"closed\",\"ping_status\":\"closed\",\"sticky\":false,\"date_gmt\":\"2015-01-20T12:19:01\",\"modified_gmt\":\"2015-01-22T19:38:43\",\"meta\":{\"links\":{\"self\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/posts\\/69153\",\"author\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/users\\/1\",\"collection\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/posts\",\"replies\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/posts\\/69153\\/comments\",\"version-history\":\"http:\\/\\/central.wordcamp.dev\\/wp-json\\/posts\\/69153\\/revisions\"}},\"foo\":{\"_edit_lock\":[\"1421955751:1\"],\"_edit_last\":[\"1\"],\"Start Date (YYYY-mm-dd)\":[\"1423699200\"],\"End Date (YYYY-mm-dd)\":[\"1424044800\"],\"Location\":[\"Hyatt Ahmedabad\"],\"URL\":[\"http:\\/\\/\"],\"E-mail Address\":[\"aagam94@wordpress.com\"],\"Twitter\":[\"\"],\"WordCamp Hashtag\":[\"\"],\"Number of Anticipated Attendees\":[\"\"],\"Multi-Event Sponsor Region\":[\"0\"],\"Organizer Name\":[\"Aagam\"],\"WordPress.org Username\":[\"aagam94\"],\"Email Address\":[\"aa@aa.com\"],\"Telephone\":[\"933\"],\"Mailing Address\":[\"32 ad\\r\\nasdads\\r\\nadsad\"],\"Sponsor Wrangler Name\":[\"\"],\"Sponsor Wrangler E-mail Address\":[\"\"],\"Budget Wrangler Name\":[\"\"],\"Budget Wrangler E-mail Address\":[\"\"],\"Venue Name\":[\"Hyatt Hotel\"],\"Physical Address\":[\"\"],\"Maximum Capacity\":[\"750\"],\"Available Rooms\":[\"11\"],\"Website URL\":[\"\"],\"Contact Information\":[\"\"],\"wcor_sent_email_ids\":[\"a:6:{i:0;s:0:\\\"\\\";i:1;i:50307;i:2;i:2935;i:3;i:17700;i:4;i:14399;i:5;i:35574;}\"],\"_timestamp_added_to_planning_schedule\":[\"1421756429\"]},\"featured_image\":null,\"terms\":[]}";
}
