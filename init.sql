-- Initialize tables and data for user-service
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
INSERT INTO users (first_name, last_name, email, role, password) VALUES ('admin','admin', 'admin@mail.com', 'ADMIN', 'admin');

-------------------------------------------------------------------------------------

-- Initialize tables and data for payment-processing-service
CREATE TABLE user_accounts (
    id SERIAL NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    account VARCHAR(255) NOT NULL,
    balance DECIMAL(38, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    from_account INTEGER NOT NULL,
    to_account INTEGER,
    amount DECIMAL(38, 2) NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    is_internal BOOLEAN NOT NULL,
    is_invalidated BOOLEAN,
    FOREIGN KEY (from_account) REFERENCES user_accounts (id),
    FOREIGN KEY (to_account) REFERENCES user_accounts (id)
);

CREATE TABLE transactions (
    id SERIAL NOT NULL PRIMARY KEY,
    payment INTEGER NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    transaction_status VARCHAR(255) NOT NULL,
    payment_system VARCHAR(255),
    FOREIGN KEY (payment) REFERENCES payments(id)
);

-- INSERT INTO user_accounts(user_id, account, balance) VALUES (1, '101', 750.56);
-- INSERT INTO user_accounts(user_id, account, balance) VALUES (2, '102', 800.50);
-- INSERT INTO user_accounts(user_id, account, balance) VALUES (3, '103', 450.00);
-- INSERT INTO payments (from_account, to_account, amount, payment_date, is_internal, is_invalidated) VALUES (1, 2, 150.55, NOW(), true, false);
-- INSERT INTO payments (from_account, to_account, amount, payment_date, is_internal, is_invalidated) VALUES (2, 1, 250.55, NOW(), true, false);
-- INSERT INTO transactions (payment, transaction_date, transaction_type, transaction_status) VALUES (2, NOW(), 'INTERNAL', 'PENDING');
-- INSERT INTO transactions (payment, transaction_date, transaction_type, transaction_status, payment_system) VALUES (2, NOW(), 'EXTERNAL', 'PENDING', 'PayPal');

-------------------------------------------------------------------------------------

-- Initialize tables and data for risk-management-service
CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO requests (name) VALUES ('request1');
INSERT INTO requests (name) VALUES ('request2');
INSERT INTO requests (name) VALUES ('request3');