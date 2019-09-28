package com.example.financial_manager;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class InsertActivity extends AppCompatActivity {

    private EditText nameEdtiText;

    private EditText categoryEdtiText;

    private EditText valueEdtiText;

    private String startDate;

    private EditText installmentsEdtiText;

    private Button insertButton;

    private DatabaseManager dbmanager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeComponents();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initializeComponents(){

        dbmanager = new DatabaseManager(this);

        this.nameEdtiText = findViewById(R.id.ID4_nameeditText);
        this.categoryEdtiText = findViewById(R.id.ID4_categoryeditText2);
        this.valueEdtiText = findViewById(R.id.ID4_valueeditText3);
        this.installmentsEdtiText = findViewById(R.id.ID4_installmentseditText4);
        this.insertButton = findViewById(R.id.ID4_button);

        insertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = nameEdtiText.getText().toString();
                String category = categoryEdtiText.getText().toString();
                String value = valueEdtiText.getText().toString();
                String startDate = getDate("");

                String installmentsValue = installmentsEdtiText.getText().toString();
                int installments = Integer.parseInt(installmentsValue);

                String month = getDate("month");
                String year = getDate("year");

                for(int i = 1; i <= installments; i++){

                    Expense expense = new Expense(0, name,
                            category, Long.valueOf(value),
                            startDate, Long.valueOf(installments),
                            i,
                            Long.valueOf(month),
                            Long.valueOf(year));

                    dbmanager.insert(expense);
                }

                ShowMessage("Expense inserted: " + name);

                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDate(String param){

        String currentDate =
                new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String[] arrOfStr = currentDate.split("-");

        if(param.equals("day")){
            return arrOfStr[0];
        }else if(param.equals("month")){
            return arrOfStr[1];
        }else if(param.equals("year")){
            return arrOfStr[2];
        }

        return currentDate;

    }

    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}
