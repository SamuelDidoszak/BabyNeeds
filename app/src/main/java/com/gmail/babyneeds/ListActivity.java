package com.gmail.babyneeds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.babyneeds.data.NeedsDatabaseHandler;
import com.gmail.babyneeds.model.Needs;
import com.gmail.babyneeds.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Needs> needsList;
    private NeedsDatabaseHandler DB;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button addButton;
    private EditText nameAddEditText, quantityAddEditText, colorAddEditText, sizeAddEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fab = findViewById(R.id.addNeedsListButton);
        recyclerView = findViewById(R.id.needsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB = new NeedsDatabaseHandler(this);
        needsList = DB.getAll();
        recyclerViewAdapter = new RecyclerViewAdapter(this, needsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
    }

    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view= getLayoutInflater().inflate(R.layout.popup, null);

        addButton = view.findViewById(R.id.addButton);
        nameAddEditText = view.findViewById(R.id.nameAddEditText);
        quantityAddEditText = view.findViewById(R.id.quantityAddEditText);
        colorAddEditText = view.findViewById(R.id.colorAddEditText);
        sizeAddEditText = view.findViewById(R.id.sizeAddEditText);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean canSave = true;
                if (nameAddEditText.getText().toString().isEmpty()) {
                    nameAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (quantityAddEditText.getText().toString().isEmpty()) {
                    quantityAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (colorAddEditText.getText().toString().isEmpty()) {
                    colorAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (sizeAddEditText.getText().toString().isEmpty()) {
                    sizeAddEditText.setHintTextColor(getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (canSave) {
                    DB.addNeeds(new Needs(nameAddEditText.getText().toString().trim(), Integer.parseInt(quantityAddEditText.getText().toString()), colorAddEditText.getText().toString().trim(), Integer.parseInt(sizeAddEditText.getText().toString())));
                    startActivity(new Intent(ListActivity.this, ListActivity.class));
                    alertDialog.dismiss();
                    finish();
                }
            }
        });
    }
}


















