package com.example.tacademy.login_signup_module;

/**
 * 프로그램 상수
 */

public class E {
    // 각종키
    public static class KEY {
        public static final String USERINFO = "userInfo";
        // 메인 광고 획득 키
        public static final String MAIN_AD = "app_init_ad_url";
        // 저장소 메인 키
        public static final String STORAGE_KEY = "pref";
        // 메인메뉴 광고 오늘만 보겠냐는 확인키
        public static final String TODAY_OK_KEY = "today_ok";
        // 메인 메뉴 광고 오늘만 보겠냔 것의 체크 시 저장되는 시간 포맷
        public static final String TODAY_SAVE_DATE_FORMAT = "yyyy-MM-dd";
        // 긴급 공지 키
        public static final String EMERGENCY_KEY = "EMERGENCY_MSG";
        // 리얼 도메인
        public static final String REAL_DOMAIN_KEY = "REAL_DOMAIN";
        // 테스트 도메인
        public static final String TEST_DOMAIN_KEY = "TEST_DOMAIN";
        // 테스트인지 아닌지
        public static final String TEST_MODE_KEY = "TEST_MODE";
    }
    // 전문 API
    public static class NET {
        public static String REAL_DOMAIN;
        public static String TEST_DOMAIN;
        public static String USE_DOMAIN;
        public static boolean TEST_MODE;

        // epl의 순위표를 가져온다.
        public static final String API_GET_EPLLIST = "/eplSelect";
    }
        // 서버 주소 => firebase로 대체
}
