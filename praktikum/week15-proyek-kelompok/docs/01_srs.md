# 01 Software Requirements Specification (SRS)
## Agri-POS - Point of Sale System

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0  
**Status:** Final

---

## 1. Pendahuluan

### 1.1 Tujuan Dokumen
Dokumen ini mendefinisikan requirement fungsional dan non-fungsional untuk sistem Agri-POS, sebuah aplikasi Point of Sale terintegrasi untuk toko pertanian.

### 1.2 Scope
Sistem Agri-POS mencakup:
- Manajemen produk pertanian
- Transaksi penjualan dengan shopping cart
- Multiple payment methods (Tunai & E-Wallet)
- Receipt generation
- Sales reporting
- User authentication dengan role-based access

### 1.3 Stakeholders
- **End Users:** Kasir, Admin Toko
- **System Users:** Developer (maintenance)
- **Business Owner:** Pemilik Toko Pertanian

---

## 2. Functional Requirements (FR)

### FR-1: Manajemen Produk
**Description:** Sistem mampu mengelola data produk pertanian dengan operasi CRUD.

#### FR-1.1 Create Product
- **Actor:** Admin
- **Precondition:** Admin login dengan role ADMIN
- **Trigger:** Admin klik tombol "Tambah Produk"
- **Main Flow:**
  1. Sistem menampilkan form input produk
  2. Admin mengisi: kode, nama, kategori, harga, stok
  3. Admin klik "Simpan"
  4. Sistem validasi input (tidak boleh kosong)
  5. Sistem simpan ke database
  6. Sistem tampilkan pesan sukses
  7. Produk muncul di tabel
- **Alternate Flow (Validasi Gagal):**
  - Jika data kosong → tampilkan error message
  - Jika kode sudah ada → tampilkan "Kode produk sudah digunakan"

#### FR-1.2 Read Product
- **Actor:** Admin, Kasir
- **Precondition:** Login berhasil
- **Main Flow:**
  1. Sistem load data dari database
  2. Tampilkan daftar produk di TableView
  3. Tampilkan: kode, nama, kategori, harga, stok

#### FR-1.3 Update Product
- **Actor:** Admin
- **Precondition:** Ada produk di database
- **Main Flow:**
  1. Admin pilih produk di tabel
  2. Form terisi dengan data produk
  3. Admin ubah data
  4. Admin klik "Update"
  5. Sistem validasi & simpan
  6. Tampilkan pesan sukses

#### FR-1.4 Delete Product
- **Actor:** Admin
- **Precondition:** Ada produk di database
- **Main Flow:**
  1. Admin pilih produk
  2. Admin klik "Hapus"
  3. Sistem tampilkan konfirmasi
  4. Jika "Iya" → hapus dari database
  5. Produk hilang dari tabel

#### FR-1.5 Atribut Produk
- `kode` (String, UNIQUE, NOT NULL)
- `nama` (String, NOT NULL)
- `kategori` (String) - Benih, Pupuk, Alat, Obat
- `harga` (Decimal, ≥ 0)
- `stok` (Integer, ≥ 0)

---

### FR-2: Transaksi Penjualan
**Description:** Sistem mampu membuat transaksi penjualan dengan keranjang belanja yang fully operational.

#### FR-2.1 Akses Transaksi
- **Actor:** Kasir
- **Precondition:** Kasir login dengan role KASIR
- **Main Flow:**
  1. Kasir login → POS View ditampilkan
  2. Sistem otomatis membuka tab "Transaksi Penjualan"
  3. Layout 2 panel:
     - **Left Panel:** Daftar Produk (Table) + Input Quantity + Tombol "Tambah ke Keranjang"
     - **Right Panel:** Keranjang Belanja (Table) + Tombol Operasi (Update Qty, Hapus, Kosongkan) + Total + Checkout

#### FR-2.2 Add Item to Cart
- **Actor:** Kasir
- **Main Flow:**
  1. Kasir **pilih produk dari tabel** di panel kiri
  2. Kasir **input quantity** di field "Qty"
  3. Kasir **klik "Tambah ke Keranjang"**
  4. Sistem validasi:
     - Produk harus dipilih (if not: error "Pilih produk dari daftar terlebih dahulu!")
     - Qty field harus diisi (if empty: error "Masukkan jumlah (Qty)!")
     - Qty harus angka (if not: error "Jumlah harus berupa angka!")
     - Qty > 0 (if ≤ 0: error "Jumlah harus lebih dari 0!")
     - Qty ≤ stok tersedia (if > stok: error "Jumlah melebihi stok tersedia! Stok: XXX")
  5. Jika item sudah ada di keranjang:
     - Update quantity (jangan duplicate)
     - Contoh: ada 3, tambah 2 → menjadi 5
  6. Jika item baru:
     - Tambah baris baru di keranjang
  7. Refresh CartTable
  8. **Update total secara otomatis**
  9. Tampilkan success message: "✓ Produk ditambahkan ke keranjang"
