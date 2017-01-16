package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.os.Bundle;

import com.kakao.usermgmt.LoginButton;

public class LoginActivity extends kakaoLoginActivity {
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
        // 로그인 버튼을 커스터마이즈하는 방법 -> xml만 손대서 변경
    }

    //회원가입
    public void onSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
