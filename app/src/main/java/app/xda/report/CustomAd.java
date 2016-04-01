package app.xda.report;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CustomAd extends ParseQueryAdapter<ParseObject>  {


    private Context context;
    TextView titleTextView;
    protected Uri fileUri;
    double l , lg;

private DispatchActivity dispatchActivitya;

    public ParseObject object ;
    String buo;
    String ob;

     ParseGeoPoint g;



    public CustomAd(final Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(Pid.P_POST);
                ParseUser user = ParseUser.getCurrentUser();
                CustomAd c;
                // c = new CustomAd(context);
                //ParseGeoPoint geoPoint = new ParseGeoPoint(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());



                query.orderByDescending("createdAt");
                query.whereEqualTo("spanr", user.getString("spanr"));
                query.whereEqualTo("spanr2",user.getString("spanr2"));
                query.whereEqualTo("dd",false);






                return query;
            }
        });

    }


    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.posts, null);

        }


        super.getItemView(object, v, parent);

        // Add and download the image
        ImageView todoImage = (ImageView) v.findViewById(R.id.imageView);
        final ParseFile imageFile = object.getParseFile(Pid.I_IMG);

        if (imageFile != null) {
        final Uri fileUri = Uri.parse(imageFile.getUrl());
            ImageLoader.getInstance().displayImage(fileUri.toString(), todoImage);
        }

        // Covert CreatedAt to String
        Date date = object.getCreatedAt();
        Format dtatformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String reportDate = dtatformat.format(date);


        // object into list
        final TextView titleTextView = (TextView) v.findViewById(R.id.tvi1);
        titleTextView.setText(object.getString(Pid.B_BULD));
        final TextView parkText = (TextView) v.findViewById(R.id.tvi2);
        parkText.setText(object.getString(Pid.P_PARK));
        final TextView plate = (TextView) v.findViewById(R.id.tvi3);
        plate.setText(object.getString(Pid.P_PLAT));
        final TextView ctr = (TextView) v.findViewById(R.id.tvi4);
        ctr.setText(reportDate);
        final CardView cardView = (CardView)v.findViewById(R.id.card_view1);
        cardView.setPreventCornerOverlap(false);









        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(getContext(),ViewPost.class);
                i.putExtra("build",titleTextView.getText().toString());
                i.putExtra("park",parkText.getText().toString());
                i.putExtra("plate",plate.getText().toString());
                i.putExtra("d", ctr.getText().toString());
                i.setData(Uri.parse(imageFile.getUrl()));
                i.putExtra("pObjectId",object.getObjectId());




                getContext().startActivity(i);
            }
        });

        return v;

    }





}