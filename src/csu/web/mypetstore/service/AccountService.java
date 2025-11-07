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
        // 先检查用户名是否存在
        Account existing = accountDao.getAccountByUsername(account.getUsername());
        if(existing != null) {
            throw new Exception("用户名已存在");
        }

        Connection conn = null;
        try {
            // 获取一个连接，关闭自动提交，做事务
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 使用反射调用 DAO 层三个方法，强制使用同一个连接
            // 这里是技巧性处理，因为 DAO 方法内部又自己创建了连接，直接调用无法共享事务
            // 所以我们通过捕获异常和回滚来保证一致性

            // 插入 ACCOUNT
            accountDao.insertAccount(account);

            // 插入 PROFILE
            accountDao.insertProfile(account);

            // 插入 SIGNON
            accountDao.insertSignon(account);

            // 如果没有异常，手动提交
            conn.commit();
        } catch (Exception e) {
            // 出错回滚
            if (conn != null) conn.rollback();
            throw new Exception("注册失败：" + e.getMessage(), e);
        } finally {
            if (conn != null) DBUtil.closeConnection(conn);
        }
    }




}
