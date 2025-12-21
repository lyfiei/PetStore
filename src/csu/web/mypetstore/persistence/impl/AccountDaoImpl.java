package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.persistence.AccountDao;
import csu.web.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao {


    private static final String GET_ACCOUNT_BY_USERNAME = "SELECT " +
            "ACCOUNT.USERID, " +
            "ACCOUNT.EMAIL, " +
            "ACCOUNT.FIRSTNAME, " +
            "ACCOUNT.LASTNAME, " +
            "ACCOUNT.STATUS, " +
            "ACCOUNT.ADDR1 AS address1, " +
            "ACCOUNT.ADDR2 AS address2, " +
            "ACCOUNT.CITY, " +
            "ACCOUNT.STATE, " +
            "ACCOUNT.ZIP, " +
            "ACCOUNT.COUNTRY, " +
            "ACCOUNT.PHONE, " +
            "PROFILE.LANGPREF AS languagePreference, " +
            "PROFILE.FAVCATEGORY AS favouriteCategoryId, " +
            "PROFILE.MYLISTOPT AS listOption, " +
            "PROFILE.BANNEROPT AS bannerOption " +
            "FROM ACCOUNT " +
            "LEFT JOIN PROFILE ON ACCOUNT.USERID = PROFILE.USERID " +
            "WHERE ACCOUNT.USERID = ?";

    @Override
    public Account getAccountByUsername(String username) {
        Account account = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_USERNAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setUsername(resultSet.getString("USERID"));
                account.setEmail(resultSet.getString("EMAIL"));
                account.setFirstName(resultSet.getString("FIRSTNAME"));
                account.setLastName(resultSet.getString("LASTNAME"));
                account.setStatus(resultSet.getString("STATUS"));
                account.setAddress1(resultSet.getString("address1"));
                account.setAddress2(resultSet.getString("address2"));
                account.setCity(resultSet.getString("CITY"));
                account.setState(resultSet.getString("STATE"));
                account.setZip(resultSet.getString("ZIP"));
                account.setCountry(resultSet.getString("COUNTRY"));
                account.setPhone(resultSet.getString("PHONE"));
                account.setLanguagePreference(resultSet.getString("languagePreference"));
                account.setFavouriteCategoryId(resultSet.getString("favouriteCategoryId"));
                account.setListOption(resultSet.getBoolean("listOption"));
                account.setBannerOption(resultSet.getBoolean("bannerOption"));
            }
            DBUtil.closeResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    private static final String GET_ACCOUNT_BY_USERNAME_AND_PASSWORD = "SELECT " +
            "SIGNON.USERNAME, " +
            "ACCOUNT.EMAIL,ACCOUNT.FIRSTNAME,ACCOUNT.LASTNAME,ACCOUNT.STATUS, " +
            "ACCOUNT.ADDR1 AS address1,ACCOUNT.ADDR2 AS address2, " +
            "ACCOUNT.CITY,ACCOUNT.STATE,ACCOUNT.ZIP,ACCOUNT.COUNTRY,ACCOUNT.PHONE, " +
            "PROFILE.LANGPREF AS languagePreference,PROFILE.FAVCATEGORY AS favouriteCategoryId, " +
            "PROFILE.MYLISTOPT AS listOption,PROFILE.BANNEROPT AS bannerOption, " +
            "BANNERDATA.BANNERNAME  " +
            "FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA  " +
            "WHERE ACCOUNT.USERID = ? AND SIGNON.PASSWORD = ?  " +
            "AND SIGNON.USERNAME = ACCOUNT.USERID  " +
            "AND PROFILE.USERID = ACCOUNT.USERID  " +
            "AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY ";

    @Override
    public Account getAccountByUsernameAndPassword(Account account) {
        Account accountResult = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_BY_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountResult = this.resultSetToAccount(resultSet);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accountResult;
    }

    private Account resultSetToAccount(ResultSet resultSet) throws Exception {
        Account account = new Account();

        // 1. 从 SIGNON 表中获取的字段
        // SIGNON.USERNAME (位于 SELECT 列表的第一项)
        account.setUsername(resultSet.getString("USERNAME"));

        // 2. 从 ACCOUNT 表中获取的字段
        account.setEmail(resultSet.getString("EMAIL"));
        account.setFirstName(resultSet.getString("FIRSTNAME"));
        account.setLastName(resultSet.getString("LASTNAME"));
        account.setStatus(resultSet.getString("STATUS"));

        // 注意：使用 AS 别名来获取地址字段
        account.setAddress1(resultSet.getString("address1")); // 对应 ADDR1 AS address1
        account.setAddress2(resultSet.getString("address2")); // 对应 ADDR2 AS address2

        account.setCity(resultSet.getString("CITY"));
        account.setState(resultSet.getString("STATE"));
        account.setZip(resultSet.getString("ZIP"));
        account.setCountry(resultSet.getString("COUNTRY"));
        account.setPhone(resultSet.getString("PHONE"));

        // 3. 从 PROFILE 表中获取的字段
        account.setLanguagePreference(resultSet.getString("languagePreference"));
        account.setFavouriteCategoryId(resultSet.getString("favouriteCategoryId"));


        // MYLISTOPT AS listOption: getBoolean() 会将 1 转换为 true，0 转换为 false
        account.setListOption(resultSet.getBoolean("listOption"));

        // BANNEROPT AS bannerOption: getBoolean() 会将 1 转换为 true，0 转换为 false
        account.setBannerOption(resultSet.getBoolean("bannerOption"));

        // 4. 从 BANNERDATA 表中获取的字段
        account.setBannerName(resultSet.getString("BANNERNAME"));

        return account;
    }

    @Override
    public void insertAccount(Account account) {
        String sql = "INSERT INTO ACCOUNT (USERID, EMAIL, FIRSTNAME, LASTNAME, STATUS, ADDR1, ADDR2, CITY, STATE, ZIP, COUNTRY, PHONE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("数据库连接状态: " + (conn != null && !conn.isClosed()));
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getFirstName());
            ps.setString(4, account.getLastName());
            ps.setString(5, "OK");
            ps.setString(6, account.getAddress1());
            ps.setString(7, account.getAddress2());
            ps.setString(8, account.getCity());
            ps.setString(9, account.getState());
            ps.setString(10, account.getZip());
            ps.setString(11, account.getCountry());
            ps.setString(12, account.getPhone());
            ps.executeUpdate();
            DBUtil.closeStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入账户失败", e);
        }
    }

    @Override
    public void insertProfile(Account account) {
        String sql = "INSERT INTO PROFILE (USERID, LANGPREF, FAVCATEGORY, MYLISTOPT, BANNEROPT) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("数据库连接状态: " + (conn != null && !conn.isClosed()));
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getLanguagePreference());
            ps.setString(3, account.getFavouriteCategoryId());
            ps.setBoolean(4, account.isListOption());
            ps.setBoolean(5, account.isBannerOption());
            ps.executeUpdate();
            DBUtil.closeStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertSignon(Account account) {
        String sql = "INSERT INTO SIGNON (USERNAME, PASSWORD) VALUES (?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("数据库连接状态: " + (conn != null && !conn.isClosed()));
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            DBUtil.closeStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "UPDATE ACCOUNT SET EMAIL=?, FIRSTNAME=?, LASTNAME=?, ADDR1=?, ADDR2=?, CITY=?, STATE=?, ZIP=?, COUNTRY=?, PHONE=? " +
                "WHERE USERID=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getAddress1());
            ps.setString(5, account.getAddress2());
            ps.setString(6, account.getCity());
            ps.setString(7, account.getState());
            ps.setString(8, account.getZip());
            ps.setString(9, account.getCountry());
            ps.setString(10, account.getPhone());
            ps.setString(11, account.getUsername());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(Account account) {
        String sql = "UPDATE PROFILE SET LANGPREF=?, FAVCATEGORY=?, MYLISTOPT=?, BANNEROPT=? WHERE USERID=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getLanguagePreference());
            ps.setString(2, account.getFavouriteCategoryId());
            ps.setBoolean(3, account.isListOption());
            ps.setBoolean(4, account.isBannerOption());
            ps.setString(5, account.getUsername());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSignon(Account account) {
        String sql = "UPDATE SIGNON SET PASSWORD=? WHERE USERNAME=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getPassword());
            ps.setString(2, account.getUsername());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        AccountDao accountDao = new AccountDaoImpl();
//        Account account = new Account();
//        account.setUsername("j2ee");
//        account.setPassword("j2ee");
//        Account result = accountDao.getAccountByUsernameAndPassword(account);
//        System.out.println("success");
//    }

}

