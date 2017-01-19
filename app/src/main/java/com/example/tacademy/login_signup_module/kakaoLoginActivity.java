package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class kakaoLoginActivity extends AppCompatActivity {
    private SessionCallback callback;

    class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.i("KAKA", "onSessionOpened() call");
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.i("KAKA", "onSessionOpenFailed() call :" + exception.getMessage());
                Logger.e(exception);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 카카오 로그인에 대한 세션 체킹을 위한 아답터 생성
        callback = new SessionCallback();
        // 세션 객체에 등록
        Session.getCurrentSession().addCallback(callback);
        // 세션 체킹
        Session.getCurrentSession().checkAndImplicitOpen();
    }
    // 액티비티가 소멸되면 세션에 등록된 아답터를 제거한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
    // 로그인 수행 수 돌아왔을 때 호출된다 (데이터를 가지고)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("KAKA", "requestCode:" + requestCode);
        Log.i("KAKA", "resultCode:" + resultCode);
        if (data != null)
            Log.i("KAKA", "Intent:" + data.toString());

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    // 간단하게 로그인 하는 액티비티로 이동
    protected void redirectSignupActivity() {
        //사용자가 입맛에 맞게 고쳐라 = 재정의 = override
    }
    // 세션이 열렸을대만 활성화 하고 -> 마이 메뉴쪽 하단에 배치
    public void onLogout()
    {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //redirectLoginActivity();
                // 저장된 로그인 정보도 모두 삭제
            }
        });
    }
}