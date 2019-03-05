package com.example.regroup.Events;

//import com.google.firebase.database.core.Context;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.regroup.R;
import com.example.regroup.Users.user;

import java.util.List;

public class eventsAdapter  extends ArrayAdapter<Event> {

    Context context;

    public eventsAdapter(Context context, int resourceId, List<Event> items){
        super(context, resourceId, items);
    }
   /* public eventsAdapter(Context context, List<Event> items){
        super(context, 0, items);
    }*/

   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
       }
       // Lookup view for data population
       TextView name = (TextView) convertView.findViewById(R.id.Name);
       TextView date = (TextView) convertView.findViewById(R.id.Date);
       TextView organizer = (TextView) convertView.findViewById(R.id.Organizer);
       // Populate the data into the template view using the data object
       name.setText(event.getName());
       date.setText(event.getDate().toString());
       organizer.setText(event.getOrganizer().toString());
       // Return the completed view to render on screen
       return convertView;
   }
}

