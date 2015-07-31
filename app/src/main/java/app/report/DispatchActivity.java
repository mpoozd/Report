package app.report;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class DispatchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
    }

    private void refreshPostList() {

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Post");
        q.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> llist, ParseException e) {
                if (e == null) {
                    // Access data using the `get` methods for the object
                    String build = llist.getBuild();
                    // Access special values that are built-in to each object
                    String objectId = llist.getObjectId();
                    // Do whatever you want with the data...
                    ListView postsListView = (ListView) findViewById(R.id.listView);

                } else {
                }
            }
        });
    }
