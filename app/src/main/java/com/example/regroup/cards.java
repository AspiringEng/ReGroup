package com.example.regroup;

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
