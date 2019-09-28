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

        String sql = "CREATE TABLE EXPENSES(" +
                "ID INTEGER PRIMARY KEY, " +
                " NAME TEXT, "+
                " CATEGORY TEXT,"+
                " VALUE INTEGER,"+
                " STARTDATE TEXT,"+
                " INSTALLMENTS INTEGER,"+
                " INSTALLMENT INTEGER,"+
                " MONTH INTEGER,"+
                " YEAR INTEGER"+
                ")";
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

        register.put("INSTALLMENT", expense.getInstallments());

        register.put("MONTH", expense.getInstallments());

        register.put("YEAR", expense.getInstallments());

        db.insert("EXPENSES", null, register);

    }

    public void load(ArrayList expenseList){

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        String cols[] = new String[9];

        cols[0] = "ID";
        cols[1] = "NAME";
        cols[2] = "CATEGORY";
        cols[3] = "VALUE";
        cols[4] = "STARTDATE";
        cols[5] = "INSTALLMENTS";
        cols[6] = "INSTALLMENT";
        cols[7] = "MONTH";
        cols[8] = "YEAR";

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
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

    }

    public void search(ArrayList expenseList, String parameter){

        expenseList.clear();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        if(parameter.equals("")){

            String cols[] = new String[9];

            cols[0] = "ID";
            cols[1] = "NAME";
            cols[2] = "CATEGORY";
            cols[3] = "VALUE";
            cols[4] = "STARTDATE";
            cols[5] = "INSTALLMENTS";
            cols[6] = "INSTALLMENT";
            cols[7] = "MONTH";
            cols[8] = "YEAR";

            cursor = db.query("EXPENSES", cols,
                    null, null, null, null, null);
        }
        else {
            String[] args = {parameter};

            String sql = "SELECT * FROM EXPENSES WHERE ID = ? ORDER BY ID ASC";

            cursor = db.rawQuery(sql, args);
        }

        Boolean next;

        if(cursor == null){
            return;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                expenseList.add(expense.toString());

                next = cursor.moveToNext();

            }
        }

    }

    public ArrayList<Expense> search(String parameter){

        ArrayList<Expense> expenseList = new ArrayList<Expense>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        if(parameter.equals("")){

            String cols[] = new String[6];

            cols[0] = "ID";
            cols[1] = "NAME";
            cols[2] = "CATEGORY";
            cols[3] = "VALUE";
            cols[4] = "STARTDATE";
            cols[5] = "INSTALLMENTS";
            cols[6] = "INSTALLMENT";
            cols[7] = "MONTH";
            cols[8] = "YEAR";

            cursor = db.query("EXPENSES", cols,
                    null, null, null, null, null);
        }
        else {
            String[] args = {parameter};

            String sql = "SELECT * FROM EXPENSES WHERE ID = ? ORDER BY ID ASC";

            cursor = db.rawQuery(sql, args);
        }

        Boolean next;

        if(cursor == null){
            return expenseList;
        }
        else{

            next = cursor.moveToFirst();

            while(next){

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int value = cursor.getInt(3);
                String startDate = cursor.getString(4);
                int installments = cursor.getInt(5);
                int installment = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                Expense expense = new Expense(id, name, category, value, startDate,
                        installments, installment, month, year);

                expenseList.add(expense);

                next = cursor.moveToNext();

            }
        }

        return expenseList;
    }

    public void updateItem(Expense expense){

        SQLiteDatabase db = getWritableDatabase();

        String sql =
                "UPDATE EXPENSES SET" +
                " NAME = " + expense.getName() +
                ", CATEGORY = " + expense.getCategory() +
                ", VALUE = " + expense.getValue() +
                ", STARTDATE = " + expense.getStartDate() +
                ", INSTALLMENTS = " + expense.getInstallments() +
                ", INSTALLMENT = " + expense.getInstallment() +
                ", MONTH = " + expense.getMonth() +
                ", YEAR = " + expense.getYear() +
                " WHERE ID = " + expense.getId();

        db.execSQL(sql);

    }

    public void clearData(){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM EXPENSES";

        db.execSQL(sql);

    }
}
