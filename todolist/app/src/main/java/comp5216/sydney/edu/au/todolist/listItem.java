package comp5216.sydney.edu.au.todolist;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class listItem implements Serializable {

    private String title;
    private String dateTime;

    public listItem(String title, String dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }

    public String getTitle(){
        return title;
    }

    public String getDateTime(){
        return dateTime;
    }

    public static ArrayList<listItem> getListItem(){

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String dateStr = dateFormat.format(date);
        ArrayList<listItem> listItems = new ArrayList<listItem>();
        listItems.add(new listItem("Shopping List",dateStr));
        listItems.add(new listItem("Reminder 1",dateStr));
        return listItems;
    }
}
