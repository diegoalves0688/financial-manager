package com.example.financial_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(Context context) {

        super(context, "expenses_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE EXPENSES(ID INTEGER PRIMARY KEY,NAME TEXT, CATEGORY TEXT, VALUE INTEGER, STARTDATE TEXT, INSTALLMENTS INTEGER)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Expense expense){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues register = new ContentValues();

        register.put("NAME", expense.getName());

        register.put("CATEGORY", expense.getCategory());

        register.put("VALUE", expense.getValue());

        register.put("STARTDATE", expense.getStartDate());

        register.put("INSTALLMENTS", expense.getInstallments());

        db.insert("EXPENSES", null, register);

    }

    public void load(ArrayList expenseList){

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        String cols[] = new String[6];

        cols[0] = "ID";
        cols[1] = "NAME";
        cols[2] = "CATEGORY";
        cols[3] = "VALUE";
        cols[4] = "STARTDATE";
        cols[5] = "INSTALLMENTS";

        Cursor cursor = db.query("EXPENSES", cols,
                null, null, null, null, null);

        Boolean next;

        if(cursor == null){
            return;
        }else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);

                Expense expense = new Expense(name, category, value, startDate, installments);

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

    }

    public void updateItem(Expense expense){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE EXPENSES SET";
        sql.concat(" NAME = " + expense.getName());
        sql.concat(", CATEGORY = " + expense.getCategory());
        sql.concat(", VALUE = " + expense.getValue());
        sql.concat(", STARTDATE = " + expense.getStartDate());
        sql.concat(", INSTALLMENTS = " + expense.getInstallments());
        sql.concat(" WHERE ID = " + expense.getId());

        db.execSQL(sql);

    }

    public void clearData(){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM EXPENSES";

        db.execSQL(sql);

    }
}
