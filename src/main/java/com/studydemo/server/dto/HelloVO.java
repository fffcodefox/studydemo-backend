package com.studydemo.server.dto;

/**
 * 打招呼接口返回视图对象（View Object）。
 */
public class HelloVO {

    private String name;
    private String message;
    private Long visits;
    private Long dbCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public Long getDbCount() {
        return dbCount;
    }

    public void setDbCount(Long dbCount) {
        this.dbCount = dbCount;
    }
}
