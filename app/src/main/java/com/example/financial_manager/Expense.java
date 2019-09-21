package com.example.financial_manager;

public class Expense {

    private int id;

    private String name;

    private String category;

    private long value;

    private String startDate;

    private long installments;

    public Expense(String name, String category, long value, String startDate, long installments){
        this.name = name;
        this.category = category;
        this.value = value;
        this.startDate = startDate;
        this.installments = installments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getInstallments() {
        return installments;
    }

    public void setInstallments(long installments) {
        this.installments = installments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void save(DatabaseManager dbmanager){

        dbmanager.updateItem(this);

    }
}
