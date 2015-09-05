package app.report;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ViewPost extends Activity {

    String build;
    String park;
    String plate;
    String imgv;
    String crt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);
        TextView buildText = (TextView)findViewById(R.id.tvi11);
        TextView parkText = (TextView)findViewById(R.id.tvi22);
        TextView plateText = (TextView)findViewById(R.id.tvi33);
        TextView crtx = (TextView)findViewById(R.id.tvi44);

        ImageView img = (ImageView)findViewById(R.id.imgv);
        Intent i = getIntent();
        build = i.getStringExtra("build");
        park = i.getStringExtra("park");
        plate = i.getStringExtra("plate");
        crt = i.getStringExtra("d");

        final Uri imgUri = getIntent().getData();
        ImageLoader.getInstance().displayImage(imgUri.toString(), img);


        buildText.setText(build);
        parkText.setText(park);
        plateText.setText(plate);
        crtx.setText(crt);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ViewPost.this,ImageAcv.class);
                ii.setData(imgUri);
                startActivity(ii);
            }
        });
    }

}
