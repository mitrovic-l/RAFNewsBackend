package com.raf.rafnews_jun.repository.category;

import com.raf.rafnews_jun.entities.Category;
import com.raf.rafnews_jun.entities.User;
import com.raf.rafnews_jun.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public List<Category> allCategories() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Category");
            while (resultSet.next()){
                Category category = new Category(resultSet.getInt(1), resultSet.getString("name"), resultSet.getString("description"));
                categories.add(category);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE name=?");
            preparedStatement.setString(1, category.getName());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                String[] generatedColumns = {"id"};
                preparedStatement = connection.prepareStatement("INSERT INTO Category (name, description) VALUES (?, ?)", generatedColumns);
                preparedStatement.setString(1, category.getName());
                preparedStatement.setString(2, category.getDescription());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    category.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Category findCategory(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                category = new Category(resultSet.getInt(1), resultSet.getString("name"), resultSet.getString("description"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Category updateCategory(Category category, String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE Category SET Category.name = ?, Category.description = ? WHERE Category.name = ?");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public void deleteCategory(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Category WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
