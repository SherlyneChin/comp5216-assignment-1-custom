package comp5216.sydney.edu.au.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<listItem> arrayOfItems;
    itemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom);

        arrayOfItems = listItem.getListItem();

        itemAdapter = new itemAdapter(this, arrayOfItems);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(itemAdapter);
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        setupListViewListener();
    }

    private void setupListViewListener(){
        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId) {
                Log.i("MainActivity","Long Clicked item"+ position);
                arrayOfItems.remove(position);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItem updateItem = itemAdapter.getItem(position);
                Log.i("MainActivity", "Clicked item"+position+": ");

            }
        });
    }

}
