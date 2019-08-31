package comp5216.sydney.edu.au.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom);

        ArrayList<listItem> arrayOfItems = listItem.getListItem();

        itemAdapter itemAdapter = new itemAdapter(this, arrayOfItems);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(itemAdapter);
    }

}
