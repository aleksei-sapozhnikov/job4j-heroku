package ru.job4j.json;

import java.io.Closeable;
import java.util.Properties;

/**
 * Holder for single-instance-needed objects.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class SingleHolder implements Closeable {
    private final Database database;
    private final UserStorage userStorage;

    public SingleHolder(Properties properties) {
        this.database = new Database(properties);
        this.userStorage = new UserStorage(this.database);
    }

    /**
     * Returns database.
     *
     * @return Value of database field.
     */
    public Database getDatabase() {
        return this.database;
    }

    /**
     * Returns userStorage.
     *
     * @return Value of userStorage field.
     */
    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    @Override
    public void close() {
        this.database.close();
    }
}
