package ru.job4j.db;

import ru.job4j.models.Post;

import java.sql.Connection;
import java.util.List;

public class PsqlStore implements Store, AutoCloseable {

    private Connection connection;

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void save(Post post) {

    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post findById(String id) {
        return null;
    }
}
