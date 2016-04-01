package app.xda.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CustomAdDone<T extends ParseObject > extends RecyclerView.Adapter<CustomAdDone.ViewHolder> {
    private Context context;
    public TextView titleTextView;
    protected Uri fileUri;

    protected List<ParseObject> objectList;
    public ParseQueryAdapter<ParseObject> queryAdapter;
    public CustomAdDone customAdDone = this ;
    public ViewGroup viewGroupPro;
    private QueryFactory<T> queryFactory;

    public interface QueryFactory<T extends ParseObject> {
        ParseQuery<T> create();

    }


    public CustomAdDone(Context context, ViewGroup parentIn) {


        viewGroupPro = parentIn;
        queryAdapter = new ParseQueryAdapter<ParseObject>(context, "Post") {
            public ParseQuery<ParseObject> create() {

                // Here we can configure a ParseQuery to our heart's desire.
                ParseQuery query = new ParseQuery("Post");
                query.whereEqualTo("dd",true);
                return query;
            }

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                if (v == null) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts, parent, false);
                }

                super.getItemView(object, v, parent);
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


                return v;
            }
        };

        queryAdapter.addOnQueryLoadListener(new OnQueryLoadListener());
        queryAdapter.loadObjects();



    }




    @Override
    public CustomAdDone.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(CustomAdDone.ViewHolder holder, int position) {
        queryAdapter.getView(position,holder.cardVieww,viewGroupPro);

    }

    @Override
    public int getItemCount() {
        return queryAdapter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardVieww;

        public ViewHolder(View itemView) {
            super(itemView);
            cardVieww = itemView;


        }
    }

    public class OnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

        public void onLoading() {

        }

        public void onLoaded(List<ParseObject> objects, Exception e) {
            customAdDone.notifyDataSetChanged();
        }
    }
}
