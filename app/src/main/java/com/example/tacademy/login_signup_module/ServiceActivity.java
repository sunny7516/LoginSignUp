package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServiceActivity extends kakaoLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void onLogout(View view) {
        // 1. 카카오 로그 아웃 진행 (세션닫음)
        super.onLogout();
        // 2. 페이스북 로그아욳 진행 (세션닫음)
        // 3. member 객체 null 처리
        U.getInstance().setMember(null);
        // 4. AUTOLOGIN false 세팅
        StorageHelper.getInstance().setBoolean(this, "AUTOLOGIN", false);
        // 5. 선택 ; 저장된 USER 데이터 삭제
        // 6. 로그아웃후 가야하는 화면으로 이동
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
