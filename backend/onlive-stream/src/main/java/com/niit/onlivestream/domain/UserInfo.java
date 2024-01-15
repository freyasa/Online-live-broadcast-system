package com.niit.onlivestream.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 
     */
    @TableId
    private String uuid;

    /**
     * 
     */
    private String useraccount;

    /**
     * 
     */
    private String userpassword;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private Integer userage;

    /**
     * 
     */
    private String useremail;

    /**
     * 
     */
    private Integer userprivilege;

    /**
     * 
     */
    private Date usercreatetime;

    /**
     * 
     */
    private Date userupdatetime;

    /**
     * 
     */
    private Integer userisdelete;

    /**
     * 
     */
    private byte[] usersex;

    /**
     * 
     */
    private byte[] useravatar;

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
        UserInfo other = (UserInfo) that;
        return (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getUseraccount() == null ? other.getUseraccount() == null : this.getUseraccount().equals(other.getUseraccount()))
            && (this.getUserpassword() == null ? other.getUserpassword() == null : this.getUserpassword().equals(other.getUserpassword()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getUserage() == null ? other.getUserage() == null : this.getUserage().equals(other.getUserage()))
            && (this.getUseremail() == null ? other.getUseremail() == null : this.getUseremail().equals(other.getUseremail()))
            && (this.getUserprivilege() == null ? other.getUserprivilege() == null : this.getUserprivilege().equals(other.getUserprivilege()))
            && (this.getUsercreatetime() == null ? other.getUsercreatetime() == null : this.getUsercreatetime().equals(other.getUsercreatetime()))
            && (this.getUserupdatetime() == null ? other.getUserupdatetime() == null : this.getUserupdatetime().equals(other.getUserupdatetime()))
            && (this.getUserisdelete() == null ? other.getUserisdelete() == null : this.getUserisdelete().equals(other.getUserisdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getUseraccount() == null) ? 0 : getUseraccount().hashCode());
        result = prime * result + ((getUserpassword() == null) ? 0 : getUserpassword().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUserage() == null) ? 0 : getUserage().hashCode());
        result = prime * result + ((getUseremail() == null) ? 0 : getUseremail().hashCode());
        result = prime * result + ((getUserprivilege() == null) ? 0 : getUserprivilege().hashCode());
        result = prime * result + ((getUsercreatetime() == null) ? 0 : getUsercreatetime().hashCode());
        result = prime * result + ((getUserupdatetime() == null) ? 0 : getUserupdatetime().hashCode());
        result = prime * result + ((getUserisdelete() == null) ? 0 : getUserisdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", useraccount=").append(useraccount);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", username=").append(username);
        sb.append(", userage=").append(userage);
        sb.append(", useremail=").append(useremail);
        sb.append(", userprivilege=").append(userprivilege);
        sb.append(", usercreatetime=").append(usercreatetime);
        sb.append(", userupdatetime=").append(userupdatetime);
        sb.append(", userisdelete=").append(userisdelete);
        sb.append(", usersex=").append(usersex);
        sb.append(", useravatar=").append(useravatar);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}