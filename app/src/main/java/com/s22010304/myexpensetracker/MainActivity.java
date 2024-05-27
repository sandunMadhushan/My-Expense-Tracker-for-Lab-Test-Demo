package com.s22010304.myexpensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editExpenseType, editExpenseValue, editExpenseID;
    Button addButton, viewButton, updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a new DatabaseHelper instance
        myDb = new DatabaseHelper(this);

        editExpenseType = findViewById(R.id.editExpenseType);
        editExpenseValue = findViewById(R.id.editExpenseValue);
        editExpenseID = findViewById(R.id.editExpenseID);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        updateButton = findViewById(R.id.upadteButton);
        deleteButton = findViewById(R.id.deleteButton);

        insertData();
        viewAll();
        updateData();
        deleteData();
    }

    // Insert data to the database
    public void insertData(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDataInserted = myDb.insertData(editExpenseType.getText().toString(),editExpenseValue.getText().toString());

                if(isDataInserted == true)
                    Toast.makeText(MainActivity.this,"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    // Get data from the database or table
    public void viewAll(){
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = myDb.getAllData();
                if (results.getCount()==0){
                    showMessage("Error Message : ", "No data available in the table");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(results.moveToNext()){
                    buffer.append("ID : " + results.getString(0) + " \n");
                    buffer.append("Type : " + results.getString(1) + " \n");
                    buffer.append("Value : " + results.getString(2) + " \n\n");

                    showMessage("List of Data: ",buffer.toString());
                }
            }
        });
    }

    // Alert DialogBox
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editExpenseID.getText().toString(),editExpenseType.getText().toString(),editExpenseValue.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteData(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedatarows = myDb.deleteData(editExpenseID.getText().toString());
                if (deletedatarows>0)
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
}