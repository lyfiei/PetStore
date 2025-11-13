package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Category;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final String GET_PRODUCT = "SELECT PRODUCTID,NAME, DESCN as description," +
            "CATEGORY as categoryId FROM PRODUCT WHERE PRODUCTID = ?";

    private static final String GET_PRODUCT_LIST_BY_CATEGORY = " SELECT PRODUCTID ,NAME ," +
            "DESCN as description ,CATEGORY as categoryId FROM PRODUCT WHERE `CATEGORY` = ?";

    private static final String SEARCH_PRODUCT_LIST =
            "SELECT DISTINCT P.PRODUCTID, P.NAME, P.DESCN AS description, P.CATEGORY AS categoryId " +
                    "FROM PRODUCT P " +
                    "JOIN CATEGORY C ON P.CATEGORY = C.CATID " +
                    "LEFT JOIN ITEM I ON P.PRODUCTID = I.PRODUCTID " +
                    "WHERE LOWER(P.NAME) LIKE ? OR LOWER(P.DESCN) LIKE ? " +
                    "   OR LOWER(C.NAME) LIKE ? OR LOWER(C.DESCN) LIKE ? " +
                    "   OR LOWER(I.ATTR1) LIKE ? OR LOWER(I.ATTR2) LIKE ? OR LOWER(I.ATTR3) LIKE ? " +
                    "ORDER BY P.PRODUCTID";


    @Override
    public List<Product> getProductListByCategory(String categoryId) {
        List<Product> productList = new ArrayList<Product>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_LIST_BY_CATEGORY);
            statement.setString(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getString("PRODUCTID"));
                product.setName(resultSet.getString("NAME"));
                product.setDescription(resultSet.getString("description"));
                product.setCategoryId(resultSet.getString("categoryId"));
                productList.add(product);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getProduct(String productId) {
        Product product = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT);
            statement.setString(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                product = new Product();
                product.setProductId(resultSet.getString("PRODUCTID"));
                product.setName(resultSet.getString("NAME"));
                product.setDescription(resultSet.getString("description"));
                product.setCategoryId(resultSet.getString("categoryId"));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> searchProductList(String keywords) {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_PRODUCT_LIST)) {

            String key = "%" + keywords.toLowerCase() + "%";
            for (int i = 1; i <= 7; i++) {
                statement.setString(i, key);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getString("PRODUCTID"));
                product.setName(resultSet.getString("NAME"));
                product.setDescription(resultSet.getString("description"));
                product.setCategoryId(resultSet.getString("categoryId"));
                productList.add(product);
            }

            DBUtil.closeResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }


}
