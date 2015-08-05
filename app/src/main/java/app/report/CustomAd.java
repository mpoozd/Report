package app.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class CustomAd extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<Post> postList = null;
    private ArrayList<Post> arraylist;

    public CustomAd(Context context,
                           List<Post> post) {
        this.context = context;
        this.postList = postList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Post>();
        this.arraylist.addAll(post);
        imageLoader = new ImageLoader(context);
    }



    public class ViewHolder {
        TextView build;
        TextView park;
        TextView plate;
        ImageView picture;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.posts, null);
            // Locate the TextViews in listview_item.xml
            holder.build = (TextView) view.findViewById(R.id.tvi1);
            holder.park = (TextView) view.findViewById(R.id.tvi2);
            holder.plate = (TextView) view.findViewById(R.id.tvi3);
            // Locate the ImageView in listview_item.xml
            holder.picture = (ImageView) view.findViewById(R.id.imagevv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.build.setText(postList.get(position).getBuild());
        holder.park.setText(postList.get(position).getPark());
        holder.plate.setText(postList.get(position)
                .getPlate());
        // Set the results into ImageView
        imageLoader.DisplayImage(postList.get(position).getPicture(),
                holder.picture);


        return view;
    }

}

