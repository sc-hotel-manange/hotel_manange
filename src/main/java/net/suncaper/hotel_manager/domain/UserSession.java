package net.suncaper.hotel_manager.domain;

public class UserSession {         //这个类用于存储session
    private int id;
    public int getId() {
        return id;
    }
    public UserSession(int id){
        this.id=id;
    }

}
