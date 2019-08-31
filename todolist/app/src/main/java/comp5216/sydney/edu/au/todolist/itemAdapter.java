package comp5216.sydney.edu.au.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class itemAdapter extends ArrayAdapter<listItem> {
    public itemAdapter(Context context, ArrayList<listItem> listItems) {
        super(context, 0, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_display_layout,parent,false);
        }
        listItem listItem = getItem(position);

        TextView itemTitleVIew = (TextView) convertView.findViewById(R.id.itemTitleVIew);
        TextView dateTimeDisplay = (TextView) convertView.findViewById(R.id.dateTimeDisplay);

        itemTitleVIew.setText(listItem.getTitle());
        dateTimeDisplay.setText(listItem.getDateTime());

        return convertView;
    }
}

