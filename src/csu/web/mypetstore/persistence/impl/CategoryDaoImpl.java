package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Category;
import csu.web.mypetstore.persistence.CategoryDao;
import csu.web.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private static final String GET_CATEGORY_LIST = "SELECT CATID AS categoryId,NAME, DESCN AS description FROM CATEGORY";

    private static final String GET_CATEGORY = "    SELECT CATID AS categoryId,NAME, DESCN AS description FROM CATEGORY WHERE CATID = ?";


    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<Category>();
        try{
            Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_CATEGORY_LIST);
            while(resultSet.next()){
                Category category = new Category();
                category.setCategoryId(resultSet.getString("categoryId"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                categoryList.add(category);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public Category getCategory(String categoryId){
        Category category = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_CATEGORY);
            statement.setString(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                category = new Category();
                category.setCategoryId(resultSet.getString("categoryId"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    //public static void main(String[] args) {
    //    CategoryDao categoryDao = new CategoryDaoImpl();
    //    System.out.println(categoryDao.getCategory("BIRDS"));
    //}
}
