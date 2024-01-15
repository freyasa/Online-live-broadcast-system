package com.niit.onlivestream.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName room_info
 */
@TableName(value ="room_info")
@Data
public class RoomInfo implements Serializable {
    /**
     * 直播间号
     */
    @TableId(type = IdType.AUTO)
    private Integer liveid;

    /**
     * 名字
     */
    private String roomname;

    /**
     * 拥有者的uuid
     */
    private String uuid;

    /**
     * 分区
     */
    private Integer partitionid;

    /**
     * 简介
     */
    private String profile;

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
        RoomInfo other = (RoomInfo) that;
        return (this.getLiveid() == null ? other.getLiveid() == null : this.getLiveid().equals(other.getLiveid()))
            && (this.getRoomname() == null ? other.getRoomname() == null : this.getRoomname().equals(other.getRoomname()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getPartitionid() == null ? other.getPartitionid() == null : this.getPartitionid().equals(other.getPartitionid()))
            && (this.getProfile() == null ? other.getProfile() == null : this.getProfile().equals(other.getProfile()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLiveid() == null) ? 0 : getLiveid().hashCode());
        result = prime * result + ((getRoomname() == null) ? 0 : getRoomname().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getPartitionid() == null) ? 0 : getPartitionid().hashCode());
        result = prime * result + ((getProfile() == null) ? 0 : getProfile().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", liveid=").append(liveid);
        sb.append(", roomname=").append(roomname);
        sb.append(", uuid=").append(uuid);
        sb.append(", partitionid=").append(partitionid);
        sb.append(", profile=").append(profile);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}