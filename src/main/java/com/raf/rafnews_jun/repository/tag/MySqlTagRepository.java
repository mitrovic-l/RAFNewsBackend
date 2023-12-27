package com.raf.rafnews_jun.repository.tag;

import com.raf.rafnews_jun.entities.Tag;
import com.raf.rafnews_jun.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepository extends MySqlAbstractRepository implements TagRepository {
    @Override
    public List<Tag> allTags() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Tag> tags = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Tag");
            while (resultSet.next()){
                Tag tag = new Tag(resultSet.getInt(1), resultSet.getString("tag"));
                tags.add(tag);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tags;
    }

    @Override
    public Tag addTag(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag=?");
            preparedStatement.setString(1, tag.getTag());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                String[] generatedColumns = {"id"};
                preparedStatement = connection.prepareStatement("INSERT INTO Tag (tag) VALUES (?)", generatedColumns);
                preparedStatement.setString(1, tag.getTag());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    tag.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tag;
    }

    @Override
    public Tag findTag(String tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Tag tagObj = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag = ?");
            preparedStatement.setString(1, tag);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                tagObj = new Tag(resultSet.getInt(1), resultSet.getString("tag"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tagObj;
    }

    @Override
    public Tag findTagById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Tag tagObj = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                tagObj = new Tag(resultSet.getInt(1), resultSet.getString("tag"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tagObj;
    }

    @Override
    public void deleteTag(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Tag WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
