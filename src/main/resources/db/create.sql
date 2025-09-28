SET MODE PostgresSQL;

CREATE DATABASE utalii;
\c utalii;

CREATE TABLE IF NOT EXISTS tours (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    imageUrl VARCHAR,
    imageId INT,
    price int
);



CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    tourId INT,
    imageData bytea
);


CREATE TABLE IF NOT EXISTS reviews (
 id SERIAL PRIMARY KEY,
 author VARCHAR,
 rating VARCHAR,
 content VARCHAR,
 kidId INTEGER,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    imageUrl VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    color VARCHAR(50),
    category VARCHAR(20) NOT NULL,
    brand VARCHAR(100),
    size VARCHAR(10),
    material VARCHAR(100),
    isActive BOOLEAN DEFAULT true,

    -- Inventory Management
    stockQuantity INTEGER DEFAULT 0,
    reservedQuantity INTEGER DEFAULT 0,
    minimumStockLevel INTEGER DEFAULT 5,
    trackInventory BOOLEAN DEFAULT true,
    maxOrderQuantity INTEGER DEFAULT 10,

    -- E-commerce Features
    compareAtPrice DECIMAL(10,2),
    isFeatured BOOLEAN DEFAULT false,
    allowBackorders BOOLEAN DEFAULT false,
    backorderLimit INTEGER DEFAULT 0,
    sku VARCHAR(50) UNIQUE,
    weight DECIMAL(8,3) DEFAULT 0,
    tags TEXT,

    -- Timestamps
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Shopping carts table
CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    sessionId VARCHAR(255),
    customerId INTEGER,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiresAt TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL '24 hours')
);

-- Cart items table
CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cartId INTEGER NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    productId INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    priceAtAdd DECIMAL(10,2) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(cartId, productId)
);

-- Orders table
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    orderNumber VARCHAR(50) UNIQUE NOT NULL,
    customerId INTEGER,
    status VARCHAR(20) DEFAULT 'pending',

    -- Order totals
    subtotal DECIMAL(10,2) DEFAULT 0,
    taxAmount DECIMAL(10,2) DEFAULT 0,
    shippingAmount DECIMAL(10,2) DEFAULT 0,
    discountAmount DECIMAL(10,2) DEFAULT 0,
    totalAmount DECIMAL(10,2) DEFAULT 0,

    -- Shipping address
    shippingFirstName VARCHAR(100),
    shippingLastName VARCHAR(100),
    shippingAddress1 VARCHAR(255),
    shippingAddress2 VARCHAR(255),
    shippingCity VARCHAR(100),
    shippingState VARCHAR(50),
    shippingZip VARCHAR(20),
    shippingCountry VARCHAR(50),
    shippingPhone VARCHAR(20),

    -- Billing address
    billingFirstName VARCHAR(100),
    billingLastName VARCHAR(100),
    billingAddress1 VARCHAR(255),
    billingAddress2 VARCHAR(255),
    billingCity VARCHAR(100),
    billingState VARCHAR(50),
    billingZip VARCHAR(20),
    billingCountry VARCHAR(50),

    -- Timestamps
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shippedAt TIMESTAMP,
    deliveredAt TIMESTAMP
);

-- Order items table
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    orderId INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    productId INTEGER NOT NULL REFERENCES products(id),
    productName VARCHAR(255) NOT NULL,    -- Snapshot at order time
    productSku VARCHAR(50),               -- Snapshot at order time
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL,          -- Price at order time
    totalPrice DECIMAL(10,2) NOT NULL
);

-- Single reviews table!
CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    productId INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    reviewerName VARCHAR(100),
    reviewerEmail VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_carts_session ON carts(sessionId);
CREATE INDEX idx_carts_customer ON carts(customerId);
CREATE INDEX idx_carts_expires ON carts(expiresAt);

CREATE INDEX idx_cart_items_cart ON cart_items(cartId);
CREATE INDEX idx_cart_items_product ON cart_items(productId);

CREATE INDEX idx_orders_customer ON orders(customerId);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_number ON orders(orderNumber);
CREATE INDEX idx_orders_created ON orders(createdAt);

CREATE INDEX idx_order_items_order ON order_items(orderId);
CREATE INDEX idx_order_items_product ON order_items(productId);

CREATE DATABASE utalii_test WITH TEMPLATE utalii;