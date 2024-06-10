-- Create the database
CREATE DATABASE IF NOT EXISTS speedtech_automotive;
USE speedtech_automotive;

-- Create the customer table
create table if not exists customer
(
    cus_id INT AUTO_INCREMENT PRIMARY KEY,
    name         varchar(255) not null,
    contact      int(10)      null,
    number_plate varchar(255) null,
    address      varchar(200) null,
    customer_nic varchar(20)  null
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
create table if not exists batch
(
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    supplierName varchar(200) null,
    companyName  varchar(200) null,
    contact      int          null,
    description  text         null,
    addedDate    datetime     null
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

-- Create the order table
create table if not exists orders
(
    order_id      INT AUTO_INCREMENT PRIMARY KEY,
    totalPrice    decimal(10, 2)                      null,
    totalDiscount decimal(10, 2)                      null,
    finalTotal    decimal(10, 2)                      null,
    addedDate     timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    customer_id   varchar(100)                        null,
    numberPlate   varchar(200)                        null
);



-- Create the stock table
CREATE TABLE stock(
                      stock_id INT AUTO_INCREMENT PRIMARY KEY,
                      product_id INT NOT NULL,
                      batch_id INT,
                      unit_price DECIMAL(10, 2),
                      initialQty INT,
                      qty INT,
                      status INT,
                      addedDate DATETIME,
                      product_name VARCHAR(255),
                      company_name VARCHAR(255),
                      FOREIGN KEY (product_id) REFERENCES product(product),
                      FOREIGN KEY (batch_id) REFERENCES batch(batch_id)
);

-- Create the order_details table
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
                              FOREIGN KEY (product_id) REFERENCES product(product)
);


