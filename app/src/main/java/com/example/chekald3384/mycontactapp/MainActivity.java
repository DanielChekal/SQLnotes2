package com.example.chekald3384.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);

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

}
