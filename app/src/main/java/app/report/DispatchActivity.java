package app.report;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DispatchActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLatestPosts();
    }

    protected void getLatestPosts() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> buildTalks, ParseException e) {
                if (e == null) {
                    ArrayList<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();
                    for (ParseObject result : buildTalks) {
                        HashMap<String, String> article = new HashMap<>();
                        article.put("build",
                                result.getString("build"));
                        article.put("park",
                                result.getString("park"));
                        article.put("plate",
                                result.getString("plate"));
                        articles.add(article);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(
                            DispatchActivity.this, articles,
                            R.layout.posts, new String[] {
                            "build",
                           "park",
                            "plate" }, new int[] {
                            R.id.tvi1, R.id.tvi2, R.id.tvi3});
                    setListAdapter(adapter);
                } else {

                }
            }
        });
    }
}