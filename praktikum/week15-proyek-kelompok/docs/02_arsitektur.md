# 02 Arsitektur Sistem
## Agri-POS - Architecture Design Document

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0  
**Status:** Final

---

## 1. Overview Arsitektur

### 1.1 Arsitektur Style
**Clean Architecture dengan Layering** dan **Dependency Inversion Principle**

```
┌─────────────────────────────────────┐
│  LAYER 1: Presentation (JavaFX)     │ View
├─────────────────────────────────────┤
│  LAYER 2: Controller (MVC)          │ Orchestration
├─────────────────────────────────────┤
│  LAYER 3: Business Logic (Service)  │ Domain Rules
├─────────────────────────────────────┤
│  LAYER 4: Data Access (DAO)         │ CRUD Operations
├─────────────────────────────────────┤
│  LAYER 5: Database (PostgreSQL)     │ Persistence
└─────────────────────────────────────┘
```

### 1.2 Prinsip Desain
- **Single Responsibility Principle:** Setiap class punya 1 tanggung jawab
- **Open/Closed Principle:** Terbuka ekstensi, tertutup modifikasi
- **Dependency Inversion:** Depend on abstraction, bukan concrete
- **Interface Segregation:** Interface kecil & spesifik
- **Liskov Substitution:** Subclass dapat substitute parent

---

## 2. Layer Architecture Detail

### 2.1 Layer 1: Presentation (View)

**Teknologi:** JavaFX 21.0.1

#### Components:

```
com.upb.agripos.view
├── LoginView
│   ├── username: TextField
│   ├── password: PasswordField
│   ├── roleComboBox: ComboBox
│   └── loginButton: Button
│
├── PosView (Main Application)
│   ├── productTable: TableView<Product>
│   ├── cartTable: TableView<CartItem>
│   ├── totalLabel: Label
│   ├── buttons: [Add, Remove, Checkout, Clear]
│   └── tabPane: [Admin Tab, Kasir Tab]
│
├── ProductTableView
│   ├── productTable: TableView
│   ├── inputs: [code, name, category, price, stock]
│   └── buttons: [Add, Update, Delete]
│
├── CartTableView
│   ├── cartTable: TableView
│   └── totalLabel: Label
│
└── ReportView
    ├── reportTable: TableView
    └── dateSelector: DatePicker
```

**Responsibilities:**
- Display data ke user
- Handle user input (button clicks, form input)
- Refresh UI based on model changes
- Delegate logic ke controller

---

### 2.2 Layer 2: Controller (MVC Controller)

**Teknologi:** Java Event Handlers

#### Components:

```
com.upb.agripos.controller
├── LoginController
│   ├── authService: AuthService
│   ├── userDAO: UserDAO
│   └── handleLogin()
│
├── PosController (Main)
│   ├── productService: ProductService
│   ├── cartService: CartService
│   ├── transactionService: TransactionService
│   ├── paymentService: PaymentService
│   ├── handleAddProduct()
│   ├── handleDeleteProduct()
│   ├── handleAddToCart()
│   ├── handleCheckout()
│   └── [other handlers]
│
├── ProductController
│   ├── productService: ProductService
│   ├── handleSaveProduct()
│   ├── handleUpdateProduct()
│   └── handleDeleteProduct()
│
└── PaymentController
    ├── paymentService: PaymentService
    └── handlePaymentMethodSelection()
```

**Responsibilities:**
- Orchestrate flow
- Handle user events
- Call service methods
- Update view based on results
- Error handling & validation

**Design Pattern:** MVC Controller

---

### 2.3 Layer 3: Business Logic (Service)

**Teknologi:** Java Classes dengan Business Rules

#### Components:

