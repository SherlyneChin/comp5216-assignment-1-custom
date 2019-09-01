package comp5216.sydney.edu.au.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private final int ADD_ITEM_REQUEST_CODE = 646;
    private final int EDIT_ITEM_REQUEST_CODE = 647;
    ListView listView;
    ArrayList<listItem> arrayOfItems;
    itemAdapter itemAdapter;
    listItem listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom);

        arrayOfItems = listItem.getListItem();

        ListView listView = (ListView) findViewById(R.id.lstView);
        readItemsFromFile();
        //using Collection's sorting function to sort items in descending order
        // (# comparator interface must be implemented in the class)
        Collections.sort(arrayOfItems, Collections.reverseOrder());
        //setup ItemAdapter with the ArrayList of items and load items into the ListView container
        itemAdapter = new itemAdapter(this, arrayOfItems);
        listView.setAdapter(itemAdapter);

        setupListViewListener();
    }



    private void setupListViewListener(){

        ListView listView = (ListView) findViewById(R.id.lstView);
        //long click listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId) {
                Log.i("MainActivity","Long Clicked item"+ position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //initiate a dialog box
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayOfItems.remove(position);
                                itemAdapter.notifyDataSetChanged();
                                Collections.sort(arrayOfItems, Collections.reverseOrder());
                                saveItemsToFile();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        });
                builder.create().show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItem updateItem = itemAdapter.getItem(position);
                Log.i("MainActivity", "Clicked item"+position+": ");

                Intent intent = new Intent(MainActivity.this, EditToDoItemActivity.class);
                if (intent != null){
                    intent.putExtra("title", updateItem.getTitle());
                    intent.putExtra("dateTime",updateItem.getDateTime());
                    intent.putExtra("position", position);

                    startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
                    itemAdapter.notifyDataSetChanged();
                    Collections.sort(arrayOfItems, Collections.reverseOrder());
                    saveItemsToFile();
                }
            }
        });
    }

    //follow up on startActivityFor Result
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == EDIT_ITEM_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                String editedItem = data.getExtras().getString("title");
                int position = data.getIntExtra("position", -1);
                String dateTime = data.getExtras().getString("dateTime");
                listItem = new listItem(editedItem,dateTime);
                arrayOfItems.set(position, listItem);
                Log.i("Updated Item in list: ", editedItem + ",position: "+position);

                Toast.makeText(this, "updated: "+ editedItem, Toast.LENGTH_SHORT).show(); //little notice box appears and disappear after sometime
                itemAdapter.notifyDataSetChanged();
                Collections.sort(arrayOfItems, Collections.reverseOrder());
                saveItemsToFile();
            }
        } else if (requestCode == ADD_ITEM_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                String editedItem = data.getExtras().getString("title");
                int position = data.getIntExtra("position", -1);
                String dateTime = data.getExtras().getString("dateTime");
                listItem = new listItem(editedItem,dateTime);
                arrayOfItems.add(listItem);
                Log.i("Added Item in list: ", editedItem);

                Toast.makeText(this, "added: "+ editedItem, Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
                Collections.sort(arrayOfItems, Collections.reverseOrder());
                saveItemsToFile();
            }
        }


    }

    public void onAddItemClick(View view) {

        Intent goAddEditPage = new Intent(MainActivity.this, EditToDoItemActivity.class);
        startActivityForResult(goAddEditPage, ADD_ITEM_REQUEST_CODE);
        itemAdapter.notifyDataSetChanged();
        Collections.sort(arrayOfItems, Collections.reverseOrder());
        saveItemsToFile();
    }

    private void readItemsFromFile(){

        File file = new File(getFilesDir(),"todoFile");
        //File todoFile = new File(filesDir, "todo.txt");

        if (!file.exists()) {
            arrayOfItems = new ArrayList<listItem>();
        }else {
            try {

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                arrayOfItems = (ArrayList) ois.readObject();

                ois.close();
                fis.close();

                for (listItem item : arrayOfItems){
                    Log.i("Reading", item.getTitle());
                }
            }
            catch (IOException ex){
                arrayOfItems = new ArrayList<listItem>();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void saveItemsToFile(){

        File file = new File(getFilesDir(),"todoFile");
        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayOfItems);
            oos.close();
            fos.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
