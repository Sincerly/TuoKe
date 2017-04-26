package com.douding.tuoke.bean.list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sincerly on 2017/4/24.
 */
@Entity
public class MessageBean {

    @Id
    private Long id;
    private String image;
    private String name;
    private String time;
    private String content;
    private String object;
    private String people;//发送人
    private int totalPeople;
    private int sendNum;//

    @Override
    public String toString() {
        return "MessageBean{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", object='" + object + '\'' +
                ", people='" + people + '\'' +
                ", totalPeople=" + totalPeople +
                ", sendNum=" + sendNum +
                '}';
    }

    public MessageBean() {
    }

    public MessageBean(String image, String name, String time, String content, String object, String people, int totalPeople, int sendNum) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.content = content;
        this.object = object;
        this.people = people;
        this.totalPeople = totalPeople;
        this.sendNum = sendNum;
    }

    @Generated(hash = 2126076646)
    public MessageBean(Long id, String image, String name, String time, String content, String object, String people, int totalPeople,
            int sendNum) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.time = time;
        this.content = content;
        this.object = object;
        this.people = people;
        this.totalPeople = totalPeople;
        this.sendNum = sendNum;
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

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
