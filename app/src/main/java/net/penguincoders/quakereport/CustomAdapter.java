package net.penguincoders.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.penguincoders.quakereport.Earthquake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public CustomAdapter(Activity context, ArrayList<Earthquake> earthquake){
        super(context,0,earthquake);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);

        DecimalFormat  formatter = new DecimalFormat("0.0");

        // Formatted magnitude to display only one decimal place
        String magnitudeFormatted = formatter.format(currentEarthquake.getMagnitude());
        magnitude.setText(magnitudeFormatted);


        // Separating location into Primary and Offset types based on ',' and setting the two
        // TextViews respectively. If no Offset_location is present, show "Near the"

        String location = currentEarthquake.getLocation();


        TextView locationOffset = (TextView) listItemView.findViewById(R.id.location_offset);
        TextView primaryLocation = (TextView) listItemView.findViewById(R.id.primary_location);

        if (location.contains(LOCATION_SEPARATOR)) {
            String[] parts = location.split(LOCATION_SEPARATOR);
            locationOffset.setText(parts[0] + LOCATION_SEPARATOR);
            primaryLocation.setText(parts[1]);
        } else {
            locationOffset.setText(getContext().getString(R.string.near_the));
            primaryLocation.setText(location);
        }


        Date dateObject = new Date(currentEarthquake.getDate());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;

    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1: magnitudeColorResourceId = R.color.magnitude1;
                    break;
            case 2: magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3: magnitudeColorResourceId=R.color.magnitude3;
                break;
            case 4: magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5: magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6 :magnitudeColorResourceId=R.color.magnitude6;
                break;
            case 7 : magnitudeColorResourceId=R.color.magnitude7;
                break;
            case 8: magnitudeColorResourceId=R.color.magnitude8;
                break;
            case 9:magnitudeColorResourceId=R.color.magnitude9;
                break;
            default:magnitudeColorResourceId=R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);

    }
}
