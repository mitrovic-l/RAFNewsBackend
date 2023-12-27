package com.raf.rafnews_jun.repository.news;

import com.raf.rafnews_jun.entities.Comment;
import com.raf.rafnews_jun.entities.News;
import com.raf.rafnews_jun.entities.Tag;
import com.raf.rafnews_jun.helpers.CommentSortDate;
import com.raf.rafnews_jun.helpers.NewsSortDate;
import com.raf.rafnews_jun.repository.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {
    @Override
    public List<News> allNews() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        ResultSet resultSet2 = null;
        List<News> news = new ArrayList<>();
        List<News> finalNews = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement("SELECT * FROM NEWS");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                News newsObj = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), resultSet.getInt("category_id") );
                statement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
                statement.setInt(1, newsObj.getCategory_id());
                resultSet2 = statement.executeQuery();
                if (resultSet2.next()){
                    newsObj.setCategoryName(resultSet2.getString("name"));
                }
                news.add(newsObj);
            }
            news.sort(new NewsSortDate().reversed());
            int i =0;
            int bound = 0;
            if (news.size() < 10){
                bound = news.size();
            } else
                bound = 10;
            for(i =0;i<bound;i++){
                finalNews.add( news.get(i));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            //this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSet2);
            this.closeConnection(connection);
        }
        return finalNews;
    }

    @Override
    public List<News> allNewsByViews() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> news = new ArrayList<>();
        ResultSet resultSet1 = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM News ORDER BY views DESC");
            while (resultSet.next()){
                News newsObj = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), resultSet.getInt("category_id") );
                preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
                preparedStatement.setInt(1, newsObj.getCategory_id());
                resultSet1 = preparedStatement.executeQuery();
                if (resultSet1.next()){
                    newsObj.setCategoryName(resultSet1.getString("name"));
                }
                news.add(newsObj);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            if (preparedStatement!=null){
                this.closeStatement(preparedStatement);
            }
            this.closeResultSet(resultSet);
            if (resultSet1!=null){
                this.closeResultSet(resultSet1);
            }
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public List<News> allNewsInCategory(Integer category_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        List<News> news = new ArrayList<>();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE category_id = ?");
            preparedStatement.setInt(1, category_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                News newsObj = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), category_id );
                preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
                preparedStatement.setInt(1, category_id);
                resultSet2 = preparedStatement.executeQuery();
                if (resultSet2.next()){
                    newsObj.setCategoryName(resultSet2.getString("name"));
                }
                news.add(newsObj);
            }
            news.sort(new NewsSortDate().reversed());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null){
                this.closeResultSet(resultSet);
            }
            if (resultSet2 != null){
                this.closeResultSet(resultSet2);
            }
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allNewsWithTag(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        List<News> news = new ArrayList<>();
        List<Integer> newsIDs = new ArrayList<>();
        String searchedTag = "";
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM NewsTag WHERE tag_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                newsIDs.add( resultSet.getInt("news_id"));
            }
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                searchedTag = resultSet.getString("tag");
            }
            for (Integer newsId : newsIDs){
                preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE id = ?");
                preparedStatement.setInt(1, newsId);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    News newsObj = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), resultSet.getInt("category_id") );
                    preparedStatement2 = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
                    preparedStatement2.setInt(1, newsObj.getCategory_id());
                    resultSet2 = preparedStatement2.executeQuery();
                    if (resultSet2.next()){
                        newsObj.setCategoryName( resultSet2.getString("name"));
                    }
                    newsObj.setSearchedTag( searchedTag);
                    news.add( newsObj);
                }
            }
            news.sort( new NewsSortDate().reversed());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement2);
            if (resultSet != null){
                this.closeResultSet(resultSet);
            }
            if (resultSet2 != null){
                this.closeResultSet(resultSet2);
            }
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO News (title, author, content, createdAt, views, category_id) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getAuthor());
            preparedStatement.setString(3, news.getContent());
            preparedStatement.setString(4, LocalDate.now().toString());
            preparedStatement.setInt(5, news.getViews());
            preparedStatement.setInt(6, news.getCategory_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                news.setId(resultSet.getInt(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News updateNews(News news, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE News SET News.title = ?, News.content = ?, News.category_id = ? WHERE id = ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setInt(3, news.getCategory_id());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return null;
    }

    @Override
    public News findNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                news = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), resultSet.getInt("category_id") );
                preparedStatement = connection.prepareStatement("UPDATE News SET News.views = ? WHERE id = ?");
                preparedStatement.setInt(1, (news.getViews()+1));
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
            //izvlacenje imena kategorije
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
            preparedStatement.setInt(1, news.getCategory_id());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                news.setCategoryName(resultSet.getString("name"));
            }

            //izvlacenje komentara
            preparedStatement = connection.prepareStatement("SELECT * FROM Comment WHERE news_id = ?");
            preparedStatement.setInt(1, news.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                news.getComments().add( new Comment(resultSet.getInt(1), resultSet.getString("author"), resultSet.getString("text"), resultSet.getString("postedAt"), news.getId()));
            }
            news.getComments().sort( new CommentSortDate().reversed());

            //izvlacenje tag-ova
            preparedStatement = connection.prepareStatement("SELECT * FROM NewsTag WHERE news_id = ?");
            preparedStatement.setInt(1, news.getId());
            resultSet = preparedStatement.executeQuery();
            List<Integer> tagIds = new ArrayList<>();
            while (resultSet.next()){
                tagIds.add( resultSet.getInt("tag_id"));
            }
            for (Integer tagId : tagIds){
                preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE id = ?");
                preparedStatement.setInt(1, tagId);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    news.getTags().add( new Tag(resultSet.getInt(1), resultSet.getString("tag")));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News findNewsForCreator(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                news = new News(resultSet.getInt(1), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("views"), resultSet.getInt("category_id") );
            }
            //izvlacenje imena kategorije
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
            preparedStatement.setInt(1, news.getCategory_id());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                news.setCategoryName(resultSet.getString("name"));
            }

            //izvlacenje komentara
            preparedStatement = connection.prepareStatement("SELECT * FROM Comment WHERE news_id = ?");
            preparedStatement.setInt(1, news.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                news.getComments().add( new Comment(resultSet.getInt(1), resultSet.getString("author"), resultSet.getString("text"), resultSet.getString("postedAt"), news.getId()));
            }
            news.getComments().sort( new CommentSortDate().reversed());

            //izvlacenje tag-ova
            preparedStatement = connection.prepareStatement("SELECT * FROM NewsTag WHERE news_id = ?");
            preparedStatement.setInt(1, news.getId());
            resultSet = preparedStatement.executeQuery();
            List<Integer> tagIds = new ArrayList<>();
            while (resultSet.next()){
                tagIds.add( resultSet.getInt("tag_id"));
            }
            for (Integer tagId : tagIds){
                preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE id = ?");
                preparedStatement.setInt(1, tagId);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    news.getTags().add( new Tag(resultSet.getInt(1), resultSet.getString("tag")));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        try {
            connection = this.newConnection();
            //brisanje tagova iz NewsTag
            preparedStatement = connection.prepareStatement("DELETE FROM NewsTag WHERE news_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM Comment WHERE news_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM News WHERE id = ?");
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
