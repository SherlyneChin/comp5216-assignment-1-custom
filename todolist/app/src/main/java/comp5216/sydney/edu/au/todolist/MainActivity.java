package comp5216.sydney.edu.au.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.apache.commons.io.FileUtils;

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

        itemAdapter = new itemAdapter(this, arrayOfItems);

        ListView listView = (ListView) findViewById(R.id.lstView);
        //readItemsFromFile();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayOfItems.remove(position);
                                itemAdapter.notifyDataSetChanged();
                                //saveItemsToFile();
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
                    //saveItemsToFile();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == EDIT_ITEM_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                String editedItem = data.getExtras().getString("title");
                int position = data.getIntExtra("position", -1);
                String dateTime = data.getExtras().getString("dateTime");
                listItem = new listItem(editedItem,dateTime);
                arrayOfItems.set(position, listItem);
                Log.i("Updated Item in list: ", editedItem + ",position: "+position);

                Toast.makeText(this, "updated: "+ editedItem, Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
                //saveItemsToFile();
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
                //saveItemsToFile();
            }
        }


    }

    public void onAddItemClick(View view) {
        //Intent intent = new Intent(MainActivity.this, EditToDoItemActivity.class);
        //Intent goAddEditPage = new Intent(getApplicationContext(), EditToDoItemActivity.class);
        //startActivity(goAddEditPage);
        Intent goAddEditPage = new Intent(MainActivity.this, EditToDoItemActivity.class);
        startActivityForResult(goAddEditPage, ADD_ITEM_REQUEST_CODE);
        itemAdapter.notifyDataSetChanged();
        //saveItemsToFile();
    }

//    private void readItemsFromFile(){
//        //arrayOfItems = new ArrayList<listItem>();
//        File filesDir = getFilesDir();
//
//        File todoFile = new File(filesDir, "todo.txt");
//
//        if (!todoFile.exists()) {
//            arrayOfItems = new ArrayList<listItem>();
//        }else {
//            try {
//                //arrayOfItems = new ArrayList<listItem>(FileUtils.readLines(todoFile));
//                FileInputStream fis = new FileInputStream(todoFile);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//
//                arrayOfItems = (ArrayList) ois.readObject();
//
//                ois.close();
//                fis.close();
//            }
//            catch (IOException ex){
//                arrayOfItems = new ArrayList<listItem>();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void saveItemsToFile(){
//        File filesDir = getFilesDir();
//
//        File todoFile = new File(filesDir,"todo.txt");
//        try {
//            //FileUtils.writeLines(todoFile,arrayOfItems);
//            FileOutputStream fos = new FileOutputStream(todoFile);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(arrayOfItems);
//            oos.close();
//            fos.close();
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
}
