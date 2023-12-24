package com.example.zuba.model;

public class ObligationSerialiszerModel {
    private int id;
    private int total_price;
    private int initial_payment;
    private int paid;
    private int reminder;
    private double price_per_month;
    private String deadline;
    private String next_date_payment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getInitial_payment() {
        return initial_payment;
    }

    public void setInitial_payment(int initial_payment) {
        this.initial_payment = initial_payment;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public double getPrice_per_month() {
        return price_per_month;
    }

    public void setPrice_per_month(double price_per_month) {
        this.price_per_month = price_per_month;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getNext_date_payment() {
        return next_date_payment;
    }

    public void setNext_date_payment(String next_date_payment) {
        this.next_date_payment = next_date_payment;
    }
}
