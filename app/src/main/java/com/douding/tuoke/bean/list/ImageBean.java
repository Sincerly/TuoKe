package com.douding.tuoke.bean.list;

/**
 * Created by Sincerly on 2017/4/26.
 */

public class ImageBean {
    private String content;
    private int imageId;

    public ImageBean() {
    }

    public ImageBean(String content, int imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
