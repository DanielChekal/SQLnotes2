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

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editAddress = findViewById(R.id.editText_address);
        editPhone = findViewById(R.id.editText_phone);

        mydb = new DataBaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated mydb");
    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = mydb.insertData(editName.getText().toString());
        if(isInserted == true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void viewData (View view){
        Cursor res = mydb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: Received cursor");

        if (res.getCount() == 0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            if (res.moveToNext() = 0){

            }
            buffer.append(res + "\n");
            showMessage("Contacts", "Name: "+editName.getText().toString()+"\nPhone: "+editPhone.getText().toString() + "\nAddress: " +editAddress.getText().toString());
            //Append res column 0,1,2,3, to the buffer - see StringBuffer and Cursor api.
        //Delimit each of the "appends" with line feed "\n"
        }

        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.chekald3384.mycontactapp.MESSAGE";
    public void SearchRecord (View view){
        Log.d("MyContactApp", "MainActivity: launching SearchActivity");
        android.content.Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_MESSAGE, editName.getText().toString());
        startActivity(intent);

    }
}