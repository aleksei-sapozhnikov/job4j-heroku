package ru.job4j.json;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.util.database.CertainPropertiesHolder;
import ru.job4j.util.database.Connector;
import ru.job4j.util.database.DbConnector;
import ru.job4j.util.database.DbExecutor;
import ru.job4j.util.methods.ConnectionUtils;

import java.util.Properties;

/**
 * Database provider.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Database implements AutoCloseable {
    private final CertainPropertiesHolder queries;
    private final Connector connector;

    public Database(Properties properties) {
        this.queries = new CertainPropertiesHolder(properties, ".sql");
        this.connector = new DbConnector(new BasicDataSource(), properties);
    }

    public DbExecutor getExecutor() {
        try {
            return new DbExecutor(ConnectionUtils.rollbackOnClose(this.connector.getConnection()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getQuery(String key) {
        return this.queries.get(key);
    }

    @Override
    public void close()  {
        try {
            this.connector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