```
com.upb.agripos.service
├── ProductService
│   ├── productDAO: ProductDAO
│   ├── addProduct(Product): void
│   ├── getAllProducts(): List<Product>
│   ├── updateProduct(Product): void
│   ├── deleteProduct(String code): void
│   └── getProductByCode(String): Product
│
├── CartService
│   ├── cart: Cart
│   ├── addItem(Product, int qty): void
│   ├── removeItem(String code): void
│   ├── updateQuantity(String code, int qty): void
│   ├── getTotalPrice(): double
│   ├── clearCart(): void
│   └── getItems(): List<CartItem>
│
├── TransactionService
│   ├── transactionDAO: TransactionDAO
│   ├── createTransaction(Cart, PaymentMethod): Transaction
│   ├── saveTransaction(Transaction): void
│   └── getTransactionHistory(): List<Transaction>
│
├── PaymentService
│   ├── processPayment(PaymentMethod, double): Receipt
│   └── validatePayment(PaymentMethod): boolean
│
├── AuthService
│   ├── userDAO: UserDAO
│   ├── login(username, password): User
│   ├── authenticate(User): boolean
│   └── validateRole(User, requiredRole): boolean
│
├── ReceiptService
│   ├── generateReceipt(Transaction): Receipt
│   ├── formatReceipt(Receipt): String
│   └── printReceipt(Receipt): void
│
└── ReportService
    ├── transactionDAO: TransactionDAO
    ├── getReportByDate(LocalDate): List<Transaction>
    ├── calculateTotalSales(): double
    └── calculateTotalPerPaymentMethod(): Map
```

**Responsibilities:**
- Business logic & validation
- Orchestrate DAO calls
- Throw custom exceptions
- No UI/Database code

**Design Pattern:** Dependency Injection

---

### 2.4 Layer 4: Data Access (DAO)

**Teknologi:** JDBC dengan PreparedStatement

#### Components:

```
com.upb.agripos.dao
├── ProductDAO (interface)
│   ├── insert(Product): void
│   ├── update(Product): void
│   ├── delete(String code): void
│   ├── findByCode(String): Product
│   └── findAll(): List<Product>
│
├── ProductDAOImpl (JDBC)
│   └── [all above methods with SQL]
│
├── UserDAO (interface)
│   ├── insert(User): void
│   ├── findByUsername(String): User
│   ├── update(User): void
│   └── delete(int id): void
│
├── UserDAOImpl (JDBC)
│   └── [all above methods with SQL]
│
├── TransactionDAO (interface)
│   ├── insert(Transaction): void
│   ├── findAll(): List<Transaction>
│   ├── findByDate(LocalDate): List<Transaction>
│   └── findById(int): Transaction
│
├── TransactionDAOImpl (JDBC)
│   └── [all above methods with SQL]
│
└── DatabaseConnection (Singleton)
    ├── instance: DatabaseConnection
    ├── connection: Connection
    ├── getInstance(): DatabaseConnection
    ├── getConnection(): Connection
    ├── closeConnection(): void
    └── reconnect(): void
```

**Responsibilities:**
- SQL query execution
- JDBC connection management
- Result set mapping
- PreparedStatement usage (SQL Injection prevention)

**Design Pattern:** DAO + Singleton

---

### 2.5 Layer 5: Database

**Teknologi:** PostgreSQL

#### Tables:

```sql
-- Products
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

-- Users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Transactions
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    payment_method VARCHAR(50),
    status VARCHAR(50) DEFAULT 'COMPLETED'
);

-- Transaction Items
CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id INT REFERENCES transactions(id),
    product_id INT REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2)
);
```

---

## 3. Dependency Injection & Inversion of Control

### 3.1 Dependency Flow

```
View
  └─> (event handler)
        └─> Controller
              └─> (method call)
                    └─> Service (depends on DAO interface)
                          └─> (DAO call)
                                └─> DAO Implementation (JDBC)
                                      └─> (SQL query)
                                            └─> Database
```

### 3.2 Constructor Injection Pattern

```java
// Bad: Tight coupling
public class ProductService {
    private ProductDAOImpl productDAO = new ProductDAOImpl();
}

// Good: Dependency Inversion
public class ProductService {
    private final ProductDAO productDAO;
    
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}

// Usage
ProductDAO productDAO = new ProductDAOImpl(connection);
ProductService service = new ProductService(productDAO);
```

---

## 4. Design Patterns Used

### 4.1 Singleton Pattern

**Location:** `DatabaseConnection.java`

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
```

**Purpose:** Ensure only 1 database connection instance

---

### 4.2 Strategy Pattern

**Location:** Payment processing

```java
public interface PaymentMethod {
    Receipt processPayment(double amount) throws ValidationException;
    void validatePayment() throws ValidationException;
}

