package com.bitshift.saams.helper;

import java.util.ArrayList;
import java.util.HashMap;

public class Constant {
    //MODIFICATION PART
    public static final String MAINBASEUrl = "http://student.saams.org/"; //Admin panel url
    //public static final String MAINBASEUrl = "http://192.168.225.106/shivanshcs/";

    //set your jwt secret key here...key must same in PHP and Android
    public static final String JWT_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI3NDEwNTU2MTMyIiwibmFtZSI6IkhhbnVtYW50IE11bGUiLCJpYXQiOjE1MTYyMzkwMjJ9.9LA8H5RtiBDdBcsvHXq80mYyrwsoR6EkHtKhUOT-3N8";


    public static final int LOAD_ITEM_LIMIT = 200; //Load items limit in listing ,Maximum load item once
    public static final int LOAD_ITEM_LIMIT2 = 10;
    //MODIFICATION PART END

    public static final String BaseUrl = MAINBASEUrl + "api/";

    //Do not change anything in this link**************************************************
    public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
    public static final String PLAY_STORE_RATE_US_LINK = "market://details?id=";

    //*************************************************************************************

    //**********APIS**********
    public static final String FAQ_URL = BaseUrl + "faq";
    public static final String RegisterUrl = BaseUrl + "user-registration.php";
    public static final String LoginUrl = BaseUrl + "auth/login";
    public static final String GET_STAFFS_URL = BaseUrl + "staff/";;
    public static final String GET_EVENTS_URL = BaseUrl + "events/";;
    public static final String GET_NOTICEBOARD_URL = BaseUrl + "noticeboard/";;
    public static final String GET_TIMETABLE_URL = BaseUrl + "timetable/";;
    public static final String GET_REPORTCARD_URL = BaseUrl + "reportcard/";
    public static final String GET_GALLERY_URL = BaseUrl + "gallery/";
    public static final String GET_FEESDETAILS_URL = BaseUrl + "fees/";
    public static final String GET_ATTENDANCE_URL = BaseUrl + "attendance/";
    public static final String GET_PROFILE_URL = BaseUrl + "profile/";
    public static final String READ_NOTICE_URL = BaseUrl + "readnotice/";
    public static final String APP_DATA_URL = BaseUrl + "appdata";
    public static final String SETTING_URL = BaseUrl + "settings/timezone";
     public static final String REMOVE_FCM_URL = BaseUrl + "remove-fcm-id.php";
     public static final String REGISTER_DEVICE_URL = BaseUrl + "devices";
    public static final String PREVENT_MULTIPLE_LOGIN_URL = BaseUrl + "prevent_multiple_login.php";
    public static final String GET_HOME_SLIDER_IMAGES_URL = BaseUrl + "settings/slider";
    public static final String REGISTER_QUE_URL = BaseUrl + "register_que.php";
    public static final String GET_NOTIFICATON_URL = BaseUrl + "notifications";
    public static final String SEND_ILLEGAL_DEIVICE_NOTIF_TO_ADMIN_URL = BaseUrl + "send_illegal_device_notification.php";
    //**************parameters***************

     public static final String AccessKey = "accesskey";
    public static final String AccessKeyVal = "90336";
    public static final String PROFILE = "profile";
    public static final String UPLOAD_PROFILE = "upload_profile";
    public static final String GetVal = "1";
    public static final String GET_PRIVACY = "get_privacy";
    public static final String GET_TERMS = "get_terms";
      public static final String GET_CONTACT = "get_contact";
    public static final String GET_ABOUT_US = "get_about_us";
   public static final String GET_NOTIFICATIONS = "get-notifications";
    public static final String REMOVE_FCM_ID = "remove_fcm_id";
    public static final String URL = "url";
     public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY = "country";
    public static final String IS_DEFAULT = "is_default";
    public static final String UNREAD_NOTIFICATION_COUNT = "unread_notification_count";
    public static final String UNREAD_WALLET_COUNT = "unread_wallet_count";
    public static final String UNREAD_TRANSACTION_COUNT = "unread_transaction_count";
    public static final String CITY_ID = "city_id";
    public static final String CITY = "city";
    public static final String AREA_ID = "area_id";
    public static final String REFERRAL_CODE = "referral_code";
   public static final String DELETE_ORDER = "delete_order";
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String TYPE = "type";
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String REGISTER = "register";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String FCM_ID = "fcm_id";
    public static final String IS_USER_LOGIN = "is_user_login";
    public static final String PINCODE = "pincode";
    public static final String STATE = "state";
    public static final String ERROR = "error";
    public static final String GET_TIMEZONE = "get_timezone";
    public static final String VERIFY_USER = "verify-user";
    public static final String USER_ID = "user_id";
    public static final String OTP = "otp";
    public static final String EDIT_PROFILE = "edit-profile";
    public static final String CHANGE_PASSWORD = "change-password";
    public static final String SETTINGS = "settings";
    public static final String GET_SETTINGS = "get_settings";
    public static final String GET_FAQS = "get_faqs";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
     public static final String TOTAL = "total";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_LINE1 = "address_line1";
    public static final String POSTAL_CODE = "postal_code";
    public static final String LANDMARK = "landmark";
    public static final String MESSAGE = "message";
    public static final String AUTHORIZATION = "Authorization";
    public static final String FROM = "from";
    public static final String minimum_version_required = "minimum_version_required";
    public static final String is_version_system_on = "is-version-system-on";
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghjiklmnopqrstuvwxyz";
    public static final String NUMERIC_STRING = "123456789";
    public static final String Contact_Number = "9673967836";
    public static final String GET_HOME_SLIDER_IMAGES ="get_home_slider_images" ;
    public static final String UPDATE_PLOT_URL = BaseUrl + "update_plot.php";;
    public static final String DATA = "data";
    public static final String REGISTER_QUE = "register_que";
    public static final String GET_VARIETY = "get_variety";
    public static final String MAINORG_NAME = "mainorg";
    public static final String SUBORG_NAME = "suborg";
    public static final String SECTION_NAME = "section";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String ADMISSIONS = "admissions";

    public static final CharSequence[] filtervalues = {" All ", " Unread "};
    public static final String ALL = "ALL";
    public static final String UNREAD = "UNREAD";
    public static final String SORT = "sort";


    public static String TOOLBAR_TITLE;

    //**************Constants Values***************
  public static String U_ID = "";
    public static HashMap<String, String> CartValues = new HashMap<>();
    public static int TOTAL_CART_ITEM = 0;
    public static String FRND_CODE = "";
    public static String MERCHANT_ID = "";
    public static String MERCHANT_KEY = "";
    public static String PAYUMONEY_MODE = "";
    public static String MERCHANT_SALT = "";
    public static String illegal_access_on = "illegal_access_on";
    public static String token = "";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
