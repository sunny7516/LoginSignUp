package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends kakaoLoginActivity {
    String kakaoID, facebookID, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        U.getInstance().addActivity(this);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("토큰|:","" + refreshedToken);

        //해시키 구하기
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        // 버전 체크 등등... 이후
        // 만약, 자동 로그인 상태라면 로그인 후 바로 서비스 화면으로 진행
        if(!U.getInstance().isAutoLogin(this)){
            // 로그인 해야한다.
            goLogin(null);
        }else{
            // 로그인 수행
            // 서비스로 이동!!
            String json = StorageHelper.getInstance().getString(this, "USER");  // 저장된 json 정보 뽑아오기
            Member member = new Gson().fromJson(json, Member.class);
            kakaoID = member.getKakaoID();
            facebookID = member.getFacebookID();
            password = member.getPassword();
            email = member.getEmail();
            U.getInstance().onLogin(this, kakaoID, facebookID, password, email);
        }
    }

    // LoginActivity로 이동
    public void goLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        // 이미 현 액티비티는 제거가 되므로, 리스트상에서 제외시킨다.
        U.getInstance().removeActivity(this);
        finish();
    }
}
