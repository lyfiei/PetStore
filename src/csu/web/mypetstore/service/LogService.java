package csu.web.mypetstore.service;


import csu.web.mypetstore.domain.LogData;
import csu.web.mypetstore.persistence.LogDao;
import csu.web.mypetstore.persistence.impl.LogDaoImpl;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class LogService {

    private final LogDao logDao;
    private static final ExecutorService logExecutor =
            Executors.newFixedThreadPool(5, r -> new Thread(r, "Log-Writer-Thread"));

    public LogService() {
        this.logDao = new LogDaoImpl(); // 可改成依赖注入
    }

    /**
     * 异步写日志入口
     */
    private void submitLog(LogData logData) {
        logExecutor.submit(() -> {
            try {
                // 调用原来的 recordAction
                recordAction(logData);
            } catch (Exception e) {
                System.err.println("Async Log Failed: " + e.getMessage() + " | " + logData);
            }
        });


    }

    /**
     * 核心方法：记录用户行为日志（同步，供线程池调用）
     */
    private boolean recordAction(LogData logData) {
        if (logData.getActionType() == null || logData.getSessionId() == null) {
            System.err.println("Log Error: Missing required fields: " + logData);
            return false;
        }
        return logDao.insertLog(logData);
    }

    // ================== 异步快捷方法 ==================

    public void logAddToCart(String sessionId, String userId, String itemId, int quantity) {
        LogData log = LogData.create(sessionId, "ADD_TO_CART")
                .withUserId(userId)
                .withTarget(userId,"ITEM")
                .withDetails(Map.of("quantity", quantity));
        submitLog(log);
    }

    public void logAddToCart(String sessionId, String itemId, int quantity) {
        LogData log = LogData.create(sessionId, "ADD_TO_CART")
                .withTarget(itemId, "ITEM")
                .withDetails(Map.of("quantity", quantity));
        submitLog(log);
    }

    public void logPlaceOrder(String sessionId, String userId, String orderId, double amount) {
        LogData log = LogData.create(sessionId, "PLACE_ORDER")
                .withUserId(userId)
                .withTarget(orderId, "ORDER")
                .withDetails(Map.of("amount", amount));
        submitLog(log);
    }

    public void logClickButton(String sessionId, String userId, String buttonId, String page) {
        LogData log = LogData.create(sessionId, "CLICK_BUTTON")
                .withUserId(userId)
                .withTarget(null, "BUTTON")
                .withDetails(Map.of("buttonId", buttonId, "page", page));
        submitLog(log);
    }

    public void logClickButton(String sessionId, String buttonId, String page) {
        LogData log = LogData.create(sessionId, "CLICK_BUTTON")
                .withTarget(null, "BUTTON")
                .withDetails(Map.of("buttonId", buttonId, "page", page));
        submitLog(log);
    }

    public void removeFromCart(String sessionId, String userId, String itemId) {
        LogData log = LogData.create(sessionId, "REMOVE_FROM_CART")
                .withUserId(userId)
                .withTarget(itemId, "ITEM");
        submitLog(log);
    }

    public void removeFromCart(String sessionId, String itemId) {
        LogData log = LogData.create(sessionId, "REMOVE_FROM_CART")
                .withTarget(itemId, "ITEM");
        submitLog(log);
    }

    public void search(String sessionId, String userId, String searchText) {
        LogData log = LogData.create(sessionId, "SEARCH")
                .withUserId(userId)
                .withTarget(null, "TEXT")
                .withDetails(Map.of("searchText", searchText));
        submitLog(log);
    }

    public void search(String sessionId, String searchText) {
        LogData log = LogData.create(sessionId, "SEARCH")
                .withTarget(null, "TEXT")
                .withDetails(Map.of("searchText", searchText));
        submitLog(log);
    }

    public void logLogin(String sessionId, String userId) {
        LogData log = LogData.create(sessionId, "LOGIN")
                .withUserId(userId);
        submitLog(log);
    }

    public void logLogout(String sessionId, String userId) {
        LogData log = LogData.create(sessionId, "LOGOUT")
                .withUserId(userId);
        submitLog(log);
    }

    public void updateCart(String sessionId, String userId, int quantity) {
        LogData log = LogData.create(sessionId, "UPDATE_CART")
                .withUserId(userId)
                .withDetails(Map.of("quantity", quantity));
        submitLog(log);
    }

    public void updateCart(String sessionId, int quantity) {
        LogData log = LogData.create(sessionId, "UPDATE_CART")
                .withDetails(Map.of("quantity", quantity));
        submitLog(log);
    }

    public void register(String sessionId, String userId) {
        LogData log = LogData.create(sessionId, "REGISTER")
                .withUserId(userId);
        submitLog(log);
    }

    // ================== 可在应用关闭时安全关闭线程池 ==================
    public static void shutdown() {
        logExecutor.shutdown();
    }
}