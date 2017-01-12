package com.example.kuet.traveldirectory;

/**
 * Created by shuvro on 1/9/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>
{

    private final Activity context;
    private final String[] itemName;
    private final Integer[] imageId;
    public CustomList(Activity context, String[] itemName, Integer[] imageId) {
        super(context, R.layout.mylist, itemName);
        this.context = context;
        this.itemName = itemName;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle= (TextView) rowView.findViewById(R.id.item);
        ImageView imageView= (ImageView) rowView.findViewById(R.id.icon);
        TextView extraTxt= (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemName[position]);
        imageView.setImageResource(imageId[position]);
        extraTxt.setText("Description "+itemName[position]);

        return rowView;
    }
}
