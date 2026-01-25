# 06 User Guide
## Agri-POS - Panduan Pengguna

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0

---

## 1. Pendahuluan

Agri-POS adalah sistem point-of-sale (POS) terintegrasi untuk toko pertanian yang memudahkan:
- Proses penjualan cepat
- Manajemen inventori produk
- Laporan penjualan
- Kontrol akses berbasis peran

---

## 2. Login & Akses

### 2.1 Membuka Aplikasi

1. Buka terminal/command prompt
2. Navigasi ke folder project: `cd week15-proyek-kelompok`
3. Jalankan: `mvn javafx:run`
4. Layar login akan muncul

### 2.2 Kredensial Default

| Username | Password | Role | Deskripsi |
|----------|----------|------|-----------|
| kasir1 | kasir123 | KASIR | Operator penjualan |
| admin | admin123 | ADMIN | Manajemen produk |
| owner | owner123 | OWNER | Laporan & analisis |

### 2.3 Login

**Langkah:**
1. Masukkan username
2. Masukkan password
3. Pilih Role (auto-filled based on username)
4. Klik tombol "Login"

**Pesan Error:**
- "Username atau password salah" → Cek kredensial
- "Pengguna tidak ditemukan" → Username tidak terdaftar

---

## 3. Menu KASIR - Penjualan

### 3.1 Akses Menu Penjualan

Setelah login sebagai KASIR, layar utama POS ditampilkan dengan 2 bagian:
- **Bagian kiri:** Daftar produk (Table) + Input Quantity + Tombol "Tambah ke Keranjang"
- **Bagian kanan:** Keranjang belanja dengan tombol operasi (Update Qty, Hapus, Kosongkan)

### 3.2 Menambah Produk ke Keranjang

**Langkah:**
1. Lihat daftar produk di bagian kiri (menampilkan: Kode, Nama, Kategori, Harga, Stok)
2. **Klik produk yang ingin dibeli** di table
3. Input jumlah (quantity) di field "Qty:" 
4. Klik tombol "Tambah ke Keranjang"
5. Produk akan muncul di keranjang di sebelah kanan

**Validasi Otomatis:**
- ✅ Produk harus dipilih (jika tidak ada error: "Pilih produk dari daftar terlebih dahulu!")
- ✅ Qty harus diisi (jika kosong: "Masukkan jumlah (Qty)!")
- ✅ Qty harus angka (jika tidak: "Jumlah harus berupa angka!")
- ✅ Qty harus > 0 (jika ≤ 0: "Jumlah harus lebih dari 0!")
- ✅ Qty ≤ stok tersedia (jika > stok: "Jumlah melebihi stok tersedia! Stok: XXX")

**Contoh:**
```
1. Pilih "Benih Padi" (Harga: 15.000, Stok: 100)
2. Input Qty: 5
3. Klik "Tambah ke Keranjang"
✓ Result: Item masuk keranjang, Total diperbarui: Rp 75.000
```

**Jika Produk Sudah Ada di Keranjang:**
- Produk akan di-update quantity-nya (bukan duplikat)
- Contoh: Ada 3 Benih Padi, tambah 2 lagi → menjadi 5

### 3.3 Mengubah Jumlah Produk (Update Quantity)

**Langkah:**
1. **Klik item yang ingin diubah** di keranjang (table sebelah kanan)
2. Input jumlah baru di field "Qty:"
3. Klik tombol "Update Qty"
4. Jumlah item akan diperbarui
5. Total akan dihitung ulang otomatis

**Validasi:**
- ✅ Item harus dipilih di keranjang (jika tidak: "Pilih item di keranjang untuk update!")
- ✅ Qty harus diisi (jika kosong: "Masukkan jumlah baru (Qty)!")
- ✅ Qty harus angka
- ✅ Qty harus > 0
- ✅ Qty ≤ stok tersedia

**Contoh:**
```
1. Keranjang ada: Benih Padi qty 5 (Total: Rp 75.000)
2. Klik item tersebut di table keranjang
3. Input Qty: 10
4. Klik "Update Qty"
✓ Result: Qty menjadi 10, Total: Rp 150.000
```

### 3.4 Menghapus Produk dari Keranjang

