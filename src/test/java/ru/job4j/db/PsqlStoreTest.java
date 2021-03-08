package ru.job4j.db;

import org.junit.Before;
import ru.job4j.service.Settings;

import java.io.InputStream;
import java.sql.SQLException;

public class PsqlStoreTest {

    private PsqlStore psqlStore;

    @Before
    public void init() throws SQLException {
        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();
        InputStream inputStream = loader.getResourceAsStream("rabbit.properties");
        settings.load(inputStream);
        psqlStore = new PsqlStore(settings.getProperties());
    }

}