package com.douding.tuoke.bean.list;

/**
 * Created by Sincerly on 2017/5/15.
 */

public class ResponseBean<T>{

    /**
     * result : 0
     * describing : 修改成功
     */

    private String result;
    private String describing;

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
}