**Langkah:**
1. **Klik item yang ingin dihapus** di keranjang
2. Klik tombol "Hapus Item"
3. Item akan dihapus dari keranjang
4. Total akan dihitung ulang

**Validasi:**
- ✅ Item harus dipilih (jika tidak: "Pilih item di keranjang untuk dihapus")

**Contoh:**
```
1. Keranjang ada: Benih Padi, Pupuk Urea
2. Klik "Pupuk Urea" di table keranjang
3. Klik "Hapus Item"
✓ Result: Pupuk Urea dihapus, Total berkurang
```

### 3.5 Kosongkan Keranjang

**Langkah:**
1. Klik tombol "Kosongkan"
2. Semua item akan dihapus
3. Total akan reset ke Rp 0

**Peringatan:** Operasi ini tidak bisa di-undo, pastikan Anda yakin

### 3.6 Melihat Total Belanja

**Informasi Yang Ditampilkan:**
- **Total:** Rp XXX (otomatis dihitung dari semua item di keranjang)
- **Perhitungan:** Total = ∑(Quantity × Unit Price) untuk semua item

**Update Otomatis Ketika:**
- ✅ Produk ditambahkan ke keranjang
- ✅ Quantity diperbarui
- ✅ Item dihapus dari keranjang
- ✅ Keranjang dikosongkan

### 3.7 Proses Checkout

**Langkah:**
1. Verifikasi semua item di keranjang sudah benar
2. Lihat total harga yang harus dibayar
3. Pilih metode pembayaran dari dropdown (Tunai / E-Wallet)
4. Klik tombol **"CHECKOUT"**
5. Dialog konfirmasi akan muncul
6. Klik "OK" untuk melanjutkan atau "Cancel" untuk batal

**Informasi di Dialog Konfirmasi:**
```
Lanjutkan Checkout?
Total: Rp 85.000
Metode: Tunai
```

**Validasi:**
- ✅ Keranjang tidak boleh kosong (jika kosong: "Keranjang kosong")

### 3.8 Metode Pembayaran

**Pilihan Pembayaran (di dropdown):**
- **Tunai:** Pembayaran dengan uang tunai/cash
- **E-Wallet:** Pembayaran digital (GCash, Dana, OVO, dll)

**Langkah Pembayaran:**
1. Pilih metode dari dropdown: "Tunai" atau "E-Wallet"
2. Klik "CHECKOUT"
3. Transaksi diproses
4. Struk ditampilkan di dialog

**Proses di Backend:**
- ✅ PaymentService memvalidasi metode pembayaran
- ✅ Untuk Tunai: Cek amount > 0
- ✅ Untuk E-Wallet: Cek saldo cukup
- ✅ TransactionService menyimpan transaksi ke DB
- ✅ Stock produk otomatis berkurang

### 3.9 Struk Transaksi

**Kapan Struk Ditampilkan:**
- Setelah checkout berhasil (dialog pop-up dengan isi struk)

**Informasi di Struk:**
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
Pupuk Urea           2 x  5000.00 =   10000.00
────────────────────────────────────
TOTAL:                          Rp 85000.00

════════════════════════════════════
     Thank you for your purchase!
