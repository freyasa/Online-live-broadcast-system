package com.niit.entity;

import java.util.Date;

public class User_view_log {
    private int id;
    private String uuid;
    private Date startViewTime;
    private Date endViewTime;
    private int stayTime; //单位：秒
    private int room_log_id;//访问的哪场直播
}
