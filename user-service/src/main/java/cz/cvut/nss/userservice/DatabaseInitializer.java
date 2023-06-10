package cz.cvut.nss.userservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Profile("dev")
@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("DROP ALL OBJECTS;");
        jdbcTemplate.execute("CREATE TABLE users (\n" +
                "    id IDENTITY NOT NULL PRIMARY KEY,\n" +
                "    first_name VARCHAR(255) NOT NULL,\n" +
                "    last_name VARCHAR(255) NOT NULL,\n" +
                "    email VARCHAR(255) NOT NULL,\n" +
                "    role VARCHAR(255) NOT NULL,\n" +
                "    password VARCHAR(255) NOT NULL\n" +
                ");");
//        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, password) VALUES ('firstName1','lastName1', 'email1', 'password1');");
//        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, password) VALUES ('firstName2','lastName2', 'email2', 'password2');");
//        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, password) VALUES ('firstName3','lastName3', 'email3', 'password3');");
    }
}

