package com.example.goalkeeper;

public class AppUtils {

    // Request Codes
    final static int ACK_REQUEST = 100;
    final static int READ_REQUEST = 101;
    final static int WRITE_REQUEST = 102;
    final static int WEB_WRITE_REQUEST = 103;
    final static int WEB_READ_REQUEST = 104;
    final static int NOTIFICATION_READ_REQUEST = 105;
    final static int NOTIFICATION_WRITE_REQUEST = 106;
    final static int LAUNCH_ACTIVITY_REQUEST = 107;

    // Reply Codes
    final static int ACK = 200;
    final static int NACK = 201;
    final static int READ_OK_RESULT_INCLUDED = 202;
    final static int READ_BAD_NO_DATA = 203;


    final static int DB_WRITE_OK = 206;
    final static int DB_WRITE_FAILED = 207;
    final static int WEB_WRITE_OK = 208;
    final static int WEB_WRITE_BAD = 209;
    final static int WEB_READ_OK = 210;
    final static int WEB_READ_BAD = 211;
    final static int NOTIFICATION_WRITE_OK = 212;
    final static int NOTIFICATION_WRITE_BAD = 213;

    //Source Codes
    final static int MAIN_ACTIVITY = 301;
    final static int APP_IO = 302;
    final static int APP_WEB_CONNECT = 303;
    final static int APP_NOTIFICATION = 304;
    final static int APP_SETTINGS = 305;
    final static int APP_DAY = 306;
    final static int APP_WEEK = 307;
    final static int APP_MONTH = 308;
    final static int APP_GOAL = 309;
    final static int APP_GOALS_DAY = 310;
    final static int APP_GOALS_WEEK = 311;
    final static int APP_GOALS_MONTH = 312;
    final static int APP_GOALS_YEAR = 313;
    final static int APP_GOALS_MASTER = 314;
    final static int APP_GOALS_BUILDER = 315;
    final static int APP_TASK_VIEW = 316;
    final static int APP_TASK_CREATE = 317;
    final static int APP_PLANNER_SERVICE = 318;
    final static int APP_ADD_EVENT = 319;
}
