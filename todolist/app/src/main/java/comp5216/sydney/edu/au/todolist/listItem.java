package comp5216.sydney.edu.au.todolist;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Serializable interface for serializing ArrayList<Obj> to enable read and write
//Comparable interface for Java's collection's sort
public class listItem implements Serializable,Comparable<listItem> {

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
        //retrieve date in str format
        String dateStr = dateFormat.format(date);
        ArrayList<listItem> listItems = new ArrayList<listItem>();
        listItems.add(new listItem("Shopping List",dateStr));
        listItems.add(new listItem("Reminder 1",dateStr));
        return listItems;
    }
    public Date getinDateFormat (){ //retrieve date in Date format
        Date strToDate = null;
        try {
            strToDate = new SimpleDateFormat("dd MMM yyyy HH:mm").parse(this.dateTime);
            //return strToDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strToDate;
    }

    @Override //collection's sort base on this
    public int compareTo(listItem listItem) {
        //Date compareDate = (listItem.getinDateFormat());

        return getinDateFormat().compareTo(listItem.getinDateFormat());
    }

}
