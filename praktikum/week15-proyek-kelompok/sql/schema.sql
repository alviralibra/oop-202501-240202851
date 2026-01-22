-- ============================================================
-- AGRI-POS Database Schema (PostgreSQL)
-- ============================================================

-- Drop existing tables if they exist
DROP TABLE IF EXISTS transaction_items CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================================
-- CREATE TABLES
-- ============================================================

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('KASIR', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price DECIMAL(12, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transactions table
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12, 2) NOT NULL CHECK (total_amount >= 0),
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'COMPLETED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT
);

-- Transaction Items table
CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12, 2) NOT NULL CHECK (unit_price >= 0),
    subtotal DECIMAL(12, 2) NOT NULL CHECK (subtotal >= 0),
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- ============================================================
-- CREATE INDEXES
-- ============================================================

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transaction_items_transaction ON transaction_items(transaction_id);
CREATE INDEX idx_products_code ON products(code);

-- ============================================================
-- INSERT SAMPLE DATA
-- ============================================================

-- Insert users
INSERT INTO users (username, password, role) VALUES 
('kasir001', 'pass123', 'KASIR'),
('kasir002', 'pass123', 'KASIR'),
('admin001', 'admin123', 'ADMIN');

-- Insert products (seed data)
INSERT INTO products (code, name, category, price, stock) VALUES 
('BNH-001', 'Benih Padi Premium', 'Benih', 25000.00, 150),
('BNH-002', 'Benih Jagung Hibrida', 'Benih', 30000.00, 120),
('BNH-003', 'Benih Cabai Keriting', 'Benih', 45000.00, 80),
('PES-001', 'Pupuk Urea 50kg', 'Pupuk', 150000.00, 80),
('PES-002', 'Pupuk NPK 50kg', 'Pupuk', 180000.00, 60),
('OBT-001', 'Insektisida Organik 1L', 'Obat Tanaman', 65000.00, 40),
('OBT-002', 'Fungisida Organik 1L', 'Obat Tanaman', 75000.00, 35),
('ALT-001', 'Hand Sprayer 5L', 'Alat', 85000.00, 25),
('ALT-002', 'Cangkul Standar', 'Alat', 45000.00, 50),
('ALT-003', 'Selang 20m', 'Alat', 120000.00, 15);

-- ============================================================
-- VERIFY DATA
-- ============================================================

-- Check users
SELECT COUNT(*) as total_users FROM users;

-- Check products
SELECT COUNT(*) as total_products FROM products;

-- Sample queries
SELECT code, name, category, price, stock FROM products WHERE category = 'Benih' ORDER BY code;
SELECT * FROM users WHERE role = 'KASIR';
