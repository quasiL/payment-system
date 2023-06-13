package cz.cvut.nss.paymentprocessingservice;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
        jdbcTemplate.execute("CREATE TABLE user_accounts (\n" +
                "    id IDENTITY NOT NULL PRIMARY KEY,\n" +
                "    user_id INTEGER NOT NULL,\n" +
                "    account VARCHAR(255) NOT NULL,\n" +
                "    balance VARCHAR(255) NOT NULL,\n" +
                "    FOREIGN KEY (user_id) REFERENCES users(id)\n" +
                ");");
        jdbcTemplate.execute("CREATE TABLE payments (\n" +
                "    id IDENTITY NOT NULL PRIMARY KEY,\n" +
                "    from_account INTEGER NOT NULL,\n" +
                "    to_account INTEGER NOT NULL,\n" +
                "    amount DECIMAL(38, 2) NOT NULL,\n" +
                "    payment_date TIMESTAMP NOT NULL,\n" +
                "    is_internal BOOLEAN NOT NULL,\n" +
                "    is_invalidated BOOLEAN,\n" +
                "    FOREIGN KEY (from_account) REFERENCES user_accounts (id),\n" +
                "    FOREIGN KEY (to_account) REFERENCES user_accounts (id)\n" +
                ");");
        jdbcTemplate.execute("CREATE TABLE transactions (\n" +
                "    id IDENTITY NOT NULL PRIMARY KEY,\n" +
                "    payment INTEGER NOT NULL,\n" +
                "    transaction_date TIMESTAMP NOT NULL,\n" +
                "    transaction_type VARCHAR(255),\n" +
                "    transaction_status VARCHAR(255) NOT NULL,\n" +
                "    payment_system VARCHAR(255),\n" +
                "    FOREIGN KEY (payment) REFERENCES payments (id)\n" +
                ");");
        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, role, password) VALUES ('firstName1','lastName1', 'email1', 'USER', 'password1');");
        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, role, password) VALUES ('firstName2','lastName2', 'email2', 'USER', 'password2');");
//        jdbcTemplate.execute("INSERT INTO users (first_name, last_name, email, role, password) VALUES ('firstName2','lastName2', 'email2', 'USER', 'password2');");
        jdbcTemplate.execute("INSERT INTO user_accounts(user_id, account, balance) VALUES (2, '101', 750.56);");
//        jdbcTemplate.execute("INSERT INTO user_accounts(user_id, account, balance) VALUES (2, '102', 800.50);");
//        jdbcTemplate.execute("INSERT INTO user_accounts(user_id, account, balance) VALUES (3, '103', 450.00);");
//        jdbcTemplate.execute("INSERT INTO payments (from_account, to_account, amount, payment_date, is_internal, is_invalidated) VALUES (1, 2, 150.55, NOW(), true, false);");
//        jdbcTemplate.execute("INSERT INTO payments (from_account, to_account, amount, payment_date, is_internal, is_invalidated) VALUES (2, 1, 250.55, NOW(), false, false);");
        //jdbcTemplate.execute("INSERT INTO transactions (payment, transaction_date, transaction_type, transaction_status) VALUES (1, NOW(), 'INTERNAL', 'PENDING');");
        //jdbcTemplate.execute("INSERT INTO transactions (payment, transaction_date, transaction_type, transaction_status, payment_system) VALUES (2, NOW(), 'EXTERNAL', 'PENDING', 'PayPal');");
    }
}

