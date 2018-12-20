package ru.job4j.json;

import ru.job4j.util.database.DbExecutor;
import ru.job4j.util.database.DbExecutor.ObjValue;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores objects.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class UserStorage {
    /**
     * Database API.
     */
    private final Database database;

    public UserStorage(Database database) {
        this.database = database;
    }

    /**
     * Adds new object to storage.
     *
     * @param user User to store.
     */
    public void add(User user) {
        try (DbExecutor executor = this.database.getExecutor()) {
            this.add(executor, user);
            executor.commit();
        }
    }

    public void add(DbExecutor executor, User user) {
        executor.execute(
                this.database.getQuery("sql.user.add"),
                Arrays.asList(user.getFirstName(), user.getSecondName(), user.getSex(), user.getDescription()),
                PreparedStatement::execute);
    }

    public List<User> getAll() {
        try (DbExecutor executor = this.database.getExecutor()) {
            return this.getAll(executor);
        }
    }

    /**
     * Adds new object to storage.
     *
     * @return List of users.
     */
    public List<User> getAll(DbExecutor executor) {
        List<Map<Integer, ObjValue>> rows = executor.executeQuery(
                this.database.getQuery("sql.user.get_all"),
                Arrays.asList(String.class, String.class, String.class, String.class)
        ).orElse(new ArrayList<>());
        return rows.stream().map(this::formUser).collect(Collectors.toList());
    }

    private User formUser(Map<Integer, ObjValue> row) {
        int i = 0;
        return new User(
                row.get(++i).asString(),
                row.get(++i).asString(),
                User.Sex.valueOf(row.get(++i).asString()),
                row.get(++i).asString()
        );
    }

}
