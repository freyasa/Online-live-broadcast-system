package com.niit.entity;

import java.util.Date;

//redis
public class Room_log {
    //开始直播放的
    private int id;
    private Date createTime;
    private int roomForeignId;//外键，关联Room_info的那个房间推流号
    private String profile;//简介

    //结束直播放的
    private String name;//房间名
    private Date stopTime;
    private int totalStar;
    private int totalPresentValues;
    private int partitionId;//分区
}