package com.example.simpledatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editName;
    EditText editSurname;
    EditText editMark;
    EditText editId;
    Button buttonAdd;
    Button buttonShow;
    Button buttonUpdate;
    Button buttonDelete;


    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = myDb.deleteData(editId.getText().toString());
                if (deleteRows != 0)
                    Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't deleted. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editId.getText().toString(),editName.getText().toString(), editSurname.getText().toString(), editMark.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't updated. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //now i will call the insertdata from the instance of the Databasehelper here the myDb
                //because the insertdata is a boolean i will add the boolean isInserted
                boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMark.getText().toString());

                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data doesn't inserted. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                //if the res is 0 means that no data were found in the database
                if (res.getCount() == 0) {
                    showMessage("Error","Nothing found!");
                    return;
                }else {   //i use this to show the data
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        //i put the result in the buffer
                        buffer.append("Id: " + res.getString(0) + "\n");
                        buffer.append("Name: " + res.getString(1) + "\n");
                        buffer.append("Surname: " + res.getString(2) + "\n");
                        buffer.append("Marks: " + res.getString(3) + "\n\n");
                        showMessage("Data",buffer.toString());
                    }
                }
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create a new instance of my db and call the constructor of the
        //databasehelper class that i just created
        myDb = new DataBaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMark = (EditText) findViewById(R.id.editText_mark);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonShow = (Button) findViewById(R.id.button_show);
        buttonUpdate =(Button) findViewById(R.id.button_update);
        editId = (EditText) findViewById(R.id.editText_id);
        buttonDelete = (Button) findViewById(R.id.button_delete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }
}