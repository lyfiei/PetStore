package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.ItemDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDaoImpl implements ItemDao {

    private static final String GET_ITEM_LIST_BY_PRODUCT= "SELECT I.ITEMID, LISTPRICE, UNITCOST, SUPPLIER AS supplierId," +
            " I.PRODUCTID AS  product_productId , NAME AS  product_name , DESCN AS  product_description," +
            " CATEGORY AS product_categoryId, STATUS, ATTR1 AS attribute1, ATTR2 AS attribute2, ATTR3 AS attribute3," +
            " ATTR4 AS attribute4, ATTR5 AS attribute5 FROM ITEM I,PRODUCT P WHERE P.PRODUCTID = I.PRODUCTID AND I.PRODUCTID = ?";

    private static final String GET_ITEM = "SELECT I.ITEMID, LISTPRICE, UNITCOST, SUPPLIER AS supplierId," +
            " I.PRODUCTID AS  product_productId , NAME AS  product_name , DESCN AS  product_description," +
            " CATEGORY AS product_categoryId, STATUS, ATTR1 AS attribute1, ATTR2 AS attribute2, ATTR3 AS attribute3," +
            " ATTR4 AS attribute4, ATTR5 AS attribute5 ," +
            " QTY AS quantity from ITEM I, INVENTORY V, PRODUCT P where P.PRODUCTID = I.PRODUCTID and I.ITEMID = V.ITEMID and I.ITEMID = ?";

    private static final String GET_INVENTORY_QUANTITY = "SELECT QTY AS value FROM INVENTORY WHERE ITEMID = ?";

    private static final String UPDATE_INVENTORY_QUANTITY = "UPDATE INVENTORY SET QTY = QTY - ? WHERE ITEMID = ?";



    @Override
    public void updateInventoryQuantity(Map<String, Object> param) {
        int increment = (int) param.get("increment");
        String itemId = (String) param.get("itemId");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_INVENTORY_QUANTITY)) {
            statement.setInt(1, increment);
            statement.setString(2, itemId);
            statement.executeUpdate();

            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getInventoryQuantity(String itemId) {
        int quantity = 0;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_INVENTORY_QUANTITY);
            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                quantity = resultSet.getInt(1);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }


    @Override
    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList = new ArrayList<Item>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ITEM_LIST_BY_PRODUCT);
            statement.setString(1,productId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getString("ITEMID"));
                item.setListPrice(resultSet.getBigDecimal("LISTPRICE"));
                item.setUnitCost(resultSet.getBigDecimal("UNITCOST"));
                item.setSupplierId(resultSet.getInt("supplierId"));
                Product product = new Product();
                product.setProductId(resultSet.getString("product_productId"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setCategoryId(resultSet.getString("product_categoryId"));
                item.setProduct(product);
                item.setStatus(resultSet.getString("STATUS"));
                item.setAttribute1(resultSet.getString("attribute1"));
                item.setAttribute2(resultSet.getString("attribute2"));
                item.setAttribute3(resultSet.getString("attribute3"));
                item.setAttribute4(resultSet.getString("attribute4"));
                item.setAttribute5(resultSet.getString("attribute5"));
                itemList.add(item);
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public Item getItem(String itemId) {
        Item item = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ITEM);
            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item = new Item();
                item.setItemId(resultSet.getString("ITEMID"));
                item.setListPrice(resultSet.getBigDecimal("LISTPRICE"));
                item.setUnitCost(resultSet.getBigDecimal("UNITCOST"));
                item.setSupplierId(resultSet.getInt("supplierId"));
                item.setProductId(resultSet.getString("product_productId"));
                Product product = new Product();
                product.setProductId(resultSet.getString("product_productId"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setCategoryId(resultSet.getString("product_categoryId"));
                item.setProduct(product);
                item.setStatus(resultSet.getString("STATUS"));
                item.setAttribute1(resultSet.getString("attribute1"));
                item.setAttribute2(resultSet.getString("attribute2"));
                item.setAttribute3(resultSet.getString("attribute3"));
                item.setAttribute4(resultSet.getString("attribute4"));
                item.setAttribute5(resultSet.getString("attribute5"));
                item.setQuantity(resultSet.getInt("quantity"));
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
    //
    //public static void main(String[] args) {
    //    ItemDaoImpl itemDao = new ItemDaoImpl();
    //    System.out.println(itemDao.getItemListByProduct("AV-CB-01"));
    //}
}
