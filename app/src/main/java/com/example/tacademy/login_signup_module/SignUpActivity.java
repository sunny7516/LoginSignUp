package com.example.tacademy.login_signup_module;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.json.JSONObject;

// 한번만 나와야하는 Activity
public class SignUpActivity extends kakaoLoginActivity {

    String nickname, kakaoID, facebookID, password, email, profile;
    final int PERMISSION_READ_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 안드로이드 6.0 (혹은 5.0) 이상부터는 개인정보보호 정책이 강화되어서
        // 민감한 API들에 대한 퍼미션을 무조건 앱 사용 시 다시 띄워서
        // 동의를 받아야 사용이 가능하도록 변경되었다.
        // 이런 정책을 피하는 방법은 하위 버전으로 컴파일하여 서비스하면 된다.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        } else {
            // you have the permission
        }
    }

    // 답변과 상관없이 응답하면 불러오는 부분
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                } else {
                    // permission denied
                }
                return;
            }

        }
    }

    // 세션이 열리면 호출되는 메소드를 재정의하여 사용자 정보를 포함하여 가져온다.

    @Override
    protected void redirectSignupActivity() {
        super.redirectSignupActivity();
        requestAccessTokenInfo();
    }

    // 프로필 정보 가져오기
    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                onSignUp(-4);
                //redirectLoginActivity();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                // redirectLoginActivity();
                onSignUp(-5);
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                Log.i("KAKA", "UserProfile:" + userProfile);
                // redirectMainActivity();
                nickname = userProfile.getNickname();
                profile = userProfile.getThumbnailImagePath();
                onSignUp(1);
            }

            @Override
            public void onNotSignedUp() {
                //  showSignup();
                onSignUp(-6);
            }
        });
    }

    private void requestAccessTokenInfo() {
        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                // redirectLoginActivity(self);
                onSignUp(-1);
            }

            @Override
            public void onNotSignedUp() {
                // not happened
                onSignUp(-2);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failed to get access token info. msg=" + errorResult);
                onSignUp(-3);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Logger.d("this access token is for userId=" + userId);
                kakaoID = "" + userId;

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");

                //액세스 ID를 획득 후 개인정보를 획득한다.
                requestMe();
            }
        });
    }

    // 모든 정보를 획득하였다 -> 회원가입 진행
    public void onSignUp(int type) {
        if (type < 0) {
            Toast.makeText(this,
                    "인증 오류가 발생하였습니다. 잠시 후 다시 시도해 주시기 바랍니다.",
                    Toast.LENGTH_SHORT);
            return;
        } else {
            // 정상적이다 > 가입 진행!
            // req   : { header:{code:SU}, body:{nickname:xx, ... profile:xxx}}
            // 1. 파라미터 세팅 => Json 형식으로
            ReqSignUp json = new ReqSignUp();
            ReqHeader header = new ReqHeader();
            Member body = new Member(
                    0,
                    nickname,
                    kakaoID,
                    facebookID,
                    email,
                    password,
                    //////////////////////////////
                    U.getInstance().getPhoneNumber(this),
                    "eOPgZa8dy6E:APA91bGC4OCz8A7wheYWt0IGEfdd-cMC0i2KekV-1eBTEJcu8DVv_h5BYGDZHR9FzpLCuLgfudhRD6KbD3BOXnklnB6D2wQqpZ9CSrHR1rufTyALSq8Umio0TNGTQfFI-GDpmS7Nt2Vq",
                    U.getInstance().getUUID(this),
                    U.getInstance().getOSVersion() + "",
                    U.getInstance().getModel(),
                    null,
                    null,
                    0,
                    1,
                    profile
            );

            header.setCode("SU");
            json.setHeader(header);
            json.setBody(body);

            // 2. 요청객체 준비
            try {
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                "http://52.78.30.74:3000/signup",
                                new JSONObject(new Gson().toJson(json)),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // 4. 응답처리
                                        Log.i("RES:signup", response.toString());
                                        onSignUpThen(response.toString());
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
    }

    public void onSignUpThen(String json) {
        // 1. 파싱
        ResSignUp rsu = new Gson().fromJson(json, ResSignUp.class);
        // 2. 실패 처리
        if (rsu.code < 0) {
            Toast.makeText(this, rsu.getMsg(), Toast.LENGTH_SHORT);
            return;
        }
        // 3. 성공 처리 -> 로그인 진행
        onLogin();
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
            Log.i("try","success");
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
                                    Log.i("RES:login", response.toString());
                                    Toast.makeText(SignUpActivity.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUpActivity.this, resLogin.getHeader().getMsg(), Toast.LENGTH_SHORT).show();
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