package com.example.regroup;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class cards {
    private String eventId;
    private String name;
    private String date;
    private String organizer;
    private String time;
    private String address;
    private String city;
    private String description;
    private String phone;
    private String email;
    private String image;

    public cards(){}

    public cards(String eventId, String name, String date, String organizator){
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

    public cards(String eventId, String name, String date, String organizer, String time, String address, String city, String description, String phone, String email, String image) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.organizer = organizer;
        this.time = time;
        this.address = address;
        this.city = city;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }


    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
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
    public String getId(){
        return eventId;
    }
    public void setId(String eventId){
        this.eventId = eventId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getOrganizer(){
        return organizer;
    }
    public void setOrganizator(String organizator){
        this.organizer = organizator;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Event{" + "eventId='" + eventId + '\'' + ", name='" + name + '\'' + ", date=" + date + ", organizer=" + organizer + ", time=" + time + ", address='" + address + '\'' + ", city='" + city + '\'' + ", description='" + description + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + '}' + image;
    }
}


/*package com.example.regroup;

public class cards {
    private String userId;
    private String name;
    private String details;
    public cards(String userId, String name, String details){
        this.userId = userId;
        this.name = name;
        this.details = details;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
*/