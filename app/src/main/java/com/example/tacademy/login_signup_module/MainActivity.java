package com.example.tacademy.login_signup_module;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends kakaoLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            onLogin();
        }
    }

    String kakaoID, facebookID, password, email;

    // LoginActivity로 이동
    public void goLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //로그인
    public void onLogin() {
        // 1. 파라미터 세팅 -> Json 형식으로
        // { header : {code:LO}, body:{kakaoID:xx, ... uuid:xxx}}
        //  ReqLogin (ReqHeader BodyLogin}
        ReqLogin reqLogin = new ReqLogin();
        ReqHeader header = new ReqHeader();
        BodyLogin bodyLogin = new BodyLogin(
                kakaoID,
                facebookID,
                email,
                password,
                U.getInstance().getUUID(this)
        );

        reqLogin.setHeader(header);
        reqLogin.setBody(bodyLogin);
        header.setCode("LO");

        // 2. 요청객체 준비
        try {
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(
                            Request.Method.POST,
                            "http://52.78.30.74:3000/login",
                            new JSONObject(new Gson().toJson(reqLogin)),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // 4. 응답처리 -> 회원정보를 디비에 저장한다. (SharedPreferences, SqlLite, file)
                                    //      자동로그인설정 -> 이벤트 팝업 -> 메인 서비스로 이동 -> 광고(알림, 이벤트 등등)
                                    Log.i("RES", response.toString());
                                    Toast.makeText(MainActivity.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                                    onLoginThen(response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }
                    );
            // 3. 요청 (타임아웃 설정 추가 필요)
            U.getInstance().getRequestQueue(this).add(jsonObjectRequest);
        } catch (Exception e) {

        }
    }
    //로그인 후속처리
    public void onLoginThen(String json){
        // 0. 파싱
        ResLogin resLogin = new Gson().fromJson(json, ResLogin.class);
        // 1. 로그인 실패 체크
        if(resLogin.getHeader().getCode()<0){
            Toast.makeText(MainActivity.this, resLogin.getHeader().getMsg(), Toast.LENGTH_SHORT).show();
            //로그인 화면으로 이동시킴(구현해야함)
            return;
        }
        // 2. 개인정보 저장
        resLogin.getBody().toString();
        //3. 저장
        StorageHelper.getInstance().setString(this, "USER", resLogin.getBody().toString());
        // 3-2. 자동 로그인 저장
        StorageHelper.getInstance().setBoolean(this, "AUTOLOGIN", true);
        // 3-3. 유저 정보 객체로 저장
        U.getInstance().setMember(resLogin.getBody());
        // 4. 메인 서비스로 이동
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);

        //{"idx":2, "nickname":"저머니", "kakaoID":"8723487", "facebookID":"null"...}

    }

}
