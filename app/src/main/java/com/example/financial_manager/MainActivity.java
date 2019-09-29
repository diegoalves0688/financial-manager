package com.example.financial_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button loadButton;

    private Button clearButton;

    private TextView totalTextView;

    private ListView expenseListView;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> dataList;

    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbmanager = new DatabaseManager(this);

        FloatingActionButton fab = findViewById(R.id.ID1_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), InsertActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initializeComponents();
    }

    public void initializeComponents(){


        initiateNext3MonthsChart("");

    }

    public String[] getXaxisChartData(){

        String[] xAxisData = {"Sept", "Oct", "Nov", "Dec"};

        return xAxisData;
    }

    public String getMonth(String monthdigit){
        if(monthdigit.equals("01") || monthdigit.equals("Jan"))
            return "January";
        if(monthdigit.equals("02") || monthdigit.equals("Feb"))
            return "february";
        if(monthdigit.equals("03") || monthdigit.equals("Mar"))
            return "march";
        if(monthdigit.equals("04") || monthdigit.equals("Apr"))
            return "april";
        if(monthdigit.equals("05") || monthdigit.equals("May"))
            return "may";
        if(monthdigit.equals("06") || monthdigit.equals("Jun"))
            return "june";
        if(monthdigit.equals("07") || monthdigit.equals("July"))
            return "july";
        if(monthdigit.equals("08") || monthdigit.equals("Aug"))
            return "august";
        if(monthdigit.equals("09") || monthdigit.equals("Sept"))
            return "september";
        if(monthdigit.equals("10") || monthdigit.equals("Oct"))
            return "october";
        if(monthdigit.equals("11") || monthdigit.equals("Nov"))
            return "november";
        if(monthdigit.equals("12") || monthdigit.equals("Dec"))
            return "december";

        return "";
    }

    public int[] getYaxisChartData(){

        long threeMonthTotal = 0;

        this.totalTextView = findViewById(R.id.ID1_totalValuetextView20);

        int[] yAxisData = new int[4];

        dbmanager = new DatabaseManager(this);

        String[] months = getXaxisChartData();

        int index = 0;

        for(String month: months){

            String monthParsed = getMonth(month);

            int total = (int)dbmanager.searchByMonth(monthParsed);

            yAxisData[index] = total;

            threeMonthTotal = threeMonthTotal + total;

            index++;
        }

        totalTextView.setText(String.valueOf(threeMonthTotal));

        return yAxisData;
    }

    // Chart lib reference: https://github.com/lecho/hellocharts-android
    //
    // Thank you to Sveta post in Mobindustry:
    // https://www.mobindustry.net/how-to-quickly-implement-beautiful-charts-in-your-android-app/

    public void initiateChart(String title){

        LineChartView lineChartView;

        String[] xAxisData = getXaxisChartData();
        int[] yAxisData = getYaxisChartData();

        lineChartView = findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List xAxisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0; i < xAxisData.length; i++) {
            xAxisValues.add(i, new AxisValue(i).setLabel(xAxisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis xAxis = new Axis();
        xAxis.setValues(xAxisValues);
        xAxis.setTextSize(16);
        xAxis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(xAxis);

        Axis yAxis = new Axis();
        yAxis.setName(title);
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

    }

    public void initiateNext3MonthsChart(String title){

        LineChartView lineChartView;

        String[] xAxisData = getXaxisChartData();
        int[] yAxisData = getYaxisChartData();

        lineChartView = findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List xAxisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0; i < xAxisData.length; i++) {
            xAxisValues.add(i, new AxisValue(i).setLabel(xAxisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis xAxis = new Axis();
        xAxis.setValues(xAxisValues);
        xAxis.setTextSize(16);
        xAxis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(xAxis);

        Axis yAxis = new Axis();
        yAxis.setName(title);
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ID1_action_settings) {
            return true;
        }

        if (id == R.id.ID1_erase_data) {

            dbmanager = new DatabaseManager(this);
            dbmanager.clearData();

            ShowMessage("All data deleted.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_insert) {

            Intent intent = new Intent(getBaseContext(), InsertActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_expense_view) {

            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ShowMessage(String message){

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }


}
