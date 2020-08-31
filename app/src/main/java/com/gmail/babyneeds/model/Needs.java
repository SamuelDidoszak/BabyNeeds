package com.gmail.babyneeds.model;

public class Needs {
    private int id;
    private String name;
    private int quantity;
    private String color;
    private int size;
    private String date;

    public Needs(String name, int quantity, String color, int size) {
        this.name = name;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }
    public Needs(String name, int quantity, String color, int size, String date) {
        this.name = name;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.date = date;
    }
    public Needs(int id, String name, int quantity, String color, int size) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }
    public Needs(int id, String name, int quantity, String color, int size, String date) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.date = date;
    }
    public Needs() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
