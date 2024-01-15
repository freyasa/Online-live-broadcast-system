package com.niit.onlivestream.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName room_log
 */
@TableName(value ="room_log")
@Data
public class RoomLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Date createtime;

    /**
     * 外键，关联Room_info的那个房间推流号
     */
    private Integer roomforeignid;

    /**
     * 简介
     */
    private String profile;

    /**
     * 房间名
     */
    private String name;

    /**
     * 
     */
    private Date stoptime;

    /**
     * 
     */
    private Integer totalstar;

    /**
     * 
     */
    private Integer totalpresentvalues;

    /**
     * 分区
     */
    private Integer partitionid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RoomLog other = (RoomLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getRoomforeignid() == null ? other.getRoomforeignid() == null : this.getRoomforeignid().equals(other.getRoomforeignid()))
            && (this.getProfile() == null ? other.getProfile() == null : this.getProfile().equals(other.getProfile()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getStoptime() == null ? other.getStoptime() == null : this.getStoptime().equals(other.getStoptime()))
            && (this.getTotalstar() == null ? other.getTotalstar() == null : this.getTotalstar().equals(other.getTotalstar()))
            && (this.getTotalpresentvalues() == null ? other.getTotalpresentvalues() == null : this.getTotalpresentvalues().equals(other.getTotalpresentvalues()))
            && (this.getPartitionid() == null ? other.getPartitionid() == null : this.getPartitionid().equals(other.getPartitionid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getRoomforeignid() == null) ? 0 : getRoomforeignid().hashCode());
        result = prime * result + ((getProfile() == null) ? 0 : getProfile().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStoptime() == null) ? 0 : getStoptime().hashCode());
        result = prime * result + ((getTotalstar() == null) ? 0 : getTotalstar().hashCode());
        result = prime * result + ((getTotalpresentvalues() == null) ? 0 : getTotalpresentvalues().hashCode());
        result = prime * result + ((getPartitionid() == null) ? 0 : getPartitionid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createtime=").append(createtime);
        sb.append(", roomforeignid=").append(roomforeignid);
        sb.append(", profile=").append(profile);
        sb.append(", name=").append(name);
        sb.append(", stoptime=").append(stoptime);
        sb.append(", totalstar=").append(totalstar);
        sb.append(", totalpresentvalues=").append(totalpresentvalues);
        sb.append(", partitionid=").append(partitionid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}