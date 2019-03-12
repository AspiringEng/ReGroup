package com.example.regroup.Events;

import com.example.regroup.Users.user;

import java.sql.Date;

public class Event {


    private String eventId;
    private String name;
    private Date date;
    private user organizer;
    public Event(String eventId, String name, Date date, user organizator){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.organizer = organizator;
    }

    public String getEventId(){
        return eventId;
    }
    public void setEventId(String eventId){
        this.eventId = eventId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public user getOrganizer(){
        return organizer;
    }
    public void setOrganizaror(user organizator){
        this.organizer = organizator;
    }
    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", organizer=" + organizer +
                '}';
    }
}


<<<<<<< HEAD
=======
    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", organizer=" + organizer +
                '}';
    }
}
>>>>>>> dcf6ae49efd0a19a972b910874e46d3cf90827f7
