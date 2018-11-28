package com.netty.session;

public class Session {
    private  String userName;
    private String userId;

    public Session(String userId, String userName){
        // 自定义的Session包含的信息.
        this.userId = userId;
        this.userName = userName;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
