package com.gmail.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.gmail.babyneeds.data.NeedsDatabaseHandler;
import com.gmail.babyneeds.model.Needs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NeedsDatabaseHandler DB;
    private List<Needs> needsList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button addButton;
    private EditText nameAddEditText, quantityAddEditText, colorAddEditText, sizeAddEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DB = new NeedsDatabaseHandler(this);
        sendToListWhenContent();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        needsList = DB.getAll();
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
            //  findViewById
        addButton = view.findViewById(R.id.addButton);
        nameAddEditText = view.findViewById(R.id.nameAddEditText);
        quantityAddEditText = view.findViewById(R.id.quantityAddEditText);
        colorAddEditText = view.findViewById(R.id.colorAddEditText);
        sizeAddEditText = view.findViewById(R.id.sizeAddEditText);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean canSave = true;
                if(nameAddEditText.getText().toString().isEmpty())
                {
                    nameAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if(quantityAddEditText.getText().toString().isEmpty())
                {
                    quantityAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if(colorAddEditText.getText().toString().isEmpty())
                {
                    colorAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if(sizeAddEditText.getText().toString().isEmpty())
                {
                    sizeAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if(canSave) {
                        DB.addNeeds(new Needs(nameAddEditText.getText().toString().trim(), Integer.parseInt(quantityAddEditText.getText().toString()), colorAddEditText.getText().toString().trim(), Integer.parseInt(sizeAddEditText.getText().toString())));
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                startActivity(new Intent(MainActivity.this, ListActivity.class));
                                finish();
                            }
                        }, 100);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendToListWhenContent()
    {
        if(DB.getLenght() != 0)
        {
            startActivity(new Intent(this, ListActivity.class));
        }
    }
}
