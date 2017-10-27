package com.example.sami.s305047;

/**
 * Created by Sami on 26-Oct-17.
 */

public class Message {

    private long ID;
    private String messageData;
    private String messageSaved;
    private String messageSent;


    public Message(){
        //empty constructor
    }


    public Message(String messageData, String messageSaved, String messageSent){
        this.messageData = messageData;
        this.messageSaved = messageSaved;
        this.messageSent = messageSent;
    }


    public Message(long ID, String messageData, String messageSaved, String messageSent){
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

    public String getMessageSaved() {
        return messageSaved;
    }

    public void setMessageSaved(String messageSaved) {
        this.messageSaved = messageSaved;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }
}
