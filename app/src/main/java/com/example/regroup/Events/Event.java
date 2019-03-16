package com.example.regroup.Events;

import com.example.regroup.Users.user;

import java.sql.Date;
import java.sql.Time;

public class Event {


    private String eventId;
    private String name;
    private Date date;
    private user organizer;
    private Time time;
    private String address;
    private String city;
    private String description;
    private String phone;
    private String email;


    public Event(String eventId, String name, Date date, user organizator){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
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

    public void setOrganizer(user organizer) {
        this.organizer = organizer;
    }
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

}