public class CashPayment implements PaymentMethod { ... }
public class EWalletPayment implements PaymentMethod { ... }
```

**Purpose:** Allow multiple payment methods without modifying core code

---

### 4.3 DAO Pattern

**Location:** `ProductDAO`, `UserDAO`, `TransactionDAO`

**Purpose:** Abstract database access from business logic

---

### 4.4 MVC Pattern

**Location:** Entire application

**Components:**
- **Model:** Product, User, Cart, Transaction, Receipt
- **View:** JavaFX views (LoginView, PosView, etc.)
- **Controller:** MVC controllers (LoginController, PosController, etc.)

---

## 5. Cross-Cutting Concerns

### 5.1 Error Handling

**Exception Hierarchy:**

```
Exception
├── ValidationException
│   └── Thrown by service when validation fails
├── OutOfStockException
│   └── Thrown when cart item qty > stock
└── SQLException
    └── Wrapped in custom exceptions
```

### 5.2 Logging

**Strategy:** Console output (can extend to file/logger)

```java
System.out.println("✓ Database connected successfully");
System.err.println("Error: " + message);
```

### 5.3 Validation

**Location:** Service layer

```java
if (product.getPrice() < 0) {
    throw new ValidationException("Harga tidak boleh negatif");
}
```

---

## 6. Data Flow Examples

### 6.1 Add Product Flow

```
Admin Input [Form]
  ↓ (submit)
ProductController.handleAddProduct()
  ↓
ProductService.addProduct(product)
  ├─ Validate input
  ├─ Check unique code
  ↓
ProductDAO.insert(product)
  ├─ Prepare SQL
  ├─ Execute with PreparedStatement
  ↓
PostgreSQL [insert into products]
  ↓
Return to Controller
  ↓
Update TableView
  ↓
Show success message
```

### 6.2 Checkout Flow

```
Kasir click [Checkout]
  ↓
PosController.handleCheckout()
  ↓
PaymentDialog [select method]
  ├─ CashPayment: input amount
  ├─ EWalletPayment: input account
  ↓
PaymentService.processPayment()
  ├─ Validate payment
  ├─ CashPayment.validate() [amount >= total]
  ├─ EWalletPayment.validate() [balance >= total]
  ↓
TransactionService.createTransaction()
  ├─ Create Receipt
  ├─ ReceiptService.generateReceipt()
  ↓
TransactionDAO.insert(transaction)
  ├─ Save to database
  ↓
Display Receipt
  ↓
Clear Cart
```

---

## 7. Deployment Architecture

### 7.1 Development Environment

```
Developer Machine
├── Java 21 (JDK)
├── Maven (build tool)
├── JavaFX 21 (UI framework)
├── PostgreSQL (database)
└── IDE (VS Code)
```

### 7.2 Runtime Components

```
Application Runtime
├── Application (JAR)
├── JVM
├── JavaFX Runtime
├── JDBC Driver (PostgreSQL)
└── Database Connection (PostgreSQL Server)
```

---

## 8. Scalability & Extension Points

### 8.1 Adding New Payment Method

**Current:** Cash, E-Wallet

**To Add:** Transfer Bank, QRIS

```java
// 1. Create new class
public class TransferBankPayment implements PaymentMethod { ... }

// 2. Register in UI
paymentComboBox.getItems().add("Transfer Bank");

// 3. No changes needed in core logic (OCP)
```

### 8.2 Adding New Report Type

**Strategy:** Extend `ReportService` with new query methods

### 8.3 Database Migration

**Strategy:** Create new migration scripts in `sql/` folder

---

## 9. Security Considerations

### 9.1 SQL Injection Prevention
✅ Use PreparedStatement (not string concatenation)

### 9.2 Password Storage
⚠️ Current: Stored as plain text (for demo only)
✅ Production: Use bcrypt/hashing

### 9.3 Role-Based Access
✅ Implemented in AuthService & Controller guards

### 9.4 Input Validation
✅ Implemented in Service layer

---

## 10. Performance Considerations

### 10.1 Database Queries
- Use indexes on: code, username
- Use PreparedStatement for query reuse
- Target: Query response < 100ms

### 10.2 UI Responsiveness
- Use service layer for heavy operations
- Avoid blocking UI thread
- Target: UI response < 500ms

### 10.3 Memory Management
- Cart cleared after checkout
- Singleton connection reused
- Result set closed after use

---

**End of Architecture Document**
