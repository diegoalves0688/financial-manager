package com.example.financial_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText searchParameter;

    private Button searchButton;

    private Long total;

    private TextView expensesTotal;

    private TextView searchedParam;

    private ListView expenseListView;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> dataList;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initiateComponents();
    }

    public void initiateComponents(){
        dbmanager = new DatabaseManager(this);

        total = new Long(0);

        this.expensesTotal = findViewById(R.id.ID1_totalSearchtextView16);

        this.searchedParam = findViewById(R.id.ID1_searchedParamtextView18);

        this.searchParameter = findViewById(R.id.ID3_searcheditText);

        this.expenseListView = findViewById(R.id.ID1_expenseListView);
        this.dataList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,dataList);

        expenseListView.setAdapter(adapter);

        total = dbmanager.load(dataList);
        adapter.notifyDataSetChanged();

        expensesTotal.setText(String.valueOf(total));

        FloatingActionButton emailButton = findViewById(R.id.ID1_EmailfloatingActionButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendMail(null, null, null);

            }
        });

        FloatingActionButton searchButton = findViewById(R.id.ID1_searchfloatingActionButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String parameter = searchParameter.getText().toString();

                total = dbmanager.search(dataList, parameter);
                adapter.notifyDataSetChanged();

                if(dataList.size() < 1){
                    total = dbmanager.searchByMonth(dataList, parameter);
                    adapter.notifyDataSetChanged();
                }

                searchedParam.setText(parameter.equals("") ? "all expenses" : parameter);

                expensesTotal.setText(String.valueOf(total));

            }
        });



        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int current = position + 1;

                String selectedItemContent = (String) expenseListView.getItemAtPosition(position);
                String[] itemArr = selectedItemContent.split("-");
                String elementContent = itemArr[0];
                elementContent = elementContent
                                .replace("Id:", "")
                                .replace(" ", "");

                Intent intent = new Intent(getBaseContext(), ViewItemActivity.class);
                intent.putExtra("itemId", elementContent);
                startActivity(intent);
            }
        });
    }

    public void SendMail(String to, String subject, String message){

        try{

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            email.setType("message/rfc822");

            startActivity(email.createChooser(email, "Choose an Email client :"));

        }catch (Exception ex){
            ShowMessage("Error: " + ex.getMessage());
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
