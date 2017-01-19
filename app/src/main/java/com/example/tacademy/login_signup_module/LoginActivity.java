package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kakao.usermgmt.LoginButton;

public class LoginActivity extends kakaoLoginActivity {
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        U.getInstance().addActivity(this);

        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
        // 로그인 버튼을 커스터마이즈하는 방법 -> xml만 손대서 변경
    }

    //회원가입
    public void onSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        // 이미 현 액티비티는 제거가 되므로, 리스트상에서 제외시킨다.
        U.getInstance().removeActivity(this);
        finish();
    }
}
