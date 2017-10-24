package com.example.sami.s305047;


/**
 * Created by Sami on 16-Oct-17.
 */

public class Contact {
    private long ID;
    private String name;
    private String surname;
    private String number;

    public Contact(){
        //Empty constructor
    }

    public Contact(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public Contact(int ID, String name, String surname, String number) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }
}
