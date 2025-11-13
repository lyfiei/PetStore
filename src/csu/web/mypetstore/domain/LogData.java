package csu.web.mypetstore.domain;

import com.fasterxml.jackson.databind.ObjectMapper;


public class LogData {
    private String userId;
    private String sessionId;
    private String actionType;
    private String targetType;
    private String targetId;
    private Object details; // 可为 Map 或自定义对象



    // Getter / Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public Object getDetails() { return details; }
    public void setDetails(Object details) { this.details = details; }


    /**
     * 静态工厂方法：创建基础日志对象
     */
    public static LogData create(String sessionId, String actionType) {
        LogData logData = new LogData();
        logData.setSessionId(sessionId);
        logData.setActionType(actionType);
        logData.setTargetType("system"); // 默认 targetType，可根据情况改
        return logData;
    }

    /**
     * 链式设置 userId
     */
    public LogData withUserId(String userId) {
        this.setUserId(userId);
        return this;
    }

    /**
     * 链式设置 targetId 和 targetType
     */
    public LogData withTarget(String targetId, String targetType) {
        this.setTargetId(targetId);
        this.setTargetType(targetType);
        return this;
    }

    /**
     * 链式设置 details
     */
    public LogData withDetails(Object details) {
        this.setDetails(details);
        return this;
    }

    /**
     * 获取 details 的 JSON 字符串
     */
    public String getDetailsJson() {
        if (details == null) return null;
        try {
            return new ObjectMapper().writeValueAsString(details);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "LogData{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", actionType='" + actionType + '\'' +
                ", targetType='" + targetType + '\'' +
                ", targetId=" + targetId +
                ", details=" + details +
                '}';
    }
}