════════════════════════════════════
```

**Aksi Setelah Struk:**
- Klik "Close" di dialog untuk kembali ke transaksi baru
- Keranjang otomatis dikosongkan setelah checkout sukses

---

## 4. Menu ADMIN - Manajemen Produk

### 4.1 Akses Menu Produk

Setelah login sebagai ADMIN, klik menu "Produk":
- Daftar produk ditampilkan dalam tabel
- Tombol: Tambah, Edit, Hapus

### 4.2 Melihat Daftar Produk

Tabel menampilkan:
- Kode Produk
- Nama Produk
- Kategori
- Harga
- Stok

**Filter & Search:**
- Gunakan search box untuk menemukan produk
- Filter berdasarkan kategori

### 4.3 Menambah Produk Baru

**Langkah:**
1. Klik tombol "Tambah Produk"
2. Isi form:
   - **Kode:** P009 (unik, tidak boleh duplikat)
   - **Nama:** Benih Kacang
   - **Kategori:** Benih
   - **Harga:** 8000
   - **Stok:** 50
3. Klik "Simpan"

**Validasi:**
- Kode harus unik
- Harga ≥ 0
- Stok ≥ 0
- Semua field wajib diisi

### 4.4 Edit Produk

**Langkah:**
1. Pilih produk di tabel
2. Klik tombol "Edit"
3. Ubah data yang perlu diubah
4. Klik "Simpan"

**Contoh Skenario:**
- Ubah harga: Harga Pupuk naik Rp 100 → Edit & simpan
- Restock: Stok berkurang → Update stok

### 4.5 Hapus Produk

**Langkah:**
1. Pilih produk di tabel
2. Klik tombol "Hapus"
3. Konfirmasi penghapusan
4. Produk dihapus dari sistem

**Peringatan:**
- Produk yang sudah terjual tidak dapat dihapus
- Backup data sebelum menghapus

---

## 5. Menu OWNER - Laporan Penjualan

### 5.1 Akses Menu Laporan

Setelah login sebagai OWNER, klik menu "Laporan":
- Form filter tanggal
- Tombol "Lihat Laporan"

### 5.2 Filter Laporan

**Langkah:**
1. Pilih tanggal awal (From Date)
2. Pilih tanggal akhir (To Date)
3. Klik "Lihat Laporan"

**Contoh:**
- Laporan harian: 21/01/2026 - 21/01/2026
- Laporan mingguan: 15/01/2026 - 21/01/2026
- Laporan bulanan: 01/01/2026 - 31/01/2026

### 5.3 Hasil Laporan

**Tabel Menampilkan:**
| Tanggal | ID Transaksi | Total | Metode | User |
|---------|--------------|-------|--------|------|
| 21/01 | TRX-001 | 85.000 | Tunai | kasir1 |
| 21/01 | TRX-002 | 120.000 | E-Wallet | kasir1 |

**Ringkasan:**
- Total transaksi
- Total pendapatan
- Rata-rata transaksi
- Transaksi paling tinggi/rendah

### 5.4 Export Laporan

**Langkah:**
1. Lihat laporan
2. Klik "Export ke Excel" (jika tersedia)
3. File akan diunduh

---

## 6. Troubleshooting

### Masalah: Tidak bisa login

**Solusi:**
1. Cek username & password
2. Pastikan database running: `sudo service postgresql status`
3. Verifikasi kredensial di DatabaseConnection.java

### Masalah: Produk tidak muncul

**Solusi:**
1. Refresh aplikasi: F5 atau restart aplikasi
2. Verifikasi DB connection
3. Cek apakah produk sudah dihapus

### Masalah: Harga tidak sesuai

**Solusi:**
1. Cek di menu Produk (ADMIN)
2. Verifikasi harga di database
3. Update harga jika diperlukan

### Masalah: Transaksi gagal

**Solusi:**
1. Cek koneksi database
2. Verifikasi stok produk
3. Pastikan metode pembayaran valid

---

## 7. Tips & Trik

### 7.1 Tips untuk KASIR

1. **Pencarian Cepat:** Gunakan kode produk untuk pencarian
2. **Shortcut:** Tekan Enter setelah mengetik quantity
3. **Batch Proses:** Tangani beberapa transaksi dengan cepat
4. **Error Check:** Verifikasi total sebelum bayar

### 7.2 Tips untuk ADMIN

1. **Backup:** Backup data produk secara berkala
2. **Update Stok:** Update stok di awal/akhir hari
3. **Validasi:** Verifikasi kode produk unik sebelum tambah
4. **Monitor:** Pantau produk dengan stok rendah

### 7.3 Tips untuk OWNER

1. **Analisis Trend:** Bandingkan laporan antar periode
2. **Target Penjualan:** Tentukan target berdasarkan laporan
3. **Optimasi:** Fokus pada produk dengan penjualan tinggi
4. **Evaluasi:** Review performa kasir dan transaksi

---

## 8. Konvensi & Standard

### 8.1 Format Data

| Field | Format | Contoh |
|-------|--------|--------|
| Kode Produk | P### | P001, P025 |
| Harga | Rp | 15.000 |
| Tanggal | dd/MM/yyyy | 21/01/2026 |
| Jumlah | Integer | 5, 100 |

### 8.2 Kategori Produk

- **Benih:** Benih pertanian
- **Pupuk:** Pupuk tanaman
- **Alat:** Alat pertanian
- **Obat:** Obat hama & penyakit

---

**End of User Guide**