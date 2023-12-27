package com.raf.rafnews_jun.repository.comment;

import com.raf.rafnews_jun.entities.Comment;
import com.raf.rafnews_jun.helpers.CommentSortDate;
import com.raf.rafnews_jun.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> allComments(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> comments = new ArrayList<>();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Comment WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
//                Comment comment = new Comment(resultSet.getInt(1), resultSet.getString("author"), resultSet.getString("text"), resultSet.getString("postedAt"), newsId );
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setAuthor(resultSet.getString("author"));
                comment.setText(resultSet.getString("text"));
                comment.setNews_id(resultSet.getInt("news_id"));
                comment.setPostedAt(resultSet.getString("postedAt"));
                comments.add(comment);
            }
            comments.sort( new CommentSortDate().reversed());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public Comment addComment(Comment comment, Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection =  this.newConnection();
            comment.setNews_id(newsId);
            comment.setPostedAt(LocalDate.now().toString());
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO Comment (author, text, postedAt, news_id) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setString(3, comment.getPostedAt());
            preparedStatement.setInt(4, comment.getNews_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                comment.setId(resultSet.getInt(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public Comment findComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Comment comment = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Comment WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                comment = new Comment(id, resultSet.getString("author"), resultSet.getString("text"), resultSet.getString("postedAt"), resultSet.getInt("news_id") );
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Comment WHERE id = ?");
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
