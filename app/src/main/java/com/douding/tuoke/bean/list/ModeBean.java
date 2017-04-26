package com.douding.tuoke.bean.list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sincerly on 2017/4/24.
 */

@Entity
public class ModeBean {
    @Id
    private Long id;
    private String image;
    private String name;
    private String time;
    private String content;
    private String object;

    public ModeBean() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public ModeBean(String image, String name, String time, String content, String object) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.content = content;
        this.object = object;
    }

    @Generated(hash = 378876400)
    public ModeBean(Long id, String image, String name, String time, String content,
            String object) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.time = time;
        this.content = content;
        this.object = object;
    }

    @Override
    public String toString() {
        return "ModeBean{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", object='" + object + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
