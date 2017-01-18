package com.example.tacademy.login_signup_module;

/**
 * Created by Tacademy on 2017-01-17.
 */

public class Member {
    int idx;    // NOT NULL AUTO_INCREMENT COMMENT '회원고유인덱스번호',
    String nickname;    // VARCHAR(50) NULL DEFAULT NULL COMMENT '닉네임',
    String kakaoID; // VARCHAR(50) NULL DEFAULT NULL COMMENT '카카오아이디(토큰)',
    String facebookID;   // VARCHAR(50) NULL DEFAULT NULL COMMENT '페이스북아이디(토큰)',
    String email;
    String password;    // VARCHAR(50) NULL DEFAULT NULL COMMENT '비밀번호 -> 암호화',
    String phone;   // VARCHAR(50) NULL DEFAULT NULL COMMENT '전화번호 -> 암호화',
    String token; // VARCHAR(512) NULL DEFAULT NULL COMMENT '토큰 -> 푸시 FCM',
    String uuid;   // VARCHAR(256) NULL DEFAULT NULL COMMENT 'uuid -> 앱 재설치시 회원가입 안하게 하는 키값',
    String os_version;  // VARCHAR(50) NULL DEFAULT NULL COMMENT 'os 버전',
    String device;  // VARCHAR(50) NULL DEFAULT NULL COMMENT '단말기 모델',
    String regdate; // TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 가입일',
    String lastdate;    // TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '마지막 로그인일',
    int point;  // INT(11) NULL DEFAULT '0' COMMENT '포인트',
    int level;  // INT(11) NULL DEFAULT '1' COMMENT '레벨',
    String profile; // VARCHAR(256) NULL DEFAULT NUL

    public Member(int idx, String nickname, String kakaoID, String facebookID, String email, String password, String phone, String token, String uuid, String os_version, String device, String regdate, String lastdate, int point, int level, String profile) {
        this.idx = idx;
        this.nickname = nickname;
        this.kakaoID = kakaoID;
        this.facebookID = facebookID;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.token = token;
        this.uuid = uuid;
        this.os_version = os_version;
        this.device = device;
        this.regdate = regdate;
        this.lastdate = lastdate;
        this.point = point;
        this.level = level;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "{" +
                "  \"idx\":" + idx +
                ", \"nickname\":\"" + nickname + '\"' +
                ", \"kakaoID\":\"" + kakaoID + '\"' +
                ", \"facebookID\":\"" + facebookID + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"phone\":\"" + phone + '\"' +
                ", \"token\":\"" + token + '\"' +
                ", \"uuid\":\"" + uuid + '\"' +
                ", \"os_version\":\"" + os_version + '\"' +
                ", \"device\":\"" + device + '\"' +
                ", \"regdate\":\"" + regdate + '\"' +
                ", \"lastdate\":\"" + lastdate + '\"' +
                ", \"point\":" + point +
                ", \"level\":" + level +
                ", \"profile\":\"" + profile + '\"' +
                "}";
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;}
}
