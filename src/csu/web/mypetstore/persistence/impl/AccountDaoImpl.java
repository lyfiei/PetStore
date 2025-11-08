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



    private static final String GET_ACCOUNT_BY_USERNAME_AND_PASSWORD = "SELECT\n" +
            "    SIGNON.USERNAME,\n" +
            "    ACCOUNT.EMAIL,\n" +
            "    ACCOUNT.FIRSTNAME,\n" +
            "    ACCOUNT.LASTNAME,\n" +
            "    ACCOUNT.STATUS,\n" +
            "    ACCOUNT.ADDR1 AS address1,\n" +
            "    ACCOUNT.ADDR2 AS address2,\n" +
            "    ACCOUNT.CITY,\n" +
            "    ACCOUNT.STATE,\n" +
            "    ACCOUNT.ZIP,\n" +
            "    ACCOUNT.COUNTRY,\n" +
            "    ACCOUNT.PHONE,\n" +
            "    PROFILE.LANGPREF AS languagePreference,\n" +
            "    PROFILE.FAVCATEGORY AS favouriteCategoryId,\n" +
            "    PROFILE.MYLISTOPT AS listOption,\n" +
            "    PROFILE.BANNEROPT AS bannerOption,\n" +
            "    BANNERDATA.BANNERNAME\n" +
            "    FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA\n" +
            "    WHERE ACCOUNT.USERID = ?\n" +
            "    AND SIGNON.PASSWORD = ?\n" +
            "    AND SIGNON.USERNAME = ACCOUNT.USERID\n" +
            "    AND PROFILE.USERID = ACCOUNT.USERID\n" +
            "    AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY";

    @Override
    public Account getAccountByUsernameAndPassword(Account account) {
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_USERNAME_AND_PASSWORD);
            statement.setString(1,account.getUsername());
            statement.setString(2,account.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){

                account.setUsername(resultSet.getString(1));
                account.setEmail(resultSet.getString(2));
                account.setFirstName(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setStatus(resultSet.getString(5));
                account.setAddress1(resultSet.getString(6));
                account.setAddress2(resultSet.getString(7));
                account.setCity(resultSet.getString(8));
                account.setState(resultSet.getString(9));
                account.setZip(resultSet.getString(10));
                account.setCountry(resultSet.getString(11));
                account.setPhone(resultSet.getString(12));
                account.setLanguagePreference(resultSet.getString(13));
                account.setFavouriteCategoryId(resultSet.getString(14));
                account.setListOption(resultSet.getBoolean(15));
                account.setBannerOption(resultSet.getBoolean(16));
                account.setBannerName(resultSet.getString(17));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入账户失败", e);
        }
    }

    @Override
    public void insertProfile(Account account){
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
        }catch (Exception e) {
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
        }catch (Exception e) {
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



}