- **Result:**
  - Item muncul di Keranjang TableView dengan kolom: Produk, Qty, Harga, Subtotal
  - Total dihitung: ∑(qty × unit_price)

#### FR-2.3 Update Item Quantity
- **Actor:** Kasir
- **Main Flow:**
  1. Kasir **pilih item di keranjang table**
  2. Kasir **input jumlah baru** di field "Qty" (replace nilai lama)
  3. Kasir **klik "Update Qty"**
  4. Sistem validasi:
     - Item harus dipilih (if not: error "Pilih item di keranjang untuk update!")
     - Qty field harus diisi (if empty: error "Masukkan jumlah baru (Qty)!")
     - Qty harus angka
     - Qty > 0
     - Qty ≤ stok tersedia
  5. Update quantity di keranjang
  6. Recalculate total otomatis
  7. Tampilkan success message: "✓ Jumlah item diperbarui"
- **Example:**
  - Keranjang: Benih Padi qty 5 (Total Rp 75.000)
  - Pilih item, input qty 10, Update
  - Result: qty → 10, Total → Rp 150.000

#### FR-2.4 Remove Item from Cart
- **Actor:** Kasir
- **Main Flow:**
  1. Kasir **pilih item di keranjang**
  2. Kasir **klik "Hapus Item"**
  3. Sistem validasi:
     - Item harus dipilih (if not: error "Pilih item di keranjang untuk dihapus")
  4. Hapus item dari keranjang
  5. Recalculate total
  6. Refresh CartTable
  7. Tampilkan success message: "Item dihapus dari keranjang"

#### FR-2.5 Clear Cart
- **Actor:** Kasir
- **Main Flow:**
  1. Kasir **klik "Kosongkan"**
  2. Sistem hapus semua item dari keranjang
  3. Total reset ke Rp 0
  4. CartTable menjadi kosong
  5. Tampilkan success message: "Keranjang telah dikosongkan"
- **Note:** Operasi tidak bisa di-undo

#### FR-2.6 Calculate & Display Total
- **Actor:** System (automatic)
- **Trigger:** Setiap ada perubahan di keranjang (add/update/remove)
- **Main Flow:**
  1. Sistem calculate: ∑(quantity × unit_price) untuk semua items
  2. Format: "Total: Rp XXX" (dengan thousand separator)
  3. Update label secara real-time
  4. Display di panel kanan keranjang
- **Precision:** Double precision untuk handling nilai desimal uang

#### FR-2.7 View Cart Summary
- **Actor:** Kasir (viewing)
- **Information Displayed:**
  - **Cart Table:** [Produk, Qty, Harga/Unit, Subtotal]
  - **Total:** Sum of all subtotals
  - **Item Count:** Number of unique items
  - **Ready for Checkout:** Indicator ketika cart siap checkout
- **Update Trigger:** Automatic setelah setiap operasi

---

### FR-3: Metode Pembayaran & Checkout
**Description:** Sistem mendukung multiple payment methods dengan checkout flow lengkap dan design extensible.

#### FR-3.1 Initiate Checkout
- **Actor:** Kasir
- **Precondition:** Keranjang ada isi (tidak kosong)
- **Main Flow:**
  1. Kasir **pilih payment method** dari dropdown: "Tunai" atau "E-Wallet"
  2. Kasir **klik tombol "CHECKOUT"**
  3. Sistem validasi:
     - Keranjang tidak kosong (if empty: error "Keranjang kosong")
  4. Sistem tampilkan confirmation dialog:
     ```
     Konfirmasi Checkout?
     Total: Rp 85.000
     Metode: Tunai
     [OK] [Cancel]
     ```
  5. Kasir review dan klik "OK"
  6. Sistem proceed ke payment processing

