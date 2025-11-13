package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.LogData;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.LogDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogDaoImpl implements LogDao {

    @Override
    public boolean insertLog(LogData logData) {
        String sql = "INSERT INTO user_action_logs " +
                "(user_id, session_id, action_type, target_type, target_id, action_time, details) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String actionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, logData.getUserId());
            stmt.setString(2, logData.getSessionId());
            stmt.setString(3, logData.getActionType());
            stmt.setString(4, logData.getTargetType() != null ? logData.getTargetType() : "system");
            stmt.setObject(5, logData.getTargetId());
            stmt.setString(6, actionTime);
            stmt.setString(7, logData.getDetailsJson());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Database Write Log Error: " + e.getMessage() + " | " + logData);
            return false;
        }
    }
}