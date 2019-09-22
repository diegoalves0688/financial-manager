package com.example.financial_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewItemActivity extends AppCompatActivity {

    private String name;

    private EditText nameEdtiText;

    private EditText categoryEdtiText;

    private EditText valueEdtiText;

    private EditText startDateEdtiText;

    private EditText installmentsEdtiText;

    private Button insertButton;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();
    }

    public void initializeComponents(){

        dbmanager = new DatabaseManager(this);

        this.nameEdtiText = findViewById(R.id.ID2_nameeditText);

        this.categoryEdtiText = findViewById(R.id.ID2_categoryeditText2);
        this.valueEdtiText = findViewById(R.id.ID2_valueeditText3);
        this.startDateEdtiText = findViewById(R.id.ID2_startDateeditText4);
        this.installmentsEdtiText = findViewById(R.id.ID2_installmentseditText5);

        this.insertButton = findViewById(R.id.ID2_insertbutton);

        checkUpdate(dbmanager);

        insertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = nameEdtiText.getText().toString();
                String category = categoryEdtiText.getText().toString();
                String value = valueEdtiText.getText().toString();
                String startDate = startDateEdtiText.getText().toString();
                String installments = installmentsEdtiText.getText().toString();

                Expense expense = new Expense(0, name, category, Long.valueOf(value), startDate, Long.valueOf(installments));

                dbmanager.insert(expense);

                ShowMessage("Expense inserted: " + name);

                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);

            }
        });

    }

    public void checkUpdate(DatabaseManager dbmanager){
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        if(itemId!= null && !itemId.equals("")){

            ArrayList<Expense> resultList = dbmanager.search(itemId);

            for (Expense element: resultList) {
                this.nameEdtiText.setText(element.getName());
                this.categoryEdtiText.setText(element.getCategory());
                this.valueEdtiText.setText(String.valueOf(element.getValue()));
                this.startDateEdtiText.setText(element.getStartDate());
                this.installmentsEdtiText.setText(String.valueOf(element.getInstallments()));
            }

        }
    }
    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}
