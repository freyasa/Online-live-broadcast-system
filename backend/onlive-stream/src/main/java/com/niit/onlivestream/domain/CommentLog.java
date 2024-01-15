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
 * @TableName comment_log
 */
@TableName(value ="comment_log")
@Data
public class CommentLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer commentid;

    /**
     * 
     */
    private String uuid;

    /**
     * 
     */
    private String comment;

    /**
     * 
     */
    private Integer roomlogid;

    /**
     * 
     */
    private Date createtime;

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
        CommentLog other = (CommentLog) that;
        return (this.getCommentid() == null ? other.getCommentid() == null : this.getCommentid().equals(other.getCommentid()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getRoomlogid() == null ? other.getRoomlogid() == null : this.getRoomlogid().equals(other.getRoomlogid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentid() == null) ? 0 : getCommentid().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getRoomlogid() == null) ? 0 : getRoomlogid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentid=").append(commentid);
        sb.append(", uuid=").append(uuid);
        sb.append(", comment=").append(comment);
        sb.append(", roomlogid=").append(roomlogid);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}