# 03 Database Design
## Agri-POS - Database Schema & Design

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0  
**Database:** PostgreSQL

---

## 1. Overview

### 1.1 Database Technology
- **DBMS:** PostgreSQL 12+
- **Dialect:** SQL Standard
- **Connection:** JDBC with PreparedStatement
- **Driver:** org.postgresql.Driver

### 1.2 Design Goals
- Data integrity (constraints)
- Performance (indexes)
- Extensibility
- Simplicity

---

## 2. Database Schema

### 2.1 Tables

#### PRODUCTS Table
```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price DECIMAL(10,2) CHECK (price >= 0),
    stock INT CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_code ON products(code);
CREATE INDEX idx_products_category ON products(category);
```

**Columns:**
- `id`: Unique identifier
- `code`: Product code (UNIQUE)
- `name`: Product name
- `category`: Benih, Pupuk, Alat, Obat
- `price`: Selling price (≥ 0)
- `stock`: Available stock (≥ 0)

#### USERS Table
```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'KASIR',
    active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_users_username ON users(username);
```

**Seed Data:**
```sql
INSERT INTO users (username, password, role, active) VALUES
('kasir1', 'kasir123', 'KASIR', TRUE),
('admin', 'admin123', 'ADMIN', TRUE),
('owner', 'owner123', 'OWNER', TRUE);
```

#### TRANSACTIONS Table
```sql
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE RESTRICT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    payment_method VARCHAR(50),
    status VARCHAR(50) DEFAULT 'COMPLETED'
);

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
```

#### TRANSACTION_ITEMS Table
```sql
CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id INT REFERENCES transactions(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE RESTRICT,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL
);

CREATE INDEX idx_transaction_items_transaction_id ON transaction_items(transaction_id);
```

### 2.2 Relationships

**users → transactions:** 1:N
- 1 user dapat membuat banyak transaksi
- ON DELETE: RESTRICT

**products → transaction_items:** 1:N
- 1 produk dapat muncul di banyak transaksi
- ON DELETE: RESTRICT

**transactions → transaction_items:** 1:N
- 1 transaksi dapat memiliki banyak items
- ON DELETE: CASCADE

---

## 3. Constraints & Validations

| Table | Constraint | Purpose |
|-------|-----------|---------|
| products | price ≥ 0 | No negative prices |
| products | stock ≥ 0 | No negative stock |
| products | code UNIQUE | No duplicate codes |
| users | username UNIQUE | No duplicate usernames |
| transaction_items | quantity > 0 | At least 1 item |

---

## 4. Connection String

```
jdbc:postgresql://localhost:5432/agripos_database
Username: postgres
Password: JERUKAGUNG
```

---

## 5. Sample Data

```sql
INSERT INTO products (code, name, category, price, stock) VALUES
('P001', 'Benih Padi', 'Benih', 15000, 100),
('P002', 'Benih Jagung', 'Benih', 12000, 150),
('P003', 'Pupuk Urea', 'Pupuk', 5000, 200),
('P004', 'Pupuk TSP', 'Pupuk', 6000, 180),
('P005', 'Cangkul', 'Alat', 45000, 20),
('P006', 'Sabit', 'Alat', 25000, 30),
('P007', 'Obat Hama A', 'Obat', 35000, 50),
('P008', 'Obat Hama B', 'Obat', 40000, 45);
```

---

**End of Database Design Document**