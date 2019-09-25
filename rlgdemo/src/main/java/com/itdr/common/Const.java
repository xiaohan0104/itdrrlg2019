package com.itdr.common;

public class Const {

    public static final String LOGINUSER = "login_user";
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String AUTOLOGINTOKEN = "autoLoginToken";
    private static final String JESSESSIONID_COOKIE = "JESSIONID_COOKIE";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String LOGOUT = "退出成功";
    public static final String NOEONR = "没有该用户";
    public static final String FEIFACANSHU  = "非法参数";
    public static final String LIMTQUANTYSUCCESS  = "LIMT_NUM_SUCCESS";
    public static final String LIMTQUANTYFATLED  = "LIMT_NUM_FATLED";
    public static final String NOTHING = "不存在";


    /*
    * 成功时通用状态码*/
    public static final int SUCESS=200;

    /**
     * 失败时通用状态码
     * */
    public static final int ERROR=100;
//    是否选中
    public static final int UNCHECK=0;
    public static final int CHECK=1;

    public enum UsersEnum{
        NEED_LOGIN(2,"需要登陆"),
        ON_LOGIN(101,"用户未登录");

        //状态信息
        private int code;
        private String desc;

        private UsersEnum(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
