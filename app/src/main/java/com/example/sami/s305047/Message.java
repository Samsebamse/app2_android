package com.example.sami.s305047;

/**
 * Created by Sami on 26-Oct-17.
 */

public class Message {

    private long ID;
    String message;


    public Message(){
        //empty constructor
    }

    public Message(String message){
        this.message = message;
    }

    public Message(long ID, String message){
        this.ID = ID;
        this.message = message;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