#### FR-3.2 Cash Payment (Tunai)
- **Actor:** Kasir
- **Payment Method:** TUNAI
- **Main Flow:**
  1. Kasir pilih "Tunai" dari dropdown
  2. Kasir klik "CHECKOUT"
  3. Kasir klik "OK" di dialog konfirmasi
  4. Sistem process pembayaran:
     - **Validation:** amount > 0 (always true untuk checkout)
     - **Processing:** CashPayment.process(amount) → success
  5. Sistem save transaksi ke database:
     - Create transaction record
     - Save cart items sebagai transaction items
     - Update product stock (qty kurang)
  6. Sistem generate receipt
  7. Sistem tampilkan receipt di dialog:
     ```
     ════════════════════════════════════
             AGRI-POS RECEIPT SYSTEM
     ════════════════════════════════════
     
     Transaction ID    : TRX-001
     Cashier           : kasir1
     Date & Time       : 22/01/2026 14:30:00
     Payment Method    : Tunai
     
     ────────────────────────────────────
     Item Details:
     ────────────────────────────────────
     Benih Padi           5 x 15000.00 =   75000.00
     ────────────────────────────────────
     TOTAL:                          Rp 75000.00
     ════════════════════════════════════
     ```
  8. Kasir klik "Close"
  9. Sistem **clear cart otomatis**
  10. Siap untuk transaksi berikutnya
- **Alternate Flow:**
  - Jika user klik "Cancel" di dialog → transaction cancelled, cart tetap

#### FR-3.3 E-Wallet Payment
- **Actor:** Kasir
- **Payment Method:** E-WALLET (GCash, Dana, OVO, etc)
- **Main Flow:**
  1. Kasir pilih "E-Wallet" dari dropdown
  2. Kasir klik "CHECKOUT"
  3. Kasir klik "OK" di dialog konfirmasi
  4. Sistem process pembayaran:
     - **Validation:** EWalletPayment.validate(amount)
       - Check: balance ≥ amount
       - Check: amount > 0
     - **Processing:** EWalletPayment.process(amount) → reduce balance
  5. If balance insufficient: error "Saldo tidak cukup"
  6. If payment success: continue to save transaction
  7. Sistem save transaksi (sama seperti cash)
  8. Sistem generate receipt (payment method = "E-Wallet")
  9. Tampilkan receipt
  10. Clear cart & ready for next
- **Extensibility:**
  - Easy to add new methods: implement PaymentMethod interface
  - Contoh: BankTransferPayment, QRISPayment, CreditCardPayment
  - Tidak perlu ubah checkout logic

#### FR-3.4 Strategy Pattern for Payment Methods
- **Interface:** `PaymentMethod`
  - `boolean process(double amount)` - Execute payment
  - `String getDescription()` - Display name
  - `boolean validate(double amount)` - Verify payment can proceed
- **Implementations:**
  - `CashPayment` - Always valid if amount > 0
  - `EWalletPayment` - Check balance ≥ amount
- **Benefit:** Open/Closed Principle - easy to extend without modifying existing code

#### FR-3.5 Transaction Recording
- **Trigger:** After successful payment
- **Data Saved:**
  - Transaction header: user_id, total_amount, payment_method, timestamp
  - Transaction items: product_id, quantity, unit_price, subtotal
  - **Stock update:** Reduce product stock by quantity
- **Database:** PostgreSQL (transactions + transaction_items tables)
- **Atomicity:** All or nothing (if stock update fails, transaction rolled back)

---

### FR-4: Struk dan Laporan

#### FR-4.1 Generate Receipt (Automatic)
- **Actor:** System (triggered after successful payment)
- **Trigger:** PaymentService.checkout() success
- **Main Flow:**
  1. After payment validation and processing success
  2. ReceiptService.generateReceipt() called
  3. Receipt generation:
     ```
     ════════════════════════════════════
     AGRI-POS - POS KASIR
     Toko: AGRI FARMING
     Lokasi: [Alamat]
     ════════════════════════════════════
     Tanggal: 2024-01-15 14:35:42
     Kasir: Admin User
     ────────────────────────────────────
     ITEM             QTY   PRICE    SUBTOTAL
     ────────────────────────────────────
     Benih Padi       5   15000.00  75000.00
     Pupuk NPK        2   25000.00  50000.00
     ────────────────────────────────────
     SUBTOTAL:                     125000.00
     DISKON (jika ada):                  0.00
     ────────────────────────────────────
     TOTAL:                        125000.00
     Metode: Tunai / E-Wallet
     Kembalian: 25000.00 (jika tunai)
     ════════════════════════════════════
     Terima kasih! Selamat berbelanja
     ════════════════════════════════════
     ```
  4. Receipt displayed in Dialog & Console
  5. User can close receipt dialog
  6. System ready for next transaction

#### FR-4.2 Print Receipt
- **Actor:** Kasir
- **Main Flow:**
  1. After checkout and receipt displayed
  2. Kasir can print receipt (if printer available)
  3. System: System.out.println(receipt) → console output
  4. Alternative: Export to file or send to printer

