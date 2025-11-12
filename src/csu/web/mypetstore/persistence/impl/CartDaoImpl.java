package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.persistence.CartDao;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.ItemDao;
import csu.web.mypetstore.persistence.ProductDao;
import jdk.jshell.spi.SPIResolutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDao {


    private static final String GET_CARTITEMS_BY_USERID = "SELECT item_id, in_stock, quantity " +
            "FROM cart " +
            "WHERE user_id = ?";

    private static final String GET_CARTITEM =
            "SELECT item_id, in_stock, quantity, " +
                    "FROM cart" +
                    "WHERE user_id = ? AND item_id = ?";

    private static final String INSERT_CARTITEM =
            "INSERT INTO cart (user_id, item_id, product_id, description, in_stock, quantity, list_price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUANTITY =
            "UPDATE cart SET quantity = ? WHERE user_id = ? AND item_id = ?";

    private static final String REMOVE_CARTITEM =
            "DELETE FROM cart WHERE user_id = ? AND item_id = ?";



    @Override
    public List<CartItem> getCartItemsByUserId(String userId) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(GET_CARTITEMS_BY_USERID);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                ItemDao itemDao = new ItemDaoImpl();
                Item item = itemDao.getItem(resultSet.getString("item_id"));

                cartItem.setItem(item);
                cartItem.setInStock(resultSet.getBoolean("in_stock"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(con);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cartItems;
    }


    @Override
    public CartItem getCartItem(String userId, String itemId) {
        CartItem cartItem =null;
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(GET_CARTITEM);
            statement.setString(1, userId);
            statement.setString(2, itemId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                cartItem = new CartItem();
                ItemDao itemDao = new ItemDaoImpl();
                Item item = itemDao.getItem(resultSet.getString("item_id"));
                cartItem.setItem(item);
                cartItem.setInStock(resultSet.getBoolean("in_stock"));
                cartItem.setQuantity(resultSet.getInt("quantity"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cartItem;
    }


    @Override
    public void addCartItem(CartItem cartItem,String userId) {
        Connection conn = null;
        PreparedStatement statement = null;
        try  {

             conn = DBUtil.getConnection();
             statement = conn.prepareStatement(INSERT_CARTITEM);

            statement.setString(1, userId);
            statement.setString(2, cartItem.getItem().getItemId());
            statement.setString(3, cartItem.getItem().getProductId());
            statement.setString(4, cartItem.getItem().getProduct().getDescription());
            statement.setBoolean(5, cartItem.isInStock());
            statement.setInt(6, cartItem.getQuantity());
            statement.setBigDecimal(7, cartItem.getItem().getListPrice());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
            DBUtil.closeStatement(statement);
        }
    }

    @Override
    public void updateQuantity(String userId, String itemId, int quantity) {
        Connection con = null;
        PreparedStatement statement = null;
        try{
            con = DBUtil.getConnection();
            statement = con.prepareStatement(UPDATE_QUANTITY);
            statement.setInt(1, quantity);
            statement.setString(2, userId);
            statement.setString(3, itemId);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
            DBUtil.closeStatement(statement);
        }
    }

    @Override
    public void removeCartItem(String userId, String itemId) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = DBUtil.getConnection();
            statement = con.prepareStatement(REMOVE_CARTITEM);
            statement.setString(1, userId);
            statement.setString(2, itemId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            DBUtil.closeConnection(con);
            DBUtil.closeStatement(statement);
        }
    }

    @Override
    public void clearCart(String userId) {

    }
}
