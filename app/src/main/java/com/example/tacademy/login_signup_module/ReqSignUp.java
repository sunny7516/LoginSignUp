package com.example.tacademy.login_signup_module;

/**
 * Created by Tacademy on 2017-01-17.
 */

public class ReqSignUp {
    ReqHeader header;
    Member body;

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public Member getBody() {
        return body;
    }

    public void setBody(Member body) {
        this.body = body;
    }
}