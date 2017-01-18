package com.example.tacademy.login_signup_module;

/**
 * Created by Tacademy on 2017-01-18.
 */

public class ReqLogin {
    ReqHeader header;
    BodyLogin body;

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public BodyLogin getBody() {
        return body;
    }

    public void setBody(BodyLogin body) {
        this.body = body;
    }
}
