-- Create the database
CREATE DATABASE IF NOT EXISTS speedtech_automotive;
USE speedtech_automotive;

-- Create the customer table
CREATE TABLE IF NOT EXISTS customer (
    cus_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact INT(10),
    number_plate VARCHAR(255)
);

-- Create the product table
CREATE TABLE IF NOT EXISTS product (
    product INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50),
    addedDate DATETIME,
    description TEXT
    );

-- Create the user table
CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact INT(10),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create the batch table
CREATE TABLE IF NOT EXISTS batch (
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    datetime DATETIME,
    description TEXT
);

-- Create the batch_details table
CREATE TABLE IF NOT EXISTS batch_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT,
    product INT,
    status BOOLEAN,
    unit_price DECIMAL(10, 2),
    qty INT,
    FOREIGN KEY (batch_id) REFERENCES batch(batch_id),
    FOREIGN KEY (product) REFERENCES product(product)
);

-- Create the Invoice table
CREATE TABLE IF NOT EXISTS Invoice (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    total DECIMAL(10, 2),
    advance DECIMAL(10, 2),
    datetime DATETIME,
    cus_id INT,
    FOREIGN KEY (cus_id) REFERENCES customer(cus_id)
);

-- Create the Invoice_details table
CREATE TABLE IF NOT EXISTS Invoice_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT,
    product INT,
    qty INT,
    selling_price DECIMAL(10, 2),
    product_total DECIMAL(10, 2),
    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id),
    FOREIGN KEY (product) REFERENCES product(product)
);

CREATE TABLE order_details(
                              orderDetail_id INT AUTO_INCREMENT PRIMARY KEY,
                              order_id       VARCHAR(50) NOT NULL,
                              product_id     VARCHAR(50) NOT NULL,
                              productName    VARCHAR(255),
                              productCode    VARCHAR(50),
                              sellingPrice   DECIMAL(10, 2),
                              quantity       INT,
                              discount       DECIMAL(10, 2),
                              productTotal   DECIMAL(10, 2),
                              FOREIGN KEY (order_id) REFERENCES orders(order_id),
                              FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE orders(
    order_id      INT AUTO_INCREMENT PRIMARY KEY,
    totalPrice    DECIMAL(10, 2),
    totalDiscount DECIMAL(10, 2),
    finalTotal    DECIMAL(10, 2),
    addedDate     TIMESTAMP
);

