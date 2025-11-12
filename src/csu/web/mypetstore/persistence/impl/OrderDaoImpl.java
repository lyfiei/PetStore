package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Order;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static final String GET_ORDERS_BY_USERNAME="SELECT BILLADDR1 AS billAddress1, BILLADDR2 AS billAddress2," +
            " BILLCITY, BILLCOUNTRY, BILLSTATE, BILLTOFIRSTNAME, BILLTOLASTNAME, BILLZIP," +
            " SHIPADDR1 AS shipAddress1, SHIPADDR2 AS shipAddress2, SHIPCITY, SHIPCOUNTRY, SHIPSTATE, SHIPTOFIRSTNAME, SHIPTOLASTNAME, SHIPZIP," +
            " CARDTYPE, COURIER, CREDITCARD, EXPRDATE AS expiryDate, LOCALE, ORDERDATE, ORDERS.ORDERID, TOTALPRICE, USERID AS username, STATUS" +
            " FROM ORDERS, ORDERSTATUS WHERE ORDERS.USERID = ? AND ORDERS.ORDERID = ORDERSTATUS.ORDERID ORDER BY ORDERDATE";
    private static final String GET_ORDER="select  BILLADDR1 AS billAddress1,BILLADDR2 AS billAddress2," +
            " BILLCITY, BILLCOUNTRY,BILLSTATE,BILLTOFIRSTNAME,BILLTOLASTNAME,BILLZIP," +
            " SHIPADDR1 AS shipAddress1,SHIPADDR2 AS shipAddress2,SHIPCITY,SHIPCOUNTRY,SHIPSTATE,SHIPTOFIRSTNAME,SHIPTOLASTNAME,SHIPZIP," +
            " CARDTYPE,COURIER,CREDITCARD,EXPRDATE AS expiryDate,LOCALE, ORDERDATE,ORDERS.ORDERID, TOTALPRICE,USERID AS username,STATUS" +
            " FROM ORDERS, ORDERSTATUS  WHERE ORDERS.ORDERID = ?  AND ORDERS.ORDERID = ORDERSTATUS.ORDERID";
    private static final String INSERT_ORDER="INSERT INTO ORDERS (ORDERID, USERID, ORDERDATE, SHIPADDR1, SHIPADDR2, SHIPCITY, SHIPSTATE," +
            "      SHIPZIP, SHIPCOUNTRY, BILLADDR1, BILLADDR2, BILLCITY, BILLSTATE, BILLZIP, BILLCOUNTRY," +
            "      COURIER, TOTALPRICE, BILLTOFIRSTNAME, BILLTOLASTNAME, SHIPTOFIRSTNAME, SHIPTOLASTNAME," +
            "      CREDITCARD, EXPRDATE, CARDTYPE, LOCALE)" +
            "    VALUES(?, ?, ?, ?, ?, ?, ?," +
            "      ?, ?, ?, ?, ?," +
            "      ?, ?, ?, ?, ?, ?, ?," +
            "      ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_STATUS="INSERT INTO ORDERSTATUS (ORDERID, LINENUM, TIMESTAMP, STATUS)" +
            "    VALUES (?, ?, ?, ?)";

    @Override
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ORDERS_BY_USERNAME)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = resultSetToOrder(rs);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        Order order = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ORDER)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = resultSetToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void insertOrder(Order order) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER)) {

            ps.setInt(1, order.getOrderId());
            ps.setString(2, order.getUsername());
            ps.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            ps.setString(4, order.getShipAddress1());
            ps.setString(5, order.getShipAddress2());
            ps.setString(6, order.getShipCity());
            ps.setString(7, order.getShipState());
            ps.setString(8, order.getShipZip());
            ps.setString(9, order.getShipCountry());
            ps.setString(10, order.getBillAddress1());
            ps.setString(11, order.getBillAddress2());
            ps.setString(12, order.getBillCity());
            ps.setString(13, order.getBillState());
            ps.setString(14, order.getBillZip());
            ps.setString(15, order.getBillCountry());
            ps.setString(16, order.getCourier());
            ps.setBigDecimal(17, order.getTotalPrice());
            ps.setString(18, order.getBillToFirstName());
            ps.setString(19, order.getBillToLastName());
            ps.setString(20, order.getShipToFirstName());
            ps.setString(21, order.getShipToLastName());
            ps.setString(22, order.getCreditCard());
            ps.setString(23, order.getExpiryDate());
            ps.setString(24, order.getCardType());
            ps.setString(25, order.getLocale());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertOrderStatus(Order order) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_STATUS)) {

            ps.setInt(1, order.getOrderId());
            ps.setInt(2, 1); // 状态行号固定为 1（JPetStore 约定逻辑）
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, order.getStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 将查询结果 */
    private Order resultSetToOrder(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getInt("ORDERID"));
        order.setUsername(rs.getString("username"));
        order.setOrderDate(rs.getTimestamp("ORDERDATE"));
        order.setShipAddress1(rs.getString("shipAddress1"));
        order.setShipAddress2(rs.getString("shipAddress2"));
        order.setShipCity(rs.getString("shipCity"));
        order.setShipState(rs.getString("shipState"));
        order.setShipZip(rs.getString("shipZip"));
        order.setShipCountry(rs.getString("shipCountry"));
        order.setBillAddress1(rs.getString("billAddress1"));
        order.setBillAddress2(rs.getString("billAddress2"));
        order.setBillCity(rs.getString("billCity"));
        order.setBillState(rs.getString("billState"));
        order.setBillZip(rs.getString("billZip"));
        order.setBillCountry(rs.getString("billCountry"));
        order.setCourier(rs.getString("COURIER"));
        order.setTotalPrice(rs.getBigDecimal("TOTALPRICE"));
        order.setBillToFirstName(rs.getString("BILLTOFIRSTNAME"));
        order.setBillToLastName(rs.getString("BILLTOLASTNAME"));
        order.setShipToFirstName(rs.getString("SHIPTOFIRSTNAME"));
        order.setShipToLastName(rs.getString("SHIPTOLASTNAME"));
        order.setCreditCard(rs.getString("CREDITCARD"));
        order.setExpiryDate(rs.getString("expiryDate"));
        order.setCardType(rs.getString("CARDTYPE"));
        order.setLocale(rs.getString("LOCALE"));
        order.setStatus(rs.getString("STATUS"));
        return order;
    }
}
