package com.example.add.bean;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/31.
 */
public class Books {

    private String start;
    private String count;
    private String total;
    private ArrayList<Book> books;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}