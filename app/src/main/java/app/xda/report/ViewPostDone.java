package app.xda.report;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class ViewPostDone extends Activity {
    String build;
    String park;
    String plate;
    String imgv;
    String crt;
    Button returnb;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post_done);
        TextView buildText = (TextView)findViewById(R.id.tvi11);
        TextView parkText = (TextView)findViewById(R.id.tvi22);
        TextView plateText = (TextView)findViewById(R.id.tvi33);
        TextView crtx = (TextView)findViewById(R.id.tvi44);
        TextView user = (TextView)findViewById(R.id.doneuser);
        Button rebut = (Button)findViewById(R.id.doneBut);
        CardView cardView = (CardView)findViewById(R.id.card_view1);
        cardView.setPreventCornerOverlap(false);

        ImageView img = (ImageView)findViewById(R.id.imgv);
        Intent i = getIntent();
        build = i.getStringExtra("build");
        park = i.getStringExtra("park");
        plate = i.getStringExtra("plate");
        crt = i.getStringExtra("d");
        userId = i.getStringExtra("us");

        final Uri imgUri = getIntent().getData();
        ImageLoader.getInstance().displayImage(imgUri.toString(), img);


        buildText.setText(build);
        parkText.setText(park);
        plateText.setText(plate);
        crtx.setText(crt);
        user.setText(userId);

        rebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
