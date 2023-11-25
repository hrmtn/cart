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

INSERT INTO products (id, name, price, quantity)
VALUES
    ('becd5e12-defd-4238-8739-5ac69fb06b0f', 'H8 LED', 30, 11),
    ('462538de-0cc5-46ed-9a4c-034980242fe9', 'H3 LED', 25, 5),
    ('a517dec9-e025-4681-9b7d-584ebd5f990a', 'D1S HID', 40, 44);
