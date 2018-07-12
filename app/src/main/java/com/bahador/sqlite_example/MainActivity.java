package com.bahador.sqlite_example;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bahador.sqlite_example.DataBase_Helper.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDB;

    EditText txtName_insert, txtSurname_insert, txtPhone_insert,
            txtID_update, txtName_update, txtSurname_update, txtPhone_update,
            txtID_delete;

    TextView txtRead;

    Button btn_insert, btn_update, btn_delete;

    String name_insert, surname_insert, phone_insert,
            id_update, name_update, surname_update, phone_update,
            id_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtRead = findViewById(R.id.idRes);
        FloatingActionButton fab = findViewById(R.id.Inserting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dl_insert = new Dialog(MainActivity.this); // for opening dialog box
                dl_insert.setContentView(R.layout.insert);
                dl_insert.setTitle("Insert Data");
                btn_insert = dl_insert.findViewById(R.id.Btn_Insert);
                btn_insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtName_insert = dl_insert.findViewById(R.id.name_insert);
                        txtSurname_insert = dl_insert.findViewById(R.id.surname_insert);
                        txtPhone_insert = dl_insert.findViewById(R.id.phone_insert);

                        name_insert = txtName_insert.getText().toString();
                        surname_insert = txtSurname_insert.getText().toString();
                        phone_insert = txtPhone_insert.getText().toString();
                        Boolean results = myDB.insertData(name_insert, surname_insert, phone_insert);
                        if (results)
                            Toast.makeText(getApplicationContext(), "Data Inserted Successfully",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Data Insertion Failed",
                                    Toast.LENGTH_SHORT).show();
                        dl_insert.dismiss();
                    }
                });
                dl_insert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @param item you can get menu item from "menu_main.xml"
     * @return it shows you menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.read) {
            Cursor cursor = myDB.getAllData();
            StringBuffer stringBuffer = new StringBuffer();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID: " + cursor.getString(0) + "\n");
                    stringBuffer.append("NAME: " + cursor.getString(1) + "\n");
                    stringBuffer.append("SURNAME: " + cursor.getString(2) + "\n");
                    stringBuffer.append("PHONE: " + cursor.getString(3) + "\n" + "\n");
                }
                txtRead.setText(stringBuffer.toString());
                Toast.makeText(getApplicationContext(), "Data Retrieved Successfully",
                        Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "No Data To retrieve",
                        Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.update) {
            final Dialog dl_update = new Dialog(MainActivity.this);
            dl_update.setContentView(R.layout.update);
            dl_update.setTitle("Update Data");
            btn_update = dl_update.findViewById(R.id.btn_update);
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtID_update = dl_update.findViewById(R.id.id_update);
                    txtName_update = dl_update.findViewById(R.id.name_update);
                    txtSurname_update = dl_update.findViewById(R.id.surname_update);
                    txtPhone_update = dl_update.findViewById(R.id.phone_update);

                    id_update = txtID_update.getText().toString();
                    name_update = txtName_update.getText().toString();
                    surname_update = txtSurname_update.getText().toString();
                    phone_update = txtPhone_update.getText().toString();

                    Boolean results = myDB.updateData(id_update, name_update, surname_update, phone_update);
                    if (results)
                        Toast.makeText(getApplicationContext(), "Data Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "No Rows Affected",
                                Toast.LENGTH_SHORT).show();
                    dl_update.dismiss();
                }
            });
            dl_update.show();
        }
        if (id == R.id.delete) {
            final Dialog dl_delete = new Dialog(MainActivity.this);
            dl_delete.setContentView(R.layout.delete);
            dl_delete.setTitle("Update Data");
            btn_delete = dl_delete.findViewById(R.id.btn_Delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtID_delete = dl_delete.findViewById(R.id.idDelete);

                    id_delete = txtID_delete.getText().toString();
                    Toast.makeText(getApplicationContext(), myDB.deleteData(id_delete) +
                            " :Rows Affected", Toast.LENGTH_SHORT).show();
                    dl_delete.dismiss();
                }
            });
            dl_delete.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
