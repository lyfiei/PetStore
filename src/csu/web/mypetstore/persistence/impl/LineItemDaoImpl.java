package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.LineItem;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.LineItemDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoImpl implements LineItemDao {

    private static final String GET_LINEITEMS_BY_ORDERID="SELECT ORDERID,LINENUM AS lineNumber,ITEMID,QUANTITY,UNITPRICE FROM LINEITEM WHERE ORDERID = ?";
    private static final String INSERT_LINEITEM="INSERT INTO LINEITEM (ORDERID, LINENUM, ITEMID, QUANTITY, UNITPRICE) VALUES (?, ?, ?, ?, ?)";

    @Override
    public List<LineItem> getLineItemsByOrderId(int orderId) {
        List<LineItem> lineItems = new ArrayList<>();
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_LINEITEMS_BY_ORDERID)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(rs.getInt("ORDERID"));
                lineItem.setLineNumber(rs.getInt("lineNumber"));
                lineItem.setItemId(rs.getString("ITEMID"));
                lineItem.setQuantity(rs.getInt("QUANTITY"));
                lineItem.setUnitPrice(rs.getBigDecimal("UNITPRICE"));
                lineItems.add(lineItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineItems;
    }

    @Override
    public void insertLineItem(LineItem lineItem) {
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_LINEITEM)
        ) {
            ps.setInt(1, lineItem.getOrderId());
            ps.setInt(2, lineItem.getLineNumber());
            ps.setString(3, lineItem.getItemId());
            ps.setInt(4, lineItem.getQuantity());
            ps.setBigDecimal(5, lineItem.getUnitPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
