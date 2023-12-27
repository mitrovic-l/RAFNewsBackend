package com.raf.rafnews_jun.repository.user;

import com.raf.rafnews_jun.entities.User;
import com.raf.rafnews_jun.repository.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {
    @Override
    public List<User> allUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()){
                User user = new User(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getInt("status"), resultSet.getInt("type"));
                users.add(user);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            // da li postoji korisnik sa ovim email-om??
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                String[] generatedColumns = {"id"};
                preparedStatement = connection.prepareStatement("INSERT INTO Users (first_name, last_name, email, password, status, type) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
                preparedStatement.setString(1, user.getFirst_name());
                preparedStatement.setString(2, user.getLast_name());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, DigestUtils.sha256Hex(user.getPassword()));
                preparedStatement.setInt(5, user.getStatus());
                preparedStatement.setInt(6, user.getType());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    user.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                Integer status = resultSet.getInt("status");
                Integer type = resultSet.getInt("type");
                Integer id = resultSet.getInt(1);
                user = new User(id, first_name, last_name, email, password, status, type);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                Integer status = resultSet.getInt("status");
                Integer type = resultSet.getInt("type");
                String email = resultSet.getString("email");
                user = new User(id, first_name, last_name, email, password, status, type);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public void deleteUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE email = ?");
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void changeUserActivity(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ? ");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int status = resultSet.getInt("status");
                preparedStatement = connection.prepareStatement("UPDATE Users SET status = ? WHERE email = ? ");
                if (status == 0) {
                    status = 1;
                } else {
                    status = 0;
                }
                preparedStatement.setInt(1, status);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public User updateUser(User user, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        try {
            connection = this.newConnection();
            if (!(email.equals(user.getEmail()))) {
                preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ? ");
                preparedStatement.setString(1, user.getEmail());
                resultSet = preparedStatement.executeQuery();
            }
            if (resultSet == null || !resultSet.next() || email.equals(user.getEmail())) {
                preparedStatement = connection.prepareStatement("UPDATE Users SET Users.email = ?, Users.first_name = ?" +
                        ", Users.last_name = ?, Users.type = ? WHERE email = ?");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirst_name());
                preparedStatement.setString(3, user.getLast_name());
                preparedStatement.setInt(4, user.getType());
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
                preparedStatement.setString(1, email);
                resultSet2 = preparedStatement.executeQuery();
                if (resultSet2.next()){
                    user.setId(resultSet2.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            if (resultSet2 != null) {
                this.closeResultSet(resultSet2);
            }
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User updateUserById(User user, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        User updatedUser = new User();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return updatedUser;
            }
            if (resultSet == null || !resultSet.next()) {
                preparedStatement = connection.prepareStatement("UPDATE Users SET Users.email = ?, Users.first_name = ?" +
                        ", Users.last_name = ?, Users.type = ? WHERE id = ?");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirst_name());
                preparedStatement.setString(3, user.getLast_name());
                preparedStatement.setInt(4, user.getType());
                preparedStatement.setInt(5, id);
                resultSet2 = preparedStatement.executeQuery();
                if (resultSet2.next()){
                    updatedUser.setId(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            if (resultSet2 != null){
                this.closeResultSet(resultSet2);
            }
            this.closeConnection(connection);
        }

        return updatedUser;
    }
}
