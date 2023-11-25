CREATE TABLE IF NOT EXISTS products (
                                        id VARCHAR(255) PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        price DECIMAL(10, 2) NOT NULL,
                                        quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_items (
                                          product_id VARCHAR(255) NOT NULL,
                                          user_id INT NOT NULL,
                                          quantity INT NOT NULL,
                                          PRIMARY KEY (product_id, user_id),
                                          FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS orders (
                                      id VARCHAR(255) PRIMARY KEY,
                                      order_status VARCHAR(255),
                                      created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders_products (
                                               order_id VARCHAR(255) NOT NULL ,
                                               user_id INT NOT NULL,
                                               product_id VARCHAR(255) NOT NULL,
                                               price DECIMAL(10, 2) NOT NULL,
                                               quantity INT NOT NULL,
                                               PRIMARY KEY (order_id, user_id, product_id),
                                               FOREIGN KEY (order_id) REFERENCES orders(id),
                                               FOREIGN KEY (product_id) REFERENCES products(id)
);
