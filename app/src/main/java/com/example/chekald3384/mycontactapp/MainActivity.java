package com.example.chekald3384.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    EditText editName;
    EditText editNumber;
    EditText editAddress;
    EditText editSearch;
    private String string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editText_name);
        editNumber = (EditText) findViewById(R.id.editText_number);
        editAddress = (EditText) findViewById(R.id.editText_address);
        editSearch = (EditText) findViewById(R.id.editText_search);
        mydb = new DataBaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }


    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");
        boolean isInserted = mydb.insertData(editName.getText().toString(), editNumber.getText().toString(), editAddress.getText().toString());
        if(isInserted==true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void viewData(View view){
        Cursor res = mydb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor");
        if (res.getCount()==0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            //Append res column 0, 1, 2, 3 to the buffer - see Stringbuffer and cursor api's
            //Delimit each of the "appends" with line feed "\n"
            buffer.append("\nName: " + res.getString(1));
            buffer.append("\nNumber: " + res.getString(2));
            buffer.append("\nAddress: " + res.getString(3) + "\n");
        }
        showMessage("Data", buffer.toString());
        string = buffer.toString();
    }

    private void showMessage(String title, String message){
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.ebinah6199.mycontactapp MESSAGE";
    public void searchRecord(View view){
        Log.d("MyContactApp", "MainActivity: Launching SearchActivity");
        String str = "";
        Intent intent = new Intent(this, SearchActivity.class);
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for(int i = 0; i<string.length(); i++){
            if(string.substring(i).indexOf("Name: " + editSearch.getText().toString())==0){
                indexes.add(i);
            }
            else{
                str="No match found in Database";
            }
        }
        Log.d("MyContactApp", String.valueOf(indexes));

        for(int index: indexes) {
            if (index >= 0 && index<string.length()){

                for (int i = 0; i < 3; i++) {
                    Log.d("MyContactApp", "MainActivity: Launching SearchActivity2");
                    while (string.substring(index).indexOf("\n") != 0) {
                        Log.d("MyContactApp", "MainActivity: Launching SearchActivity3");
                        if (index >= 0 && index < string.length()) {
                            if(str.equals("No match found in Database"))
                                str = "";
                            str += string.substring(index, index + 1);
                        }
                        index++;
                    }
                    index++;
                    str += "\n";
                }
                index++;
            }
        }
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);


    }
}