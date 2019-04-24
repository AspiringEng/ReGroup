package com.example.regroup;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class cards {
    private String eventId;
    private String name;
    private Date date;
    private String organizer;
    private Time time;
    private String address;
    private String city;
    private String description;
    private String phone;
    private String email;


    private String imageRef;

    public cards(){}

    public cards(String eventId, String name, Date date, String organizator){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.organizer = organizator;
    }

    public cards(String eventId, String name) {
        this.eventId = eventId;
        this.name = name;
    }
    SimpleDateFormat dateForamt = new SimpleDateFormat("dd/MM/yyyy");
    final String timeFromat = " hh:mm";

    public cards(String eventId, String name, String date, String organizer, String time, String address, String city, String description, String phone, String email, String imageRef) {
        this.eventId = eventId;
        this.name = name;
        //this.date = Date.valueOf(dateForamt.format(date));
        this.organizer = organizer;
        // this.time = Time.valueOf(String.format(time));
        this.address = address;
        this.city = city;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.imageRef = imageRef;
    }


    public void setOrganizer(String organizer) {
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
    public String getOrganizer(){
        return organizer;
    }
    public void setOrganizaror(String organizator){
        this.organizer = organizator;
    }
    public String getImageRef() {
        return imageRef;
    }
    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }


    @Override
    public String toString() {
        return "Event{" + "eventId='" + eventId + '\'' + ", name='" + name + '\'' + ", date=" + date + ", organizer=" + organizer + ", time=" + time + ", address='" + address + '\'' + ", city='" + city + '\'' + ", description='" + description + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + '}';
    }
}


/*package com.example.regroup;

public class cards {
    private String userId;
    private String name;
    public cards(String userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public String getUserId()  {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
*/