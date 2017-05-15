package com.douding.tuoke.bean.list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sincerly on 2017/5/15.
 */
@Entity
public class LoginBean {

    /**
     * result : 0
     * describing : 登录成功
     * user_ID : 1
     */
    @Id
    private Long id;
    private String result;
    private String describing;
    private String user_ID;

    @Generated(hash = 244023855)
    public LoginBean(Long id, String result, String describing, String user_ID) {
        this.id = id;
        this.result = result;
        this.describing = describing;
        this.user_ID = user_ID;
    }

    @Generated(hash = 1112702939)
    public LoginBean() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescribing() {
        return describing;
    }

    public void setDescribing(String describing) {
        this.describing = describing;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
