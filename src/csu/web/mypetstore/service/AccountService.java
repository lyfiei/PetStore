package csu.web.mypetstore.service;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.persistence.AccountDao;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.impl.AccountDaoImpl;

import java.sql.Connection;


public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountDao.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) throws Exception {
        System.out.println("开始注册用户: " + account.getUsername());
        // 检查用户名是否存在
        Account existing = accountDao.getAccountByUsername(account.getUsername());
        if (existing != null) {
            throw new Exception("用户名已存在");
        }
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            accountDao.insertAccount(account);
            accountDao.insertProfile(account);
            accountDao.insertSignon(account);

            conn.commit();
            System.out.println("用户注册成功: " + account.getUsername());
        } catch (Exception e) {
            System.out.println("用户注册失败: " + e.getMessage());
            if (conn != null) {
                conn.rollback();
                System.out.println("事务已回滚");
            }
            throw new Exception("注册失败：" + e.getMessage(), e);
        } finally {
            DBUtil.closeConnection(conn);
        }
    }


    public Account getAccountByUsername(String username) {
        if(username == null || username.isEmpty()) return null;
        return accountDao.getAccountByUsername(username);
    }


}