#### FR-4.3 View Sales Report
- **Actor:** Admin
- **Access:** Tab "Laporan" (3rd tab in PosView)
- **Main Flow:**
  1. Admin/Owner klik tab "Laporan"
  2. Sistem load report view with:
     - Date range selector (from-to dates)
     - "Generate Report" button
  3. Admin select date range
  4. Admin klik "Generate Report"
  5. Sistem query database:
     - SELECT all transactions in date range
     - Calculate total sales
     - GROUP BY payment_method
     - COUNT transactions
  6. Report display format:
     ```
     LAPORAN PENJUALAN
     Periode: 2024-01-01 s/d 2024-01-31
     
     Total Transaksi: 25
     Total Penjualan: Rp 1.500.000.00
     
     Rincian per Metode Pembayaran:
     - Tunai:       15 transaksi = Rp 900.000.00
     - E-Wallet:    10 transaksi = Rp 600.000.00
     
     Produk Terlaris:
     1. Benih Padi      - 150 unit
     2. Pupuk NPK       - 120 unit
     3. Insektisida     - 80 unit
     ```
  7. Option to export to PDF/Excel

#### FR-4.4 Database Recording
- **Data Saved:** 
  - Table: `transactions`
    - transaction_id (PK)
    - user_id (FK) - which user/kasir
    - total_amount
    - payment_method (CASH / E_WALLET)
    - transaction_date (timestamp)
    - created_at (system timestamp)
  - Table: `transaction_items`
    - transaction_item_id (PK)
    - transaction_id (FK)
    - product_id (FK)
    - quantity
    - unit_price (harga saat pembelian)
    - subtotal (qty × unit_price)
- **Atomicity Guarantee:**
  - Transaction commit only if ALL operations success:
    1. Insert transaction header → check
    2. Insert transaction items → check
    3. Update product stock → check
    4. Generate receipt → check
  - If any fails: ROLLBACK all changes
  - Database constraint: foreign keys ensure referential integrity

---

### FR-5: Login dan Hak Akses

#### FR-5.1 Authentication Flow
- **Actor:** All users (Admin, Kasir, Owner)
- **Main Flow:**
  1. Application starts → LoginView appears
  2. User input: username & password
  3. User klik "LOGIN"
  4. Validation step (AuthService):
     - Check: username exists in database
     - Check: password match (hashed comparison)
  5. If NOT match: error "Username atau password salah"
  6. If match: proceed to authorization

#### FR-5.2 Role-Based Authorization
- **Roles in Database:** users table has `role` column
  - ADMIN (all privileges)
  - KASIR (transaction only)
  - OWNER (read-only reports)
- **Main Flow:**
  1. After successful authentication
  2. AuthService retrieve user role
  3. Load appropriate view/tabs based on role:
     - ADMIN: All 3 tabs (KASIR, ADMIN, LAPORAN)
     - KASIR: Tab 1 only (KASIR - transactions)
     - OWNER: Tab 3 only (LAPORAN - reports)
  4. Session created with user info
  5. PosView initialize with role-filtered components
- **Navigation Control:**
  - ADMIN can access all tabs → all buttons enabled
  - KASIR only sees tab 1 → cannot edit products/view reports
  - OWNER only sees tab 3 → read-only, no create/edit/delete

#### FR-5.3 Permission Validation
- **Kasir Permissions:**
  - ✓ Add items to cart
  - ✓ Update cart quantity
  - ✓ Remove items
  - ✓ Checkout & payment
  - ✗ Edit products
  - ✗ Delete products
  - ✗ View sales reports
- **Admin Permissions:**
  - ✓ All Kasir permissions
  - ✓ Add products
  - ✓ Edit products (price, stock)
  - ✓ Delete products
  - ✓ View sales reports
- **Owner Permissions:**
  - ✓ View sales reports
  - ✓ Generate custom reports (by date)
  - ✗ Edit products
  - ✗ Access checkout

#### FR-5.4 Session Management
- **Session Start:**
  - After successful login
  - Store: user_id, username, role in SessionManager
  - Pass to PosController → passed to Services
  - All transactions recorded with user_id
- **Session Security:**
  - Session timeout: 30 minutes (optional future feature)
  - Logout: Clear session & return to LoginView
  - Window close: Clear session (graceful shutdown)
- **Logout Flow:**
  1. User klik "Logout" button
  2. Confirm dialog: "Yakin ingin logout?"
  3. If YES:
     - Clear session
     - Clear cart
     - Close all tabs
     - Return to LoginView
  4. If NO:
     - Cancel, stay in PosView

#### FR-5.5 Database Credentials
- **Table: users**
  - user_id (PK)
  - username (UNIQUE)
  - password (hashed - BCrypt recommended)
  - role (ADMIN / KASIR / OWNER)
  - created_at (timestamp)
