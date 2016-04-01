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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ViewPost extends Activity {

    private ParseQuery<ParseObject> mParse;
    private ParseObject mm;
    private CustomAd ad;

    String build;
    String park;
    String plate;
    String imgv;
    String crt;
    Button doneButton;
    String pObjectId;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);
        TextView buildText = (TextView)findViewById(R.id.tvi11);
        TextView parkText = (TextView)findViewById(R.id.tvi22);
        TextView plateText = (TextView)findViewById(R.id.tvi33);
        TextView crtx = (TextView)findViewById(R.id.tvi44);
        Button done = (Button)findViewById(R.id.doneBut);
        Button expandbut = (Button)findViewById(R.id.expand_button);
        CardView cardView = (CardView)findViewById(R.id.card_view1);
        cardView.setPreventCornerOverlap(false);


        ImageView img = (ImageView)findViewById(R.id.imgv);
        Intent i = getIntent();
        build = i.getStringExtra("build");
        park = i.getStringExtra("park");
        plate = i.getStringExtra("plate");
        crt = i.getStringExtra("d");
        pObjectId = i.getStringExtra("pObjectId");

        final Uri imgUri = getIntent().getData();
        ImageLoader.getInstance().displayImage(imgUri.toString(), img);



        buildText.setText(build);
        parkText.setText(park);
        plateText.setText(plate);
        crtx.setText(crt);
        expandbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ViewPost.this, ImageAcv.class);
                ii.setData(imgUri);
                startActivity(ii);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
ParseObject parseObject = ParseObject.createWithoutData(Pid.P_POST,pObjectId);
                parseObject.put("userr",ParseUser.getCurrentUser().getUsername().toString());
                parseObject.put(Pid.D_Done, true);
                parseObject.saveInBackground();
                finish();
            }
        });




    }

}
