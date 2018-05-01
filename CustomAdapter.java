package com.akseer.puzzlerz.akseer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by Hammad on 21-Jan-18.
 */

public class CustomAdapter extends SimpleAdapter {
    public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
        super(context, data, resource, from, to);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // here you let SimpleAdapter built the view normally.
        View v = super.getView(position, convertView, parent);

        // Then we get reference for Picasso
        ImageView img = (ImageView) v.getTag();
        if(img == null){
            img = (ImageView) v.findViewById(R.id.list_post_image);
            v.setTag(img); // <<< THIS LINE !!!!
        }
        //TextView list_title =  (TextView) v.findViewById(R.id.list_title);
       String url = String.valueOf(((Map)getItem(position)).get("post_image"));
       Picasso.with(v.getContext()).load(url).into(img);

       TextView impact = (TextView) v.findViewById(R.id.impact);
       if(impact.getText().equals("+"))
       {
           impact.setText("POSITIVE");
           impact.setTextColor(Color.parseColor("#008000"));
       }

       else{
           impact.setText("NEGATIVE");
           impact.setTextColor(Color.parseColor("#e82e25"));
       }
    /*Bitmap b = url;
img.setImageBitmap(b);*/
        // return the view
        return v;
    }
}
