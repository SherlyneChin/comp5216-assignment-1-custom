package comp5216.sydney.edu.au.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EditToDoItemActivity extends Activity
{
	public int position=0;
	EditText etItem;
	ArrayList<listItem> arrayOfItems;
	itemAdapter itemAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//populate the screen using the layout
		setContentView(R.layout.activity_edit_item);
		
		//Get the data from the main screen
		String editItem = getIntent().getStringExtra("title");

		position = getIntent().getIntExtra("position",-1);
		
		// show original content in the text field
		etItem = (EditText)findViewById(R.id.etEditItem);
		etItem.setText(editItem);
	}

	public void onSubmit(View v) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
		String dateStr = dateFormat.format(date);
		etItem = (EditText) findViewById(R.id.etEditItem);

		// Prepare data intent for sending it back
		Intent data = new Intent();

		// Pass relevant data back as a result
		data.putExtra("title", etItem.getText().toString());
		data.putExtra("dateTime", dateStr );
		data.putExtra("position", position);

		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}

	public void onCancel(View v) {
		Log.i("EditToDoItemActivity","Cancel Clicked ");
		AlertDialog.Builder builder = new AlertDialog.Builder(EditToDoItemActivity.this);
		builder.setTitle(R.string.dialog_cancel_title)
				.setMessage(R.string.dialog_cancel_msg)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent returnToMainActivityPage = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(returnToMainActivityPage);
					}
				})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//
					}
				});
		builder.create().show();
	}
}
