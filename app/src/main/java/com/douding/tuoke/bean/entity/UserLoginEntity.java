package com.douding.tuoke.bean.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sincerly on 2017/5/15.
 */

@Entity
public class UserLoginEntity {
    @Id
    private Long id;
    private String name;
    private String pwd;
    private String userId;
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1694044997)
    public UserLoginEntity(Long id, String name, String pwd, String userId) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.userId = userId;
    }
    @Generated(hash = 1372112009)
    public UserLoginEntity() {
    }

}
