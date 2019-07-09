package com.example.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Words> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Words> addNumber,int mColorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, addNumber);
        this.mColorResourceId=mColorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate( R.layout.list_item, parent, false);

        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Words currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView =  listItemView.findViewById(R.id.miwok_textview);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setTextColor(Color.parseColor( "#FFFFFF" ) );

        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView =  listItemView.findViewById(R.id.default_textview);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView

        defaultTextView.setTextColor(Color.parseColor( "#FFFFFF" ) );
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
       ImageView iconView =  listItemView.findViewById(R.id.image_view);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView

        if (currentWord.hasImage()) {
            iconView.setImageResource( currentWord.getmImagenumbers() );
            iconView.setVisibility( View.VISIBLE );
        }
        else {
            iconView.setVisibility( View.GONE );
        }
         //set theme color for  listitem
        View textContainer=listItemView.findViewById( R.id.text_container );
        //find color by view ID
        int color= ContextCompat.getColor( getContext(),mColorResourceId );
        //set background Color of Text Container
        textContainer.setBackgroundColor( color );
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
