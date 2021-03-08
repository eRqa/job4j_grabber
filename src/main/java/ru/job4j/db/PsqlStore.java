package ru.job4j.db;

import ru.job4j.html.SqlRuParse;
import ru.job4j.models.Post;
import ru.job4j.service.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection connection;

    public PsqlStore(Properties cfg) throws SQLException {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        connection = DriverManager.getConnection(cfg.getProperty("url"),
                cfg.getProperty("username"),
                cfg.getProperty("password"));
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO POST (LINK, NAME, TEXT, CREATED) "
                                     + "VALUES (?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getLink());
            statement.setString(2, post.getName());
            statement.setString(3, post.getText());
            statement.setDate(4, new Date(post.getCreated().getTime()));
            statement.execute();
            try (ResultSet generatedIds = statement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    post.setId(generatedIds.getString(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM POST")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getString("ID"));
                post.setLink(resultSet.getString("LINK"));
                post.setName(resultSet.getString("NAME"));
                post.setText(resultSet.getString("TEXT"));
                post.setCreated(resultSet.getDate("CREATED"));
                result.add(post);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Post findById(String id) {
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM POST WHERE id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Post newPost = new Post();
                newPost.setId(resultSet.getString("ID"));
                newPost.setName(resultSet.getString("NAME"));
                newPost.setCreated(resultSet.getDate("CREATED"));
                newPost.setLink(resultSet.getString("LINK"));
                newPost.setText(resultSet.getString("TEXT"));
                return newPost;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();
        InputStream inputStream = loader.getResourceAsStream("rabbit.properties");
        settings.load(inputStream);
        PsqlStore psqlStore = new PsqlStore(settings.getProperties());

        SqlRuParse sqlRuParse = new SqlRuParse();
        Post post = sqlRuParse.detail("https://www.sql.ru/forum/1333824/trebuetsya-devops-hadoop");
        psqlStore.save(post);

        List<Post> posts = psqlStore.getAll();
        posts.forEach(System.out::println);

    }

}
