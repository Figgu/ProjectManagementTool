package com.projectmanagementtoolapp.pkgData;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;

import java.util.ArrayList;

/**
 * Created by alexk on 13.10.2017.
 */

public class ContributorsAdapter extends ArrayAdapter<User> {
    public ContributorsAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_add_contributors, parent, false);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.imgDeleteCon);
        TextView tv = (TextView) convertView.findViewById(R.id.contributorNameAdd);
        // Populate the data into the template view using the data object
        tv.setText(user.getUsername());
        iv.setImageResource(R.drawable.delete);
        // Return the completed view to render on screen
        return convertView;
    }
}
