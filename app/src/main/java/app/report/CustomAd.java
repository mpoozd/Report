package app.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CustomAd extends ParseQueryAdapter<ParseObject> {


    private Context context;
    public TextView titleTextView;
    protected Uri fileUri;

    protected List<ParseObject> objectList;






    public CustomAd(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(Pid.P_POST);


                query.orderByDescending("createdAt");





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
        Format dtatformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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








        ctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(getContext(),ViewPost.class);
                i.putExtra("build",titleTextView.getText().toString());
                i.putExtra("park",parkText.getText().toString());
                i.putExtra("plate",plate.getText().toString());
                i.putExtra("d", ctr.getText().toString());
                i.setData(Uri.parse(imageFile.getUrl()));



                getContext().startActivity(i);
            }
        });

        return v;


    }




}