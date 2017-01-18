package com.example.tacademy.login_signup_module;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.UUID;

/**
 * Created by Tacademy on 2017-01-17.
 */
public class U {
    private static U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }
    // 전화번호 획득
    public String getPhoneNumber(Context context){
        // 애뮬레이터 혹은 테스트 모드에서 전번을 임의로 세팅해서 리턴하게도 가능함.
        try {
            String tel = null;
            TelephonyManager tm =
                    (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            tel = tm.getLine1Number();   // 전화번호 획득
            // 미개통 단말, 공기계
            if(tel == null){
              return "";
            }
            // +82
            if(tel.indexOf("+82")>=0){
                tel = tel.replace("+82", "0");
            }
            // 01x00000000:11 01x0000000:10
            // ?1x00000000
            tel = tel.substring(tel.length()-10, tel.length());
            tel = "0" + tel;
            return tel;
        } catch (Exception e) {
            return "";
        }
    }
    // UUID 획득 : 앱 삭제 후 다시 설치시 회원가입을 또 안받게 하는 단말기 고유값
    // 앱을 구동해서 회원가입 정보가 없다면, UUID를 조회하여 DB에 존재하면 회원정보를 살려서 바로 로그인 처리
    public String getUUID(Context context){
        // 단말기 정보가 나오지 않을 경우
        // 중복되지 않는 임의의 값으로 조합한다. (라이브러리 활용)
        TelephonyManager tm =
                (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String tm_id = tm.getDeviceId();
        tm_id = tm_id==null ? "111111" : tm_id;
        String tm_serial = tm.getSimSerialNumber();
        tm_serial = tm_serial == null ? "000000" : tm_serial;
        String androidID =
        android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
                );
        androidID = androidID == null? "aaaaaa" : androidID;
        UUID uuid =
                new UUID(androidID.hashCode(),
                        (long)tm_id.hashCode()<<32 | tm_serial.hashCode());
        return uuid.toString();
    }
    // 단말기 모델 정보
    public String getModel(){
        return Build.MODEL;
    }
    // OS 버전
    public int getOSVersion(){
        return Build.VERSION.SDK_INT;
    }
    // android 특정 버전에 맞춰서 분기 처리를 하고 싶다면
    public boolean isSupport(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 이하 적용
            return false;
        } else{
            // 5.0 부터 적용
            return true;
        }
    }

    private RequestQueue requestQueue;
    public RequestQueue getRequestQueue(Context context) {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    // 회원정보를 로그인한 후에 전역적으로 접근하기 위해 생성한 객체
    Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    //누구 누구 님 반갑습니다.
    public void showHello(Context context){
        if(member == null){
            return;
        }
        Toast.makeText(context, member.getNickname()+"님 반갑습니다.", Toast.LENGTH_SHORT).show();
    }
    //로그인 상태 확인
    public boolean isLoginState(){
        return member == null ? false : true;
    }

    // 자동 로그인 여부
    public boolean isAutoLogin(Context context){
        return StorageHelper.getInstance().getBoolean(context, "AUTOLOGIN");
    }

    //로그아웃
    public void logout(Context context){
        // 1. 카카오 로그아웃 진행 (세션닫음)
        // 2. 페이스북 로그아웃 진행 (세션닫음)
        // 3. member 객체 null 처리
        // 4. AUTOLOGIN false 세팅
        // 5. 선택; 저장된 USER 데이터 삭제
        // 6. 로그아웃 후 가야하는 화면으로 이동
    }
}