package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.persistence.AccountDao;
import csu.web.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account getAccountByUsername(String username) {
        return null;
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

    }

    @Override
    public void insertProfile(Account account) {

    }

    @Override
    public void insertSignon(Account account) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void updateProfile(Account account) {

    }

    @Override
    public void updateSignon(Account account) {

    }

    //public static void main(String[] args) {
    //    AccountDao accountDao = new AccountDaoImpl();
    //    Account account = new Account();
    //    account.setUsername("j2ee");
    //    account.setPassword("j2ee");
    //    Account result = accountDao.getAccountByUsernameAndPassword(account);
    //    System.out.println("success");
    //}
}
