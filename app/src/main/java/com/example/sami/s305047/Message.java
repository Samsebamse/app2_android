package com.example.sami.s305047;

/**
 * Created by Sami on 26-Oct-17.
 */

public class Message {

    private long ID;
    private String messageData;
    private long messageSaved;
    private long messageSent;


    public Message(){
        //empty constructor
    }

    public Message(String messageData, long messageSaved){
        this.messageData = messageData;
        this.messageSaved = messageSaved;
    }

    public Message(String messageData, long messageSaved, long messageSent){
        this.messageData = messageData;
        this.messageSaved = messageSaved;
        this.messageSent = messageSent;
    }


    public Message(long ID, String messageData, long messageSaved, long messageSent){
        this.ID = ID;
        this.messageData = messageData;
        this.messageSaved = messageSaved;
        this.messageSent = messageSent;
    }


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public long getMessageSaved() {
        return messageSaved;
    }

    public void setMessageSaved(long messageSaved) {
        this.messageSaved = messageSaved;
    }

    public long getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(long messageSent) {
        this.messageSent = messageSent;
    }
}
