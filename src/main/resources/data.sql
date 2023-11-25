CREATE TABLE IF NOT EXISTS products (
                                        id VARCHAR(255) PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        price DECIMAL(10, 2) NOT NULL,
                                        quantity INT NOT NULL
);

INSERT INTO products (id, name, price, quantity)
VALUES
    ('becd5e12-defd-4238-8739-5ac69fb06b0f', 'H8 LED', 30, 11),
    ('462538de-0cc5-46ed-9a4c-034980242fe9', 'H3 LED', 25, 5),
    ('a517dec9-e025-4681-9b7d-584ebd5f990a', 'D1S HID', 40, 44);
