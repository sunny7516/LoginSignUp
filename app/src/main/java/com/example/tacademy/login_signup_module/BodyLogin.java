package com.example.tacademy.login_signup_module;

/**
 * Created by Tacademy on 2017-01-17.
 */

public class BodyLogin {
    String kakaoID; // VARCHAR(50) NULL DEFAULT NULL COMMENT '카카오아이디(토큰)',
    String facebookID;   // VARCHAR(50) NULL DEFAULT NULL COMMENT '페이스북아이디(토큰)',
    String email;
    String password;    // VARCHAR(50) NULL DEFAULT NULL COMMENT '비밀번호 -> 암호화',
    String uuid;   // VARCHAR(256) NULL DEFAULT NULL COMMENT 'uuid -> 앱 재설치시 회원가입 안하게 하는 키값',


    public BodyLogin(String kakaoID, String facebookID, String email, String password, String uuid) {
        this.kakaoID = kakaoID;
        this.facebookID = facebookID;
        this.email = email;
        this.password = password;
        this.uuid = uuid;
    }

    public String getKakaoID() {
        return kakaoID;
    }

    public void setKakaoID(String kakaoID) {
        this.kakaoID = kakaoID;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