- **Initial Users (Seed Data):**
  ```sql
  INSERT INTO users VALUES
  (1, 'admin', 'hashed_password_admin', 'ADMIN', NOW()),
  (2, 'kasir1', 'hashed_password_kasir', 'KASIR', NOW()),
  (3, 'owner', 'hashed_password_owner', 'OWNER', NOW());
  ```
- **Password Handling:**
  - Never store plain text
  - Hash with BCrypt or similar
  - Compare hashed values during login

---

### FR-5: Login dan Hak Akses

#### FR-5.1 User Authentication
- **Actor:** User (Kasir/Admin)
- **Precondition:** Application startup
- **Main Flow:**
  1. Sistem tampilkan LoginView
  2. User input username & password
  3. User pilih role (KASIR/ADMIN)
  4. User klik "Login"
  5. Sistem query database
  6. Jika cocok → login sukses
  7. Sistem tampilkan PosView dengan menu sesuai role
- **Alternate Flow:**
  - Jika username/password salah → error

#### FR-5.2 Role-Based Access Control
- **KASIR:**
  - Akses: POS (transaksi)
  - Tidak bisa: manajemen produk, laporan
- **ADMIN:**
  - Akses: Manajemen Produk + Laporan
  - Tidak bisa: transaksi checkout
- **OWNER:**
  - Akses: Semua (full access)

---

## 3. Non-Functional Requirements (NFR)

| NFR ID | Requirement | Target | Implementation |
|--------|-------------|--------|-----------------|
| **NFR-1** | Performance | Response time < 1 detik | PreparedStatement, indexed queries |
| **NFR-2** | Usability | UI intuitif | JavaFX dengan layout rapi |
| **NFR-3** | Maintainability | SOLID principles | DIP, Service layer |
| **NFR-4** | Security | Input validation | ValidationException, prepared statement |
| **NFR-5** | Reliability | No data loss | Transaction handling, constraints |
| **NFR-6** | Scalability | Support 50+ transactions/day | Stateless service layer |
| **NFR-7** | Compatibility | Java 21, PostgreSQL | Tested & working |
| **NFR-8** | Documentation | Clear & complete | JavaDoc, README, guides |

---

## 4. Data Requirements

### 4.1 Data Entities

#### Product
- id (PK)
- code (UNIQUE)
- name
- category
- price (≥ 0)
- stock (≥ 0)
- created_at
- updated_at

#### User
- id (PK)
- username (UNIQUE)
- password
- role (ENUM: KASIR, ADMIN, OWNER)
- active (BOOLEAN)

#### Transaction
- id (PK)
- user_id (FK → User)
- transaction_date
- total_amount
- payment_method
- status (ENUM: COMPLETED, CANCELLED)

#### TransactionItem
- id (PK)
- transaction_id (FK → Transaction)
- product_id (FK → Product)
- quantity
- unit_price

---

## 5. Acceptance Criteria

### FR-1 (Manajemen Produk)
- [x] CRUD semua berhasil (Create, Read, Update, Delete)
- [x] Data validasi (tidak boleh kosong, unique code)
- [x] Data tersimpan di PostgreSQL
- [x] Tampil di UI TableView
- [x] Tidak ada SQL injection (PreparedStatement)

### FR-2 (Transaksi Penjualan)
- [x] Cart add/update/remove berhasil
- [x] Total calculate otomatis
- [x] Validasi stok (tidak boleh > stok)
- [x] Multiple items di keranjang
- [x] Clear cart functionality

### FR-3 (Metode Pembayaran)
- [x] Tunai payment berhasil
- [x] E-Wallet payment berhasil
- [x] Hitung kembalian (cash)
- [x] Validasi saldo (e-wallet)
- [x] Strategy pattern applied

### FR-4 (Struk & Laporan)
- [x] Receipt generated & displayed
- [x] Laporan per tanggal
- [x] Format rapi

### FR-5 (Login & Role)
- [x] Login berhasil
- [x] Role-based menu tampil berbeda
- [x] Hak akses terbatasi per role

---

## 6. Constraints & Assumptions

### Constraints
- Single user session (tidak concurrent)
- Struk tidak di-print fisik (hanya preview di UI)
- Database setup manual
- Tidak ada payment gateway real (mock only)

### Assumptions
- User sudah familiar dengan POS
- Database server tersedia
- Network stable
- Java 21 installed

---

## 7. Out of Scope

- Multi-user concurrent session
- Physical printer integration
- Real payment gateway
- Mobile app
- Cloud sync
- Advanced analytics
- Inventory forecasting

---

**End of SRS Document**
